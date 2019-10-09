package it.teamgdm.sms.dibapp;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

class ProfessorClassDetailFragment extends Fragment {

    private int classID;

    public ProfessorClassDetailFragment() {
    }

    static ProfessorClassDetailFragment newInstance(int classID, boolean twoPanel) {
        Log.i(Constants.TAG, StudentLessonDetailFragment.class.getSimpleName() + " -newInstance-");
        ProfessorClassDetailFragment fragment = new ProfessorClassDetailFragment();
        Bundle arguments = new Bundle();
        arguments.putSerializable(Constants.KEY_CLASS_ID, classID);
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
            classID = getArguments().getInt(Constants.KEY_CLASS_ID);
        }

    }
}
