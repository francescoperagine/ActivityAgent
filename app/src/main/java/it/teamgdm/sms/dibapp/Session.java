package it.teamgdm.sms.dibapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

class Session {

    private final static String USER_STORED_OBJECT = "user";

    private static Context applicationContext;

    private static SharedPreferences.Editor sharedPreferencesEditor;
    private static SharedPreferences sharedPreferences;
    private static User user;
    private static String USER_IS_LOGGED_IN = "userIsLoggedIn";
    private long sessionExpireTime;

    @SuppressLint("CommitPrefEdits")
    Session(Context context) {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -Constructor-");
        applicationContext = context;
        sharedPreferences = applicationContext.getSharedPreferences(Settings.PREFERENCE_FILE_KEY, Context.MODE_PRIVATE);
        sharedPreferencesEditor = sharedPreferences.edit();
    }

    static void setSharedPreferences(String key, String value) {
        sharedPreferencesEditor.putString(key, value);
        sharedPreferencesEditor.commit();
    }

    static void setSharedPreferences(String key, int value) {
        sharedPreferencesEditor.putInt(key, value);
        sharedPreferencesEditor.commit();
    }

    static void setSharedPreferences(String key, boolean value) {
        sharedPreferencesEditor.putBoolean(key, value);
        sharedPreferencesEditor.commit();
    }

    static String getSharedPreferences(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

    static int getSharedPreferences(String key, int defaultValue) {
        return sharedPreferences.getInt(key, defaultValue);
    }

    static boolean getSharedPreferences(String key, boolean defaultValue) {
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    void login(Context context, String email) {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -login-");
        JSONObject userDetails = getUserDetails(context, email);
        assert userDetails != null;
        setUserDetails(userDetails);
        setUserInSharedPreferences();
    }

    private JSONObject getUserDetails(Context context, String email) {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -getUserDetails-");
        JSONObject data = new JSONObject();
        try {
            data.put(Settings.KEY_ACTION, Settings.GET_USER_DETAILS);
            data.put(Settings.KEY_USER_EMAIL, email);
            AsyncTaskConnection asyncTaskConnection = new AsyncTaskConnection();
            asyncTaskConnection.execute(data);
            JSONArray response = asyncTaskConnection.get();
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
        user = new User();
        try {
            user.setID(userDetails.getInt(Settings.KEY_USER_ID));
            user.setName(userDetails.getString(Settings.KEY_USER_NAME));
            user.setSurname(userDetails.getString(Settings.KEY_USER_SURNAME));
            user.setEmail(userDetails.getString(Settings.KEY_USER_EMAIL));
            user.setRole(userDetails.getString(Settings.KEY_USER_ROLE_NAME));
            user.setRegistrationDate(userDetails.getString(Settings.KEY_REGISTRATION_DATE));
    //        user.setSessionExpireTime(getExpireSessionTime());
        } catch (JSONException e) {
            Log.i(Settings.TAG, getClass().getSimpleName() + " -setUserDetails-Exception");
            e.printStackTrace();
        }
    }

    /**
     * Stores the User object into the Sharedpreferences
     */

    private void setUserInSharedPreferences() {
        Log.i(Settings.TAG, Session.class.getSimpleName() + " -setSharedPreferences-");
        Gson gson = new Gson();
        String jsonUser = gson.toJson(user);
        setSharedPreferences(USER_STORED_OBJECT, jsonUser);
        setSharedPreferences(USER_IS_LOGGED_IN, true);
    }

    /**
     * Retrieves the User object from the stored SharedPreferences
     */

    void getUserFromSharedPreferences() {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -getUserFromSharedPreferences-");
        Gson gson = new Gson();
        String jsonUser = getSharedPreferences(USER_STORED_OBJECT, null);
        user = gson.fromJson(jsonUser, User.class);
        Log.i(Settings.TAG, getClass().getSimpleName() + " -getUserFromSharedPreferences- User- " + user);
    }

    boolean userIsLoggedIn() {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -userIsLoggedIn-");
        if(getSharedPreferences(USER_IS_LOGGED_IN, false)) {
            Log.i(Settings.TAG, getClass().getSimpleName() + " -userIsLoggedIn-user is logged in");
            return true;
        } else {
            Log.i(Settings.TAG, getClass().getSimpleName() + " -userIsLoggedIn-user is not logged in");
            return false;
        }
    }

    /**
     * Logs out user by clearing the session
     */

    void logout(){
        Log.i(Settings.TAG, getClass().getSimpleName() + " -logout-");
        sharedPreferencesEditor.clear().commit();
        Intent mainActivityIntent = new Intent(applicationContext, MainActivity.class);
        applicationContext.startActivity(mainActivityIntent);
    }

    boolean isStudent() {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -isStudent-");
        return user.isStudent();
    }

    public boolean isTeacher() {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -isTeacher-");
        return user.isTeacher();
    }

    static int getUserID() {
        Log.i(Settings.TAG, Session.class.getSimpleName() + " -getUserID-");
        return user.getID();
    }

    void setSessionExpireTime(long sessionExpireTime) {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -setSessionExpireTime-");
        this.sessionExpireTime = sessionExpireTime;
    }

    long getSessionExpireTime() {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -getSessionExpireTime-");
        return this.sessionExpireTime;
    }
}