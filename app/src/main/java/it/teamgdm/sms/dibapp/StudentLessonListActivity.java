package it.teamgdm.sms.dibapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * An activity representing a list of Exams. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link StudentLessonDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class StudentLessonListActivity extends BaseActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    Intent loginIntent;
    RecyclerView recyclerView;
    TextView textViewEmptyClassList;

    protected void onCreate(Bundle savedInstanceState) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onCreate-");
        super.onCreate(savedInstanceState);
        loginIntent = getIntent();

        if (findViewById(R.id.class_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        recyclerView = findViewById(R.id.class_list);
        textViewEmptyClassList = findViewById(R.id.lesson_list_empty);
        String today = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.getDefault()).format(new Date());
        getSupportActionBar().setTitle(getString(R.string.lesson_today_schedule) + Constants.KEY_BLANK + today);
        disableBackButton();
        setupRecyclerView();
    }

    @Override
    protected int getLayoutResource() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -getLayoutResource-");
        return R.layout.item_list_activity;
    }

    private void setupRecyclerView() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -setupRecyclerView-");
        LessonList lessonListData = new StudentCareer();
        JSONArray lessonListLoader = DAO.getLessonList(Session.getUserID(), Constants.KEY_ROLE_STUDENT);
        lessonListData.setLessonList(lessonListLoader);
        ArrayList<Lesson> classList = lessonListData.getLessonList();
        if(classList.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            textViewEmptyClassList.setVisibility(View.VISIBLE);
        } else {
            Log.i(Constants.TAG, getClass().getSimpleName() + " -setupRecyclerView-");
            recyclerView.setVisibility(View.VISIBLE);
            recyclerView.setAdapter(new LessonRecyclerViewAdapter(this, classList, mTwoPane));
            textViewEmptyClassList.setVisibility(View.GONE);
        }
    }


}
