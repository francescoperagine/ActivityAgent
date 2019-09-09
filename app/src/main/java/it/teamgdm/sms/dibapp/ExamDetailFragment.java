package it.teamgdm.sms.dibapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

/**
 * A fragment representing a single Exam detail screen.
 * This fragment is either contained in a {@link ExamListActivity}
 * in two-pane mode (on tablets) or a {@link ExamDetailActivity}
 * on handsets.
 */
public class ExamDetailFragment extends Fragment {
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
    public ExamDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -onCreate-");
        super.onCreate(savedInstanceState);

        assert getArguments() != null;
        if (getArguments().containsKey(ARG_ITEM_ID)) {
            Log.i(Settings.TAG, getClass().getSimpleName() + " -onCreate-argument " + getArguments().getInt(ARG_ITEM_ID));
            int id = getArguments().getInt(ARG_ITEM_ID);
            exam = StudentCareer.getExam(id);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -onCreateView-");
        View rootView = inflater.inflate(R.layout.exam_detail_fragment, container, false);
        if (exam != null) {
            ((TextView) rootView.findViewById(R.id.exam_detail)).setText(exam.getName());
        }
        return rootView;
    }
}
