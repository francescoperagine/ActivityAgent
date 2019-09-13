package it.teamgdm.sms.dibapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    private static int firstLaunch = 0; //this flags checks if it's the first time that MainActivity is called

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onCreate- The activity is getting created.");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    protected void onStart() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onStart- The activity is getting started.");
        super.onStart();
        // Calls LoginActivity if it's the first time MainActivity is called else closes the app
        if(firstLaunch == 0){
            Intent loginIntent = new Intent(this, LoginActivity.class);
            firstLaunch++;
            startActivity(loginIntent);
        } else {
            firstLaunch = 0;
            finishAffinity();
        }
    }

    @Override
    protected void onResume() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onResume- The activity is being resumed.");
        super.onResume();
        //Closes the app if MainActivity is not called for the first time
        if(firstLaunch != 0) {
            firstLaunch = 0;
            finishAffinity();
        }
    }
    @Override
    protected void onPause() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onPause- The activity is being paused.");
        super.onPause();
    }
    @Override
    protected void onStop() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onStop- The activity is being stopped.");
        super.onStop();
    }
    @Override
    protected void onDestroy() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onDestroy- The activity is being destroyed.");
        super.onDestroy();
    }
}
