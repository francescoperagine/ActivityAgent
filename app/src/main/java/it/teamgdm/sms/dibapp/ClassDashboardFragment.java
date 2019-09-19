package it.teamgdm.sms.dibapp;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;

import com.google.android.gms.location.Geofence;

import java.util.Objects;

public class ClassDashboardFragment extends BaseFragment implements View.OnClickListener {

    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    static final String ARG_ITEM_ID = "item_id";
    private boolean classPartecipation;
    private Exam exam;
    ToggleButton buttonPartecipate;
    Button buttonEvaluate, buttonHistory, buttonInformation;

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
        if (getArguments().containsKey(ARG_ITEM_ID)) {
            Log.i(Constants.TAG, getClass().getSimpleName() + " -onCreate-argument " + getArguments().getInt(ARG_ITEM_ID));
            int id = getArguments().getInt(ARG_ITEM_ID);
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
        buttonEvaluate = rootView.findViewById(R.id.evaluate);
        buttonHistory = rootView.findViewById(R.id.history);
        buttonInformation = rootView.findViewById(R.id.information);

        buttonPartecipate.setOnClickListener(this);
        buttonEvaluate.setOnClickListener(this);
        buttonHistory.setOnClickListener(this);
        buttonInformation.setOnClickListener(this);

        buttonPartecipate.setEnabled(false);
        buttonEvaluate.setEnabled(false);

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
        Toast.makeText(getContext(), Constants.GEOFENCE_TRANSITION_ACTION + " " + geofenceAction, Toast.LENGTH_LONG).show();
        if(geofenceAction == Geofence.GEOFENCE_TRANSITION_EXIT) {
            buttonPartecipate.setEnabled(false);
        } else if (geofenceAction == Geofence.GEOFENCE_TRANSITION_ENTER) {
            buttonPartecipate.setEnabled(true);
        } else { //user is dwelling in the geofence
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
    public void onAttach(@NonNull Context context) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onAttach-");
        super.onAttach(context);
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
