<?php

include 'functions.php';

//Get the input request parameters
$inputJSON = file_get_contents('php://input');
$input = json_decode($inputJSON, TRUE); //convert JSON into array

//Check for Mandatory parameters
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
			$connection = null;
		}
	} else {
		$response = new Response(USER_ALREADY_EXISTS_CODE,USER_ALREADY_EXISTS);
	}
} else { 
	$response = new Response(MISSING_MANDATORY_PARAMETERS,MISSING_MANDATORY_PARAMETERS_TEXT);
}
echo json_encode($response);
?>