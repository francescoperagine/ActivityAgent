package it.teamgdm.sms.dibapp;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;

/**
 * A fragment representing a single Exam detail screen.
 * This fragment is either contained in a {@link ExamListActivity}
 * in two-pane mode (on tablets) or a {@link ExamDetailActivity}
 * on handsets.
 */
public class ExamDashboardFragment extends BaseFragment implements View.OnClickListener {

    // This interface can be implemented by the Activity, parent Fragment,
    // or a separate test implementation.


    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    static final String ARG_ITEM_ID = "item_id";
    private Exam exam;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ExamDashboardFragment() {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -ExamDashboardFragment-");

    }

    @Override
    public void onAttach(@NonNull Context context) {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -onAttach-");
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -onCreate-");
        super.onCreate(savedInstanceState);

        assert getArguments() != null;
        if (getArguments().containsKey(ARG_ITEM_ID)) {
            Log.i(Settings.TAG, getClass().getSimpleName() + " -onCreate-argument " + getArguments().getInt(ARG_ITEM_ID));
            int id = getArguments().getInt(ARG_ITEM_ID);
            exam = (Exam) getArguments().getSerializable(String.valueOf(id));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -onCreateView-");
        View rootView = inflater.inflate(R.layout.exam_dashboard_fragment, container, false);

        Button buttonPartecipate = rootView.findViewById(R.id.partecipate);
        Button buttonEvaluate = rootView.findViewById(R.id.evaluate);
        Button buttonHistory = rootView.findViewById(R.id.history);
        Button buttonInformation = rootView.findViewById(R.id.information);

        buttonPartecipate.setOnClickListener(this);
        buttonEvaluate.setOnClickListener(this);
        buttonHistory.setOnClickListener(this);
        buttonInformation.setOnClickListener(this);

        if (exam != null) {
            Log.i(Settings.TAG, getClass().getSimpleName() + " -onCreateView-exam not null. Exam" + exam);
            getActivity().setTitle(exam.getName());
        }
        return rootView;
    }

    @Override
    public void onClick(View view) {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -onClick-");
        callback.onItemSelected(view.getId());
    }
}
