<?php

include 'db_connect.php';
include 'config.php';

define("USER_EXISTS_QUERY", "SELECT count(*) as count FROM user WHERE email = ?");
define("VERIFY_PASSWORD_QUERY", "SELECT passwordHash, salt FROM user WHERE email = ?");
define("GET_ROLE_LIST", "SELECT name as roleName FROM role");
define("REGISTER_NEW_USER_QUERY", "INSERT INTO user(serialNumber, name, surname, email, passwordHash, salt) VALUES (:serialNumber, :name, :surname, :email, :passwordHash, :salt)");
define("REGISTER_NEW_USER_ROLE_QUERY", "UPDATE user SET user.roleID = (SELECT ID from role WHERE name = :roleName) WHERE user.email = :email"); 
define("GET_USER_DETAILS_QUERY", "SELECT u.name as name, u.surname as surname, u.email as email, u.registrationDate as registrationDate, u.roleID as roleId, r.name as roleName FROM user as u, role as r WHERE u.roleID = r.ID AND email = ?");

class Response {
	
	var $message;
	var $code;
	
	function __construct(string $message, int $code) {
		$this->message = $message;
		$this->code = $code;
	}
}

function login(array $input) {
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
	if(isset($input['name']) && isset($input['surname']) && isset($input['serialNumber']) && isset($input['roleName']) && isset($input['email']) && isset($input['password'])){
	//Register the user if doesn't exists
		try {
			userExists($input['email']);
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
	$stmt = $connection->prepare(GET_ROLE_LIST);
	$stmt->execute();
	$response = $stmt->fetchAll(PDO::FETCH_OBJ);
	return $response;
}

function registerNewUser(array $input) {
	global $connection;
	//Get a unique Salt
	$salt = getSalt();
	//Generate a unique password Hash
	$passwordHash = password_hash(concatPasswordWithSalt($input['password'],$salt),PASSWORD_DEFAULT);
	$response = null;
	
	//Query to register new user
	$stmt = $connection->prepare(REGISTER_NEW_USER_QUERY);
	$stmt->bindValue(':name', $input['name']);
	$stmt->bindValue(':surname', $input['surname']);
	$stmt->bindValue(':serialNumber', $input['serialNumber']);
	$stmt->bindValue(':email', $input['email']);
	$stmt->bindValue(':passwordHash', $passwordHash);
	$stmt->bindValue(':salt', $salt);
	$stmt->execute();
	
	$stmt2 = $connection->prepare(REGISTER_NEW_USER_ROLE_QUERY);
	$stmt2->bindValue(':email', $input['email']);
	$stmt2->bindValue(':roleName', $input['roleName']);
	$stmt2->execute();
	
	if($stmt && $stmt2) {
		$response = new Response(USER_CREATED_TEXT,USER_CREATED_CODE);
	} else {
		$response = new Response(USER_NOT_CREATED_TEXT,USER_NOT_CREATED_CODE);
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