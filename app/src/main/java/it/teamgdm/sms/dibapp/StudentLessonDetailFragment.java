package it.teamgdm.sms.dibapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.Objects;

public class StudentLessonDetailFragment extends Fragment {

    private Lesson lesson;

    public StudentLessonDetailFragment() {
        // Required empty public constructor
    }

    static StudentLessonDetailFragment newInstance(Lesson lesson, boolean twoPanel) {
        Log.i(Constants.TAG, StudentLessonDetailFragment.class.getSimpleName() + " -newInstance-");
        StudentLessonDetailFragment fragment = new StudentLessonDetailFragment();
        Bundle arguments = new Bundle();
        arguments.putSerializable(Constants.KEY_CLASS_LESSON, lesson);
        arguments.putBoolean(Constants.TWO_PANEL, twoPanel);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onCreate-");
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            Log.i(Constants.TAG, StudentLessonDetailFragment.class.getSimpleName() + " -onCreate-arguments \n" + getArguments());
            lesson = (Lesson) getArguments().getSerializable(Constants.KEY_CLASS_LESSON);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onCreateView-");
        View rootView = inflater.inflate(R.layout.student_lesson_detail_fragment, container, false);

        TextView className = rootView.findViewById(R.id.className);
        TextView classYear = rootView.findViewById(R.id.classYear);
        TextView classSemester = rootView.findViewById(R.id.classSemester);
        TextView classCode = rootView.findViewById(R.id.classCode);
        TextView classDescription = rootView.findViewById(R.id.classDescription);
        TextView classLessonDate = rootView.findViewById(R.id.classLessonDate);
        TextView classLessonTimeStart = rootView.findViewById(R.id.classLessonTimeStart);
        TextView classLessonTimeEnd = rootView.findViewById(R.id.classLessonTimeEnd);
        TextView classLessonSummary = rootView.findViewById(R.id.classLessonSummary);
        TextView classLessonDescription = rootView.findViewById(R.id.classLessonDescription);

        className.setText(lesson.name);
        if(getActivity().getClass().equals(StudentLessonDetailActivity.class)) className.setVisibility(View.GONE);

        String year = getString(R.string.classYearText) + ": " + lesson.year;
        classYear.setText(year);

        String semester = getString(R.string.classSemesterText) + ": " + lesson.semester;
        classSemester.setText(semester);

        String code = getString(R.string.classCodeText) + ": " + lesson.code;
        classCode.setText(code);

        if(lesson.classDescription.equals("null")) {
            classDescription.setText(getString(R.string.noClassDescriptionSetText));
        } else {
            classDescription.setText(lesson.classDescription);
        }

        String lessonDate = getString(R.string.lessonDate) + "\n" + lesson.getDate();
        classLessonDate.setText(lessonDate);

        String lessonTimeStart = getString(R.string.lessonStartAt) + "\n" + lesson.getTimeStringFromDate(lesson.timeStart);
        classLessonTimeStart.setText(lessonTimeStart);

        String lessonTimeEnd = getString(R.string.lessonEndAt) + "\n" + lesson.getTimeStringFromDate(lesson.timeEnd);
        classLessonTimeEnd.setText(lessonTimeEnd);

        if(lesson.lessonSummary.equals("null")) {
            classLessonSummary.setText(getString(R.string.noSummarySetText));
        } else {
            classLessonSummary.setText(lesson.lessonSummary);
        }

        if(lesson.lessonDescription.equals("null")) {
            classLessonDescription.setText(getString(R.string.noLessonDescriptionSetText));
        } else {
            classLessonDescription.setText(lesson.lessonDescription);
        }

        if (lesson != null) {
            Objects.requireNonNull(getActivity()).setTitle(lesson.name);
        }
        return rootView;
    }

}