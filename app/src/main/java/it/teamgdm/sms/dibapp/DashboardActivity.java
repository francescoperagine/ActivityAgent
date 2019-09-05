package it.teamgdm.sms.dibapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DashboardActivity extends AppCompatActivity {

    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -onCreate-");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
    }

    @Override
    protected void onStart() {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -onStart-");
        super.onStart();
        ExecutorService executor = Executors.newSingleThreadExecutor();
        JSONObject data = new JSONObject();
        JSONArray studentCareerData = loadClassList();
        StudentCareer studentCareer = new StudentCareer();
        studentCareer.setExamList(studentCareerData);
        ArrayList<Exam> examsList = studentCareer.getExamList();
        RecyclerView recyclerView = findViewById(R.id.classListView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MyRecycleViewAdapter myAdapter = new MyRecycleViewAdapter(examsList);
        recyclerView.setAdapter(myAdapter);
    }

    private JSONArray loadClassList() {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -loadClassList-");
        JSONObject data = new JSONObject();
        JSONArray response = new JSONArray();
        try {
            data.put(Settings.KEY_ACTION, Settings.GET_EXAMS_LIST);
            data.put(Settings.KEY_USER_ID, Session.getUserID());
            Log.i(Settings.TAG, getClass().getSimpleName() + " -loadClassList-data "+data);
            AsyncTaskConnection asyncTaskConnection = new AsyncTaskConnection();
            asyncTaskConnection.execute(data);
            response = asyncTaskConnection.get();
            Log.i(Settings.TAG, getClass().getSimpleName() + " -loadClassList-response "+response);
        } catch (JSONException | ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -onCreateOptionsMenu-");
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.dashboard_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -onOptionsItemSelected-" +item.toString());
        // Handle item selection
        switch (item.getItemId()) {
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
