package it.teamgdm.sms.dibapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Objects;

/**
 * An activity representing a single Exam detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link ClassListActivity}.
 */
public class ClassDetailActivity extends GeofenceActivity implements BaseFragment.OnClickedItemListener {

    ClassLesson classLesson;
    boolean lessonAttendance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        arguments  = new Bundle();
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
            Log.i(Constants.TAG, getClass().getSimpleName() + " -onStart-");
            // Create the detail fragment and add it to the activity using a fragment transaction.
            if(getIntent().getAction() != null && getIntent().getAction().equals(Constants.KEY_CLASS_LESSON_DETAIL_ACTION)) {
                intentHandler();
                startFragment();
            }
        }
    }

    void intentHandler() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -intentHandler-");
        if(Objects.equals(getIntent().getAction(), Constants.KEY_CLASS_LESSON_DETAIL_ACTION) & getIntent().hasExtra(Constants.KEY_CLASS_LESSON)) {
            classLesson = (ClassLesson) getIntent().getSerializableExtra(Constants.KEY_CLASS_LESSON);
            assert classLesson != null;
            lessonAttendance = userIsAttendingLesson(classLesson.lessonID);
            arguments.putSerializable(Constants.KEY_CLASS_LESSON, classLesson);
            arguments.putBoolean(Constants.KEY_ATTENDANCE, lessonAttendance);
        }
    }

    @Override
    public void onGeofenceTransitionAction(int geofenceTransitionAction) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onGeofenceTransitionAction-"+ geofenceTransitionAction);
        if(geofenceTransitionAction != GeofenceActivity.geofenceReceiverLastAction) {
            // Forwards the new geofence's transition code to the fragment
            arguments.putInt(Constants.GEOFENCE_TRANSITION_ACTION, geofenceTransitionAction);
            GeofenceActivity.geofenceReceiverLastAction = geofenceTransitionAction;
            startFragment();
        }
    }

    void startFragment() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -startFragment-");
        ClassDashboardFragment fragment = new ClassDashboardFragment();
        fragment.setArguments(arguments);
        getSupportFragmentManager().beginTransaction().replace(R.id.classDetailContainer, fragment).commit();
    }

    int getLayoutResource() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -getLayoutResource-");
        return R.layout.activity_class_detail;
    }

    public void onItemSelected(int selectedActionResource) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onItemSelected-action " + selectedActionResource);

        switch (selectedActionResource) {
            case R.id.evaluateButton:
                Log.i(Constants.TAG, getClass().getSimpleName() + " -evaluateButton-");
                EvaluateFragment evaluateFragment = new EvaluateFragment();
                arguments.putInt(Constants.KEY_CLASS_LESSON_ID, classLesson.lessonID);
                evaluateFragment.setArguments(arguments);
                evaluateFragment.show(getSupportFragmentManager(), "Send a review");
                break;
            case R.id.questionButton:
                Log.i(Constants.TAG, getClass().getSimpleName() + " -questionButton-");
                QuestionFragment questionFragment = new QuestionFragment();
                arguments.putInt(Constants.KEY_CLASS_LESSON_ID, classLesson.lessonID);
                questionFragment.setArguments(arguments);
                questionFragment.show(getSupportFragmentManager(), "Ask a question - lessonID" + classLesson.lessonID);
                break;
            default:
                break;
        }
    }

    @Override
    public void sendQuestion(int lessonID, String input) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -sendQuestion- lessonID " + lessonID);
        HashMap<String, String> params = new HashMap<>();
        params.put(Constants.KEY_ACTION, Constants.USER_QUESTION_ASK);
        params.put(Constants.KEY_CLASS_LESSON_ID, String.valueOf(lessonID));
        params.put(Constants.KEY_USER_ID, String.valueOf(Session.getUserID()));
        params.put(Constants.KEY_QUESTION, input);
        if(isDataSent(params, Constants.QUESTION_SENT_CODE)) {
            Toast.makeText(this, getString(R.string.question_sent), Toast.LENGTH_SHORT).show();
            Log.i(Constants.TAG, getClass().getSimpleName() + " -sendQuestion-question sent-");
        } else {
            Toast.makeText(this, getString(R.string.question_not_sent), Toast.LENGTH_SHORT).show();
            Log.i(Constants.TAG, getClass().getSimpleName() + " -sendQuestion-question not sent-");
        }
    }

    @Override
    public void setAttendance(int lessonID, boolean attendance) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -updateClassAttendance- set to " + attendance);
        // Registers the lesson's attendance into the DB
        HashMap<String, String> params = new HashMap<>();
        params.put(Constants.KEY_ACTION, Constants.KEY_SET_ATTENDANCE);
        params.put(Constants.KEY_CLASS_LESSON_ID, String.valueOf(lessonID));
        params.put(Constants.KEY_USER_ID, String.valueOf(Session.getUserID()));
        params.put(Constants.KEY_ATTENDANCE, String.valueOf(attendance));
        if(isDataSent(params, Constants.ATTENDANCE_SET_CODE)) {
            Toast.makeText(this, getString(R.string.attendance_set), Toast.LENGTH_SHORT).show();
            Log.i(Constants.TAG, getClass().getSimpleName() + " -updateClassAttendance-attendance set-");
        } else {
            Toast.makeText(this, getString(R.string.attendance_not_set), Toast.LENGTH_SHORT).show();
            Log.i(Constants.TAG, getClass().getSimpleName() + " -updateClassAttendance-attendance not set-");
        }
    }

    @Override
    public void setReview(int lessonID, String summary, String review, int rating) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -sendQuestion- lessonID " + lessonID);
        HashMap<String, String> params = new HashMap<>();
        params.put(Constants.KEY_ACTION, Constants.USER_SEND_REVIEW);
        params.put(Constants.KEY_CLASS_LESSON_ID, String.valueOf(lessonID));
        params.put(Constants.KEY_USER_ID, String.valueOf(Session.getUserID()));
        params.put(Constants.KEY_REVIEW_RATING, String.valueOf(rating));
        params.put(Constants.KEY_REVIEW_SUMMARY, summary);
        params.put(Constants.KEY_REVIEW_TEXT, review);
        if(isDataSent(params, Constants.OK_CODE)) {
            Toast.makeText(this, getString(R.string.review_sent), Toast.LENGTH_SHORT).show();
            Log.i(Constants.TAG, getClass().getSimpleName() + " -sendQuestion-question sent-");
        } else {
            Toast.makeText(this, getString(R.string.review_not_sent), Toast.LENGTH_SHORT).show();
            Log.i(Constants.TAG, getClass().getSimpleName() + " -sendQuestion-question not sent-");
        }
    }

    private boolean userIsAttendingLesson(int lessonID) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -userIsAttendingLesson-");
        HashMap<String, String> params = new HashMap<>();
        params.put(Constants.KEY_ACTION, Constants.KEY_IS_USER_ATTENDING_LESSON);
        params.put(Constants.KEY_CLASS_LESSON_ID, String.valueOf(lessonID));
        params.put(Constants.KEY_USER_ID, String.valueOf(Session.getUserID()));
        int response = 0;
        try {
            response = getFromDB(params).getJSONObject(0).getInt(Constants.KEY_ATTENDANCE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return response == 1;
    }


}