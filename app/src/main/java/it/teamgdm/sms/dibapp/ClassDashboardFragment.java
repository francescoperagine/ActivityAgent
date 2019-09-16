package it.teamgdm.sms.dibapp;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;

import java.util.Objects;

public class ClassDashboardFragment extends BaseFragment implements View.OnClickListener {

    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    static final String ARG_ITEM_ID = "item_id";
    boolean userDwellsInGeofence;
    private Exam exam;

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
            userDwellsInGeofence = getArguments().getBoolean(Constants.GEOFENCE_TRANSITION_DWELLS);
            exam = (Exam) getArguments().getSerializable(String.valueOf(id));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onCreateView-");
        View rootView = inflater.inflate(R.layout.fragment_class_dashboard, container, false);

        Button buttonPartecipate = rootView.findViewById(R.id.partecipate);
        Button buttonEvaluate = rootView.findViewById(R.id.evaluate);
        Button buttonHistory = rootView.findViewById(R.id.history);
        Button buttonInformation = rootView.findViewById(R.id.information);

        buttonPartecipate.setOnClickListener(this);
        buttonEvaluate.setOnClickListener(this);
        buttonHistory.setOnClickListener(this);
        buttonInformation.setOnClickListener(this);

        if(/* Session.GEOFENCE_PERMISSION_GRANTED && */ userDwellsInGeofence) {
            Log.i(Constants.TAG, getClass().getSimpleName() + " -onCreateView-GEOFENCE_PERMISSION_GRANTED");
            buttonPartecipate.setEnabled(true);
        } else {
            Log.i(Constants.TAG, getClass().getSimpleName() + " -onCreateView-GEOFENCE_PERMISSION_NOT_GRANTED");
            buttonPartecipate.setEnabled(false);
        }

        if (exam != null) {
            Log.i(Constants.TAG, getClass().getSimpleName() + " -onCreateView-exam not null. Exam" + exam);
            Objects.requireNonNull(getActivity()).setTitle(exam.getName());
        }
        return rootView;
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
