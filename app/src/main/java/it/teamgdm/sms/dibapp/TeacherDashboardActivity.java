package it.teamgdm.sms.dibapp;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

public class TeacherDashboardActivity extends AppCompatActivity {

    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -onCreate-");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_activity_dashboard);
    }

    @Override
    protected void onStart() {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -onStart-");
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -onCreateOptionsMenu-" + menu.toString());
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.teacher_dashboard_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -onOptionsItemSelected-" +item.toString());
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.logoutButton:
                FragmentManager fragmentManager = this.getSupportFragmentManager();
                LogoutDialogFragment logoutDialogFragment = new LogoutDialogFragment(this);
                logoutDialogFragment.show(fragmentManager, "logout_fragment");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -onBackPressed-");
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        doubleBackToExitPressedOnce = true;
        Toast.makeText(this, R.string.logout_double_back_confirm, Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.i(Settings.TAG, getClass().getSimpleName() + " -onBackPressed-run-");
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}
