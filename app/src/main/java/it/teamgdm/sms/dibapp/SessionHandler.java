package it.teamgdm.sms.dibapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.concurrent.ExecutionException;

class SessionHandler {

    private final Context context;
    private final SharedPreferences.Editor sharedPreferencesEditor;
    private final SharedPreferences sharedPreferences;
    private JSONObject userDetails;
    private User user;

    @SuppressLint("CommitPrefEdits")
    SessionHandler(Context context) {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -Constructor-");
        this.context = context;
        sharedPreferences = context.getSharedPreferences(Settings.PREFERENCE_FILE_KEY, Context.MODE_PRIVATE);
        this.sharedPreferencesEditor = sharedPreferences.edit();
        user = new User();
    }

    void login(String email) {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -login-");
        userDetails = getUserDetails(email);
        assert userDetails != null;
        setUserDetails(userDetails);
        setSharedPreferences(userDetails);
    }

    private JSONObject getUserDetails(String email) {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -getUserDetails-");
        JSONObject data = new JSONObject();
        try {
            data.put(Settings.KEY_ACTION, Settings.ACTION_GET_USER_DETAILS);
            data.put(Settings.KEY_EMAIL, email);
            Log.i(Settings.TAG, getClass().getSimpleName() + " -getUserDetails-data: " + data.toString());
            Connection connection = new Connection(data, Settings.REQUEST_METHOD);
            connection.execute();
            JSONArray response = connection.get();
            Log.i(Settings.TAG, getClass().getSimpleName() + " -getUserDetails-response: " + response.toString());
            return response.getJSONObject(0);
        } catch (ExecutionException | InterruptedException | JSONException e) {
            Log.i(Settings.TAG, getClass().getSimpleName() + " -getUserDetails-Exception");
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            return null;
        }
    }

    private void setUserDetails(JSONObject userDetails) {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -setUserDetails-");
        try {
            user.setName(userDetails.getString(Settings.KEY_NAME));
            user.setSurname(userDetails.getString(Settings.KEY_SURNAME));
            user.setEmail(userDetails.getString(Settings.KEY_EMAIL));
            user.setRole(userDetails.getString(Settings.KEY_ROLE_NAME));
            //TODO fix setRegistrationDate with DB data;
    //        user.setRegistrationDate(Long.getLong(userDetails.getString(Settings.KEY_REGISTRATION_DATE)));
            user.setSessionExpiryDate(getExpireSessionDate());
        } catch (JSONException e) {
            Log.i(Settings.TAG, getClass().getSimpleName() + " -setUserDetails-Exception");
            e.printStackTrace();
        }
    }

    private void setSharedPreferences(JSONObject userDetails) {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -setSharedPreferences-");
        try {
            sharedPreferencesEditor.putString(Settings.KEY_NAME, userDetails.getString(Settings.KEY_NAME));
            sharedPreferencesEditor.putString(Settings.KEY_SURNAME, userDetails.getString(Settings.KEY_SURNAME));
            sharedPreferencesEditor.putString(Settings.KEY_EMAIL, userDetails.getString(Settings.KEY_EMAIL));
            sharedPreferencesEditor.putInt(Settings.KEY_ROLE_ID, userDetails.getInt(Settings.KEY_ROLE_ID));
            sharedPreferencesEditor.putString(Settings.KEY_ROLE_NAME, userDetails.getString(Settings.KEY_ROLE_NAME));
            long millis = getExpireSessionDate();
            sharedPreferencesEditor.putLong(Settings.KEY_EXPIRE_TIME, millis);
            sharedPreferencesEditor.commit();
        } catch (JSONException e) {
            Log.i(Settings.TAG, getClass().getSimpleName() + " -setSharedPreferences-Exception");
            e.printStackTrace();
        }
    }

    private long getExpireSessionDate() {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -getExpireSessionDate-");
        Date date = new Date();
        //Set user session for next 7 days
        return date.getTime() + (7 * 24 * 60 * 60 * 1000);
    }

    /**
     * Check if session is expired by comparing current date and Session expiry date
     * If shared preferences does not have a value then user is not logged in.
     * @return boolean
     */

    boolean isLoggedIn() {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -isLoggedIn-");
        Date currentDate = new Date();

        long millis = sharedPreferences.getLong(Settings.KEY_EXPIRE_TIME, Settings.KEY_ZERO);

        if (millis == 0) {
            return false;
        }
        Date expiryDate = new Date(millis);
        return currentDate.before(expiryDate);
    }

    /**
     * Logs out user by clearing the session
     */
    void logout(){
        Log.i(Settings.TAG, getClass().getSimpleName() + " -logout-");
        sharedPreferencesEditor.clear().commit();
        Intent mainIntent = new Intent(context, MainActivity.class);
        context.startActivity(mainIntent);
    }

    boolean isStudent() {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -isStudent-");
        return user.isStudent();
    }

    public boolean isTeacher() {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -isTeacher-");
        return user.isTeacher();
    }

    void setUserWithSharedPreferences() {
        user.setName(sharedPreferences.getString(Settings.KEY_NAME, Settings.KEY_EMPTY));
        user.setSurname(sharedPreferences.getString(Settings.KEY_SURNAME, Settings.KEY_EMPTY));
        user.setEmail(sharedPreferences.getString(Settings.KEY_EMAIL, Settings.KEY_EMPTY));
        user.setRole(sharedPreferences.getString(Settings.KEY_ROLE_NAME, Settings.KEY_EMPTY));
        //TODO setSharedPreferences DegreeCourses
    //    user.setDegreeCourseId(sharedPreferences.getInt(Settings.KEY_DEGREE_COURSE_ID, Settings.KEY_ZERO));
    //    user.setDegreeCourseName(sharedPreferences.getString(Settings.KEY_DEGREE_COURSE_NAME, Settings.KEY_EMPTY));
    //    user.setRegistrationDate(sharedPreferences.getLong(Settings.KEY_REGISTRATION_DATE, Settings.KEY_ZERO));
        user.setSessionExpiryDate(getExpireSessionDate());
    }
}