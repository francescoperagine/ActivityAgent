package it.teamgdm.sms.dibapp;

public final class Settings {
    static final String TAG = "dibApp";
    static final String PREFERENCE_FILE_KEY = "it.teamgdm.dibApp.userSession";

    static final String REQUEST_METHOD = "POST";
    static final String KEY_ACTION = "action";
    static final String KEY_CODE = "code";
    static final String KEY_MESSAGE = "message";
    static final String GET_USER_DETAILS = "getUserDetails";

    static final String KEY_NAME = "name";
    static final String KEY_SURNAME = "surname";
    static final String KEY_SERIAL_NUMBER = "serialNumber";
    static final String KEY_EMAIL = "email";
    static final String KEY_PASSWORD = "password";
    static final String KEY_ROLE_ID = "roleId";
    static final String KEY_ROLE_NAME = "roleName";
    static final int KEY_ROLE_TEACHER = 1;
    static final int KEY_ROLE_STUDENT = 2;
    public static final String KEY_DEGREE_COURSE_ID = "degreeCourseId";
    public static final String KEY_DEGREE_COURSE_NAME = "degreeCourseName";
    public static final String KEY_REGISTRATION_DATE = "registrationDate";
    static final String KEY_EXPIRE_TIME = "expirationTime";
    static final int KEY_ZERO = 0;
    static final String KEY_EMPTY = "";
    static final int LOGIN_OK_CODE = 201;
    static final int USER_CREATED_CODE = 202;

    static final String ACTION_LOGIN = "login";
    static final String ACTION_REGISTRATION = "registration";
}