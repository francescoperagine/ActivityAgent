package it.teamgdm.sms.dibapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onCreate- The activity is getting created.");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    protected void onStart() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onStart- The activity is getting started.");
        super.onStart();

        session = new Session(getApplicationContext());
        if(session.userIsLoggedIn() && ! Session.getUserEmail().equals(Constants.KEY_EMPTY)) {
            session.setAccess(this, Session.getUserEmail());
            loadDashboard();
        } else if(getIntent().hasExtra(Constants.USER_LOGIN) & getIntent().getIntExtra(Constants.USER_LOGIN,0) == Constants.LOGIN_OK_CODE){
            String email = getIntent().getStringExtra(Constants.KEY_USER_EMAIL);
            session.setAccess(this, email);
            loadDashboard();
        } else {
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(loginIntent);
        }
    }

    private void loadDashboard() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -loadDashboard-");
        Intent listActivityIntent = null;
        if(session.userIsProfessor()) {
            Log.i(Constants.TAG, getClass().getSimpleName() + " -loadDashboard-userIsProfessor");
            listActivityIntent = new Intent(this, ProfessorClassListActivity.class);
        } else {
            Log.i(Constants.TAG, getClass().getSimpleName() + " -loadDashboard-userIsStudent");
            listActivityIntent = new Intent(this, StudentLessonListActivity.class);
        }
        startActivity(listActivityIntent);
        finish();
    }
}