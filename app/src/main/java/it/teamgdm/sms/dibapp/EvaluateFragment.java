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

import java.util.Objects;

public class EvaluateFragment extends BaseFragment {

    private int classLessonID;
    private EditText reviewSummary;
    private EditText reviewText;
    private RatingBar reviewRating;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onCreate-");
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        classLessonID = getArguments().getInt(Constants.KEY_CLASS_LESSON_ID, 0);
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

        fragmentCallback.setReview(classLessonID, summary, text, rating);
        Objects.requireNonNull(getDialog()).dismiss();
    };

}
