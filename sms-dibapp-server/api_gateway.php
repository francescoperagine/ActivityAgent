<?php 
include 'functions.php';

error_reporting(E_ALL);

//Get the input request parameters
$inputJSON = file_get_contents('php://input');
$input = json_decode($inputJSON, TRUE); //convert JSON into array

if(isset($input[ACTION])){	
	switch($input[ACTION]) {
		case ACTION_LOGIN: 
			$response = login($input);
			break;
		case ACTION_GET_ROLE_LIST:
			$response = getRoleList();
			break;
		case ACTION_GET_DEGREECOURSE_LIST:
			$response = getDegreecourseList();
			break;
		case ACTION_REGISTRATION:
			$response = registration($input);
			break;
		case ACTION_GET_USER_DETAILS: 
			$response = getUserDetails($input[KEY_USER_EMAIL]);
			break;
		case ACTION_GET_CLASS_LIST: 
			$response = getClassList($input[KEY_USER_ID]);
			break;
		case ACTION_GET_STUDENT_LESSON_LIST: 
			$response = getStudentLessonList($input[KEY_USER_ID], $input[KEY_LESSON_DATE]);
			break;
		case ACTION_GET_PROFESSOR_LESSON_LIST: 
			$response = getProfessorLessonList($input[KEY_CLASS_ID]);
			break;
		case ACTION_ASK_A_QUESTION:
			$response = askAQuestion($input[KEY_CLASS_LESSON_ID], $input[KEY_USER_ID], $input[KEY_QUESTION], $input[KEY_TIME]);
			break;
		case ACTION_SET_ATTENDANCE:
			$response = setAttendance($input[KEY_CLASS_LESSON_ID], $input[KEY_USER_ID], $input[KEY_CLASS_LESSON_ATTENDANCE], $input[KEY_TIME]);
			break;
		case ACTION_SET_REVIEW: 
			$response = setReview($input);
			break;
		case ACTION_IS_USER_ATTENDING_LESSON:
			$response = isUserAttendingLesson($input[KEY_CLASS_LESSON_ID], $input[KEY_USER_ID]);
			break;
		case ACTION_CHECK_LESSON_EVALUATED:
			$response = checkEvaluatedLesson($input);
			break;
		case ACTION_GET_LESSON_QUESTION:
			$response = getLessonQuestions($input);
			break;
		case ACTION_GET_LESSON_REVIEW:
			$response = getLessonReviews($input);
			break;
		case ACTION_GET_CLASS_NAME:
			$response = getClassName($input[KEY_CLASS_ID]);
			break;
		case ACTION_GET_AVERAGE_RATING:
			$response = getAverageRating($input);
			break;		
		case ACTION_GET_TOTAL_MEMBERS:
			$response = getTotalMembers($input);
			break;		
		case ACTION_GET_ATTENDANCE_CHART:
			$response = getAttedanceChart($input);
			break;
		case ACTION_GET_LESSON_IN_PROGRESS:
			$response = getLessonInProgress($input[KEY_CLASS_LESSON_ID]);
			break;
		default:
			$response = new Response(ACTION_NOT_DEFINED_TEXT, ACTION_NOT_DEFINED_CODE); 
			break;
	}
} else {
	$message = $input[ACTION] + " - " + ACTION_NOT_DEFINED_TEXT;
	$response = new Response($message, ACTION_NOT_DEFINED_CODE); 
}
echo json_encode($response);

?>