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
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import java.util.Objects;

public class StudentReviewFragment extends DialogFragment {

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

    static StudentReviewFragment newInstance(int lessonID) {
        Log.i(Constants.TAG, StudentReviewFragment.class.getSimpleName() + " -StudentReviewFragment- lessonID " + lessonID);
        StudentReviewFragment evaluateFragment = new StudentReviewFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(Constants.KEY_LESSON_ID, lessonID);
        evaluateFragment.setArguments(arguments);
        return evaluateFragment;
    }

    static StudentReviewFragment newInstance(int lessonID, String summary, String description, float rating) {
        Log.i(Constants.TAG, StudentReviewFragment.class.getSimpleName() + " -StudentReviewFragment-");
        StudentReviewFragment evaluateFragment = new StudentReviewFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(Constants.KEY_LESSON_ID, lessonID);
        arguments.putFloat(Constants.KEY_LESSON_REVIEW_RATING, rating);
        arguments.putString(Constants.KEY_LESSON_REVIEW_SUMMARY, summary);
        arguments.putString(Constants.KEY_LESSON_REVIEW_DESCRIPTION, description);
        evaluateFragment.setArguments(arguments);
        return evaluateFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onCreate-");
        super.onCreate(savedInstanceState);
        if(getArguments() != null && getArguments().containsKey(Constants.KEY_LESSON_ID)) {
            classLessonID = getArguments().getInt(Constants.KEY_LESSON_ID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onCreateView-");
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.student_review_fragment, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onViewCreated-");
        super.onViewCreated(view, savedInstanceState);

        reviewSummary = view.findViewById(R.id.reviewSummary);
        reviewText = view.findViewById(R.id.reviewText);
        reviewRating = view.findViewById(R.id.reviewRating);

        Button cancelButton = view.findViewById(R.id.cancelReview);
        cancelButton.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.secondaryColor));
        Button submitButton = view.findViewById(R.id.submitReview);
        submitButton.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.secondaryColor));

        //SEND button starts disabled
        submitButton.setEnabled(false);

        //setting old review text and rating if there were an old one
        if(getArguments() != null && getArguments().containsKey(Constants.KEY_LESSON_REVIEW_RATING)) {
            reviewRating.setRating(getArguments().getFloat(Constants.KEY_LESSON_REVIEW_RATING));
            String tmpSummary = getArguments().getString(Constants.KEY_LESSON_REVIEW_SUMMARY);
            if(tmpSummary != null && !tmpSummary.isEmpty()){
                reviewSummary.setText(tmpSummary);
            }
            String tmpDescription = getArguments().getString(Constants.KEY_LESSON_REVIEW_DESCRIPTION);
            if(tmpDescription != null && !tmpDescription.isEmpty()){
                reviewText.setText(tmpDescription);
            }
            submitButton.setEnabled(true);
            submitButton.setText(R.string.edit);
        }

        //listener on rating bar changes
        reviewRating.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            if(rating == 0){
                submitButton.setEnabled(false);
            } else{
                submitButton.setEnabled(true);
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
        //int not float because rating 1 to 5 with width 1
        int rating = (int) reviewRating.getRating();
        Log.i(Constants.TAG, getClass().getSimpleName() + " -submitButtonListener-classLessonID " + classLessonID + " summary " + summary + " text " + text + " rating " + rating);

        studentEvaluateFragmentInterfaceCallback.setReview(classLessonID, summary, text, rating);
        getFragmentManager().beginTransaction().remove(StudentReviewFragment.this).commit();
    };
}
