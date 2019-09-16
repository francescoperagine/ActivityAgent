package it.teamgdm.sms.dibapp;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

public class ClassInformationFragment extends BaseFragment implements View.OnClickListener {

    static final String ARG_ITEM_ID = "item_id";
    private Exam exam;

    public ClassInformationFragment() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -ClassInformationFragment-");
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_class_information, container, false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onAttach-");
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }

    @Override
    public void onClick(View view) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onClick-");
        callback.onItemSelected(view.getId());
    }
}
