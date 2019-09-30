package it.teamgdm.sms.dibapp;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

class BaseFragment extends DialogFragment {

    OnClickedItemListener fragmentCallback;

    public interface OnClickedItemListener {
        void onItemSelected(int selectedAction);
        void sendQuestion(int classLessonID, String input);
        void setAttendance(int lessonID, boolean attendance);
        void setReview(int lessonID, String summary, String review, int rating);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        Log.i(Constants.TAG, QuestionFragment.class.getSimpleName() + " -onAttach-");
        super.onAttach(context);
        try {
            fragmentCallback = (OnClickedItemListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnClickedItemListener");
        }
    }

    @Override
    public void onDetach() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onDetach-");
        super.onDetach();
        fragmentCallback = null;
    }
}