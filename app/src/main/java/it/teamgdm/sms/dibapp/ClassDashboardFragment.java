package it.teamgdm.sms.dibapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.location.Geofence;

import java.util.Objects;

public class ClassDashboardFragment extends GeofenceFragment implements View.OnClickListener {

    private boolean classPartecipation;
    private Exam exam;
    private ToggleButton buttonPartecipate;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ClassDashboardFragment() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -ClassDashboardFragment-");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onCreate-");
        super.onCreate(savedInstanceState);

        assert getArguments() != null;
        if (getArguments().containsKey(Constants.KEY_ITEM_ID)) {
            Log.i(Constants.TAG, getClass().getSimpleName() + " -onCreate-argument " + getArguments().getInt(Constants.KEY_ITEM_ID));
            int id = getArguments().getInt(Constants.KEY_ITEM_ID);
            exam = (Exam) getArguments().getSerializable(String.valueOf(id));
        }

        if(getArguments().containsKey(Constants.KEY_CLASS_PARTECIPATION)) {
            classPartecipation = getArguments().getBoolean(Constants.KEY_CLASS_PARTECIPATION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onCreateView-");
        View rootView = inflater.inflate(R.layout.fragment_class_dashboard, container, false);

        buttonPartecipate = rootView.findViewById(R.id.partecipate);
        Button buttonEvaluate = rootView.findViewById(R.id.evaluate);
        Button buttonHistory = rootView.findViewById(R.id.history);

        buttonPartecipate.setOnClickListener(this);
        buttonEvaluate.setOnClickListener(this);
        buttonHistory.setOnClickListener(this);

        buttonPartecipate.setEnabled(false);
        buttonEvaluate.setEnabled(false);

        assert getArguments() != null;
        if(getArguments().containsKey(Constants.GEOFENCE_RECEIVER_ACTION)) {
            buttonHandler(getArguments().getInt(Constants.GEOFENCE_RECEIVER_ACTION));
        }

        if (exam != null) {
            Log.i(Constants.TAG, getClass().getSimpleName() + " -onCreateView-exam not null. Exam" + exam);
            Objects.requireNonNull(getActivity()).setTitle(exam.getName());
        }
        return rootView;
    }

    private void buttonHandler(int geofenceAction) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -toggleEnabler-");
        if(geofenceAction == Geofence.GEOFENCE_TRANSITION_EXIT) {
            Toast.makeText(getContext(), getResources().getString(R.string.geofence_transition_left), Toast.LENGTH_LONG).show();
            buttonPartecipate.setEnabled(false);
        } else if (geofenceAction == Geofence.GEOFENCE_TRANSITION_ENTER) {
            Toast.makeText(getContext(), getResources().getString(R.string.geofence_transition_enter), Toast.LENGTH_LONG).show();
            buttonPartecipate.setEnabled(true);
        } else { //user is dwelling in the geofence
            Toast.makeText(getContext(), getResources().getString(R.string.geofence_transition_dwelling), Toast.LENGTH_LONG).show();
            buttonPartecipate.setEnabled(true);
            attendanceHandler();
        }
    }

    private void attendanceHandler() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -setAttendance-");
        if(classPartecipation) {
            Log.i(Constants.TAG, getClass().getSimpleName() + " -setAttendance-checked TRUE");
            buttonPartecipate.setChecked(true);
        } else {
            Log.i(Constants.TAG, getClass().getSimpleName() + " -setAttendance-checked FALSE");
            buttonPartecipate.setChecked(false);
        }
    }

    @Override
    public void onDetach() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onDetach-");
        super.onDetach();
        callback = null;
    }

    @Override
    public void onClick(View view) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onClick-");
        callback.onItemSelected(view.getId());
    }
}