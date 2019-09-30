<?php
define("ACTION","action");
define("ACTION_LOGIN", "login");

define("KEY_USER_ID", "userID");
define("KEY_USER_NAME", "name");
define("KEY_USER_SURNAME", "surname");
define("KEY_USER_SERIAL_NUMBER", "serialNumber");
define("KEY_DEGREECOURSE", "degreecourse");
define("KEY_USER_PASSWORD", "password");
define("KEY_USER_ROLE_NAME", "roleName");
define("KEY_USER_EMAIL", "email");
define("KEY_CLASS_ID", "classID");
define("KEY_QUESTION", "question");
define("KEY_CLASS_LESSON_ID", "lessonID");
define("KEY_CLASS_LESSON_ATTENDANCE", "attendance");
define("ACTION_IS_USER_ATTENDING_LESSON", "isUserAttendingLesson");

define("ACTION_REGISTRATION", "userRegistration");
define("ACTION_GET_USER_DETAILS", "getUserDetails");
define("ACTION_GET_DEGREECOURSE_LIST", "getDegreecourseList");
define("ACTION_GET_ROLE_LIST", "getRoleList");
define("ACTION_GET_CURRENT_CLASS_LIST", "getCurrentClassList");
define("ACTION_GET_CLASS_LESSON_DETAIL", "getClassLessonDetail");
define("ACTION_ASK_A_QUESTION", "askAQuestion");
define("ACTION_SET_ATTENDANCE", "setAttendance");
define("ACTION_SET_REVIEW", "sendReview");

define("ACTION_NOT_DEFINED_TEXT", "Action not defined");
define("ACTION_NOT_DEFINED_CODE", 101);

define("LOGIN_OK_TEXT", "Login successful.");
define("LOGIN_OK_CODE", 201);

define("USER_CREATED_TEXT", "User has been created.");
define("USER_CREATED_CODE", 202);

define("USER_NOT_CREATED_CODE", 204);
define("USER_NOT_CREATED_TEXT", "User has not been created.");

define("INVALID_PASSWORD_TEXT", "Invalid password.");
define("INVALID_PASSWORD_CODE", 301);

define("INVALID_EMAIL_TEXT", "Email not registered.");
define("INVALID_EMAIL_CODE", 303);

define("MISSING_MANDATORY_PARAMETERS_TEXT", "Missing mandatory parameters.");
define("MISSING_MANDATORY_PARAMETERS_CODE", 304);

define("USER_ALREADY_EXISTS_TEXT", "Email already in use");
define("USER_ALREADY_EXISTS_CODE", 305);

define("QUESTION_SENT_TEXT", "Question sent.");
define("QUESTION_SENT_CODE", 401);

define("QUESTION_NOT_SENT_TEXT", "Question not sent");
define("QUESTION_NOT_SENT_CODE", 402);

define("ATTENDANCE_SET_TEXT", "Attendance set");
define("ATTENDANCE_SET_CODE", 501);

define("ATTENDANCE_NOT_SET_TEXT", "Attendance not set");
define("ATTENDANCE_NOT_SET_CODE", 502);

define("ATTENDANCE_ERROR_TEXT", "Attendance error");
define("ATTENDANCE_ERROR_CODE", 503);

define("QUERY_OK_TEXT", "Query ok");
define("QUERY_OK_CODE", 100);
define("QUERY_NOT_OK_TEXT", "Query not ok");
define("QUERY_NOT_OK_CODE", 101);



define("USER_ROLE_BACKOFFICE_OPERATOR", "backoffice_operator");
define("USER_ROLE_STUDENT", "student");
define("USER_ROLE_PROFESSOR", "professor");

?>