package it.teamgdm.sms.dibapp;

import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.location.Geofence;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * An activity representing a single Exam detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link StudentLessonListActivity}.
 */
public class StudentLessonDetailActivity extends BaseActivity implements
        StudentEvaluateFragment.StudentEvaluateFragmentInterface,
        StudentQuestionFragment.StudentQuestionFragmentInterface,
        DibappBroadcastReceiver.GeofenceReceiverInterface {

    Bundle savedInstanceState;
    DibappBroadcastReceiver dibappBroadcastReceiver;
    private Lesson lesson;
    private GeofenceAPI geofenceAPI;
    IntentFilter intentFilter = new IntentFilter(Constants.GEOFENCE_TRANSITION_ACTION);

    ToggleButton buttonPartecipate;
    Button buttonEvaluate, buttonQuestion;
    TextView buttonMenuAlternateView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onCreate-");
        super.onCreate(savedInstanceState);
        dibappBroadcastReceiver = new DibappBroadcastReceiver(this);
        geofenceAPI = new GeofenceAPI(this);

        buttonPartecipate = findViewById(R.id.partecipateButton);
        buttonEvaluate = findViewById(R.id.evaluateButton);
        buttonQuestion = findViewById(R.id.questionButton);

        buttonMenuAlternateView = findViewById(R.id.bottomMenuAlternateView);
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

        buttonPartecipate.setOnClickListener(partecipateButtonListener);
        buttonEvaluate.setOnClickListener(evaluateButtonListener);
        buttonQuestion.setOnClickListener(questionButtonListener);

        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity using a fragment transaction.
            lesson = (Lesson) getIntent().getSerializableExtra(Constants.KEY_CLASS_LESSON);

            if(GeofenceAPI.hasGeofencePermissions && lesson.isInProgress()) {
                Log.i(Constants.TAG, getClass().getSimpleName() + " -onStart-Has geofence permission.");
                bottomPanelHandler(true, "");
            } else if (! lesson.isInProgress()) {
                Log.i(Constants.TAG, getClass().getSimpleName() + " -onStart-Lesson not in progress.");
                bottomPanelHandler(false, getResources().getString(R.string.lesson_not_in_progress_text));
            } else {
                Log.i(Constants.TAG, getClass().getSimpleName() + " -onStart-No geofence permission.");
                bottomPanelHandler(false, getResources().getString(R.string.no_geofence_permission_text));
            }
        }
    }

    int getLayoutResource() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -getLayoutResource-");
        return R.layout.student_lesson_detail_activity;
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

    private void startLocationUpdates() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -startLocationUpdates-");
        if(GeofenceAPI.hasGeofencePermissions) {
            geofenceAPI.startLocationUpdates();
        }
    }

    private void stopLocationUpdates() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -stopLocationUpdates-");
        if(GeofenceAPI.hasGeofencePermissions) {
            geofenceAPI.stopLocationUpdates();
        }
    }

    @Override
    public void onGeofenceTransitionAction(int geofenceReceiverAction) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onGeofenceTransitionAction-"+ geofenceReceiverAction + " lesson in progress " + lesson.isInProgress() );
        if(lesson.isInProgress()) {
            setGeofenceResponse(geofenceReceiverAction);
        }
    }

    void setGeofenceResponse(int geofenceTransitionAction) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -getBottomFragment-"+ geofenceTransitionAction);
        if (geofenceTransitionAction == Geofence.GEOFENCE_TRANSITION_DWELL) {
            String message = getResources().getString(R.string.geofence_transition_dwelling);
            Log.i(Constants.TAG, getClass().getSimpleName() + " " + message);
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            bottomPanelHandler(true, "");
        } else {
            Log.i(Constants.TAG, getClass().getSimpleName() + " " + getResources().getString(R.string.geofence_transition_left));
            bottomPanelHandler(false, getResources().getString(R.string.geofence_transition_left));
        }
    }

    private void bottomPanelHandler(boolean menuVisibility, String alternateViewText) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -bottomPanelHandler-");
        ConstraintLayout bottomMenu = findViewById(R.id.bottomMenu);
        if(menuVisibility) {
            buttonMenuAlternateView.setVisibility(View.INVISIBLE);
            bottomMenu.setVisibility(View.VISIBLE);
            bottomPanelButtonEnabler(DAO.isUserAttendingLesson(lesson.lessonID, Session.getUserID()));
        } else {
            bottomMenu.setVisibility(View.INVISIBLE);
            buttonMenuAlternateView.setVisibility(View.VISIBLE);
            buttonMenuAlternateView.setText(alternateViewText);
        }
    }

    private void bottomPanelButtonEnabler(boolean isUserAttendingLesson) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -bottomPanelButtonEnabler-");
        buttonPartecipate.setChecked(isUserAttendingLesson);
        buttonEvaluate.setEnabled(isUserAttendingLesson);
        buttonQuestion.setEnabled(isUserAttendingLesson);
    }

    private final View.OnClickListener partecipateButtonListener = v -> {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -partecipateButtonListener-");
        setAttendance(lesson.lessonID, buttonPartecipate.isChecked());
        bottomPanelButtonEnabler(buttonPartecipate.isChecked());
    };

    private final View.OnClickListener evaluateButtonListener = v -> {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -evaluateButtonListener-");
        showEvaluationUI();
    };

    private final View.OnClickListener questionButtonListener = v -> {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -questionButtonListener-");
        showQuestionUI();
    };

    private void showEvaluationUI() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -evaluateButton-");

        HashMap<String, String> params = new HashMap<>();
        params.put(Constants.KEY_ACTION, Constants.GET_EXISTING_EVALUATION);
        params.put(Constants.KEY_USER_ID, String.valueOf(Session.getUserID()));
        params.put(Constants.KEY_LESSON_ID, String.valueOf(lesson.lessonID));
        JSONArray response = DAO.getFromDB(params);

        StudentEvaluateFragment evaluateFragment = null;

        if(!DAO.evaluatedLessonResponseIsNull(response)){
            evaluateFragment = StudentEvaluateFragment.newInstance(lesson.lessonID);
        }
        else{
            JSONObject objResponse;
            try {
                objResponse = response.getJSONObject(0);
                evaluateFragment = StudentEvaluateFragment.newInstance(lesson.lessonID, objResponse.optString("summary"), objResponse.optString("review"), (float) objResponse.optDouble("rating"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.alternateDetailContainer, evaluateFragment).commit();
    }

    private void showQuestionUI() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -questionButton-");
        StudentQuestionFragment questionFragment = StudentQuestionFragment.newInstante(lesson.lessonID);
        getSupportFragmentManager().beginTransaction().replace(R.id.alternateDetailContainer, questionFragment).commit();
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

    public void setAttendance(int lessonID, boolean isUserAttendingLesson) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -setAttendance-");
        if(DAO.setAttendance(lessonID, isUserAttendingLesson)) {
            Toast.makeText(this, getString(R.string.attendance_set), Toast.LENGTH_SHORT).show();
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