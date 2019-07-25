<?php

error_reporting(E_ALL);

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

//error_reporting('ERROR_REPORTING');
$random_salt_length = 32;

/**
* Creates a unique Salt for hashing the password
* 
* @return
*/

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
	}
	else{
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