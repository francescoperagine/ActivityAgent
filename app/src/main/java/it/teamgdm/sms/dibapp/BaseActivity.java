package it.teamgdm.sms.dibapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Objects;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public abstract class BaseActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView titleView;
    private boolean backButtonEnabled = true;
    private boolean toolbarEnabled = true;
    BroadcastReceiver receiver;
    IntentFilter intentFilter = new IntentFilter();

    protected void onCreate(Bundle savedInstanceState) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onCreate-");
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        titleView = findViewById(R.id.toolbarTitle);
        intentFilter.addAction(Constants.KEY_ACTION_LOGOUT);
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d("onReceive","Logout in progress");
                //At this point you should start the login activity and finish this one
                Intent mainActivityIntent = new Intent(context, MainActivity.class);
                mainActivityIntent.addFlags(FLAG_ACTIVITY_CLEAR_TOP);
                mainActivityIntent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(mainActivityIntent);
                finish();
            }
        };
        registerReceiver(receiver, intentFilter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(toolbarEnabled) {
            titleView.setText(Objects.requireNonNull(getSupportActionBar()).getTitle());
            titleView.setSelected(true);
            titleView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        }
        if(toolbarEnabled && backButtonEnabled) {
            if(getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    abstract int getLayoutResource();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onCreateOptionsMenu-");
        getMenuInflater().inflate(R.menu.app_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onOptionsItemSelected-");
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
                LogoutDialog logoutDialog = new LogoutDialog(this);
                logoutDialog.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        this.onBackPressed();
        return true;
    }

    protected void disableToolbar() { toolbarEnabled = false; }

    protected void disableBackButton() {
        backButtonEnabled = false;
    };
}
