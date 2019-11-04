package it.teamgdm.sms.dibapp;

import android.content.Intent;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ClassRecyclerViewAdapter extends RecyclerView.Adapter<ClassRecyclerViewAdapter.ViewHolder> {
    private final ProfessorClassDetailActivity parent;
    private final boolean mTwoPane;
    private ArrayList<Lesson> lessonList;

    ClassRecyclerViewAdapter(ProfessorClassDetailActivity parent, ArrayList<Lesson> lessonList, boolean twoPane) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -ClassRecyclerViewAdapter-");
        this.lessonList = lessonList;
        this.parent = parent;
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
            TransitionManager.beginDelayedTransition(parent.recyclerView);
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
            lessonInProgressImage = view.findViewById(R.id.lessonInProgressProfessorIcon);
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

            String title = parent.getString(R.string.lesson_of) + " " + lesson.getDate();
            lessonTitle.setText(title);

            String lessonCalendarTime = parent.getString(R.string.from) + " " + lesson.getTimeStringFromDate(lesson.timeStart) + " " + parent.getString(R.string.to) + " " +  lesson.getTimeStringFromDate(lesson.timeEnd);
            lessonTime.setText(lessonCalendarTime);

            boolean expanded = lesson.isExpanded();
            lessonDetail.setVisibility(expanded ? View.VISIBLE : View.GONE);

            //checking if lesson is in progress
            if(lesson.isInProgress()) {
                lessonInProgressImage.setVisibility(View.VISIBLE);
                lessonInProgressImage.setColorFilter(ContextCompat.getColor(parent.getApplicationContext(), R.color.secondaryColor));
                //      lessonInProgressImage.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorSecondary));
            } else{
                lessonInProgressImage.setVisibility(View.INVISIBLE);
            }
            ratingBarProf.setRating(lesson.rating);
            ratingBarProf.setIsIndicator(true);

            if(lesson.rating > 0) {
                String rating = String.format("%.1f", lesson.rating);
                ratingValueProf.setText(rating);
            } else {
                ratingValueProf.setText("0.0");
            }

            String attendance = parent.getString(R.string.attendance) + lesson.attendance;
            lessonAttendance.setText(attendance);

            //checking if there are any questions
            if (DAO.questionCount(lesson.lessonID) == 0){
                questionButtonProf.setEnabled(false);
            } else {
                questionButtonProf.setEnabled(true);
            }

            questionButtonProf.setOnClickListener(v -> {
                // Perform action on click
                Intent questionsListIntent = new Intent(parent, ProfessorListQuestionActivity.class);
                questionsListIntent.putExtra(Constants.KEY_LESSON_ID, lesson.lessonID);
                questionsListIntent.putExtra(Constants.KEY_LESSON_DATE, lesson.getDate());
                parent.startActivity(questionsListIntent);
            });

            //checking if there are any reviews
            if( DAO.reviewCount(lesson.lessonID) == 0 ){
                reviewButtonProf.setEnabled(false);
            } else{
                reviewButtonProf.setEnabled(true);
            }

            reviewButtonProf.setOnClickListener(v -> {
                // Perform action on click
                Intent reviewsListIntent = new Intent(parent, ProfessorListReviewActivity.class);
                reviewsListIntent.putExtra(Constants.KEY_LESSON_ID, lesson.lessonID);
                reviewsListIntent.putExtra(Constants.KEY_LESSON_DATE, lesson.getDate());
                parent.startActivity(reviewsListIntent);
            });
        }
    }
}