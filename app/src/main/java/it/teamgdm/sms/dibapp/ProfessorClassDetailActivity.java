package it.teamgdm.sms.dibapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;

import java.util.ArrayList;

public class ProfessorClassDetailActivity extends BaseActivity {

    private boolean mTwoPane;

    Bundle savedInstanceState;
    RecyclerView recyclerView;
    TextView textViewEmptyClassList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onCreate-");
        super.onCreate(savedInstanceState);
        Intent fromClassListIntent = getIntent();
        int classID = 0;
        if(fromClassListIntent.hasExtra(Constants.KEY_CLASS_ID)) {
            classID = fromClassListIntent.getIntExtra(Constants.KEY_CLASS_ID, 0);
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

        setupRecyclerView(classID);
    }

    @Override
    protected int getLayoutResource() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -getLayoutResource-");
        return R.layout.item_list_activity;
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
            Log.i(Constants.TAG, getClass().getSimpleName() + " -setupRecyclerView-");
            recyclerView.setVisibility(View.VISIBLE);
            recyclerView.setAdapter(new ProfessorClassDetailActivity.ClassRecyclerViewAdapter(this, lessonList, mTwoPane));
            textViewEmptyClassList.setVisibility(View.GONE);
        }
    }

    public class ClassRecyclerViewAdapter extends RecyclerView.Adapter<ProfessorClassDetailActivity.ClassRecyclerViewAdapter.ViewHolder> {
        private final ProfessorClassDetailActivity mParentActivity;
        private final boolean mTwoPane;
        ArrayList<Lesson> lessonList;
        private int mExpandedPosition =-1;

        ClassRecyclerViewAdapter(ProfessorClassDetailActivity parent, ArrayList<Lesson> lessonList, boolean twoPane) {
            Log.i(Constants.TAG, getClass().getSimpleName() + " -ClassRecyclerViewAdapter-");
            this.lessonList = lessonList;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }
/*
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                int classID = (Integer) view.getTag();
                Log.i(Constants.TAG, getClass().getSimpleName() + " ClassRecyclerViewAdapter-OnClickListener-");
                if (mTwoPane) {
                    Log.i(Constants.TAG, getClass().getSimpleName() + " ClassRecyclerViewAdapter-OnClickListener-mTwoPane- arguments");
               //     ProfessorClassDetailFragment detailFragment = ProfessorClassDetailFragment.newInstance(classID, true);
               //     mParentActivity.getSupportFragmentManager().beginTransaction().replace(R.id.class_detail_container, detailFragment).commit();
                } else {
                    Context context = view.getContext();
                    Intent profClassDetailIntent = new Intent (context, ProfessorClassDetailActivity.class);
                    profClassDetailIntent.putExtra(Constants.KEY_CLASS_ID, classID);
                    context.startActivity(profClassDetailIntent);
                }
            }
        };
*/
        @NonNull
        @Override
        public ProfessorClassDetailActivity.ClassRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Log.i(Constants.TAG, getClass().getSimpleName() + " -onCreateViewHolder-");
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.professor_lesson_list_content, parent, false);
            return new ProfessorClassDetailActivity.ClassRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ProfessorClassDetailActivity.ClassRecyclerViewAdapter.ViewHolder holder, int position) {
            Log.i(Constants.TAG, getClass().getSimpleName() + " -onBindViewHolder-");
            final boolean isExpanded = position==mExpandedPosition;
            holder.lessonName.setText(lessonList.get(position).name);
            holder.lessonName.setVisibility(View.VISIBLE);
            holder.lessonDetail.setVisibility(isExpanded?View.VISIBLE:View.GONE);
            holder.itemView.setActivated(isExpanded);
            holder.itemView.setOnClickListener(v -> {
                mExpandedPosition = isExpanded ? -1:position;
                TransitionManager.beginDelayedTransition(recyclerView);
                notifyDataSetChanged();
            });


            holder.itemView.setTag(lessonList.get(position).lessonID);

            //checking if lesson is in progress
            if(lessonList.get(position).isInProgress()) {
                holder.lessonInProgress.setText(R.string.lesson_in_progress);
                holder.lessonInProgress.setBackgroundColor(Color.GREEN);
            } else {
                holder.lessonInProgress.setText(R.string.lesson_not_in_progress);
                holder.lessonInProgress.setEnabled(false);
            }

            String lessonCalendarTime = getString(R.string.from) + lessonList.get(position).getDate() + " - " + lessonList.get(position).timeStart + " - " + getString(R.string.to) + " " +  lessonList.get(position).timeEnd;
            holder.lessonTime.setText(lessonCalendarTime);

            String attendance = getString(R.string.attendance) + lessonList.get(position).attendance;
            holder.lessonAttendance.setText(attendance);

            holder.ratingBarProf.setRating(lessonList.get(position).rating);

            holder.questionButtonProf.setOnClickListener(v -> {
                // Perform action on click
                Intent questionsListIntent = new Intent(getApplicationContext(), ProfessorListQuestionActivity.class);
                questionsListIntent.putExtra(Constants.KEY_LESSON_ID, lessonList.get(position).lessonID);
                startActivity(questionsListIntent);
            });

            holder.reviewButtonProf.setOnClickListener(v -> {
                // Perform action on click
                Intent reviewsListIntent = new Intent(getApplicationContext(), ProfessorListReviewActivity.class);
                reviewsListIntent.putExtra(Constants.KEY_LESSON_ID, lessonList.get(position).lessonID);
                startActivity(reviewsListIntent);
            });
        }

        @Override
        public int getItemCount() {
            Log.i(Constants.TAG, getClass().getSimpleName() + " -getItemCount-");
            return lessonList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView lessonName;
            final ViewGroup lessonDetail;
            final TextView lessonInProgress;
            final TextView lessonTime;
            final TextView lessonAttendance;
            final RatingBar ratingBarProf;
            final Button questionButtonProf;
            final Button reviewButtonProf;

            ViewHolder(View view) {
                super(view);
                Log.i(Constants.TAG, getClass().getSimpleName() + " -ViewHolder-");
                lessonName = view.findViewById(R.id.lessonName);

                lessonDetail = view.findViewById(R.id.lessonDetail);
                ratingBarProf = view.findViewById(R.id.ratingBarProf);
                lessonInProgress = view.findViewById(R.id.lessonInProgress);
                lessonTime = view.findViewById(R.id.lessonTime);
                lessonAttendance = view.findViewById(R.id.attendance);

                questionButtonProf = view.findViewById(R.id.questionButton);
                reviewButtonProf = view.findViewById(R.id.reviewButton);

            }
        }
    }
}
