package it.teamgdm.sms.dibapp;

import android.Manifest;
import android.app.AlertDialog;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.util.Objects;

/**
 * An activity representing a single Exam detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link ClassListActivity}.
 */
public class ClassDetailActivity extends BaseActivity implements BaseFragment.OnClickedItemListener{

    Bundle savedInstanceState;
    Bundle arguments = new Bundle();
    ClassDashboardFragment fragment = new ClassDashboardFragment();

    GeofenceBroadcastReceiver geofenceBroadcastReceiver = new GeofenceBroadcastReceiver();
    int geofenceReceiverLastAction = 0;
    GeofenceAPI geofenceAPI = new GeofenceAPI(this);

    protected void onCreate(Bundle savedInstanceState) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onCreate-");
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
        askGeofencePermissions();
        geofenceAPI.geofenceInit();
        // Sets the BroadcastReceiver to receive only the transition's updates from the GeofenceAPI pending intent
        IntentFilter intentFilter = new IntentFilter(Constants.GEOFENCE_TRANSITION_ACTION);
        registerReceiver(geofenceBroadcastReceiver, intentFilter);
    }

    protected void onStart() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onStart-");
        super.onStart();

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //

        actionHandler();

        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity using a fragment transaction.
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction().add(R.id.examDetailContainer, fragment).commit();
        }
    }

    void actionHandler() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -actionHandler-");
        switch (Objects.requireNonNull(getIntent().getAction())) {
            case Constants.CLASS_LIST_ACTION:
                // Sends the exam details to the fragment
                int examID = Objects.requireNonNull(getIntent()).getIntExtra(ClassDashboardFragment.ARG_ITEM_ID, 0);
                Exam exam = StudentCareer.getClassFromID(examID);
                arguments.putInt(ClassDashboardFragment.ARG_ITEM_ID, examID);
                arguments.putSerializable(String.valueOf(examID), exam);
                break;
            case Constants.GEOFENCE_RECEIVER_ACTION:
                int geofenceTransitionAction = getIntent().getIntExtra(Constants.GEOFENCE_RECEIVER_ACTION, 0);
                if(geofenceTransitionAction != geofenceReceiverLastAction) {
                    // Forwards the new geofence's transition code to the fragment
                    arguments.putInt(Constants.GEOFENCE_RECEIVER_ACTION, geofenceTransitionAction);
                    geofenceReceiverLastAction = geofenceTransitionAction;
                }
                break;
            default:
                break;
        }
    }

    void askGeofencePermissions() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -askGeofencePermissions-");
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED) {
            Log.i(Constants.TAG, getClass().getSimpleName() + " -askGeofencePermissions-PERMISSION DENIED");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Constants.GEOFENCE_PERMISSION_REQUEST_CODE);
        } else {
            Log.i(Constants.TAG, getClass().getSimpleName() + " -askGeofencePermissions-PERMISSION GRANTED");
            Session.geofencePermissionGranted = true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onRequestPermissionsResult-");
        // If request is cancelled, the result arrays are empty.
        if(requestCode == Constants.GEOFENCE_PERMISSION_REQUEST_CODE) {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.i(Constants.TAG, getClass().getSimpleName() + " -onRequestPermissionsResult-PERMISSION GRANTED");
                // permission was granted, yay!
                Session.geofencePermissionGranted = true;
            } else {
                Log.i(Constants.TAG, getClass().getSimpleName() + " -onRequestPermissionsResult-PERMISSION DENIED");
                Session.geofencePermissionGranted = false;
                // permission denied, boo!
                new AlertDialog.Builder(this)
                        .setTitle(R.string.geofence_insufficient_permissions_title)
                        .setMessage(R.string.geofence_insufficient_permissions_message)
                        .create()
                        .show();
            }
        }
    }

    @Override
    public void onAttachFragment(@NonNull Fragment fragment) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onAttachFragment-");
        if(fragment instanceof BaseFragment) {
            BaseFragment baseFragment = (BaseFragment) fragment;
            baseFragment.setOnClickedItemListener(this);
        }
    }

    int getLayoutResource() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -getLayoutResource-");
        return R.layout.activity_class_detail;
    }

    public void onItemSelected(int selectedActionResource) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onItemSelected-action " + selectedActionResource);
        Fragment fragment = null;
        switch (selectedActionResource) {
            case R.id.partecipate:
                setClassAttendance();
                fragment = new ClassDashboardFragment();
                break;
            case R.id.evaluate:
                //          fragment = new ClassEvaluateFragment();
                break;
            case R.id.history:
                //          fragment = new ClassHistoryFragment();
                break;
            case R.id.information:
                //          fragment = new ClassInformationFragment();
                break;
            default:
                break;
        }
        assert fragment != null;
        fragment.setArguments(arguments);
    }

    // Updates the class attendance flag
    private void setClassAttendance() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -setClassAttendance-");
        if(Session.getSharedPreference(Constants.KEY_CLASS_PARTECIPATION, false)) {
            Log.i(Constants.TAG, getClass().getSimpleName() + " -setClassAttendance-FALSE");
            arguments.putBoolean(Constants.KEY_CLASS_PARTECIPATION, false);
        } else {
            Log.i(Constants.TAG, getClass().getSimpleName() + " -setClassAttendance-TRUE");
            arguments.putBoolean(Constants.KEY_CLASS_PARTECIPATION, true);
        }
    }

    @Override
    protected void onResume() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onResume-");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onPause-");
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onDestroy-");
        super.onDestroy();
        geofenceAPI.removeGeofences();
        unregisterReceiver(geofenceBroadcastReceiver);
    }
}
