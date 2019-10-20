package it.teamgdm.sms.dibapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ExecutionException;

class DAO {

    static JSONArray getFromDB(Map params) {
        Log.i(Constants.TAG, DAO.class.getSimpleName() + " -getFromDB-" + params);
        JSONObject data = new JSONObject();
        JSONArray response = null;
        try {
            for (Object o : params.entrySet()) {
                Map.Entry entry = (Map.Entry) o;
                data.put((String) entry.getKey(), entry.getValue());
            }
            Log.i(Constants.TAG, DAO.class.getSimpleName() + " -getFromDB-data "+data);
            AsyncTaskConnection asyncTaskConnection = new AsyncTaskConnection();
            asyncTaskConnection.execute(data);
            response = asyncTaskConnection.get();
            Log.i(Constants.TAG, DAO.class.getSimpleName() + " -getFromDB-response "+response);
        } catch (JSONException | ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }

    private static boolean isDataSent(HashMap params) {
        Log.i(Constants.TAG, DAO.class.getSimpleName() + " -isDataSent-");
        JSONArray response = getFromDB(params);
        int codeResult = 0;
        try {
            codeResult = response.getJSONObject(0).getInt(Constants.KEY_CODE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return codeResult == Constants.OK_CODE;
    }

    static JSONArray getClassList(int userID) {
        Log.i(Constants.TAG, DAO.class.getSimpleName() + " -getClassList-");
        HashMap<String, String> params = new HashMap<>();
        params.put(Constants.KEY_ACTION, Constants.GET_CLASS_LIST);
        params.put(Constants.KEY_USER_ID, String.valueOf(userID));
        return getFromDB(params);
    }

    static JSONArray getLessonList(int ID, String roleName) {
        Log.i(Constants.TAG, DAO.class.getSimpleName() + " -getLessonList-");
        HashMap<String, String> params = new HashMap<>();
        if(roleName.equals(Constants.KEY_ROLE_PROFESSOR)) {
            params.put(Constants.KEY_ACTION, Constants.GET_PROFESSOR_LESSON_LIST);
            params.put(Constants.KEY_CLASS_ID, String.valueOf(ID));
        } else {
            params.put(Constants.KEY_ACTION, Constants.GET_STUDENT_LESSON_LIST);
            params.put(Constants.KEY_USER_ID, String.valueOf(ID));

            String date = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.getDefault()).format(new Date()) + "%";
            params.put(Constants.KEY_LESSON_DATE, date);
        }
        params.put(Constants.KEY_USER_ROLE_NAME, roleName);
        return getFromDB(params);
    }

    static boolean isUserAttendingLesson(int lessonID, int userID) {
        Log.i(Constants.TAG, DAO.class.getSimpleName() + " -isUserAttendingLesson-");
        HashMap<String, String> params = new HashMap<>();
        params.put(Constants.KEY_ACTION, Constants.IS_USER_ATTENDING_LESSON);
        params.put(Constants.KEY_LESSON_ID, String.valueOf(lessonID));
        params.put(Constants.KEY_USER_ID, String.valueOf(userID));
        int response = 0;
        try {
            response = getFromDB(params).getJSONObject(0).getInt(Constants.KEY_USER_ATTENDANCE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return response == 1;
    }

    static boolean sendQuestion(int lessonID, String input) {
        Log.i(Constants.TAG, DAO.class.getSimpleName() + " -sendQuestion- lessonID " + lessonID);
        HashMap<String, String> params = new HashMap<>();
        params.put(Constants.KEY_ACTION, Constants.USER_QUESTION_ASK);
        params.put(Constants.KEY_LESSON_ID, String.valueOf(lessonID));
        params.put(Constants.KEY_USER_ID, String.valueOf(Session.getUserID()));
        params.put(Constants.KEY_QUESTION, input);
        params.put(Constants.KEY_TIME, new SimpleDateFormat(Constants.DATETIME_FORMAT, Locale.getDefault()).format(new Date()));
        return isDataSent(params);
    }

    static boolean setAttendance(int lessonID, boolean isUserAttendingLesson) {
        Log.i(Constants.TAG, DAO.class.getSimpleName() + " -setAttendance- set to " + isUserAttendingLesson);
        // Registers the lesson's classAttendance into the DB
        HashMap<String, String> params = new HashMap<>();
        params.put(Constants.KEY_ACTION, Constants.KEY_SET_ATTENDANCE);
        params.put(Constants.KEY_LESSON_ID, String.valueOf(lessonID));
        params.put(Constants.KEY_USER_ID, String.valueOf(Session.getUserID()));
        params.put(Constants.KEY_TIME, new SimpleDateFormat(Constants.DATETIME_FORMAT, Locale.getDefault()).format(new Date()));
        params.put(Constants.KEY_USER_ATTENDANCE, String.valueOf(isUserAttendingLesson));
        return isDataSent(params);
    }

    static boolean setReview(int lessonID, String summary, String review, int rating) {
        Log.i(Constants.TAG, DAO.class.getSimpleName() + " -setReview- lessonID " + lessonID);
        HashMap<String, String> params = new HashMap<>();
        params.put(Constants.KEY_ACTION, Constants.USER_SEND_REVIEW);
        params.put(Constants.KEY_LESSON_ID, String.valueOf(lessonID));
        params.put(Constants.KEY_USER_ID, String.valueOf(Session.getUserID()));
        params.put(Constants.KEY_REVIEW_RATING, String.valueOf(rating));
        params.put(Constants.KEY_REVIEW_SUMMARY, summary);
        params.put(Constants.KEY_REVIEW_TEXT, review);
        return isDataSent(params);
    }

    static boolean loginUser(String email, String password) {
        Log.i(Constants.TAG, DAO.class.getSimpleName() + " -loginUser- " + email);
        HashMap<String, String> params = new HashMap<>();
        params.put(Constants.KEY_ACTION, Constants.USER_LOGIN);
        params.put(Constants.KEY_USER_EMAIL, email);
        params.put(Constants.KEY_USER_PASSWORD, password);
        return isDataSent(params);
    }

    static boolean registerUser(User tmpUser) {
        Log.i(Constants.TAG, DAO.class.getSimpleName() + " -registerUser- " + tmpUser);
        HashMap<String, String> params = new HashMap<>();
        params.put(Constants.KEY_ACTION, Constants.USER_REGISTRATION);
        params.put(Constants.KEY_USER_NAME, tmpUser.getName());
        params.put(Constants.KEY_USER_SURNAME, tmpUser.getSurname());
        params.put(Constants.KEY_USER_SERIAL_NUMBER, tmpUser.getSsn());
        params.put(Constants.KEY_DEGREECOURSE, tmpUser.getDegreeCourse());
        params.put(Constants.KEY_USER_ROLE_NAME, tmpUser.getRoleName());
        params.put(Constants.KEY_USER_EMAIL, tmpUser.getEmail());
        params.put(Constants.KEY_USER_PASSWORD, tmpUser.getPassword());
        params.put(Constants.KEY_USER_REGISTRATION_DATE, tmpUser.getRegistrationDate());
        return isDataSent(params);
    }

    static JSONArray getInputList(String inputList) {
        Log.i(Constants.TAG, DAO.class.getSimpleName() + " -getInputList- " + inputList);
        HashMap<String, String> params = new HashMap<>();
        params.put(Constants.KEY_ACTION, inputList);
        return getFromDB(params);
    }

    static JSONArray getUserDetail(String email) {
        Log.i(Constants.TAG, DAO.class.getSimpleName() + " -getUserDetail- email" + email);
        HashMap<String, String> param = new HashMap<>();
        param.put(Constants.KEY_ACTION, Constants.GET_USER_DETAILS);
        param.put(Constants.KEY_USER_EMAIL, email);
        return getFromDB(param);
    }

    static boolean evaluatedLessonResponseIsNull(JSONArray response){
        Log.i(Constants.TAG, DAO.class.getSimpleName() + " -evaluatedLessonResponseIsNull- " + response);
        boolean result = false;
        try {
            JSONObject o = response.getJSONObject(0);
            Log.i(Constants.TAG, o.toString());
            result = o.optInt("count") != 0;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    static JSONArray getLessonQuestion(int lessonID) {
        Log.i(Constants.TAG, DAO.class.getSimpleName() + " -getLessonQuestion- lessonID" + lessonID);
        HashMap<String, String> params = new HashMap<>();
        params.put(Constants.KEY_ACTION, Constants.GET_LESSON_QUESTIONS);
        params.put(Constants.KEY_LESSON_ID, String.valueOf(lessonID));
        return getFromDB(params);
    }

    static JSONArray getLessonReview(int lessonID) {
        Log.i(Constants.TAG, DAO.class.getSimpleName() + " -getLessonReview- lessonID" + lessonID);
        HashMap<String, String> params = new HashMap<>();
        params.put(Constants.KEY_ACTION, Constants.GET_LESSON_REVIEWS);
        params.put(Constants.KEY_LESSON_ID, String.valueOf(lessonID));
        return getFromDB(params);
    }

    static String getClassName(int classID) {
        Log.i(Constants.TAG, DAO.class.getSimpleName() + " -getClassName- classID " + classID);
        HashMap<String, String> params = new HashMap<>();
        params.put(Constants.KEY_ACTION, Constants.GET_CLASS_NAME);
        params.put(Constants.KEY_CLASS_ID, String.valueOf(classID));
        String className = null;
        try {
            className = getFromDB(params).getJSONObject(0).getString(Constants.KEY_CLASS_NAME);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return className;
    }

    static JSONArray getExistingEvaluation(int userID, int lessonID) {
        HashMap<String, String> params = new HashMap<>();
        params.put(Constants.KEY_ACTION, Constants.GET_EXISTING_EVALUATION);
        params.put(Constants.KEY_USER_ID, String.valueOf(userID));
        params.put(Constants.KEY_LESSON_ID, String.valueOf(lessonID));
        return getFromDB(params);
    }
}
