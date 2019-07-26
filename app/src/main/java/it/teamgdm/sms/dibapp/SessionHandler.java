package it.teamgdm.sms.dibapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.concurrent.ExecutionException;

public class SessionHandler {

    private final String TAG = "dibApp.RegisterActivity";

    private static final String REQUEST_METHOD = "POST";
    private static final String KEY_ACTION = "action";
    private static final String ACTION = "getUserDetails";
    private static final String PREFERENCE_FILE_KEY = "it.teamgdm.dibApp.userSession";
    private static final String KEY_NAME = "name";
    private static final String KEY_SURNAME = "surname";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_ROLE_ID = "roleId";
    private static final String KEY_ROLE_NAME = "roleName";
    private static final String KEY_DEGREE_COURSE_ID = "degreeCourseId";
    private static final String KEY_DEGREE_COURSE_NAME = "degreeCourseName";
    private static final String KEY_EXPIRES = "expires";
    private static final String KEY_EMPTY = "";
    private static final int KEY_ZERO = 0;

    private Context context;
    private SharedPreferences.Editor sharedPreferencesEditor;
    private SharedPreferences sharedPreferences;

    public SessionHandler(Context context) {
        Log.i(TAG, getClass().getSimpleName() + " -Constructor-");
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREFERENCE_FILE_KEY, Context.MODE_PRIVATE);
        this.sharedPreferencesEditor = sharedPreferences.edit();
    }

    public void loginUser(String email) {
        Log.i(TAG, getClass().getSimpleName() + " -loginUser-");
        User user = getUserDetails(email);
        sharedPreferencesEditor.putString(KEY_NAME, user.getName());
        sharedPreferencesEditor.putString(KEY_SURNAME, user.getSurname());
        sharedPreferencesEditor.putString(KEY_EMAIL, email);
        sharedPreferencesEditor.putInt(KEY_ROLE_ID, user.getRoleId());
        sharedPreferencesEditor.putString(KEY_ROLE_NAME, user.getRoleName());
        sharedPreferencesEditor.putInt(KEY_DEGREE_COURSE_ID, user.getDegreeCourseId());
        sharedPreferencesEditor.putString(KEY_DEGREE_COURSE_NAME, user.getDegreeCourseName());
        long millis = getExpireSessionTimer();
        sharedPreferencesEditor.putLong(KEY_EXPIRES, millis);
        sharedPreferencesEditor.commit();
    }

    private long getExpireSessionTimer() {
        Log.i(TAG, getClass().getSimpleName() + " -getExpireSessionTimer-");
        Date date = new Date();
        //Set user session for next 7 days
        long millis = date.getTime() + (7 * 24 * 60 * 60 * 1000);
        return millis;
    }

    /**
     * Check if session is expired by comparing current date and Session expiry date
     * If shared preferences does not have a value then user is not logged in.
     * @return boolean
     */

    public boolean isLoggedIn() {
        Log.i(TAG, getClass().getSimpleName() + " -isLoggedIn-");
        Date currentDate = new Date();

        long millis = sharedPreferences.getLong(KEY_EXPIRES, KEY_ZERO);

        if (millis == 0) {
            return false;
        }
        Date expiryDate = new Date(millis);
        return currentDate.before(expiryDate);
    }

    private User getUserDetails(String email) {
        Log.i(TAG, getClass().getSimpleName() + " -getUserDetails-");
        JSONObject data = new JSONObject();
        JSONObject response;
        User user = new User();
        try {
            data.put(KEY_ACTION, ACTION);
            data.put(KEY_EMAIL, email);
            Connection connection = new Connection(data, REQUEST_METHOD);
            connection.execute();
            response = connection.get();
            user.setName(response.getString(KEY_NAME));
            user.setSurname(response.getString(KEY_SURNAME));
            user.setRoleId(response.getInt(KEY_ROLE_ID));
            user.setRoleName(response.getString(KEY_ROLE_NAME));
            user.setDegreeCourseId(response.getInt(KEY_DEGREE_COURSE_ID));
            user.setDegreeCourseName(response.getString(KEY_DEGREE_COURSE_NAME));
        } catch (JSONException | ExecutionException | InterruptedException e) {
            Log.i(TAG, getClass().getSimpleName() + " -getUserDetails- Exception");
            e.printStackTrace();
        }
        return user;
    }

    /**
     * Logs out user by clearing the session
     */
    public void logoutUser(){
        Log.i(TAG, getClass().getSimpleName() + " -logoutUser-");
        sharedPreferencesEditor.clear().commit();
        Intent homeIntent = new Intent(context, MainActivity.class);
        context.startActivity(homeIntent);
    }

}