package it.teamgdm.sms.dibapp;


import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class StudentLessonBottomNoLessonFragment extends Fragment {

    public StudentLessonBottomNoLessonFragment() {
        // Required empty public constructor
    }

    static StudentLessonBottomNoLessonFragment newInstance() {
        Log.i(Constants.TAG, StudentLessonBottomNoLessonFragment.class.getSimpleName() + " -newInstance-");
        return new StudentLessonBottomNoLessonFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onCreateView-");
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.student_lesson_bottom_no_lesson_fragment, container, false);
        TextView noLesson = rootView.findViewById(R.id.noLessonInProgress);
        noLesson.setTextColor(Color.RED);
        return rootView;
    }

}
