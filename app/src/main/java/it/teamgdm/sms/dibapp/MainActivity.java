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
        if(session.userIsLoggedIn()){
            loadDashboard();
        } else {
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivityForResult(loginIntent, Constants.LOGIN_OK_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onActivityResult-");
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Constants.LOGIN_OK_CODE && resultCode == RESULT_OK) {
            Log.i(Constants.TAG, getClass().getSimpleName() + " -onActivityResult-RESULT_OK");
            session.setAccess(this, data.getStringExtra(Constants.KEY_USER_EMAIL));
            loadDashboard();
        }
    }

    private void loadDashboard() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -loadDashboard-");
        Intent classListActivityIntent = new Intent(this, ClassListActivity.class);
        if(session.userIsProfessor()) {
            Log.i(Constants.TAG, getClass().getSimpleName() + " -loadDashboard-userIsProfessor");
            classListActivityIntent.putExtra(Constants.KEY_ROLE_PROFESSOR, true);
        }
        startActivity(classListActivityIntent);
        finish();
    }
}