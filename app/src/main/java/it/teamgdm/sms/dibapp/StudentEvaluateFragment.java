package it.teamgdm.sms.dibapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Objects;

public class StudentEvaluateFragment extends DialogFragment {

    private StudentEvaluateFragmentInterface studentEvaluateFragmentInterfaceCallback;

    void setStudentEvaluateFragmentInterfaceCallback(StudentEvaluateFragmentInterface studentEvaluateFragmentInterfaceCallback) {
        this.studentEvaluateFragmentInterfaceCallback = studentEvaluateFragmentInterfaceCallback;
    }

    interface StudentEvaluateFragmentInterface {
        void setReview(int lessonID, String summary, String review, int rating);
    }

    private int classLessonID;
    private EditText reviewSummary;
    private EditText reviewText;
    private RatingBar reviewRating;

    static StudentEvaluateFragment newInstante(int classLessonID) {
        StudentEvaluateFragment evaluateFragment = new StudentEvaluateFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(Constants.KEY_CLASS_LESSON_ID, classLessonID);
        evaluateFragment.setArguments(arguments);
        return evaluateFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onCreate-");
        super.onCreate(savedInstanceState);
        if(getArguments() != null && getArguments().containsKey(Constants.KEY_CLASS_LESSON_ID)) {
            classLessonID = getArguments().getInt(Constants.KEY_CLASS_LESSON_ID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onCreateView-");
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.evaluate_fragment, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onViewCreated-");
        super.onViewCreated(view, savedInstanceState);

        reviewSummary = view.findViewById(R.id.reviewSummary);
        reviewText = view.findViewById(R.id.reviewText);
        reviewRating = view.findViewById(R.id.reviewRating);

        Button cancelButton = view.findViewById(R.id.cancelReview);
        Button submitButton = view.findViewById(R.id.submitReview);

        //SEND button starts disabled
        submitButton.setEnabled(false);

        //listener on rating bar changes
        reviewRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener(){

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {

                if(rating == 0){
                    submitButton.setEnabled(false);
                }
                else{
                    submitButton.setEnabled(true);
                }

            }

        });

        cancelButton.setOnClickListener(cancelButtonListener);
        submitButton.setOnClickListener(submitButtonListener);
    }

    private final View.OnClickListener cancelButtonListener = v -> {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -cancelButtonListener-");
        Objects.requireNonNull(getDialog()).dismiss();
    };

    private final View.OnClickListener submitButtonListener = v -> {
        String summary = reviewSummary.getText().toString();
        String text = reviewText.getText().toString();
        int rating = reviewRating.getNumStars();
        Log.i(Constants.TAG, getClass().getSimpleName() + " -submitButtonListener-classLessonID " + classLessonID + " summary " + summary + " text " + text + " rating " + rating);

        studentEvaluateFragmentInterfaceCallback.setReview(classLessonID, summary, text, rating);
        Objects.requireNonNull(getDialog()).dismiss();
    };

}