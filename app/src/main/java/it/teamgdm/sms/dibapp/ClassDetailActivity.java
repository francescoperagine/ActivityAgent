package it.teamgdm.sms.dibapp;

import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

/**
 * An activity representing a single Exam detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link ClassListActivity}.
 */
public class ClassDetailActivity extends BaseActivity implements
        StudentDashboardButtonFragment.StudentDashboardButtonFragmentInterface,
        StudentEvaluateFragment.StudentEvaluateFragmentInterface,
        StudentQuestionFragment.StudentQuestionFragmentInterface,
        GeofenceBroadcastReceiver.GeofenceBroadcastReceiverInterface{

    Bundle savedInstanceState;
    boolean lessonInProgress;
    boolean isUserAttendingLesson;
    ClassLesson classLesson;
    private GeofenceBroadcastReceiver geofenceBroadcastReceiver;
    private GeofenceAPI geofenceAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onCreate-");
        super.onCreate(savedInstanceState);
        geofenceBroadcastReceiver = new GeofenceBroadcastReceiver(this);
        IntentFilter intentFilter = new IntentFilter(Constants.GEOFENCE_TRANSITION_ACTION);
        registerReceiver(geofenceBroadcastReceiver, intentFilter);
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
            if(getIntent().getAction() != null && getIntent().getAction().equals(Constants.KEY_CLASS_LESSON_DETAIL_ACTION)) {
                classLesson = (ClassLesson) getIntent().getSerializableExtra(Constants.KEY_CLASS_LESSON);
                lessonInProgress = getIntent().getBooleanExtra(Constants.LESSON_IN_PROGRESS, false);
                isUserAttendingLesson = getIntent().getBooleanExtra(Constants.IS_USER_ATTENDING_LESSON, false);
                StudentDashboardDetailFragment dashboardDetailFragment = StudentDashboardDetailFragment.newInstance(classLesson, false);
                Fragment fragment;
                if (! lessonInProgress) {
                    Log.i(Constants.TAG, getClass().getSimpleName() + " -onStart-Lesson not in progress.");
                    fragment = StudentLessonButtonNoLessonFragment.newInstance();
                } else if(GeofenceAPI.hasGeofencePermissions) {
                    Log.i(Constants.TAG, getClass().getSimpleName() + " -onStart-Has geofence permission.");
                    fragment = StudentDashboardButtonFragment.newInstance(classLesson.lessonID, isUserAttendingLesson);
                } else {
                    Log.i(Constants.TAG, getClass().getSimpleName() + " -onStart-No geofence permission.");
                    fragment = StudentLessonButtonNoGeofencePermissionFragment.newInstance();
                }
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.dashboardDetailContainer, dashboardDetailFragment)
                        .add(R.id.dashboardButtonContainer, fragment).commit();
            }
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
        unregisterReceiver(geofenceBroadcastReceiver);
    }

    @Override
    protected void onDestroy() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onDestroy-");
        super.onDestroy();
        geofenceAPI.removeGeofences();
    }

    int getLayoutResource() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -getLayoutResource-");
        return R.layout.activity_class_detail;
    }

    @Override
    public void onAttachFragment(@NonNull Fragment fragment) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onAttachFragment-");
        if(fragment instanceof StudentDashboardButtonFragment) {
            ((StudentDashboardButtonFragment) fragment).setStudentDashboardButtonFragmentInterfaceCallback(this);
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
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onGeofenceTransitionAction-"+ geofenceReceiverAction);
        if(GeofenceAPI.hasGeofencePermissions && lessonInProgress) {
            StudentDashboardButtonFragment dashboardButtonFragment = StudentDashboardButtonFragment.newInstance(classLesson.lessonID, isUserAttendingLesson);
            getSupportFragmentManager().beginTransaction().replace(R.id.dashboardButtonContainer, dashboardButtonFragment).commit();
        }
    }

    @Override
    public void onItemSelected(int selectedActionResource) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onItemSelected-action " + selectedActionResource);
        switch (selectedActionResource) {
            case R.id.evaluateButton:
                Log.i(Constants.TAG, getClass().getSimpleName() + " -evaluateButton-");
                StudentEvaluateFragment evaluateFragment = StudentEvaluateFragment.newInstante(classLesson.lessonID);
                evaluateFragment.show(getSupportFragmentManager(), "Send a review");
                break;
            case R.id.questionButton:
                Log.i(Constants.TAG, getClass().getSimpleName() + " -questionButton-");
                StudentQuestionFragment questionFragment = StudentQuestionFragment.newInstante(classLesson.lessonID);
                questionFragment.show(getSupportFragmentManager(), "Ask a question - lessonID" + classLesson.lessonID);
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
            Log.i(Constants.TAG, DAO.class.getSimpleName() + " -sendQuestion-question sent-");
        } else {
            Toast.makeText(this, getString(R.string.question_not_sent), Toast.LENGTH_SHORT).show();
            Log.i(Constants.TAG, DAO.class.getSimpleName() + " -sendQuestion-question not sent-");
        }
    }

    @Override
    public void setAttendance(int lessonID, boolean isUserAttendingLesson) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -setAttendance-");
        if(DAO.setAttendance(lessonID, isUserAttendingLesson)) {
            Toast.makeText(this, getString(R.string.attendance_set), Toast.LENGTH_SHORT).show();
            StudentDashboardButtonFragment dashboardButtonFragment = StudentDashboardButtonFragment.newInstance(classLesson.lessonID, isUserAttendingLesson);
            getSupportFragmentManager().beginTransaction().replace(R.id.dashboardButtonContainer, dashboardButtonFragment).commit();
            Log.i(Constants.TAG, DAO.class.getSimpleName() + " -updateClassAttendance-classAttendance set to " + isUserAttendingLesson);
        } else {
            Toast.makeText(this, getString(R.string.attendance_not_set), Toast.LENGTH_SHORT).show();
            Log.i(Constants.TAG, DAO.class.getSimpleName() + " -updateClassAttendance-classAttendance not set-");
        }
    }

    @Override
    public void setReview(int lessonID, String summary, String review, int rating) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -setReview-");
        if(DAO.setReview(lessonID,summary,review,rating))  {
            Toast.makeText(this, getString(R.string.review_sent), Toast.LENGTH_SHORT).show();
            Log.i(Constants.TAG, DAO.class.getSimpleName() + " -sendQuestion-question sent-");
        } else {
            Toast.makeText(this, getString(R.string.review_not_sent), Toast.LENGTH_SHORT).show();
            Log.i(Constants.TAG, DAO.class.getSimpleName() + " -sendQuestion-question not sent-");
        }
    }
}