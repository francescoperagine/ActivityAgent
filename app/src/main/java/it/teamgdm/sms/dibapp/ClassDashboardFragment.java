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

import com.google.android.gms.location.Geofence;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Objects;

public class ClassDashboardFragment extends BaseFragment implements View.OnClickListener {

    private ClassLesson classLesson;
    private boolean classPartecipation;

    ToggleButton buttonPartecipate;
    Button buttonEvaluate, buttonHistory;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onCreate-");
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            if (getArguments().containsKey(Constants.KEY_ITEM_ID)) {
                Log.i(Constants.TAG, getClass().getSimpleName() + " -onCreate-argument " + getArguments().getInt(Constants.KEY_ITEM_ID));

                int classLessonID = getArguments().getInt(Constants.KEY_ITEM_ID);
                classLesson = getClassLessonDetail(classLessonID);
            }

            if(getArguments().containsKey(Constants.KEY_CLASS_PARTECIPATION)) {
                classPartecipation = getArguments().getBoolean(Constants.KEY_CLASS_PARTECIPATION);
            }
        }
    }

    private ClassLesson getClassLessonDetail(int classLessonID) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -getClassLessonDetail-");
        HashMap<String, String> params = new HashMap<>();
        params.put(Constants.KEY_ACTION, Constants.GET_CLASS_LESSON_DETAIL);
        params.put(Constants.KEY_CLASS_ID, String.valueOf(classLessonID));
        JSONArray classLessonDetailLoader = BaseActivity.getFromDB(params);
        ClassLesson classLesson = null;
        try {
            JSONObject classLessonDetail = classLessonDetailLoader.getJSONObject(0);
            classLesson = new ClassLesson();
            classLesson.setClassLesson(classLessonDetail);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return classLesson;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onCreateView-");
        View rootView = inflater.inflate(R.layout.fragment_class_dashboard, container, false);

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
        TextView classLessonInProgress = rootView.findViewById(R.id.classLessonInProgress);

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

        String lessonTimeStart = getString(R.string.lessonStartAt) + "\n" + classLesson.getTimeStartString();
        classLessonTimeStart.setText(lessonTimeStart);

        String lessonTimeEnd = getString(R.string.lessonEndAt) + "\n" + classLesson.getTimeEndString();
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

        buttonPartecipate = rootView.findViewById(R.id.partecipate);
        buttonEvaluate = rootView.findViewById(R.id.evaluate);
        buttonHistory = rootView.findViewById(R.id.history);

        buttonPartecipate.setOnClickListener(buttonPartecipateListener);
        buttonEvaluate.setOnClickListener(buttonEvaluateListener);
        buttonHistory.setOnClickListener(buttonHistoryListener);

        colorLessonInProgress(classLessonInProgress);

        if(getArguments() != null && getArguments().containsKey(Constants.GEOFENCE_TRANSITION_ACTION)) {
            int geofenceTransitionAction = getArguments().getInt(Constants.GEOFENCE_TRANSITION_ACTION);
            buttonRangeEnabler(geofenceTransitionAction);
            setAttendance();
            setEvaluation();
        }

        if (classLesson != null) {
            Objects.requireNonNull(getActivity()).setTitle(classLesson.getName());
        }
        return rootView;
    }

    private void colorLessonInProgress(TextView classLessonInProgress) {
        if(classLesson.isInProgress()) {
            classLessonInProgress.setBackgroundColor(Color.GREEN);
            classLessonInProgress.setEnabled(true);
        } else {
            classLessonInProgress.setEnabled(false);
        }
    }

    private final View.OnClickListener buttonPartecipateListener = v -> {

    };

    private final View.OnClickListener buttonEvaluateListener = v -> {

    };

    private final View.OnClickListener buttonHistoryListener = v -> {

    };

    /**
     * Enables or disables the partecipateButton
     */

    private void buttonRangeEnabler(int geofenceAction) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -buttonRangeEnabler-");
        if(geofenceAction == Geofence.GEOFENCE_TRANSITION_EXIT) {
            Toast.makeText(getContext(), getResources().getString(R.string.geofence_transition_left), Toast.LENGTH_LONG).show();
            buttonPartecipate.setEnabled(false);
        } else if (geofenceAction == Geofence.GEOFENCE_TRANSITION_ENTER) {
            Toast.makeText(getContext(), getResources().getString(R.string.geofence_transition_enter), Toast.LENGTH_LONG).show();
            buttonPartecipate.setEnabled(true);
        } else { //user is dwelling in the geofence
            Toast.makeText(getContext(), getResources().getString(R.string.geofence_transition_dwelling), Toast.LENGTH_LONG).show();
            buttonPartecipate.setEnabled(true);
        }
    }

    private void setAttendance() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -setAttendance-");
        if(classPartecipation) {
            Log.i(Constants.TAG, getClass().getSimpleName() + " -setAttendance-checked TRUE");
            buttonPartecipate.setEnabled(true);
        } else {
            Log.i(Constants.TAG, getClass().getSimpleName() + " -setAttendance-checked FALSE");
            buttonPartecipate.setEnabled(false);
        }
    }

    private void setEvaluation() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -setEvaluation-");
        if(classPartecipation) {
            buttonEvaluate.setEnabled(true);
        } else {
            buttonEvaluate.setEnabled(false);
        }
    }

    @Override
    public void onDetach() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onDetach-");
        super.onDetach();
        fragmentCallback = null;
    }

    @Override
    public void onClick(View view) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onClick-");
        fragmentCallback.onItemSelected(view.getId());
    }
}