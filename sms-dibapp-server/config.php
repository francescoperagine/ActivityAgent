<?php

// Actions

define("ACTION","action");
define("ACTION_LOGIN", "login");
define("ACTION_REGISTRATION", "userRegistration");
define("ACTION_GET_USER_DETAILS", "getUserDetails");
define("ACTION_GET_DEGREECOURSE_LIST", "getDegreecourseList");
define("ACTION_GET_ROLE_LIST", "getRoleList");
define("ACTION_GET_CLASS_LIST", "getClassList");
define("ACTION_GET_STUDENT_LESSON_LIST", "getStudentLessonList");
define("ACTION_GET_PROFESSOR_LESSON_LIST", "getProfessorLessonList");
define("ACTION_ASK_A_QUESTION", "askAQuestion");
define("ACTION_SET_ATTENDANCE", "setAttendance");
define("ACTION_IS_USER_ATTENDING_LESSON", "isUserAttendingLesson");
define("ACTION_SET_REVIEW", "sendReview");
define("ACTION_CHECK_LESSON_EVALUATED", "checkEvaluatedLesson");
define("ACTION_GET_LESSON_QUESTION", "getLessonQuestions");
define("ACTION_GET_LESSON_REVIEW", "getLessonReviews");
define("ACTION_GET_AVERAGE_RATING", "getAverageRating");
define("ACTION_GET_TOTAL_MEMBERS", "getTotalMembers");
define("ACTION_GET_ATTENDANCE_CHART", "getAttedanceChart");
define("ACTION_GET_CLASS_NAME", "getClassName");
define("ACTION_GET_LESSON_IN_PROGRESS", "getLessonInProgress");
define("IS_QUESTION_RATED", "isQuestionRated");
define("ACTION_DELETE_QUESTION_RATE", "deleteQuestionRate");
define("ACTION_SET_QUESTION_RATE", "setQuestionRate");
define("ACTION_GET_QUESTION_COUNT", "getQuestionCount");
define("ACTION_GET_REVIEW_COUNT", "getReviewCount");
define("ACTION_GET_DEGREECOURSE_NAME", "getDegreeCourseName");

define("ACTION_NOT_DEFINED_TEXT", "Action not defined");
define("ACTION_NOT_DEFINED_CODE", 101);

// Roles

define("USER_ROLE_STUDENT", "student");
define("USER_ROLE_PROFESSOR", "professor");

// DB keys

define("KEY_USER_ID", "userID");
define("KEY_USER_NAME", "name");
define("KEY_USER_SURNAME", "surname");
define("KEY_USER_SERIAL_NUMBER", "serialNumber");
define("KEY_DEGREECOURSE", "degreecourse");
define("KEY_USER_PASSWORD", "password");
define("KEY_USER_REGISTRATION_DATE", "registrationDate");
define("KEY_USER_ROLE_NAME", "roleName");
define("KEY_USER_EMAIL", "email");
define("KEY_TIME", "time");
define("KEY_CLASS_ID", "classID");
define("KEY_QUESTION", "question");
define("KEY_QUESTION_ID", "questionID");
define("KEY_QUESTION_RATE", "rate");
define("KEY_CLASS_LESSON_ID", "lessonID");
define("KEY_CLASS_LESSON_ATTENDANCE", "attendance");
define("KEY_CLASS_LESSON_REVIEW_SUMMARY", "reviewSummary");
define("KEY_CLASS_LESSON_REVIEW_TEXT", "reviewText");
define("KEY_CLASS_LESSON_REVIEW_RATING", "reviewRating");
define("KEY_LESSON_DATE", "lessonDate");

// Response

define("QUERY_OK_TEXT", "Query ok");
define("QUERY_OK_CODE", 100);
define("QUERY_NOT_OK_TEXT", "Query not ok");
define("QUERY_NOT_OK_CODE", 101);

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







?>