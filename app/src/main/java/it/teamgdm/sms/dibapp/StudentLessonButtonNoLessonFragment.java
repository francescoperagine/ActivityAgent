package it.teamgdm.sms.dibapp;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class StudentLessonButtonNoLessonFragment extends Fragment {


    public StudentLessonButtonNoLessonFragment() {
        // Required empty public constructor
    }

    static StudentLessonButtonNoLessonFragment newInstance() {
        Log.i(Constants.TAG, StudentLessonButtonNoLessonFragment.class.getSimpleName() + " -newInstance-");
        return new StudentLessonButtonNoLessonFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onCreateView-");
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.student_lesson_button_no_lesson_fragment, container, false);
    }

}
