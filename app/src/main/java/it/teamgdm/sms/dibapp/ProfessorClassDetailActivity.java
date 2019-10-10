package it.teamgdm.sms.dibapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;

import java.util.ArrayList;

public class ProfessorClassDetailActivity extends BaseActivity {

    private boolean mTwoPane;

    Bundle savedInstanceState;
    Intent fromClassListIntent;
    RecyclerView recyclerView;
    TextView textViewEmptyClassList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onCreate-");
        super.onCreate(savedInstanceState);
        fromClassListIntent = getIntent();

        if (findViewById(R.id.class_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }



    }

    @Override
    int getLayoutResource() {
        return R.layout.professor_class_detail_activity;
    }

    @Override
    protected void onStart() {
        super.onStart();
        int classID;
        if (savedInstanceState == null) {
            // Gets all the lessons of the class
            classID = getIntent().getIntExtra(Constants.KEY_CLASS_ID, 0);
            setupRecyclerView();

        }
    }

    private void setupRecyclerView() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -setupRecyclerView-");
        LessonList lessonListData = new ProfessorTeaching();
        JSONArray lessonListLoader = DAO.getLessonList(Session.getUserID(), Constants.KEY_ROLE_PROFESSOR);
        lessonListData.setLessonList(lessonListLoader);
        ArrayList<Lesson> classList = lessonListData.getLessonList();
        if(classList.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            textViewEmptyClassList.setVisibility(View.VISIBLE);
        } else {
            Log.i(Constants.TAG, getClass().getSimpleName() + " -setupRecyclerView-");
            recyclerView.setVisibility(View.VISIBLE);
            recyclerView.setAdapter(new ProfessorClassDetailActivity.ClassRecyclerViewAdapter(this, classList, mTwoPane));
            textViewEmptyClassList.setVisibility(View.GONE);
        }
    }

    public class ClassRecyclerViewAdapter extends RecyclerView.Adapter<ProfessorClassDetailActivity.ClassRecyclerViewAdapter.ViewHolder> {
        private final ProfessorClassDetailActivity mParentActivity;
        private final boolean mTwoPane;
        ArrayList<Lesson> classList;

        ClassRecyclerViewAdapter(ProfessorClassDetailActivity parent, ArrayList<Lesson> classList, boolean twoPane) {
            Log.i(Constants.TAG, getClass().getSimpleName() + " -ClassRecyclerViewAdapter-");
            this.classList = classList;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }

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

        @NonNull
        @Override
        public ProfessorClassDetailActivity.ClassRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Log.i(Constants.TAG, getClass().getSimpleName() + " -onCreateViewHolder-");
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.class_list_content, parent, false);
            return new ProfessorClassDetailActivity.ClassRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ProfessorClassDetailActivity.ClassRecyclerViewAdapter.ViewHolder holder, int position) {
            Log.i(Constants.TAG, getClass().getSimpleName() + " -onBindViewHolder-");
            holder.titleView.setText(classList.get(position).name);
            holder.itemView.setTag(classList.get(position).lessonID);
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount() {
            Log.i(Constants.TAG, getClass().getSimpleName() + " -getItemCount-");
            return classList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView titleView;

            ViewHolder(View view) {
                super(view);
                Log.i(Constants.TAG, getClass().getSimpleName() + " -ViewHolder-");
                titleView = view.findViewById(R.id.content);

            }
        }
    }
}
