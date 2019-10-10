package it.teamgdm.sms.dibapp;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

class ProfessorClassDetailFragment extends Fragment {

    private ClassLesson classLesson;

    public ProfessorClassDetailFragment() {
    }

    static ProfessorClassDetailFragment newInstance(ClassLesson classLesson, boolean twoPanel) {
        Log.i(Constants.TAG, StudentLessonDetailFragment.class.getSimpleName() + " -newInstance-");
        ProfessorClassDetailFragment fragment = new ProfessorClassDetailFragment();
        Bundle arguments = new Bundle();
        arguments.putSerializable(Constants.KEY_CLASS_LESSON, classLesson);
        arguments.putBoolean(Constants.TWO_PANEL, twoPanel);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onCreate-");
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            Log.i(Constants.TAG, StudentLessonDetailFragment.class.getSimpleName() + " -onCreate-arguments \n" + getArguments());
            classLesson = (ClassLesson) getArguments().getSerializable(Constants.KEY_CLASS_LESSON);
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.professor_lesson_detail_fragment, container, false);
        TextView lessonInProgress = rootView.findViewById(R.id.lessonInProgress);
        TextView lessonTime = rootView.findViewById(R.id.lessonTime);
        TextView attendance = rootView.findViewById(R.id.attendance);
        RatingBar ratingBarProf = rootView.findViewById(R.id.ratingBarProf);
        Button reviewButton = rootView.findViewById(R.id.reviewButton);
        Button questionButton = rootView.findViewById(R.id.questionButton);

        //setting lesson rating bar
        ratingBarProf.setRating(classLesson.rating);

        //checking if lesson is in progress
        if (classLesson.isInProgress()){
            lessonInProgress.setText(getString(R.string.lesson_progress));
            lessonInProgress.setBackgroundColor(Color.parseColor("#8BC34A"));

        } else{
            lessonInProgress.setText(getString(R.string.lesson_not_progress));
        }

        //showing lesson start and end
        String time;
        time = getString(R.string.from) + classLesson.getDate() + getString(R.string.to) + new SimpleDateFormat(Constants.DATE_FORMAT, Locale.getDefault()).format(classLesson.timeEnd);
        lessonTime.setText(time);

        //showing class attendance
        String studNum = getString(R.string.attendance) + classLesson.attendance;
        attendance.setText(studNum);


        return rootView;
    }
}
