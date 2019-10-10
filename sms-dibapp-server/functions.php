<?php

include 'db_connect.php';
include 'config.php';

define("USER_EXISTS_QUERY", "SELECT count(*) as count FROM user WHERE email = ?");
define("VERIFY_PASSWORD_QUERY", "SELECT passwordHash, salt FROM user WHERE email = ?");
define("GET_ROLE_LIST_QUERY", "SELECT name FROM role ORDER BY name");
define("GET_DEGREECOURSE_LIST_QUERY", "SELECT name FROM degreecourse ORDER BY name");
define("REGISTER_NEW_USER_QUERY", 
	"INSERT INTO user(serialNumber, name, surname, email, passwordHash, salt) VALUES (:serialNumber, :name, :surname, :email, :passwordHash, :salt)");
define("REGISTER_NEW_USER_DEGREECOURSE_QUERY", 
	"INSERT INTO user_degreecourse(userID, degreecourseID) SELECT user.ID, degreecourse.ID FROM user, degreecourse WHERE EMAIL = :email AND degreecourse.name = :degreecourse");
define("REGISTER_NEW_USER_ROLE_QUERY", 
	"UPDATE user SET user.roleID = (SELECT ID from role WHERE name = :roleName) WHERE user.email = :email"); 
define("SET_STUDENT_CAREER_QUERY",
	"INSERT INTO student_career(studentID, classID) 
	SELECT user.ID,degreecourse_class.classID
    FROM user, user_degreecourse, degreecourse, degreecourse_class
    WHERE email = :email
      AND user.ID = user_degreecourse.userID
      AND degreecourse_class.degreecourseID = degreecourse.ID
      AND degreecourse.name = :degreecourse");
// Gets the list of active classes
define("GET_STUDENT_CLASS_LIST_QUERY", 
	"SELECT crl.ID as lessonID, c.ID as classID, c.name as className, c.code as classCode, c.description as classDescription, c.year as classYear, c.semester as classSemester, crl.timeStart as classLessonTimeStart, crl.timeEnd as classLessonTimeEnd, crl.summary as classLessonSummary , crl.description as classLessonDescription 
	FROM student_career as s, class as c, class_room_calendar as crc, class_room_lesson as crl
	WHERE s.classID = c.ID 
	AND c.ID = crc.classID
	AND crc.ID = crl.calendarID
	AND s.studentID = ?
	GROUP BY className
	ORDER BY c.year, className");
define("GET_PROFESSOR_CLASS_LIST_QUERY", 
	"SELECT c.ID as classID, c.name as className, c.code as classCode, c.description as classDescription, c.year as classYear, c.semester as classSemester
	FROM professor_teaching as pt, class as c
	WHERE pt.classID = c.ID 
	AND pt.professorID = ?
    ORDER BY className");
/*define("GET_PROFESSOR_CURRENT_CLASS_LIST_QUERY", 
	"SELECT crl.ID as lessonID, c.ID as classID, c.name as className, c.year as classYear, c.semester as classSemester, crl.timeStart as classLessonTimeStart, crl.timeEnd as classLessonTimeEnd
	FROM professor_teaching as pt, class as c, class_room_calendar as crc, class_room_lesson as crl
	WHERE pt.classID = c.ID 
	AND c.ID = crc.classID
	AND crc.ID = crl.calendarID
	AND pt.professorID = ?
	GROUP BY className
	ORDER BY c.year, className");
	*/
//define("GET_STUDENT_EXAM_LIST_QUERY", "SELECT c.ID as classID, c.name as className, c.year as year, c.semester as semester, s.passed as passed, s.passedDate as passedDate, s.vote as vote, s.praise as praise FROM student_career as s, class as c WHERE s.classID = c.ID AND studentID = ? ORDER BY year, className");
//define("GET_PROFESSOR_CLASS_LIST_QUERY", "SELECT c.id as classID, c.name as className FROM class as c, professor_teaching as p, user as u WHERE c.ID = p.classID AND p.professorID = u.id AND u.id = ?");
define("GET_USER_DETAILS_QUERY", "SELECT u.ID as userID, u.name as name, u.surname as surname, u.email as email, u.registrationDate as registrationDate, r.name as roleName FROM user as u, role as r WHERE u.roleID = r.ID AND email = ?");
define("GET_CLASS_LESSON_DETAIL_QUERY", 
	"SELECT c.ID as classID, c.name as className, c.description as classDescription, c.code as classCode, c.year as classYear, c.semester as classSemester, crl.timeStart as classLessonTimeStart, crl.timeEnd as classLessonTimeEnd, crl.summary as classLessonSummary , crl.description as classLessonDescription 
	FROM class as c 
	JOIN class_room_calendar as crc ON c.ID = crc.classID
	JOIN class_room_lesson as crl ON crc.ID = crl.calendarID
	WHERE c.ID = ?");
define("ASK_A_QUESTION_QUERY", "INSERT INTO class_lesson_question (lessonID, studentID, question, time) VALUES (:lessonID, :studentID, :question, :time)");
define("SET_ATTENDANCE_QUERY", "INSERT INTO class_lesson_attendance_rating (studentID, lessonID, time) VALUES (:userID, :lessonID, :time)");
define("UNSET_ATTENDANCE_QUERY", "DELETE FROM class_lesson_attendance_rating WHERE studentID = :userID AND lessonID = :lessonID");
define("IS_USER_ATTENDING_LESSON_QUERY", "SELECT COUNT(*) as attendance FROM class_lesson_attendance_rating WHERE studentID = :userID AND lessonID = :lessonID");
define("SET_CLASS_LESSON_REVIEW_QUERY", "UPDATE class_lesson_attendance_rating SET summary = :summary, review = :review, rating = :rating WHERE studentID = :userID AND lessonID = :lessonID");
define("CHECK_IF_LESSON_ALREADY_EVALUATED", "SELECT count(*) as count FROM class_lesson_attendance_rating WHERE studentID = :studentID AND lessonID = :lessonID AND rating IS NOT NULL");
	  
class Response {
	
	var $message;
	var $code;
	
	function __construct(string $message, int $code) {
		$this->message = $message;
		$this->code = $code;
	}
}

function login(array $input) {
	if(isset($input[KEY_USER_EMAIL]) && isset($input['password'])){
		try {
			credentialAreNotNull($input['email'], $input['password']);
			userExists($input[KEY_USER_EMAIL]);
			verifyPassword($input[KEY_USER_EMAIL], $input['password']);	
			$response = new Response(QUERY_OK_TEXT, QUERY_OK_CODE);
		} catch( Exception $e) {
			$response = new Response($e->getMessage(), intval($e->getCode())); 
		}
	}
	return($response);
}

function credentialAreNotNull(string $email, string $password) {
	if($email == '' || $password == '') {
		throw new Exception(MISSING_MANDATORY_PARAMETERS_TEXT, MISSING_MANDATORY_PARAMETERS_CODE);
	}	
}

function userExists(string $email){
	global $connection;
	$stmt = $connection->prepare(USER_EXISTS_QUERY);
	$stmt->execute([$email]);
	$result = $stmt->fetch(PDO::FETCH_OBJ);
	if($result->count != 1) throw new Exception(INVALID_EMAIL_TEXT, INVALID_EMAIL_CODE);
}

function verifyPassword(string $email, string $password) {
	global $connection;
	$stmt = $connection->prepare(VERIFY_PASSWORD_QUERY);
	$stmt->execute([$email]);
	$result = $stmt->fetch(PDO::FETCH_OBJ);
	if(!password_verify(concatPasswordWithSalt($password, $result->salt), $result->passwordHash)){
		throw new Exception(INVALID_PASSWORD_TEXT, INVALID_PASSWORD_CODE);
	}
}

function registration(array $input) {
	if(isset($input[KEY_USER_NAME]) && isset($input[KEY_USER_SURNAME]) && isset($input[KEY_USER_SERIAL_NUMBER]) && isset($input[degreecourse]) && isset($input[KEY_USER_ROLE_NAME]) && isset($input[KEY_USER_EMAIL]) && isset($input[KEY_USER_PASSWORD]) && isset($input[KEY_USER_REGISTRATION_DATE])){
	//Register the user if doesn't exists
		try {
			userExists($input[KEY_USER_EMAIL]);
			$response = new Response(USER_ALREADY_EXISTS_TEXT, USER_ALREADY_EXISTS_CODE);
		} catch(Exception $e) {
			$response = registerNewUser($input);
		}
	} else { 
		$response = new Response(MISSING_MANDATORY_PARAMETERS_TEXT, MISSING_MANDATORY_PARAMETERS_CODE);
	}
	return $response;
}

function getRoleList() {
	global $connection;
	$stmt = $connection->prepare(GET_ROLE_LIST_QUERY);
	$stmt->execute();
	$response = $stmt->fetchAll(PDO::FETCH_OBJ);
	return $response;
}

function getDegreecourseList() {
	global $connection;
	$stmt = $connection->prepare(GET_DEGREECOURSE_LIST_QUERY);
	$stmt->execute();
	$response = $stmt->fetchAll(PDO::FETCH_OBJ);
	return $response;
}

function registerNewUser(array $input) {
	global $connection;
	//Get a unique Salt
	$salt = getSalt();
	//Generate a unique password Hash
	$passwordHash = password_hash(concatPasswordWithSalt($input[KEY_USER_PASSWORD],$salt),PASSWORD_DEFAULT);
	$response = null;
	
	//Query to register new user
	$stmt1 = $connection->prepare(REGISTER_NEW_USER_QUERY);
	$stmt1->bindValue(':name', $input[KEY_USER_NAME]);
	$stmt1->bindValue(':surname', $input[KEY_USER_SURNAME]);
	$stmt1->bindValue(':serialNumber', $input[KEY_USER_SERIAL_NUMBER]);
	$stmt1->bindValue(':email', $input[KEY_USER_EMAIL]);
	$stmt1->bindValue(':passwordHash', $passwordHash);
	$stmt1->bindValue(':salt', $salt);
	$stmt1->bindValue(':registrationDate', $input[KEY_USER_REGISTRATION_DATE]);
	
	$stmt2 = $connection->prepare(REGISTER_NEW_USER_DEGREECOURSE_QUERY);
	$stmt2->bindValue(':email', $input[KEY_USER_EMAIL]);
	$stmt2->bindValue(':degreecourse', $input[KEY_DEGREECOURSE]);

	$stmt3 = $connection->prepare(REGISTER_NEW_USER_ROLE_QUERY);
	$stmt3->bindValue(':email', $input[KEY_USER_EMAIL]);
	$stmt3->bindValue(':roleName', $input[KEY_USER_ROLE_NAME]);
	
	$stmt4 = $connection->prepare(SET_STUDENT_CAREER_QUERY);
	$stmt4->bindValue(':email', $input[KEY_USER_EMAIL]);
	$stmt4->bindValue(':degreecourse', $input[KEY_DEGREECOURSE]);
	
	switch ($input[KEY_USER_ROLE_NAME]) {
		case USER_ROLE_STUDENT: 
			$connection->beginTransaction();
			if($stmt1->execute() && $stmt2->execute() && $stmt3->execute() && $stmt4->execute()){
				$connection->commit();
				$response = new Response(QUERY_OK_TEXT,QUERY_OK_CODE);
			} else {
				$connection->rollBack();
				$response = new Response(QUERY_NOT_OK_TEXT,QUERY_NOT_OK_CODE);
			} 
			break;
		case USER_ROLE_BACKOFFICE_OPERATOR:
		case USER_ROLE_TEACHER:	
			$connection->beginTransaction();
			if( $stmt1->execute() && $stmt2->execute() && $stmt3->execute()){
				$connection->commit();
				$response = new Response(QUERY_OK_TEXT,QUERY_OK_CODE);
			} else {
				$connection->rollBack();
				$response = new Response(QUERY_NOT_OK_TEXT,QUERY_NOT_OK_CODE);
			}
				
		default: break;
	}
	return $response;
}

function getUserDetails(string $email) {
	global $connection;
	$stmt = $connection->prepare(GET_USER_DETAILS_QUERY);
	$stmt->execute([$email]);
	$response = $stmt->fetch(PDO::FETCH_OBJ);
	return $response;
}

function askAQuestion(int $lessonID, int $userID, string $question, string $time) {
	global $connection;
	$stmt = $connection->prepare(ASK_A_QUESTION_QUERY);
	$stmt->bindValue(':lessonID', $lessonID);
	$stmt->bindValue(':studentID', $userID);
	$stmt->bindValue(':question', $question);
	$stmt->bindValue(':time', $time);
	$connection->beginTransaction();
	if($stmt->execute()){
		$connection->commit();
		$response = new Response(QUERY_OK_TEXT, QUERY_OK_CODE);
	} else {
		$connection->rollBack();
		$response = new Response(QUESTION_NOT_SENT_TEXT, QUESTION_NOT_SENT_CODE);
	}
	return $response;
}

function setAttendance(int $lessonID, int $userID, string $attendance, string $time = null) {
	$userAttendance = ($attendance === 'true');
	global $connection;	
	if($userAttendance == true) {
		$stmt = $connection->prepare(SET_ATTENDANCE_QUERY);
		$stmt->bindValue(':time', $time);
	} else {
		$stmt = $connection->prepare(UNSET_ATTENDANCE_QUERY);
	}
	$stmt->bindValue(':lessonID', $lessonID);
	$stmt->bindValue(':userID', $userID);
	$connection->beginTransaction();
	if($stmt->execute()){
		$connection->commit();
		$result = new Response(ATTENDANCE_SET_TEXT, ATTENDANCE_SET_CODE);
	} else {
		$connection->rollBack();
		$result = new Response(ATTENDANCE_NOT_SET_TEXT, ATTENDANCE_NOT_SET_CODE);
	}
	return $result;
}

function isUserAttendingLesson(int $lessonID, int $userID) {
	global $connection;	
	$stmt = $connection->prepare(IS_USER_ATTENDING_LESSON_QUERY);
	$stmt->bindValue(':lessonID', $lessonID);
	$stmt->bindValue(':userID', $userID);
	$stmt->execute();
	$result = $stmt->fetch(PDO::FETCH_OBJ);
	return $result;
}

function setReview($input) {
	global $connection;	
	$stmt = $connection->prepare(SET_CLASS_LESSON_REVIEW_QUERY);
	$stmt->bindValue(':lessonID', $input[KEY_CLASS_LESSON_ID]);
	$stmt->bindValue(':userID', $input[KEY_USER_ID]);
	$stmt->bindValue(':summary', $input[KEY_CLASS_LESSON_REVIEW_SUMMARY]);
	$stmt->bindValue(':review', $input[KEY_CLASS_LESSON_REVIEW_TEXT]);
	$stmt->bindValue(':rating', $input[KEY_CLASS_LESSON_REVIEW_RATING]);
	$connection->beginTransaction();
	if($stmt->execute()){
		$connection->commit();
		$result = new Response(QUERY_OK_TEXT, QUERY_OK_CODE);
	} else {
		$connection->rollBack();
		$result = new Response(QUERY_NOT_OK_TEXT, QUERY_NOT_OK_CODE);
	}
	return $result;
}

function getClassList(int $userID, string $userRole) {
	global $connection;
	if($userRole == USER_ROLE_PROFESSOR) {
		$stmt = $connection->prepare(GET_PROFESSOR_CLASS_LIST_QUERY);
	} else {
		$stmt = $connection->prepare(GET_STUDENT_CLASS_LIST_QUERY);	
	}
	$stmt->execute([$userID]);
	$response = $stmt->fetchAll(PDO::FETCH_OBJ);
	return $response;
}

function getClassLessonDetail(int $classID) {
	global $connection;
	$stmt = $connection->prepare(GET_CLASS_LESSON_DETAIL_QUERY);
	$stmt->execute([$classID]);
	$response = $stmt->fetch(PDO::FETCH_OBJ);
	return $response;
}

function checkEvaluatedLesson(array $input) {
	global $connection;
	$stmt = $connection->prepare(CHECK_IF_LESSON_ALREADY_EVALUATED);
	$stmt->bindValue(':studentID', $input[KEY_USER_ID]);
	$stmt->bindValue(':lessonID', $input[KEY_CLASS_LESSON_ID]);
	$stmt->execute();
	$response = $stmt->fetchAll(PDO::FETCH_OBJ);
	return $response;
}

/**
* Creates a unique Salt for hashing the password
* 
* @return
*/

$random_salt_length = 32;

function getSalt(){
	global $random_salt_length;
	return bin2hex(openssl_random_pseudo_bytes($random_salt_length));
}

/**
* Creates password hash using the Salt and the password
* 
* @param $password
* @param $salt
* 
* @return
*/

function concatPasswordWithSalt($password, $salt){
	global $random_salt_length;
	if($random_salt_length % 2 == 0){
		$mid = $random_salt_length / 2;
	} else {
		$mid = ($random_salt_length - 1) / 2;
	}
	return substr($salt,0,$mid - 1).$password.substr($salt,$mid,$random_salt_length - 1);
}

?>