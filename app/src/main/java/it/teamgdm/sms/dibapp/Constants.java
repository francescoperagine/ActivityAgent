package it.teamgdm.sms.dibapp;

public final class Constants {

    static final String KEY_QUESTION_RATE = "rate";
    static final String KEY_QUESTION_ID = "ID";
    static final Object KEY_CLASS_LIST = "Courses";
    static final String KEY_GEOFENCE_LOCATION_UPDATE = "requestLocationUpdate";
    static final String HAS_GEOFENCE_PERMISSION = "hasGeofencePermission";
    static final String LESSON_NEW_QUESTION = "lessonNewQuestion";
    static final String KEY_TIME = "time";
    static final String TWO_PANEL = "twoPanel";
    static final String LESSON_IN_PROGRESS = "lessonInProgress";
    static final String USER_SEND_REVIEW = "sendReview";
    static final String KEY_REVIEW_TEXT = "reviewText";
    static final String KEY_REVIEW_SUMMARY = "reviewSummary";
    static final String KEY_REVIEW_RATING = "reviewRating";
    static final String IS_USER_ATTENDING_LESSON = "isUserAttendingLesson";
    static final String KEY_USER_ATTENDANCE = "attendance";
    static final String KEY_QUESTION = "question";
    static final String USER_QUESTION_ASK = "askAQuestion";
    static final String KEY_CLASS_LESSON = "lesson";
    static final String KEY_SET_ATTENDANCE = "setAttendance";
    static final String TAG = "dibApp";
    static final String PREFERENCE_FILE_KEY = "it.teamgdm.dibApp.userSession";

    static final String REQUEST_METHOD = "POST";
    static final String KEY_ACTION = "action";
    static final String KEY_CODE = "code";
    static final String KEY_MESSAGE = "message";

    static final String DATE_FORMAT = "yyyy-MM-dd";
    static final String TIME_FORMAT = "HH:mm:ss";
    static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    // USER DETAILS

    static final String USER_IS_LOGGED_IN = "userIsLoggedIn";

    static final String KEY_USER_ID = "userID";
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

    static final int OK_CODE = 100;

    // USER ROLES

    static final String KEY_ROLE_PROFESSOR = "professor";
    static final String KEY_ROLE_STUDENT = "student";

    // DB ACTIONS

    static final String USER_LOGIN = "login";
    static final String USER_REGISTRATION = "userRegistration";
    static final String GET_USER_DETAILS = "getUserDetails";
    static final String GET_ROLE_LIST = "getRoleList";
    static final String GET_DEGREECOURSE_LIST = "getDegreecourseList";
    static final String GET_CLASS_LIST = "getClassList";
    static final String GET_PROFESSOR_LESSON_LIST = "getProfessorLessonList";
    static final String GET_STUDENT_LESSON_LIST = "getStudentLessonList";
    static final String GET_LESSON_QUESTIONS = "getLessonQuestions";
    static final String GET_LESSON_REVIEWS = "getLessonReviews";
    static final String GET_AVERAGE_RATING = "getAverageRating";
    static final String GET_TOTAL_MEMBERS = "getTotalMembers";
    static final String GET_ATTENDANCE_CHART = "getAttedanceChart";
    static final String GET_EXISTING_EVALUATION = "checkEvaluatedLesson";
    static final String GET_CLASS_NAME = "getClassName";

    // DB STUDENT_CAREER TABLE ATTRIBUTES

    static final String KEY_CLASS_ID = "classID";
    static final String KEY_CLASS_NAME = "className";
    static final String KEY_CLASS_CODE = "classCode";
    static final String KEY_CLASS_DESCRIPTION = "classDescription";
    static final String KEY_LESSON_YEAR = "classYear";
    static final String KEY_LESSON_SEMESTER = "classSemester";
    static final String KEY_LESSON_DATE = "lessonDate";
    static final String KEY_LESSON_TIME_START = "classLessonTimeStart";
    static final String KEY_LESSON_TIME_END ="classLessonTimeEnd";
    static final String KEY_LESSON_SUMMARY = "classLessonSummary";
    static final String KEY_LESSON_DESCRIPTION = "classLessonDescription";
    static final String KEY_PASSED = "passed";
    static final String KEY_VOTE = "vote";
    static final String KEY_PRAISE = "praise";
    static final String KEY_PASSED_DATE = "passedDate";
    static final String KEY_LESSON_ID = "lessonID";
    static final String KEY_LESSON_CALENDAR_ID = "calendarID";
    static final String KEY_LESSON_ATTENDANCE = "attendance";


    static final String KEY_LESSON_REVIEW_SUMMARY = "classLessonReviewSummary";
    static final String KEY_LESSON_REVIEW_DESCRIPTION = "classLessonReviewDescription";
    static final String KEY_LESSON_REVIEW_RATING = "classLessonReviewRating";
    static final String KEY_LESSON_REVIEW_ATTENDANCE = "classLessonReviewAttendance";

    // GEOFENCE

    static final String GEOFENCE_TRANSITION_ACTION = "geofenceTransitionAction";

    static final int GEOFENCE_TRANSITION_DWELL_TIME = 1; // need to wait for at least some seconds before the trigger
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
