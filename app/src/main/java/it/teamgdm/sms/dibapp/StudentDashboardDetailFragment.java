package it.teamgdm.sms.dibapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Objects;

public class StudentDashboardDetailFragment extends BaseFragment {

    private ClassLesson classLesson;

    public StudentDashboardDetailFragment() {
        // Required empty public constructor
    }

    static StudentDashboardDetailFragment newInstance(ClassLesson classLesson, boolean twoPanel) {
        Log.i(Constants.TAG, StudentDashboardDetailFragment.class.getSimpleName() + " -newInstance-");
        StudentDashboardDetailFragment fragment = new StudentDashboardDetailFragment();
        Bundle arguments = new Bundle();
        arguments.putSerializable(Constants.KEY_CLASS_LESSON, classLesson);
        arguments.putBoolean(Constants.TWO_PANEL, twoPanel);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onCreate-");
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            Log.i(Constants.TAG, StudentDashboardDetailFragment.class.getSimpleName() + " -onCreate-arguments \n" + getArguments());
            classLesson = (ClassLesson) getArguments().getSerializable(Constants.KEY_CLASS_LESSON);
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

        className.setText(classLesson.name);

        String year = getString(R.string.classYearText) + ": " + classLesson.year;
        classYear.setText(year);

        String semester = getString(R.string.classSemesterText) + ": " + classLesson.semester;
        classSemester.setText(semester);

        String code = getString(R.string.classCodeText) + ": " + classLesson.code;
        classCode.setText(code);

        if(classLesson.classDescription.equals("null")) {
            classDescription.setText(getString(R.string.noClassDescriptionSetText));
        } else {
            classDescription.setText(classLesson.classDescription);
        }

        String lessonDate = getString(R.string.lessonDate) + "\n" + classLesson.getDateString();
        classLessonDate.setText(lessonDate);

        String lessonTimeStart = getString(R.string.lessonStartAt) + "\n" + classLesson.getTimeString(classLesson.timeStart);
        classLessonTimeStart.setText(lessonTimeStart);

        String lessonTimeEnd = getString(R.string.lessonEndAt) + "\n" + classLesson.getTimeString(classLesson.timeEnd);
        classLessonTimeEnd.setText(lessonTimeEnd);

        if(classLesson.lessonSummary.equals("null")) {
            classLessonSummary.setText(getString(R.string.noSummarySetText));
        } else {
            classLessonSummary.setText(classLesson.lessonSummary);
        }

        if(classLesson.lessonDescription.equals("null")) {
            classLessonDescription.setText(getString(R.string.noLessonDescriptionSetText));
        } else {
            classLessonDescription.setText(classLesson.lessonDescription);
        }

        if (classLesson != null) {
            Objects.requireNonNull(getActivity()).setTitle(classLesson.name);
        }
        return rootView;
    }

}