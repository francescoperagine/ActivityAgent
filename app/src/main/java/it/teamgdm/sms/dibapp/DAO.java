package it.teamgdm.sms.dibapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

class DAO {

    static JSONArray getFromDB(Map params) {
        Log.i(Constants.TAG, ClassListActivity.class.getSimpleName() + " -getFromDB-");
        JSONObject data = new JSONObject();
        JSONArray response = null;
        try {
            for (Object o : params.entrySet()) {
                Map.Entry entry = (Map.Entry) o;
                data.put((String) entry.getKey(), entry.getValue());
            }
            Log.i(Constants.TAG, ClassListActivity.class.getSimpleName() + " -getFromDB-data "+data);
            AsyncTaskConnection asyncTaskConnection = new AsyncTaskConnection();
            asyncTaskConnection.execute(data);
            response = asyncTaskConnection.get();
            Log.i(Constants.TAG, ClassListActivity.class.getSimpleName() + " -getFromDB-response "+response);
        } catch (JSONException | ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }

    private static boolean isDataSent(HashMap params, int expectedResultCode) {
        Log.i(Constants.TAG, BaseActivity.class.getSimpleName() + " -isDataSent-");
        JSONArray response = getFromDB(params);
        int codeResult = 0;
        try {
            codeResult = response.getJSONObject(0).getInt(Constants.KEY_CODE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return codeResult == expectedResultCode;
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
        return isDataSent(params, Constants.QUESTION_SENT_CODE);
    }

    static boolean setAttendance(int lessonID, boolean isUserAttendingLesson) {
        Log.i(Constants.TAG, DAO.class.getSimpleName() + " -updateClassAttendance- set to " + isUserAttendingLesson);
        // Registers the lesson's classAttendance into the DB
        HashMap<String, String> params = new HashMap<>();
        params.put(Constants.KEY_ACTION, Constants.KEY_SET_ATTENDANCE);
        params.put(Constants.KEY_CLASS_LESSON_ID, String.valueOf(lessonID));
        params.put(Constants.KEY_USER_ID, String.valueOf(Session.getUserID()));
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

    static boolean checkEvaluatedLessonResponse(JSONArray response){

        boolean result = false;

        Log.i(Constants.TAG, String.valueOf(Session.getUserID()));

        Log.i(Constants.TAG, response.toString());

        try {
            JSONObject o = response.getJSONObject(0);
            Log.i(Constants.TAG, o.toString());
            if(o.optInt("count") == 0){
                result = false;
            }
            else{
                result = true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;

    }
}
