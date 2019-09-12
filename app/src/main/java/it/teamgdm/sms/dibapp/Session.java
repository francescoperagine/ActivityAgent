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
    public static boolean GEOFENCE_PERMISSION_GRANTED;

    private static Context applicationContext;

    private static SharedPreferences.Editor sharedPreferencesEditor;
    private static SharedPreferences sharedPreferences;
    private static User user;
    private static String USER_IS_LOGGED_IN = "userIsLoggedIn";
    private static GeofenceAPI geofenceAPI;
    private long sessionExpireTime;

    @SuppressLint("CommitPrefEdits")
    Session(Context context) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -Constructor-");
        applicationContext = context;
        sharedPreferences = applicationContext.getSharedPreferences(Constants.PREFERENCE_FILE_KEY, Context.MODE_PRIVATE);
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
        Log.i(Constants.TAG, getClass().getSimpleName() + " -login-");
        JSONObject userDetails = getUserDetails(context, email);
        assert userDetails != null;
        setUserDetails(userDetails);
        setUserInSharedPreferences();
    }

    private JSONObject getUserDetails(Context context, String email) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -getUserDetails-");
        JSONObject data = new JSONObject();
        try {
            data.put(Constants.KEY_ACTION, Constants.GET_USER_DETAILS);
            data.put(Constants.KEY_USER_EMAIL, email);
            AsyncTaskConnection asyncTaskConnection = new AsyncTaskConnection();
            asyncTaskConnection.execute(data);
            JSONArray response = asyncTaskConnection.get();
            Log.i(Constants.TAG, getClass().getSimpleName() + " -getUserDetails-response: " + response.toString());
            return response.getJSONObject(0);
        } catch (ExecutionException | InterruptedException | JSONException e) {
            Log.i(Constants.TAG, getClass().getSimpleName() + " -getUserDetails-Exception");
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            return null;
        }
    }

    private void setUserDetails(JSONObject userDetails) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -setUserDetails-");
        user = new User();
        try {
            user.setID(userDetails.getInt(Constants.KEY_USER_ID));
            user.setName(userDetails.getString(Constants.KEY_USER_NAME));
            user.setSurname(userDetails.getString(Constants.KEY_USER_SURNAME));
            user.setEmail(userDetails.getString(Constants.KEY_USER_EMAIL));
            user.setRole(userDetails.getString(Constants.KEY_USER_ROLE_NAME));
            user.setRegistrationDate(userDetails.getString(Constants.KEY_REGISTRATION_DATE));
    //        user.setSessionExpireTime(getExpireSessionTime());
        } catch (JSONException e) {
            Log.i(Constants.TAG, getClass().getSimpleName() + " -setUserDetails-Exception");
            e.printStackTrace();
        }
    }

    private void setUserInSharedPreferences() {
        Log.i(Constants.TAG, Session.class.getSimpleName() + " -setSharedPreferences-");
        Gson gson = new Gson();
        String jsonUser = gson.toJson(user);
        setSharedPreferences(USER_STORED_OBJECT, jsonUser);
        setSharedPreferences(USER_IS_LOGGED_IN, true);
    }

    void getUserFromSharedPreferences() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -getUserFromSharedPreferences-");
        Gson gson = new Gson();
        String jsonUser = getSharedPreferences(USER_STORED_OBJECT, null);
        user = gson.fromJson(jsonUser, User.class);
        Log.i(Constants.TAG, getClass().getSimpleName() + " -getUserFromSharedPreferences- User- " + user);
    }

    boolean userIsLoggedIn() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -userIsLoggedIn-");
        if(getSharedPreferences(USER_IS_LOGGED_IN, false)) {
            Log.i(Constants.TAG, getClass().getSimpleName() + " -userIsLoggedIn-user is logged in");
            return true;
        } else {
            Log.i(Constants.TAG, getClass().getSimpleName() + " -userIsLoggedIn-user is not logged in");
            return false;
        }
    }

    void logout(){
        Log.i(Constants.TAG, getClass().getSimpleName() + " -logout-");
        sharedPreferencesEditor.clear().commit();
        Intent mainActivityIntent = new Intent(applicationContext, MainActivity.class);
        applicationContext.startActivity(mainActivityIntent);
    }

    static int getUserID() {
        Log.i(Constants.TAG, Session.class.getSimpleName() + " -getUserID-");
        return user.getID();
    }

    static void setGeofenceAPI(Context context) {
        Log.i(Constants.TAG, Session.class.getSimpleName() + " -setGeofenceAPI-");
        geofenceAPI = new GeofenceAPI(context);
        geofenceAPI.geofenceInit();
    }

    static void removeGeofences() {
        Log.i(Constants.TAG, Session.class.getSimpleName() + " -removeGeofences-");
        if(GEOFENCE_PERMISSION_GRANTED) {
            geofenceAPI.removeGeofences();
        }
    }
}