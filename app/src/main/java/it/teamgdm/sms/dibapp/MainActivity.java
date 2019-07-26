package it.teamgdm.sms.dibapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "dibApp.MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, getClass().getSimpleName() + " -onCreate- The activity is getting created.");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    protected void onStart() {
        Log.i(TAG, getClass().getSimpleName() + " -onStart- The activity is getting started.");
        super.onStart();

        //TODO: Splash screen
        Intent loginIntent = new Intent(this, LoginActivity.class);
        startActivity(loginIntent);
    }

    @Override
    protected void onResume() {
        Log.i(TAG, getClass().getSimpleName() + " -onResume- The activity is being resumed.");
        super.onResume();
    }
    @Override
    protected void onPause() {
        Log.i(TAG, getClass().getSimpleName() + " -onPause- The activity is being paused.");
        super.onPause();
    }
    @Override
    protected void onStop() {
        Log.i(TAG, getClass().getSimpleName() + " -onStop- The activity is being stopped.");
        super.onStop();
    }
    @Override
    protected void onDestroy() {
        Log.i(TAG, getClass().getSimpleName() + " -onDestroy- The activity is being destroyed.");
        super.onDestroy();
    }
}
