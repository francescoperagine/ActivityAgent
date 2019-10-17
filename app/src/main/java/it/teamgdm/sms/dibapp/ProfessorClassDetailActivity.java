package it.teamgdm.sms.dibapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;

import java.util.ArrayList;

public class ProfessorClassDetailActivity extends BaseActivity {

    private boolean mTwoPane;

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

        if (findViewById(R.id.class_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
        recyclerView = findViewById(R.id.class_list);
        textViewEmptyClassList = findViewById(R.id.class_list_empty);
        getSupportActionBar().setTitle(className);
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
        switch (id) {
            case R.id.statsButton:
                Intent statsIntent = new Intent(this, StatsActivity.class);
                statsIntent.putExtra(Constants.KEY_CLASS_ID, classID);
                statsIntent.putExtra(Constants.KEY_CLASS_NAME, className);
                startActivity(statsIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
            recyclerView.setAdapter(new ClassRecyclerViewAdapter(this, lessonList, mTwoPane));
            textViewEmptyClassList.setVisibility(View.GONE);
        }
    }

    public class ClassRecyclerViewAdapter extends RecyclerView.Adapter<ClassRecyclerViewAdapter.ViewHolder> {
        private final ProfessorClassDetailActivity mParentActivity;
        private final boolean mTwoPane;
        ArrayList<Lesson> lessonList;

        ClassRecyclerViewAdapter(ProfessorClassDetailActivity parent, ArrayList<Lesson> lessonList, boolean twoPane) {
            Log.i(Constants.TAG, getClass().getSimpleName() + " -ClassRecyclerViewAdapter-");
            this.lessonList = lessonList;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }

        @NonNull
        @Override
        public ClassRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Log.i(Constants.TAG, getClass().getSimpleName() + " -onCreateViewHolder-");
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.professor_lesson_list_content, parent, false);
            return new ClassRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ClassRecyclerViewAdapter.ViewHolder holder, int position) {
            Log.i(Constants.TAG, getClass().getSimpleName() + " -onBindViewHolder-");

            Lesson lesson = lessonList.get(position);
            holder.bind(lesson);
            holder.lessonTitle.setOnClickListener(v -> {
                // Get the current state of the item
                boolean expanded = lesson.isExpanded();
                // Change the state
                lesson.setExpanded(!expanded);
                // Notify the adapter that item has changed
                TransitionManager.beginDelayedTransition(recyclerView);
                notifyItemChanged(position);
            });
            holder.itemView.setTag(lessonList.get(position).lessonID);
        }

        @Override
        public int getItemCount() {
            Log.i(Constants.TAG, getClass().getSimpleName() + " -getItemCount-");
            return lessonList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView lessonTitle;
            final View lessonDetail;
            final ImageView lessonInProgressImage;
            final TextView lessonTime;
            final TextView lessonAttendance;
            final RatingBar ratingBarProf;
            final TextView ratingValueProf;
            final Button questionButtonProf;
            final Button reviewButtonProf;

            ViewHolder(View view) {
                super(view);
                Log.i(Constants.TAG, getClass().getSimpleName() + " -ViewHolder-");
                lessonTitle = view.findViewById(R.id.className);
                lessonInProgressImage = view.findViewById(R.id.lessonInProgressImageProfessor);
                lessonDetail = view.findViewById(R.id.lessonDetail);
                ratingBarProf = view.findViewById(R.id.ratingBarProf);
                ratingValueProf = view.findViewById(R.id.ratingValueProf);
                lessonTime = view.findViewById(R.id.professorLessonTime);
                lessonAttendance = view.findViewById(R.id.attendance);

                questionButtonProf = view.findViewById(R.id.questionButton);
                reviewButtonProf = view.findViewById(R.id.reviewButton);
            }

            void bind(Lesson lesson) {
                Log.i(Constants.TAG, getClass().getSimpleName() + " -bind- lesson" + lesson);

                String title = getString(R.string.lesson_of) + " " + lesson.getDate();
                lessonTitle.setText(title);

                String lessonCalendarTime = getString(R.string.from) + " " + lesson.getTimeStringFromDate(lesson.timeStart) + " " + getString(R.string.to) + " " +  lesson.getTimeStringFromDate(lesson.timeEnd);
                lessonTime.setText(lessonCalendarTime);

                boolean expanded = lesson.isExpanded();
                lessonDetail.setVisibility(expanded ? View.VISIBLE : View.GONE);

                //checking if lesson is in progress
                if(lesson.isInProgress()) {
                    lessonInProgressImage.setVisibility(View.VISIBLE);
                    lessonInProgressImage.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorLessonInProgressText));
                    lessonInProgressImage.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorLessonInProgressBackground));
                }
                ratingBarProf.setRating(lesson.rating);
                ratingBarProf.setIsIndicator(true);

                if(lesson.rating > 0) {
                    String rating = Float.toString(lesson.rating);
                    ratingValueProf.setText(rating);
                } else {
                    ratingValueProf.setText("0.0");
                }

                String attendance = getString(R.string.attendance) + lesson.attendance;
                lessonAttendance.setText(attendance);

                questionButtonProf.setOnClickListener(v -> {
                    // Perform action on click
                    Intent questionsListIntent = new Intent(getApplicationContext(), ProfessorListQuestionActivity.class);
                    questionsListIntent.putExtra(Constants.KEY_LESSON_ID, lesson.lessonID);
                    questionsListIntent.putExtra(Constants.KEY_LESSON_DATE, lesson.getDate());
                    startActivity(questionsListIntent);
                });

                reviewButtonProf.setOnClickListener(v -> {
                    // Perform action on click
                    Intent reviewsListIntent = new Intent(getApplicationContext(), ProfessorListReviewActivity.class);
                    reviewsListIntent.putExtra(Constants.KEY_LESSON_ID, lesson.lessonID);
                    reviewsListIntent.putExtra(Constants.KEY_LESSON_DATE, lesson.getDate());
                    startActivity(reviewsListIntent);
                });
            }


        }
    }
}
