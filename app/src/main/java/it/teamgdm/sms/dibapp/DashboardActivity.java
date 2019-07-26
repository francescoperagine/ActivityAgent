package it.teamgdm.sms.dibapp;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {

    private final String TAG = "dibApp.LoginActivity";

    private SessionHandler session;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, getClass().getSimpleName() + " -onCreate-");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        session = new SessionHandler(getApplicationContext());
    }

    @Override
    protected void onStart() {
        Log.i(TAG, getClass().getSimpleName() + " -onStart-");
        super.onStart();
    }

    public View.OnClickListener buttonLogoutListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Log.i(TAG, getClass().getSimpleName() + " -buttonLogoutListener.OnClickListener-");
            session.logoutUser();
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i(TAG, getClass().getSimpleName() + " -onCreateOptionsMenu-" + menu.toString());
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.dashboard_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i(TAG, getClass().getSimpleName() + " -onOptionsItemSelected-" +item.toString());
        // Handle item selection
        switch (item.getItemId()) {
            //TODO: set up the action bar items
            case R.id.logoutButton:
                //TODO: ask for exit confirmation
                LogoutDialogFragment logout = new LogoutDialogFragment(this.getApplicationContext());
                session.logoutUser();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        Log.i(TAG, getClass().getSimpleName() + " -onBackPressed-");
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        doubleBackToExitPressedOnce = true;
        Toast.makeText(this, R.string.logout_double_back_confirm, Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, getClass().getSimpleName() + " -onBackPressed-run-");
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}
