package it.teamgdm.sms.dibapp;

import android.app.AlertDialog;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;

import com.google.android.gms.location.Geofence;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Objects;


public class StudentDashboardButtonFragment extends BaseFragment implements GeofenceBroadcastReceiver.GeofenceBroadcastReceiverCallback {

    private IntentFilter intentFilter;
    private static int geofenceReceiverLastAction;
    private int geofenceTransitionAction;
    private GeofenceAPI geofenceAPI;

    private int lessonID;


    private boolean isUserAttendingLesson;
    private boolean lessonInProgress;

    private ToggleButton buttonPartecipate;
    private Button buttonEvaluate;
    private Button buttonQuestion;
    private GeofenceBroadcastReceiver geofenceBroadcastReceiver;

    public StudentDashboardButtonFragment() {
        // Required empty public constructor
    }

    static StudentDashboardButtonFragment newInstance(int lessonID, boolean lessonInProgress) {
        Log.i(Constants.TAG, StudentDashboardButtonFragment.class.getSimpleName() + " -newInstance-");
        StudentDashboardButtonFragment fragment = new StudentDashboardButtonFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(Constants.KEY_CLASS_LESSON_ID, lessonID);
        arguments.putBoolean(Constants.LESSON_IN_PROGRESS, lessonInProgress);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onCreate-");
        super.onCreate(savedInstanceState);
        geofenceAPI = new GeofenceAPI(getActivity());
        if (getArguments() != null) {
            Log.i(Constants.TAG, getClass().getSimpleName() + " -onCreate-\n"+getArguments());
            lessonID = getArguments().getInt(Constants.KEY_CLASS_LESSON_ID);
            lessonInProgress = getArguments().getBoolean(Constants.LESSON_IN_PROGRESS);
            isUserAttendingLesson = isUserAttendingLesson(lessonID);
            intentFilter = new IntentFilter(Constants.GEOFENCE_TRANSITION_ACTION);
            Objects.requireNonNull(getActivity()).registerReceiver(geofenceBroadcastReceiver, intentFilter);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onCreateView-");
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.student_lesson_button_fragment, container, false);

        TextView classLessonInProgress = rootView.findViewById(R.id.classLessonInProgress);

        buttonPartecipate = rootView.findViewById(R.id.partecipateButton);
        buttonEvaluate = rootView.findViewById(R.id.evaluateButton);
        buttonQuestion = rootView.findViewById(R.id.questionButton);

        buttonPartecipate.setOnClickListener(partecipateButtonListener);
        buttonEvaluate.setOnClickListener(evaluateButtonListener);
        buttonQuestion.setOnClickListener(questionButtonListener);

        if(lessonInProgress) {
            classLessonInProgress.setBackgroundColor(Color.GREEN);
            classLessonInProgress.setText(R.string.classLessonInProgress);
        } else {
            classLessonInProgress.setText(R.string.classLessonNotInProgress);
        }

        if(GeofenceAPI.hasGeofencePermissions) {
            buttonRangeChecker();
            buttonAttendanceChecker();
        } else {
            buttonDisabler();
        }

        return rootView;
    }

    private final View.OnClickListener partecipateButtonListener = v -> {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -partecipateButtonListener-");
        fragmentCallback.setAttendance(lessonID, buttonPartecipate.isChecked());
    };

    private final View.OnClickListener evaluateButtonListener = v -> {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -evaluateButtonListener-");
        fragmentCallback.onItemSelected(R.id.evaluateButton);
    };

    private final View.OnClickListener questionButtonListener = v -> {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -questionButtonListener-");
        fragmentCallback.onItemSelected(R.id.questionButton);
    };

    /**
     * Enables or disables the partecipateButton
     */

    private void buttonRangeChecker() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -buttonRangeChecker-");
        if(geofenceTransitionAction == Geofence.GEOFENCE_TRANSITION_EXIT) {
            Log.i(Constants.TAG, getClass().getSimpleName() + " -buttonRangeChecker-" + getResources().getString(R.string.geofence_transition_left));
            Toast.makeText(getContext(), getResources().getString(R.string.geofence_transition_left), Toast.LENGTH_LONG).show();
            buttonDisabler();
        } else if (geofenceTransitionAction == Geofence.GEOFENCE_TRANSITION_DWELL) {
            Log.i(Constants.TAG, getClass().getSimpleName() + " -buttonRangeChecker-" + getResources().getString(R.string.geofence_transition_dwelling));
            Toast.makeText(getContext(), getResources().getString(R.string.geofence_transition_dwelling), Toast.LENGTH_LONG).show();
            buttonPartecipate.setEnabled(true);
            buttonEvaluate.setEnabled(true);
            buttonQuestion.setEnabled(true);
        } else { //user is entering the the geofence
            Log.i(Constants.TAG, getClass().getSimpleName() + " -buttonRangeChecker-" + getResources().getString(R.string.geofence_transition_enter));
            Toast.makeText(getContext(), getResources().getString(R.string.geofence_transition_enter), Toast.LENGTH_LONG).show();
            buttonPartecipate.setEnabled(true);
        }
    }

    private void buttonAttendanceChecker() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -setAttendance-");
        if(isUserAttendingLesson) {
            buttonPartecipate.setChecked(true);
            buttonEvaluate.setEnabled(true);
            buttonQuestion.setEnabled(true);
        } else {
            buttonPartecipate.setChecked(false);
            buttonEvaluate.setEnabled(false);
            buttonQuestion.setEnabled(false);
        }
    }

    private void buttonDisabler() {
        buttonPartecipate.setEnabled(false);
        buttonEvaluate.setEnabled(false);
        buttonQuestion.setEnabled(false);
    }

    @Override
    public void onGeofenceTransitionAction(int geofenceReceiverAction) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onGeofenceTransitionAction-"+ geofenceTransitionAction);
        if(geofenceTransitionAction != geofenceReceiverLastAction) {
            // Forwards the new geofence's transition code to the fragment
            geofenceReceiverLastAction = geofenceTransitionAction;
            buttonRangeChecker();
        }
    }

    private boolean isUserAttendingLesson(int lessonID) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -isUserAttendingLesson-");
        HashMap<String, String> params = new HashMap<>();
        params.put(Constants.KEY_ACTION, Constants.IS_USER_ATTENDING_LESSON);
        params.put(Constants.KEY_CLASS_LESSON_ID, String.valueOf(lessonID));
        params.put(Constants.KEY_USER_ID, String.valueOf(Session.getUserID()));
        int response = 0;
        try {
            response = BaseActivity.getFromDB(params).getJSONObject(0).getInt(Constants.KEY_ATTENDANCE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return response == 1;
    }

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
                new AlertDialog.Builder(getActivity())
                        .setTitle(R.string.geofence_insufficient_permissions_title)
                        .setMessage(R.string.geofence_insufficient_permissions_message)
                        .create()
                        .show();
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        try {
            getActivity().unregisterReceiver(geofenceBroadcastReceiver);
        } catch (NullPointerException | IllegalArgumentException ignored) {}

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        geofenceAPI.removeGeofences();
    }
}
