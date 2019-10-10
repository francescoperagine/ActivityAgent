package it.teamgdm.sms.dibapp;

import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.Geofence;

/**
 * An activity representing a single Exam detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link StudentLessonListActivity}.
 */
public class StudentLessonDetailActivity extends BaseActivity implements
        StudentLessonBottomFragment.StudentDashboardButtonFragmentInterface,
        StudentEvaluateFragment.StudentEvaluateFragmentInterface,
        StudentQuestionFragment.StudentQuestionFragmentInterface,
        DibappBroadcastReceiver.GeofenceReceiverInterface {

    Bundle savedInstanceState;
    private boolean lessonInProgress;
    private boolean isUserAttendingLesson;
    DibappBroadcastReceiver dibappBroadcastReceiver;
    private Lesson lesson;
    private GeofenceAPI geofenceAPI;
    IntentFilter intentFilter = new IntentFilter(Constants.GEOFENCE_TRANSITION_ACTION);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onCreate-");
        super.onCreate(savedInstanceState);
        dibappBroadcastReceiver = new DibappBroadcastReceiver(this);
        registerReceiver(dibappBroadcastReceiver, intentFilter);
        geofenceAPI = new GeofenceAPI(this);
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

        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity using a fragment transaction.
            lesson = (Lesson) getIntent().getSerializableExtra(Constants.KEY_CLASS_LESSON);
            lessonInProgress = getIntent().getBooleanExtra(Constants.LESSON_IN_PROGRESS, false);
            isUserAttendingLesson = getIntent().getBooleanExtra(Constants.IS_USER_ATTENDING_LESSON, false);
            StudentLessonDetailFragment dashboardDetailFragment = StudentLessonDetailFragment.newInstance(lesson, false);
            Fragment bottomFragment;

            if(GeofenceAPI.hasGeofencePermissions && lessonInProgress) {
                Log.i(Constants.TAG, getClass().getSimpleName() + " -onStart-Has geofence permission.");
                bottomFragment = getBottomFragment(0);
            } else if (! lessonInProgress) {
                Log.i(Constants.TAG, getClass().getSimpleName() + " -onStart-Lesson not in progress.");
                bottomFragment = StudentLessonBottomNoLessonFragment.newInstance();
            } else {
                Log.i(Constants.TAG, getClass().getSimpleName() + " -onStart-No geofence permission.");
                bottomFragment = StudentLessonBottomNoGeofencePermissionFragment.newInstance();
            }
            getSupportFragmentManager().beginTransaction()
                .add(R.id.dashboardDetailContainer, dashboardDetailFragment)
                .add(R.id.dashboardButtonContainer, bottomFragment).commit();
        }
    }

    @Override
    public void onResume() {
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
    public void onPause() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onPause-");
        super.onPause();
        if(GeofenceAPI.hasGeofencePermissions){
            unregisterReceiver(dibappBroadcastReceiver);
            stopLocationUpdates();
        }

    }

    @Override
    protected void onDestroy() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onDestroy-");
        super.onDestroy();
        geofenceAPI.removeGeofences();
    }

    private void stopLocationUpdates() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -stopLocationUpdates-");
        if(GeofenceAPI.hasGeofencePermissions) {
            geofenceAPI.stopLocationUpdates();
        }
    }
