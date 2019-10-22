package it.teamgdm.sms.dibapp;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class StudentLessonListActivity extends BaseActivity implements
            DibappBroadcastReceiver.GeofenceReceiverInterface {

    static int geofenceTransitionAction = 0;
    private DibappBroadcastReceiver dibappBroadcastReceiver;
    private GeofenceAPI geofenceAPI;
    private IntentFilter intentFilter = new IntentFilter(Constants.GEOFENCE_TRANSITION_ACTION);

    private boolean mTwoPane;
    Intent loginIntent;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    TextView textViewEmptyClassList;

    protected void onCreate(Bundle savedInstanceState) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onCreate-");
        super.onCreate(savedInstanceState);
        loginIntent = getIntent();

        dibappBroadcastReceiver = new DibappBroadcastReceiver(this);
        geofenceAPI = new GeofenceAPI(this);

        if (findViewById(R.id.class_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        recyclerView = findViewById(R.id.class_list);
        textViewEmptyClassList = findViewById(R.id.lesson_list_empty);
        String today = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.getDefault()).format(new Date());
        getSupportActionBar().setTitle(getString(R.string.lesson_today_schedule) + Constants.KEY_BLANK + today);
        disableBackButton();

        setupRecyclerView();
    }

    @Override
    protected void onResume() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onResume-");
        super.onResume();

        if(GeofenceAPI.hasGeofencePermissions){
            registerReceiver(dibappBroadcastReceiver, intentFilter);
            startLocationUpdates();
        }
    }

    private void startLocationUpdates() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -startLocationUpdates-");
        if(GeofenceAPI.hasGeofencePermissions) {
            geofenceAPI.startLocationUpdates();
        }
    }

    @Override
    protected void onPause() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onPause-");
        super.onPause();
        if(GeofenceAPI.hasGeofencePermissions){
            unregisterReceiver(dibappBroadcastReceiver);
            stopLocationUpdates();
        }
    }

    private void stopLocationUpdates() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -stopLocationUpdates-");
        if(GeofenceAPI.hasGeofencePermissions) {
            geofenceAPI.stopLocationUpdates();
        }
    }

    @Override
    protected void onDestroy() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onDestroy-");
        super.onDestroy();
        geofenceAPI.removeGeofences();
    }

    @Override
    protected int getLayoutResource() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -getLayoutResource-");
        return R.layout.item_list_activity;
    }

    private void setupRecyclerView() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -setupRecyclerView-");
        LessonList lessonListData = new StudentCareer();
        JSONArray lessonListLoader = DAO.getLessonList(Session.getUserID(), Constants.KEY_ROLE_STUDENT);
        lessonListData.setLessonList(lessonListLoader);
        ArrayList<Lesson> classList = lessonListData.getLessonList();
        if(classList.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            textViewEmptyClassList.setVisibility(View.VISIBLE);
        } else {
            Log.i(Constants.TAG, getClass().getSimpleName() + " -setupRecyclerView-");
            recyclerView.setVisibility(View.VISIBLE);
            adapter = new LessonRecyclerViewAdapter(this, classList, mTwoPane);
            recyclerView.setAdapter(adapter);
            textViewEmptyClassList.setVisibility(View.GONE);
        }
    }

    @Override
    public void onGeofenceTransitionAction(int geofenceReceiverAction) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onGeofenceTransitionAction-");
        geofenceTransitionAction = geofenceReceiverAction;
        adapter.notifyDataSetChanged();
    }
}
