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
import java.util.concurrent.ExecutionException;

class DAO {

    static JSONArray getFromDB(Map params) {
        Log.i(Constants.TAG, DAO.class.getSimpleName() + " -getFromDB-");
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

    private static boolean isDataSent(HashMap params, int expectedResultCode) {
        Log.i(Constants.TAG, DAO.class.getSimpleName() + " -isDataSent-");
        JSONArray response = getFromDB(params);
        int codeResult = 0;
        try {
            codeResult = response.getJSONObject(0).getInt(Constants.KEY_CODE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return codeResult == expectedResultCode;
    }

    static JSONArray getClassList(int userID, String roleName) {
        Log.i(Constants.TAG, DAO.class.getSimpleName() + " -getClassList-");
        HashMap<String, String> params = new HashMap<>();
        params.put(Constants.KEY_ACTION, Constants.GET_CLASS_LIST);
        params.put(Constants.KEY_USER_ID, String.valueOf(userID));
        params.put(Constants.KEY_USER_ROLE_NAME, roleName);
        return getFromDB(params);
    }

    static boolean isUserAttendingLesson(int lessonID, int userID) {
        Log.i(Constants.TAG, DAO.class.getSimpleName() + " -isUserAttendingLesson-");
        HashMap<String, String> params = new HashMap<>();
        params.put(Constants.KEY_ACTION, Constants.IS_USER_ATTENDING_LESSON);
        params.put(Constants.KEY_CLASS_LESSON_ID, String.valueOf(lessonID));
        params.put(Constants.KEY_USER_ID, String.valueOf(userID));
        int response = 0;
        try {
            response = getFromDB(params).getJSONObject(0).getInt(Constants.KEY_ATTENDANCE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return response == 1;
    }

    static boolean sendQuestion(int lessonID, String input) {
        Log.i(Constants.TAG, DAO.class.getSimpleName() + " -sendQuestion- lessonID " + lessonID);
        HashMap<String, String> params = new HashMap<>();
        params.put(Constants.KEY_ACTION, Constants.USER_QUESTION_ASK);
        params.put(Constants.KEY_CLASS_LESSON_ID, String.valueOf(lessonID));
        params.put(Constants.KEY_USER_ID, String.valueOf(Session.getUserID()));
        params.put(Constants.KEY_QUESTION, input);
        params.put(Constants.KEY_TIME, new SimpleDateFormat(Constants.DATETIME_FORMAT, Locale.getDefault()).format(new Date()));
        return isDataSent(params, Constants.QUESTION_SENT_CODE);
    }

    static boolean setAttendance(int lessonID, boolean isUserAttendingLesson) {
        Log.i(Constants.TAG, DAO.class.getSimpleName() + " -updateClassAttendance- set to " + isUserAttendingLesson);
        // Registers the lesson's classAttendance into the DB
        HashMap<String, String> params = new HashMap<>();
        params.put(Constants.KEY_ACTION, Constants.KEY_SET_ATTENDANCE);
        params.put(Constants.KEY_CLASS_LESSON_ID, String.valueOf(lessonID));
        params.put(Constants.KEY_USER_ID, String.valueOf(Session.getUserID()));
        params.put(Constants.KEY_TIME, new SimpleDateFormat(Constants.DATETIME_FORMAT, Locale.getDefault()).format(new Date()));
        params.put(Constants.KEY_ATTENDANCE, String.valueOf(isUserAttendingLesson));
        return isDataSent(params, Constants.ATTENDANCE_SET_CODE);
    }

    static boolean setReview(int lessonID, String summary, String review, int rating) {
        Log.i(Constants.TAG, DAO.class.getSimpleName() + " -sendQuestion- lessonID " + lessonID);
        HashMap<String, String> params = new HashMap<>();
        params.put(Constants.KEY_ACTION, Constants.USER_SEND_REVIEW);
        params.put(Constants.KEY_CLASS_LESSON_ID, String.valueOf(lessonID));
        params.put(Constants.KEY_USER_ID, String.valueOf(Session.getUserID()));
        params.put(Constants.KEY_REVIEW_RATING, String.valueOf(rating));
        params.put(Constants.KEY_REVIEW_SUMMARY, summary);
        params.put(Constants.KEY_REVIEW_TEXT, review);
        return isDataSent(params, Constants.OK_CODE);
    }

    static boolean loginUser(String email, String password) {
        Log.i(Constants.TAG, DAO.class.getSimpleName() + " -loginUser- " + email);
        HashMap<String, String> params = new HashMap<>();
        params.put(Constants.KEY_ACTION, Constants.USER_LOGIN);
        params.put(Constants.KEY_USER_EMAIL, email);
        params.put(Constants.KEY_USER_PASSWORD, password);
        return isDataSent(params, Constants.OK_CODE);
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
        return isDataSent(params, Constants.OK_CODE);
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

    static boolean checkEvaluatedLessonResponse(JSONArray response){
        Log.i(Constants.TAG, DAO.class.getSimpleName() + " -checkEvaluatedLessonResponse- " + response);
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
}
