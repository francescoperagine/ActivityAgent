package it.teamgdm.sms.dibapp;

import androidx.fragment.app.Fragment;

class BaseFragment extends Fragment {

    OnClickedItemListener fragmentCallback;

    public interface OnClickedItemListener {
        void onItemSelected(int selectedAction);
    }
}