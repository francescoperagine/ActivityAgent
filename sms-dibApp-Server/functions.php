<?php

include 'db_connect.php';
include 'config.php';

class Response {

	var $code;
	var $message;
	
	function __construct(string $message, int $code) {
		$this->code = $code;
		$this->message = $message;
	}
}

function login($input) {
	if(isset($input['email']) && isset($input['password'])){
		try {
			credentialAreNotNull($input['email'], $input['password']);
			userExists($input['email']);
			verifyPassword($input['email'], $input['password']);	
			$response = new Response(LOGIN_OK_TEXT, LOGIN_OK_CODE);
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
	$query = "SELECT count(*) as count FROM user WHERE email = ?";
	$stmt = $connection->prepare($query);
	$stmt->execute([$email]);
	$result = $stmt->fetch(PDO::FETCH_OBJ);
	if($result->count != 1) throw new Exception(INVALID_EMAIL_TEXT, INVALID_EMAIL_CODE);
}

function verifyPassword(string $email, string $password) {
	global $connection;
	$query = "SELECT passwordHash, salt FROM user WHERE email = ?";
	$stmt = $connection->prepare($query);
	$stmt->execute([$email]);
	$result = $stmt->fetch(PDO::FETCH_OBJ);
	if(!password_verify(concatPasswordWithSalt($password, $result->salt), $result->passwordHash)){
		throw new Exception(INVALID_PASSWORD_TEXT, INVALID_PASSWORD_CODE);
	}
}

function registration($input) {
	global $connection;
	if(isset($input['serialNumber']) && isset($input['name']) && isset($input['surname']) && isset($input['email']) && isset($input['password'])){
	//Check if user already exist
		if(!userExists($input['email'])){
			//Get a unique Salt
			$salt         = getSalt();
			//Generate a unique password Hash
			$passwordHash = password_hash(concatPasswordWithSalt($input['password'],$salt),PASSWORD_DEFAULT);
			
			//Query to register new user
			$insertQuery  = "INSERT INTO user(serialNumber, name, surname, email, passwordHash, salt) VALUES (:serialNumber, :name, :surname, :email, :passwordHash, :salt)";
			if($stmt = $connection->prepare($insertQuery)){
				$stmt->bindValue(':serialNumber', $input['serialNumber']);
				$stmt->bindValue(':name', $input['name']);
				$stmt->bindValue(':surname', $input['surname']);
				$stmt->bindValue(':email', $input['email']);
				$stmt->bindValue(':passwordHash', $passwordHash);
				$stmt->bindValue(':salt', $salt);
				$stmt->execute();
				$response = $stmt->fetch(PDO::FETCH_OBJ);
				$response->code = USER_CREATED_CODE;
				$response->message = USER_CREATED_TEXT;
			}
		} else {
			$response = new Response(USER_ALREADY_EXISTS_CODE,USER_ALREADY_EXISTS);
		}
	} else { 
		$response = new Response(MISSING_MANDATORY_PARAMETERS,MISSING_MANDATORY_PARAMETERS_TEXT);
	}
	return $response;
}

function getUserDetails(string $email) {
	global $connection;
	$query = "SELECT u.name as name, u.surname as surname, u.registrationDate as registrationDate, dc.id as degreeCourseId, dc.name as degreeCourseName, r.id as roleId, r.name as roleName
		FROM user as u, user_degreeCourse as udc, degreeCourse as dc, user_role as ur, role as r
		WHERE u.id = udc.userID 
		AND udc.classID = dc.id
		AND u.id = ur.userID AND ur.roleID = r.ID
		AND email = ?";
	$stmt = $connection->prepare($query);
	$stmt->execute([$email]);
	$response = $stmt->fetch(PDO::FETCH_OBJ);
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

// ATTENDANCE

function setAttendance($user, $lecture) {

}

// returns true if the attendance is already registered

function attendanceIsSet($user, $lecture) {

}

// RATING

function setRating($user, $lecture, $rating) {

}

// returns true if the rating is already registered

function ratingIsSet($user, $lecture, $rating) {

}

// SUMMARY

function setSummary($user, $lecture, $summary) {

}

// returns true if the summary is already registered

function summaryIsSet($user, $lecture, $summary) {

}

// REVIEW

function setReview($user, $lecture, $review) {

}

// returns true if the review is already registered

function reviewIsSet($user, $lecture, $review) {

}

?>