/*
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(Constants.KEY_GEOFENCE_LOCATION_UPDATE, GeofenceAPI.hasGeofencePermissions);
        super.onSaveInstanceState(outState);
    }
*/
    int getLayoutResource() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -getLayoutResource-");
        return R.layout.student_class_detail_activity;
    }

    @Override
    public void onAttachFragment(@NonNull Fragment fragment) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onAttachFragment-");
        if(fragment instanceof StudentLessonBottomFragment) {
            ((StudentLessonBottomFragment) fragment).setStudentDashboardButtonFragmentInterfaceCallback(this);
        }
        if(fragment instanceof StudentEvaluateFragment){
            ((StudentEvaluateFragment) fragment).setStudentEvaluateFragmentInterfaceCallback(this);
        }
        if(fragment instanceof StudentQuestionFragment) {
            ((StudentQuestionFragment) fragment).setStudentQuestionFragmentInterfaceCallback(this);
        }
    }

    @Override
    public void onGeofenceTransitionAction(int geofenceReceiverAction) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onGeofenceTransitionAction-"+ geofenceReceiverAction + " lesson in progress " + lessonInProgress );
        if(lessonInProgress) {
            Fragment bottomFragment = getBottomFragment(geofenceReceiverAction);
            getSupportFragmentManager().beginTransaction().replace(R.id.dashboardButtonContainer, bottomFragment).commit();
        }
    }

    Fragment getBottomFragment(int geofenceTransitionAction) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -getBottomFragment-"+ geofenceTransitionAction);
        Fragment bottomFragment;
        switch (geofenceTransitionAction) {
            case Geofence.GEOFENCE_TRANSITION_DWELL:
                Log.i(Constants.TAG, getClass().getSimpleName() + " " + getResources().getString(R.string.geofence_transition_dwelling));
                Toast.makeText(this, getResources().getString(R.string.geofence_transition_dwelling), Toast.LENGTH_SHORT).show();
                bottomFragment = StudentLessonBottomFragment.newInstance(lesson.lessonID, isUserAttendingLesson);
                break;
            case Geofence.GEOFENCE_TRANSITION_ENTER:
                Log.i(Constants.TAG, getClass().getSimpleName() + " " + getResources().getString(R.string.geofence_transition_enter));
                Toast.makeText(this, getResources().getString(R.string.geofence_transition_enter), Toast.LENGTH_SHORT).show();
                bottomFragment = StudentLessonBottomNotInGeofenceFragment.newInstance(isUserAttendingLesson);
                break;
            default:
                Log.i(Constants.TAG, getClass().getSimpleName() + " " + getResources().getString(R.string.geofence_transition_left));
                Toast.makeText(this, getResources().getString(R.string.geofence_transition_left), Toast.LENGTH_SHORT).show();
                bottomFragment = StudentLessonBottomNotInGeofenceFragment.newInstance(isUserAttendingLesson);
                break;
        }
        return bottomFragment;
    }

    @Override
    public void onItemSelected(int selectedActionResource) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onItemSelected-action " + selectedActionResource);
        switch (selectedActionResource) {
            case R.id.evaluateButton:
                Log.i(Constants.TAG, getClass().getSimpleName() + " -evaluateButton-");
                StudentEvaluateFragment evaluateFragment = StudentEvaluateFragment.newInstante(lesson.lessonID);
                evaluateFragment.show(getSupportFragmentManager(), "Send a review");
                break;
            case R.id.questionButton:
                Log.i(Constants.TAG, getClass().getSimpleName() + " -questionButton-");
                StudentQuestionFragment questionFragment = StudentQuestionFragment.newInstante(lesson.lessonID);
                questionFragment.show(getSupportFragmentManager(), "Ask a question - lessonID" + lesson.lessonID);
                break;
            default:
                break;
        }
    }

    @Override
    public void sendQuestion(int lessonID, String input) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -sendQuestion-");
        if(DAO.sendQuestion(lessonID, input)) {
            Toast.makeText(this, getString(R.string.question_sent), Toast.LENGTH_SHORT).show();
            Log.i(Constants.TAG, getClass().getSimpleName() + " -sendQuestion-question sent-");
        } else {
            Toast.makeText(this, getResources().getString(R.string.question_not_sent), Toast.LENGTH_SHORT).show();
            Log.i(Constants.TAG, getClass().getSimpleName() + " -sendQuestion-question not sent-");
        }
    }

    @Override
    public void setAttendance(int lessonID, boolean isUserAttendingLesson) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -setAttendance-");
        if(DAO.setAttendance(lessonID, isUserAttendingLesson)) {
            Toast.makeText(this, getString(R.string.attendance_set), Toast.LENGTH_SHORT).show();
            StudentLessonBottomFragment dashboardButtonFragment = StudentLessonBottomFragment.newInstance(lesson.lessonID, isUserAttendingLesson);
            getSupportFragmentManager().beginTransaction().replace(R.id.dashboardButtonContainer, dashboardButtonFragment).commit();
            Log.i(Constants.TAG, getClass().getSimpleName() + " -updateClassAttendance-classAttendance set to " + isUserAttendingLesson);
        } else {
            Toast.makeText(this, getString(R.string.attendance_not_set), Toast.LENGTH_SHORT).show();
            Log.i(Constants.TAG, getClass().getSimpleName() + " -updateClassAttendance-classAttendance not set-");
        }
    }

    @Override
    public void setReview(int lessonID, String summary, String review, int rating) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -setReview-");
        if(DAO.setReview(lessonID,summary,review,rating))  {
            Toast.makeText(this, getString(R.string.review_sent), Toast.LENGTH_SHORT).show();
            Log.i(Constants.TAG, getClass().getSimpleName() + " -setReview-question sent-");
        } else {
            Toast.makeText(this, getString(R.string.review_not_sent), Toast.LENGTH_SHORT).show();
            Log.i(Constants.TAG, getClass().getSimpleName() + " -setReview-question not sent-");
        }
    }

}