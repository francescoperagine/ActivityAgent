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
define("GET_PROFESSOR_CLASS_LIST_QUERY", 
	"SELECT c.ID as classID, c.name as className, c.code as classCode, c.description as classDescription, c.year as classYear, c.semester as classSemester
	FROM professor_teaching as pt, class as c
	WHERE pt.classID = c.ID 
	AND pt.professorID = ?
    ORDER BY className");
define("GET_PROFESSOR_LESSON_LIST_QUERY", 
	"SELECT l.ID as lessonID, l.timeStart as classLessonTimeStart, l.timeEnd as classLessonTimeEnd, l.summary as lessonSummary, l.description as lessonDescription, r.name as roomName, tmpAttendance.rating as classLessonReviewRating, tmpAttendance.attendance as classLessonReviewAttendance
	FROM 
    class_room_lesson as l 
    JOIN  class_room_calendar as c ON c.ID = l.calendarID
    JOIN room as r on l.roomID = r.ID
    LEFT JOIN (SELECT a.lessonID as lessonID, AVG(rating) as rating, COUNT(*) as attendance FROM class_lesson_attendance_rating as a, class_room_lesson as l where a.lessonID = l.ID GROUP BY a.lessonID) as tmpAttendance on tmpAttendance.lessonID = l.ID 
	WHERE c.classID = :classID
	ORDER BY l.timeStart DESC");
// Gets the list of active lessons
define("GET_STUDENT_LESSON_LIST_QUERY", 
	"SELECT crl.ID as lessonID, c.ID as classID, c.name as className, c.code as classCode, c.description as classDescription, c.year as classYear, c.semester as classSemester, crl.timeStart as classLessonTimeStart, crl.timeEnd as classLessonTimeEnd, crl.summary as classLessonSummary , crl.description as classLessonDescription 
	FROM student_career as s, class as c, class_room_calendar as crc, class_room_lesson as crl
	WHERE s.classID = c.ID 
	AND c.ID = crc.classID
	AND crc.ID = crl.calendarID
	AND s.studentID = :studentID
	AND crl.timeStart LIKE :date
	ORDER BY c.year, className");
define("GET_USER_DETAILS_QUERY", "SELECT u.ID as userID, u.name as name, u.surname as surname, u.email as email, u.registrationDate as registrationDate, r.name as roleName FROM user as u, role as r WHERE u.roleID = r.ID AND email = ?");

define("ASK_A_QUESTION_QUERY", "INSERT INTO class_lesson_question (lessonID, studentID, question, time) VALUES (:lessonID, :studentID, :question, :time)");
define("SET_ATTENDANCE_QUERY", "INSERT INTO class_lesson_attendance_rating (studentID, lessonID, time) VALUES (:userID, :lessonID, :time)");
define("UNSET_ATTENDANCE_QUERY", "DELETE FROM class_lesson_attendance_rating WHERE studentID = :userID AND lessonID = :lessonID");
define("IS_USER_ATTENDING_LESSON_QUERY", "SELECT COUNT(*) as attendance FROM class_lesson_attendance_rating WHERE studentID = :userID AND lessonID = :lessonID");
define("SET_CLASS_LESSON_REVIEW_QUERY", "UPDATE class_lesson_attendance_rating SET summary = :summary, review = :review, rating = :rating WHERE studentID = :userID AND lessonID = :lessonID");
define("CHECK_IF_LESSON_ALREADY_EVALUATED", "SELECT count(*) as count, summary, review, rating FROM class_lesson_attendance_rating WHERE studentID = :studentID AND lessonID = :lessonID AND rating IS NOT NULL");
define("GET_LESSON_QUESTION", "SELECT question, ID, rate FROM class_lesson_question_rated WHERE lessonID = ?");
define("GET_LESSON_REVIEW", "SELECT rating, summary, review FROM class_lesson_attendance_rating WHERE lessonID = ? and rating IS NOT NULL");
define("GET_AVERAGE_RATING", "SELECT AVG(cla.rating) AS avgrating FROM class_room_calendar AS crc JOIN class_room_lesson AS crl JOIN class_lesson_attendance_rating AS cla WHERE crc.ID = crl.calendarID AND crl.ID = cla.lessonID AND crc.classID = ?");
define("GET_TOTAL_MEMBERS", "SELECT COUNT(*) as count FROM student_career WHERE classID = ?");
define("GET_ATTENDANCE_CHART", "SELECT COUNT(cla.ID) AS attendance, COUNT(cla.rating) AS review, DATE_FORMAT(crl.timeStart, '%d/%m/%Y') AS date FROM class_room_calendar AS crc JOIN class_room_lesson AS crl LEFT JOIN class_lesson_attendance_rating AS cla ON crl.ID = cla.lessonID WHERE crc.ID = crl.calendarID AND crc.classID = ? GROUP BY crl.ID ORDER BY crl.timeStart");
define("GET_CLASS_NAME_QUERY", "SELECT name as className FROM class WHERE ID = ?");	  
define("GET_LESSON_IN_PROGRESS_QUERY", "SELECT COUNT(*) AS count FROM class_room_lesson WHERE ID = ? AND CURRENT_TIMESTAMP >= timeStart AND CURRENT_TIMESTAMP <= timeEnd");

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
				$response = new Response(QUERY_NOT_OK_TEXT, QUERY_NOT_OK_CODE);
			}
			break;
		case USER_ROLE_BACKOFFICE_OPERATOR:
		case USER_ROLE_TEACHER:
			$connection->beginTransaction();
			if( $stmt1->execute() && $stmt2->execute() && $stmt3->execute()){
				$connection->commit();
				$response = new Response(QUERY_OK_TEXT, QUERY_OK_CODE);
			} else {
				$connection->rollBack();
				$response = new Response(QUERY_NOT_OK_TEXT, QUERY_NOT_OK_CODE);
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
		$response = new Response(QUERY_NOT_OK_TEXT, QUERY_NOT_OK_CODE);
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
		$result = new Response(QUERY_OK_TEXT, QUERY_OK_CODE);
	} else {
		$connection->rollBack();
		$result = new Response(QUERY_NOT_OK_TEXT, QUERY_NOT_OK_CODE);
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

function getClassList(int $professorID) {
	global $connection;
	$stmt = $connection->prepare(GET_PROFESSOR_CLASS_LIST_QUERY);
	$stmt->execute([$professorID]);
	$response = $stmt->fetchAll(PDO::FETCH_OBJ);
	return $response;
}
function getClassName(int $classID) {
	global $connection;
	$stmt = $connection->prepare(GET_CLASS_NAME_QUERY);
	$stmt->execute([$classID]);
	$response = $stmt->fetch(PDO::FETCH_OBJ);
	return $response;
}

function getProfessorLessonList($classID) {
	global $connection;
	$stmt = $connection->prepare(GET_PROFESSOR_LESSON_LIST_QUERY);
	$stmt->bindValue(':classID', $classID);
	$stmt->execute();
	$response = $stmt->fetchAll(PDO::FETCH_OBJ);
	return $response;
}

function getStudentLessonList(int $userID, string $date) {
	global $connection;
	$stmt = $connection->prepare(GET_STUDENT_LESSON_LIST_QUERY);
	$stmt->bindValue(':studentID', $userID);
	$stmt->bindValue(':date', $date);
	$stmt->execute();
	$response = $stmt->fetchAll(PDO::FETCH_OBJ);
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

function getLessonQuestions(array $input) {
	global $connection;
	$stmt = $connection->prepare(GET_LESSON_QUESTION);
	$stmt->execute([$input[KEY_CLASS_LESSON_ID]]);
	$response = $stmt->fetchAll(PDO::FETCH_OBJ);
	return $response;
}

function getLessonReviews(array $input) {
	global $connection;
	$stmt = $connection->prepare(GET_LESSON_REVIEW);
	$stmt->execute([$input[KEY_CLASS_LESSON_ID]]);
	$response = $stmt->fetchAll(PDO::FETCH_OBJ);
	return $response;
}

function getAverageRating(array $input){
	global $connection;
	$stmt = $connection->prepare(GET_AVERAGE_RATING);
	$stmt->execute([$input[KEY_CLASS_ID]]);
	$response = $stmt->fetchAll(PDO::FETCH_OBJ);
	return $response;
}

function getTotalMembers(array $input){
	global $connection;
	$stmt = $connection->prepare(GET_TOTAL_MEMBERS);
	$stmt->execute([$input[KEY_CLASS_ID]]);
	$response = $stmt->fetchAll(PDO::FETCH_OBJ);
	return $response;
}

function getAttedanceChart(array $input){
	global $connection;
	$stmt = $connection->prepare(GET_ATTENDANCE_CHART);
	$stmt->execute([$input[KEY_CLASS_ID]]);
	$response = $stmt->fetchAll(PDO::FETCH_OBJ);
	return $response;
}

function getLessonInProgress(int $lessonID) {
	global $connection;
	$stmt = $connection->prepare(GET_LESSON_IN_PROGRESS_QUERY);
	$stmt->execute([$lessonID]);
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