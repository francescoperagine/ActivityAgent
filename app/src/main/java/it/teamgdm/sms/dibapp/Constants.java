package it.teamgdm.sms.dibapp;

public final class Constants {

    static final String CLASS_LIST_ACTION = "classDetail";
    static final String GEOFENCE_RECEIVER_ACTION = "geofenceBroadcastReceiverAction";
    static final String TAG = "dibApp";
    static final String PREFERENCE_FILE_KEY = "it.teamgdm.dibApp.userSession";

    static final String REQUEST_METHOD = "POST";
    static final String KEY_ACTION = "action";
    static final String KEY_CODE = "code";
    static final String KEY_MESSAGE = "message";

    // USER DETAILS

    static String USER_IS_LOGGED_IN = "userIsLoggedIn";

    static final String KEY_ITEM_ID = "item_id";
    static final String KEY_USER_ID = "ID";
    static final String KEY_USER_NAME = "name";
    static final String KEY_USER_SURNAME = "surname";
    static final String KEY_USER_SERIAL_NUMBER = "serialNumber";
    static final String KEY_USER_EMAIL = "email";
    static final String KEY_USER_REGISTRATION_DATE = "registrationDate";
    static final String KEY_USER_PASSWORD = "password";
    static final String KEY_DEGREECOURSE = "degreecourse";
    static final String KEY_USER_ROLE_NAME = "roleName";
    static final String KEY_EMPTY = "";
    static final String KEY_BLANK = " ";
    static final int LOGIN_OK_CODE = 201;
    static final int LOGIN_FAILED_CODE = 301;
    static final int USER_CREATED_CODE = 202;

    // USER ROLES

    static final String KEY_ROLE_PROFESSOR = "professor";
    static final String KEY_ROLE_STUDENT = "student";

    // DB ACTIONS

    static final String USER_LOGIN = "login";
    static final String USER_REGISTRATION = "registration";
    static final String GET_USER_DETAILS = "getUserDetails";
    static final String GET_CLASS_LIST = "getClassList";
    static final String GET_ROLE_LIST = "getRoleList";
    static final String GET_DEGREECOURSE_LIST = "getDegreecourseList";
    static final String GET_CLASS_DETAIL = "getClassDetail";
    static final String KEY_CLASS_PARTECIPATION = "classPartecipation";

    // DB STUDENT_CAREER TABLE ATTRIBUTES

    static final String KEY_CLASS_ID = "classID";
    static final String KEY_CLASS_NAME = "className";
    static final String KEY_CLASS_YEAR = "year";
    static final String KEY_CLASS_SEMESTER = "semester";
    static final String KEY_PASSED = "passed";
    static final String KEY_VOTE = "vote";
    static final String KEY_PRAISE = "praise";
    static final String KEY_PASSED_DATE = "passedDate";

    // GEOFENCE

    static final String GEOFENCE_TRANSITION_ACTION = "geoFenceTransitionAction";

    static final int GEOFENCE_TRANSITION_DWELL_TIME = 10*1000; // need to wait for at least some seconds before the trigger
    static final int GEOFENCE_PERMISSION_REQUEST_CODE = 1;
    static final int GEOFENCE_METER_RADIUS_DIB = 100;
    static final int GEOFENCE_METER_RADIUS_PDA = 100;

    static final String GEOFENCE_DIB_NAME = "Dipartimento di Informatica";
    static final double GEOFENCE_DIB_LATITUDE = 41.109725;
    static final double GEOFENCE_DIB_LONGITUDE = 16.881521;


    static final String GEOFENCE_PDA_NAME = "Palazzo delle Aule";
    static final double GEOFENCE_PDA_LATITUDE = 41.109388;
    static final double GEOFENCE_PDA_LONGITUDE = 16.881759;
}
