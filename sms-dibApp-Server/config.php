<?php
define("ACTION","action");
define("ACTION_LOGIN", "login");
define("ACTION_REGISTRATION", "registration");
define("ACTION_GET_USER_DETAILS", "getUserDetails");
define("ACTION_NOT_DEFINED_CODE", 101);
define("ACTION_NOT_DEFINED_TEXT", "Action not defined");

define("LOGIN_OK_CODE", 201);
define("LOGIN_OK_TEXT", "Login successful.");
define("REGISTER_OK_CODE", 202);
define("REGISTER_OK_TEXT", "Registration successful.");
define("USER_CREATED_CODE", 203);
define("USER_CREATED_TEXT", "User exists");
define("INVALID_PASSWORD_CODE", 301);
define("INVALID_PASSWORD_TEXT", "Invalid password.");
define("INVALID_EMAIL_CODE", 303);
define("INVALID_EMAIL_TEXT", "Email not registered.");
define("MISSING_MANDATORY_PARAMETERS_CODE", 304);
define("MISSING_MANDATORY_PARAMETERS_TEXT", "Missing mandatory parameters.");
define("USER_ALREADY_EXISTS_CODE", 305);
define("USER_ALREADY_EXISTS_TEXT", "User exists");
?>