package it.teamgdm.sms.dibapp;

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

import androidx.fragment.app.Fragment;

import org.json.JSONArray;

import java.util.HashMap;


public class StudentLessonBottomFragment extends Fragment {

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

    public StudentLessonBottomFragment() {
        // Required empty public constructor
    }

    static StudentLessonBottomFragment newInstance(int lessonID, boolean isUserAttendingLesson) {
        Log.i(Constants.TAG, StudentLessonBottomFragment.class.getSimpleName() + " -newInstance-");
        StudentLessonBottomFragment fragment = new StudentLessonBottomFragment();
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

        buttonAttendanceChecker();

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

}
