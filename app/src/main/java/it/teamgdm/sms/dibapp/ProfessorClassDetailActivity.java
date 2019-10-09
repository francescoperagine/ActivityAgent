package it.teamgdm.sms.dibapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class ProfessorClassDetailActivity extends BaseActivity {

    private int classID;
    private String className;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onCreate-");
        super.onCreate(savedInstanceState);

        TextView professorClassName = findViewById(R.id.professorClassName);
    }

    @Override
    int getLayoutResource() {
        return R.layout.professor_class_detail_activity;
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onStart-");

            classID = getIntent().getIntExtra(Constants.KEY_CLASS_ID, 0);
            className = getIntent().getStringExtra(Constants.KEY_CLASS_NAME);
        //    professorClassName.setText(className);

    }
}
