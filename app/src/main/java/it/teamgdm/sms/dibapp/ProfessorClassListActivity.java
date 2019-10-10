package it.teamgdm.sms.dibapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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

/**
 * An activity representing a list of Exams. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link StudentLessonDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ProfessorClassListActivity extends BaseActivity {

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
        textViewEmptyClassList = findViewById(R.id.class_list_empty);

        setupRecyclerView();
    }

    @Override
    protected int getLayoutResource() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -getLayoutResource-");
        return R.layout.activity_class_list;
    }

    private void setupRecyclerView() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -setupRecyclerView-");
        ClassList classListData = new ProfessorTeaching();
        JSONArray classListLoader = DAO.getClassList(Session.getUserID(), Constants.KEY_ROLE_PROFESSOR);
        classListData.setClassList(classListLoader);
        ArrayList<ClassLesson> classList = classListData.getClassList();
        if(classList.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            textViewEmptyClassList.setVisibility(View.VISIBLE);
        } else {
            Log.i(Constants.TAG, getClass().getSimpleName() + " -setupRecyclerView-");
            recyclerView.setVisibility(View.VISIBLE);
            recyclerView.setAdapter(new ClassRecyclerViewAdapter(this, classList, mTwoPane));
            textViewEmptyClassList.setVisibility(View.GONE);
        }
    }

    public class ClassRecyclerViewAdapter extends RecyclerView.Adapter<ClassRecyclerViewAdapter.ViewHolder> {
        private final ProfessorClassListActivity mParentActivity;
        private final boolean mTwoPane;
        ArrayList<ClassLesson> classList;

        ClassRecyclerViewAdapter(ProfessorClassListActivity parent, ArrayList<ClassLesson> classList, boolean twoPane) {
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
                   // ProfessorClassDetailFragment detailFragment = ProfessorClassDetailFragment.newInstance(classID, true);
                   // mParentActivity.getSupportFragmentManager().beginTransaction().replace(R.id.class_detail_container, detailFragment).commit();
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
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Log.i(Constants.TAG, getClass().getSimpleName() + " -onCreateViewHolder-");
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.class_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
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
