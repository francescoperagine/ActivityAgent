<?php

include 'functions.php';

//Get the input request parameters
$inputJSON = file_get_contents('php://input');
$input = json_decode($inputJSON, TRUE); //convert JSON into array

if(isset($input['email']) && isset($input['password'])){
	try {
		credentialAreNotNull($input['email'], $input['password']);
		userExists($input['email']);
		verifyPassword($input['email'], $input['password']);	
		$response = new Response(LOGIN_OK_TEXT, LOGIN_OK_CODE);
	} catch( Exception $e) {
		$response = new Response($e->getMessage(), intval($e->getCode())); 
		$e->getMessage();
	}
	echo json_encode($response);
}
?>