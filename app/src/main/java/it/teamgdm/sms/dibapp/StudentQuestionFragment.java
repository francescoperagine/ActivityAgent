package it.teamgdm.sms.dibapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Objects;

public class StudentQuestionFragment extends DialogFragment {

    private StudentQuestionFragmentInterface studentQuestionFragmentInterfaceCallback;

    void setStudentQuestionFragmentInterfaceCallback(StudentQuestionFragmentInterface studentQuestionFragmentInterfaceCallback) {
        this.studentQuestionFragmentInterfaceCallback = studentQuestionFragmentInterfaceCallback;
    }

    interface StudentQuestionFragmentInterface {
        void sendQuestion(int lessonID, String input);
    }

    private int classLessonID;
    private TextView questionText;

    static StudentQuestionFragment newInstante(int classLessonID) {
        StudentQuestionFragment questionFragment = new StudentQuestionFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(Constants.KEY_LESSON_ID, classLessonID);
        questionFragment.setArguments(arguments);
        return questionFragment;
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
        return inflater.inflate(R.layout.student_question_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onViewCreated-");
        super.onViewCreated(view, savedInstanceState);

        Button cancelButton = view.findViewById(R.id.cancelQuestion);
        Button submitButton = view.findViewById(R.id.submitQuestion);

        cancelButton.setOnClickListener(cancelButtonListener);
        submitButton.setOnClickListener(submitButtonListener);

        questionText = view.findViewById(R.id.questionButton);
        questionText.addTextChangedListener(new EmptyTextButtonDisabler(submitButton, questionText));
    }

    private final View.OnClickListener cancelButtonListener = v -> {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -cancelButtonListener-");
        Objects.requireNonNull(getDialog()).dismiss();
    };

    private final View.OnClickListener submitButtonListener = v -> {
        String input = questionText.getText().toString();
        Log.i(Constants.TAG, getClass().getSimpleName() + " -submitButtonListener-classLessonID " + classLessonID + " input" + input);

        studentQuestionFragmentInterfaceCallback.sendQuestion(classLessonID, input);
        Objects.requireNonNull(getDialog()).dismiss();
    };


}
