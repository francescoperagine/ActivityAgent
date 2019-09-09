package it.teamgdm.sms.dibapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

public abstract class BaseActivity extends AppCompatActivity {

    Toolbar toolbar;
 //   String title = "title";

    protected void onCreate(Bundle savedInstanceState) {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -onCreate-");
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getActivityTitle());
      //  Objects.requireNonNull(getSupportActionBar()).setTitle(getActivityTitle());
    }

    abstract int getLayoutResource();

    abstract String getActivityTitle();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -onCreateOptionsMenu-");
        getMenuInflater().inflate(R.menu.app_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -onOptionsItemSelected-");
        int id = item.getItemId();
        switch (id) {
            case R.id.profileButton:
                Intent profileIntent = new Intent(this, ProfileActivity.class);
                startActivity(profileIntent);
                return true;
            case R.id.settingsButton:
                Intent settingIntent = new Intent(this, SettingsActivity.class);
                startActivity(settingIntent);
                return true;
            case R.id.logoutButton:
                FragmentManager fragmentManager = this.getSupportFragmentManager();
                LogoutDialogFragment logoutDialogFragment = new LogoutDialogFragment(this);
                logoutDialogFragment.show(fragmentManager, "logout_fragment");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    void showToolbar() {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -showToolbar-");
        toolbar.setVisibility(View.VISIBLE);
    }

    void hideToolbar() {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -hideToolbar-");
        toolbar.setVisibility(View.GONE);
    }


}
