package it.teamgdm.sms.dibapp;

import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


public class StudentDashboardBottomFragment extends Fragment {

    // Student button fragment interface declaration

    private StudentDashboardButtonFragmentInterface studentDashboardButtonFragmentInterfaceCallback;

    interface StudentDashboardButtonFragmentInterface {
        void onItemSelected(int selectedAction);
        void setAttendance(int lessonID, boolean attendance);
    }

    void setStudentDashboardButtonFragmentInterfaceCallback(StudentDashboardButtonFragmentInterface studentDashboardButtonFragmentInterfaceCallback) {
        this.studentDashboardButtonFragmentInterfaceCallback = studentDashboardButtonFragmentInterfaceCallback;
    }

    private int lessonID;
    private boolean isUserAttendingLesson;

    private ToggleButton buttonPartecipate;
    private Button buttonEvaluate;
    private Button buttonQuestion;


    public StudentDashboardBottomFragment() {
        // Required empty public constructor
    }

    static StudentDashboardBottomFragment newInstance(int lessonID, boolean isUserAttendingLesson) {
        Log.i(Constants.TAG, StudentDashboardBottomFragment.class.getSimpleName() + " -newInstance-");
        StudentDashboardBottomFragment fragment = new StudentDashboardBottomFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(Constants.KEY_CLASS_LESSON_ID, lessonID);
        arguments.putBoolean(Constants.IS_USER_ATTENDING_LESSON, isUserAttendingLesson);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onCreate-");
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            Log.i(Constants.TAG, getClass().getSimpleName() + " -onCreate-\n"+getArguments());
            lessonID = getArguments().getInt(Constants.KEY_CLASS_LESSON_ID);
            isUserAttendingLesson = getArguments().getBoolean(Constants.IS_USER_ATTENDING_LESSON);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onCreateView-");
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.student_lesson_bottom_fragment, container, false);

        TextView classLessonInProgress = rootView.findViewById(R.id.classLessonInProgress);

        buttonPartecipate = rootView.findViewById(R.id.partecipateButton);
        buttonEvaluate = rootView.findViewById(R.id.evaluateButton);
        buttonQuestion = rootView.findViewById(R.id.questionButton);

        buttonPartecipate.setOnClickListener(partecipateButtonListener);
        buttonEvaluate.setOnClickListener(evaluateButtonListener);
        buttonQuestion.setOnClickListener(questionButtonListener);

        classLessonInProgress.setBackgroundColor(Color.GREEN);
        classLessonInProgress.setText(R.string.classLessonInProgress);


        if(GeofenceAPI.hasGeofencePermissions) {
            buttonAttendanceChecker();
        } else {
            buttonSwitchPanel(false, false, false, false);
        }

        return rootView;
    }

    private final View.OnClickListener partecipateButtonListener = v -> {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -partecipateButtonListener-");
        studentDashboardButtonFragmentInterfaceCallback.setAttendance(lessonID, buttonPartecipate.isChecked());
    };

    private final View.OnClickListener evaluateButtonListener = v -> {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -evaluateButtonListener-");
        studentDashboardButtonFragmentInterfaceCallback.onItemSelected(R.id.evaluateButton);
    };

    private final View.OnClickListener questionButtonListener = v -> {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -questionButtonListener-");
        studentDashboardButtonFragmentInterfaceCallback.onItemSelected(R.id.questionButton);
    };

    private void buttonAttendanceChecker() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -buttonAttendanceChecker-");
        if(isUserAttendingLesson) {
            buttonSwitchPanel(true, true, true, true);
        } else {
            buttonSwitchPanel(false, true, false, false);
        }
    }

    private void buttonSwitchPanel(boolean buttonPartecipateIsChecked, boolean buttonPartecipateIsActive, boolean buttonEvaluateIsActive, boolean buttonQuerstionIsActive) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -buttonSwitchPanel-");
        buttonPartecipate.setChecked(buttonPartecipateIsChecked);
        buttonPartecipate.setEnabled(buttonPartecipateIsActive);
        buttonEvaluate.setEnabled(buttonEvaluateIsActive);
        buttonQuestion.setEnabled(buttonQuerstionIsActive);
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
}
