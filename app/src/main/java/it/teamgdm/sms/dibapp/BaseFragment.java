package it.teamgdm.sms.dibapp;

import android.util.Log;

import androidx.fragment.app.Fragment;

class BaseFragment extends Fragment {

    OnClickedItemListener callback;

    void setOnClickedItemListener(OnClickedItemListener callback) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -setOnClickedItemListener-");
        this.callback = callback;
    }

    public interface OnClickedItemListener {
        void onItemSelected(int selectedAction);
    }

}
