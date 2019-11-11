package it.teamgdm.sms.dibapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class Session {

    private static Context applicationContext;
    private static SharedPreferences.Editor sharedPreferencesEditor;
    private static SharedPreferences sharedPreferences;


    @SuppressLint("CommitPrefEdits")
    Session(Context context) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -Constructor-");
        applicationContext = context;
        sharedPreferences = applicationContext.getSharedPreferences(Constants.PREFERENCE_FILE_KEY, Context.MODE_PRIVATE);
        sharedPreferencesEditor = sharedPreferences.edit();
    }

    void setAccess(Context context, String email) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -setAccess-");
        sharedPreferencesEditor.putBoolean(Constants.USER_IS_LOGGED_IN, true);
        sharedPreferencesEditor.putString(Constants.KEY_USER_EMAIL, email);

        JSONArray response = DAO.getUserDetail(email);
        try {
            JSONObject userDetails = response.getJSONObject(0);
            setUserInSharedPreferences(userDetails);
        } catch (JSONException e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void setUserInSharedPreferences(@NonNull JSONObject userDetails) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -setUserInSharedPreferences-" + userDetails);
        try {
            sharedPreferencesEditor.putInt(Constants.KEY_USER_ID, userDetails.getInt(Constants.KEY_USER_ID));
            sharedPreferencesEditor.putString(Constants.KEY_USER_NAME, userDetails.getString(Constants.KEY_USER_NAME));
            sharedPreferencesEditor.putString(Constants.KEY_USER_SURNAME, userDetails.getString(Constants.KEY_USER_SURNAME));
            sharedPreferencesEditor.putString(Constants.KEY_USER_EMAIL, userDetails.getString(Constants.KEY_USER_EMAIL));
            sharedPreferencesEditor.putString(Constants.KEY_USER_ROLE_NAME, userDetails.getString(Constants.KEY_USER_ROLE_NAME));
            sharedPreferencesEditor.putString(Constants.KEY_USER_REGISTRATION_DATE, userDetails.getString(Constants.KEY_USER_REGISTRATION_DATE));
            sharedPreferencesEditor.commit();
        } catch (JSONException e) {
            Log.i(Constants.TAG, getClass().getSimpleName() + " -setUserInSharedPreferences-Exception");
            e.printStackTrace();
        }
    }

    boolean userIsLoggedIn() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -userIsLoggedIn-");
        if(sharedPreferences.getBoolean(Constants.USER_IS_LOGGED_IN, false)) {
            Log.i(Constants.TAG, getClass().getSimpleName() + " -userIsLoggedIn-user is logged in");
            return true;
        } else {
            Log.i(Constants.TAG, getClass().getSimpleName() + " -userIsLoggedIn-user is not logged in");
            return false;
        }
    }

    static void logout(){
        Log.i(Constants.TAG, Session.class.getSimpleName() + " -logout-");
        sharedPreferencesEditor.clear().commit();
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(Constants.KEY_ACTION_LOGOUT);
        applicationContext.sendBroadcast(broadcastIntent);
    }

    static int getUserID() {
        Log.i(Constants.TAG, Session.class.getSimpleName() + " -getUserID-");
        return sharedPreferences.getInt(Constants.KEY_USER_ID,0);
    }

    static String getUserEmail() {
        Log.i(Constants.TAG, Session.class.getSimpleName() + " -getUserEmail-");
        return sharedPreferences.getString(Constants.KEY_USER_EMAIL, Constants.KEY_EMPTY);
    }

    boolean userIsProfessor() {
        Log.i(Constants.TAG, Session.class.getSimpleName() + " -userIsProfessor?-");
        return Constants.KEY_ROLE_PROFESSOR.equals(sharedPreferences.getString(Constants.KEY_USER_ROLE_NAME, null));
    }

    static String getUserName() {
        Log.i(Constants.TAG, Session.class.getSimpleName() + " -getUserName-");
        return sharedPreferences.getString(Constants.KEY_USER_NAME, Constants.KEY_EMPTY);
    }

    static String getUserSurname(){
        Log.i(Constants.TAG, Session.class.getSimpleName() + " -getUserSurname-");
        return sharedPreferences.getString(Constants.KEY_USER_SURNAME, Constants.KEY_EMPTY);
    }

    static String getUserRole(){
        Log.i(Constants.TAG, Session.class.getSimpleName() + " -getUserRole-");
        return sharedPreferences.getString(Constants.KEY_USER_ROLE_NAME, Constants.KEY_EMPTY);



    }
}