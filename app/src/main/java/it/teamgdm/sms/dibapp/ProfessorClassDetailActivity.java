package it.teamgdm.sms.dibapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Objects;

public class ProfessorClassDetailActivity extends BaseActivity {

    Bundle savedInstanceState;
    RecyclerView recyclerView;
    TextView textViewEmptyClassList;
    int classID = 0;
    String className;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onCreate-");
        super.onCreate(savedInstanceState);
        Intent fromClassListIntent = getIntent();
        if(fromClassListIntent.hasExtra(Constants.KEY_CLASS_ID)) {
            classID = fromClassListIntent.getIntExtra(Constants.KEY_CLASS_ID, 0);
            className = DAO.getClassName(classID);
            Log.i(Constants.TAG, getClass().getSimpleName() + " -onCreate-classID " + classID);
        }

        recyclerView = findViewById(R.id.class_list);
        textViewEmptyClassList = findViewById(R.id.class_list_empty);
        Objects.requireNonNull(getSupportActionBar()).setTitle(className);
        setupRecyclerView(classID);
    }

    @Override
    protected int getLayoutResource() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -getLayoutResource-");
        return R.layout.item_list_activity;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onCreateOptionsMenu-");
        getMenuInflater().inflate(R.menu.stats_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onOptionsItemSelected-");
        int id = item.getItemId();
        if (id == R.id.statsButton) {
            Intent statsIntent = new Intent(this, StatsActivity.class);
            statsIntent.putExtra(Constants.KEY_CLASS_ID, classID);
            statsIntent.putExtra(Constants.KEY_CLASS_NAME, className);
            startActivity(statsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupRecyclerView(int classID) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -setupRecyclerView-");
        LessonList lessonListData = new ProfessorTeaching();
        JSONArray lessonListLoader = DAO.getLessonList(classID, Constants.KEY_ROLE_PROFESSOR);
        Log.i(Constants.TAG, getClass().getSimpleName() + " -setupRecyclerView-" + lessonListLoader);
        lessonListData.setLessonList(lessonListLoader);
        ArrayList<Lesson> lessonList = lessonListData.getLessonList();
        if(lessonList.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            textViewEmptyClassList.setVisibility(View.VISIBLE);
        } else {
            Log.i(Constants.TAG, getClass().getSimpleName() + " -setupRecyclerView- classname " + lessonList);
            recyclerView.setVisibility(View.VISIBLE);
            recyclerView.setAdapter(new ClassRecyclerViewAdapter(this, lessonList));
            textViewEmptyClassList.setVisibility(View.GONE);
        }
    }
}
