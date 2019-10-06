package it.teamgdm.sms.dibapp;


import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class StudentLessonBottomNotInGeofenceFragment extends Fragment {

    private boolean isUserAttendingLesson;

    private ToggleButton buttonPartecipate;

    public StudentLessonBottomNotInGeofenceFragment() {
        // Required empty public constructor
    }

    static StudentLessonBottomNotInGeofenceFragment newInstance(boolean isUserAttendingLesson) {
        Log.i(Constants.TAG, StudentLessonBottomFragment.class.getSimpleName() + " -newInstance-");
        StudentLessonBottomNotInGeofenceFragment fragment = new StudentLessonBottomNotInGeofenceFragment();
        Bundle arguments = new Bundle();
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
        Button buttonEvaluate = rootView.findViewById(R.id.evaluateButton);
        Button buttonQuestion = rootView.findViewById(R.id.questionButton);

        classLessonInProgress.setBackgroundColor(Color.GREEN);
        classLessonInProgress.setText(R.string.classLessonInProgress);

        buttonPartecipate.setEnabled(false);
        buttonEvaluate.setEnabled(false);
        buttonQuestion.setEnabled(false);

        if(isUserAttendingLesson) {
            buttonPartecipate.setChecked(true);
        }

        return rootView;
    }

}
