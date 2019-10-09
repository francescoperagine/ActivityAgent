package it.teamgdm.sms.dibapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

public class ProfessorClassDetailActivity extends AppCompatActivity {

    private AppCompatTextView professorClassName;
    private int classID;
    private String className;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onCreate-");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professor_class_detail);

        professorClassName = findViewById(R.id.professorClassName);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onStart-");
        if( getIntent().getAction().equals(Constants.KEY_CLASS_LESSON_DETAIL_ACTION_PROFESSOR)) {
            classID = getIntent().getIntExtra(Constants.KEY_CLASS_ID, 0);
            className = getIntent().getStringExtra(Constants.KEY_CLASS_NAME);
            professorClassName.setText(className);
        }
    }
}
