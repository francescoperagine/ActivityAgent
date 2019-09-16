package it.teamgdm.sms.dibapp;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

/**
 * An activity representing a single Exam detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link ExamListActivity}.
 */
public class ExamDetailActivity extends BaseActivity implements BaseFragment.OnClickedItemListener{

    Bundle savedInstanceState;
    Exam exam;
    Bundle arguments = new Bundle();
    boolean userDwellsInGeofence;
    Fragment fragment;
    GeofenceAPI geofenceAPI;

    protected void onCreate(Bundle savedInstanceState) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onCreate-");
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
        askGeofencePermissions();
    }

    protected void onStart() {

        super.onStart();
        if(Session.GEOFENCE_PERMISSION_GRANTED) {
            geofenceAPI = new GeofenceAPI(this);
            geofenceAPI.geofenceInit();
        }

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //

        if(getIntent() != null) {
            userDwellsInGeofence = getIntent().getBooleanExtra(Constants.GEOFENCE_TRANSITION_DWELLS, false);
        }
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            int examID = getIntent().getIntExtra(ExamDashboardFragment.ARG_ITEM_ID, 0);
            Log.i(Constants.TAG, getClass().getSimpleName() + " -onCreate- examID " + examID);
            arguments.putInt(ExamDashboardFragment.ARG_ITEM_ID, examID);
            arguments.putBoolean(Constants.GEOFENCE_TRANSITION_DWELLS, userDwellsInGeofence);
            exam = StudentCareer.getExam(examID);
            arguments.putSerializable(String.valueOf(examID), exam);
            ExamDashboardFragment fragment = new ExamDashboardFragment();
            initFragment(fragment);
            Log.i(Constants.TAG, getClass().getSimpleName() + " -onCreate- Exam " + exam);
        }

    }

    private void initFragment(Fragment fragment) {
        fragment.setArguments(arguments);
        getSupportFragmentManager().beginTransaction().add(R.id.examDetailContainer, fragment).commit();
    }

    @Override
    public void onAttachFragment(@NonNull Fragment fragment) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onAttachFragment-");
        if(fragment instanceof BaseFragment) {
            BaseFragment baseFragment = (BaseFragment) fragment;
            baseFragment.setOnClickedItemListener(this);
        }
    }

    @Override
    int getLayoutResource() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -getLayoutResource-");
        return R.layout.activity_exam_detail;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onOptionsItemSelected-");
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            navigateUpTo(new Intent(this, ExamListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(int selectedActionResource) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onItemSelected-action " + selectedActionResource);
        switch (selectedActionResource) {
            case R.id.partecipate:

            case R.id.evaluate:
      //          fragment = new ExamEvaluateFragment();
       //         fragment.setArguments(arguments);
            case R.id.history:
      //          fragment = new ExamHistoryFragment();
      //          fragment.setArguments(arguments);
                break;
            case R.id.information:
                fragment = new ExamInformationFragment();
                fragment.setArguments(arguments);
                break;
            default:
                break;
        }
        initFragment(fragment);
    }

    protected void onDestroy() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onDestroy-");
        super.onDestroy();
        geofenceAPI.removeGeofences();
    }

    void askGeofencePermissions() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -askGeofencePermissions-");
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED) {
            Log.i(Constants.TAG, getClass().getSimpleName() + " -askGeofencePermissions-PERMISSION DENIED");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Constants.GEOFENCE_PERMISSION_REQUEST_CODE);
        } else {
            Log.i(Constants.TAG, getClass().getSimpleName() + " -askGeofencePermissions-PERMISSION GRANTED");
            Session.GEOFENCE_PERMISSION_GRANTED = true;
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
                Session.GEOFENCE_PERMISSION_GRANTED = true;
            } else {
                Log.i(Constants.TAG, getClass().getSimpleName() + " -onRequestPermissionsResult-PERMISSION DENIED");
                Session.GEOFENCE_PERMISSION_GRANTED = false;
                // permission denied, boo!
                new AlertDialog.Builder(this)
                        .setTitle(R.string.geofence_insufficient_permissions_title)
                        .setMessage(R.string.geofence_insufficient_permissions_message)
                        .create()
                        .show();
            }
        }
    }
}
