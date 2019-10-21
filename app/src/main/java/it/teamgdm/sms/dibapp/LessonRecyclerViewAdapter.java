package it.teamgdm.sms.dibapp;

import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.Geofence;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class LessonRecyclerViewAdapter extends RecyclerView.Adapter<LessonRecyclerViewAdapter.ViewHolder> {

    private ArrayList<Lesson> lessonList;
    private StudentLessonListActivity parent;
    static int currentLessonReview = 0;

    LessonRecyclerViewAdapter(StudentLessonListActivity parent, ArrayList<Lesson> lessonList, boolean twoPane) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -ClassRecyclerViewAdapter-");
        this.parent = parent;
        this.lessonList = lessonList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onCreateViewHolder-");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_lesson_list_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onBindViewHolder-");

        Lesson lesson = lessonList.get(position);
        viewHolder.bind(lesson);
        viewHolder.studentLessonContainer.setOnClickListener(v -> {
            Log.i(Constants.TAG, getClass().getSimpleName() + " -onBindViewHolder-setOnClickListener");
            // Get the current state of the item
            boolean expanded = lesson.isExpanded();
            // Change the state
            lesson.setExpanded(! expanded);
            // Notify the adapter that item has changed
            TransitionManager.beginDelayedTransition(parent.recyclerView);
            notifyItemChanged(position);
        });
    }

    @Override
    public int getItemCount() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -getItemCount-");
        return lessonList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements
            StudentReviewFragment.StudentEvaluateFragmentInterface,
            StudentQuestionFragment.StudentQuestionFragmentInterface,
            DibappBroadcastReceiver.GeofenceReceiverInterface {

        Lesson lesson;
        final TextView titleView, classDescription, lessonSummary, lessonDescription, lessonTime, bottomMenuAlternateView;
        final ProgressBar lessonProgressBar;
        final ImageView lessonInProgressImage;
        final View studentLessonContainer, studentLessonDetail, studentLessonBottomMenu;
        View reviewContainer;
        View lessonContainer;
        View holder;

        ToggleButton buttonPartecipate, buttonReview;
        Button buttonQuestion;

        ViewHolder(View view) {
            super(view);
            Log.i(Constants.TAG, getClass().getSimpleName() + " -ViewHolder-");
            holder = view;
            titleView = view.findViewById(R.id.className);
            classDescription = view.findViewById(R.id.classDescription);
            lessonSummary = view.findViewById(R.id.lessonSummary);
            lessonDescription = view.findViewById(R.id.lessonDescription);
            lessonTime = view.findViewById(R.id.studentLessonTime);
            lessonInProgressImage = view.findViewById(R.id.lessonInProgressImage);

            studentLessonContainer = view.findViewById(R.id.studentLessonContainer);
            studentLessonDetail = view.findViewById(R.id.studentLessonDetail);
            studentLessonBottomMenu = view.findViewById(R.id.bottomMenu);

            lessonProgressBar = view.findViewById(R.id.timeProgressBar);

            buttonPartecipate = view.findViewById(R.id.partecipateButton);
            buttonReview = view.findViewById(R.id.reviewButton);
            buttonQuestion = view.findViewById(R.id.questionButton);

            bottomMenuAlternateView = view.findViewById(R.id.bottomMenuAlternateView);

            lessonContainer = view.findViewById(R.id.lessonContainer);
            reviewContainer = view.findViewById(R.id.reviewContainer);
            reviewContainer.setId(View.generateViewId());
        }

        void bind(Lesson lesson) {
            Log.i(Constants.TAG, getClass().getSimpleName() + " -bind- lesson" + lesson);

            this.lesson = lesson;

            boolean expanded = lesson.isExpanded();
            studentLessonDetail.setVisibility(expanded ? View.VISIBLE : View.GONE);
            studentLessonBottomMenu.setVisibility(expanded ? View.VISIBLE : View.GONE);

            titleView.setText(lesson.className);
            String lessonCalendarTime = parent.getString(R.string.from) + " " + lesson.getTimeStringFromDate(lesson.timeStart) + " " + parent.getString(R.string.to) + " " +  lesson.getTimeStringFromDate(lesson.timeEnd);
            lessonTime.setText(lessonCalendarTime);

            setTextViewContent(classDescription, lesson.classDescription);
            setTextViewContent(lessonSummary, lesson.lessonSummary);
            setTextViewContent(lessonDescription, lesson.lessonDescription);
            setLessonInProgressImage();

            setProgressTimeBar();

            buttonPartecipate.setOnClickListener(partecipateButtonListener);
            buttonReview.setOnClickListener(evaluateButtonListener);
            buttonQuestion.setOnClickListener(questionButtonListener);

            featurePanelHandler(lesson.isInProgress(), null);
        }

        void setTextViewContent(TextView textView, String text) {
            Log.i(Constants.TAG, getClass().getSimpleName() + " -setTextViewContent-");
            if(text == null || text.isEmpty() || text.equals("null")) {
                textView.setVisibility(View.GONE);
            } else {
                textView.setVisibility(View.VISIBLE);
                textView.setText(text);
            }
        }

        private void setLessonInProgressImage() {
            Log.i(Constants.TAG, getClass().getSimpleName() + " -setLessonInProgressImage-");
            if (lesson.isInProgress()) {
                lessonInProgressImage.setVisibility(View.VISIBLE);
                lessonInProgressImage.setColorFilter(ContextCompat.getColor(parent, R.color.secondaryColor));
            } else{
                lessonInProgressImage.setVisibility(View.INVISIBLE);
            }
        }

        private void setProgressTimeBar() {
            Log.i(Constants.TAG, getClass().getSimpleName() + " -setProgressTimeBar-");
            int progress = getTimeProgression();
            lessonProgressBar.setProgress(progress);
            if(progress == 100) {
                Log.i(Constants.TAG, getClass().getSimpleName() + " -bind- lesson progress " + progress);
                studentLessonBottomMenu.setVisibility(View.GONE);
            }
        }

        private int getTimeProgression() {
            double timeStartMillis = lesson.timeStart.getTime();
            double timeEndMillis = lesson.timeEnd.getTime();
            double lessonTotalDuration =  (timeEndMillis - timeStartMillis);
            double lessonActualProgress = new Date().getTime() - timeStartMillis;
            double lessonProgression = (lessonActualProgress / lessonTotalDuration ) * 100;
            int progress;
            if(lessonProgression > 100 ) {
                progress = 100;
            } else progress = (int) lessonProgression;
            return progress;
        }

        private final View.OnClickListener partecipateButtonListener = v -> {
            Log.i(Constants.TAG, getClass().getSimpleName() + " -partecipateButtonListener-");
            setAttendance(lesson.lessonID, buttonPartecipate.isChecked());
            featureActivator(buttonPartecipate.isChecked());
        };

        void setAttendance(int lessonID, boolean isUserAttendingLesson) {
            Log.i(Constants.TAG, getClass().getSimpleName() + " -setAttendance-" + isUserAttendingLesson);
            if(DAO.setAttendance(lessonID, isUserAttendingLesson)) {
                Toast.makeText(parent, parent.getString(R.string.attendance_set), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(parent, parent.getString(R.string.attendance_not_set), Toast.LENGTH_SHORT).show();
            }
        }

        private void featureActivator(boolean isUserAttendingLesson) {
            Log.i(Constants.TAG, getClass().getSimpleName() + " -featureActivator-");
            buttonPartecipate.setChecked(isUserAttendingLesson);
            buttonQuestion.setEnabled(isUserAttendingLesson);
            buttonReview.setEnabled(isUserAttendingLesson);
        }

        private void featurePanelHandler(boolean menuVisibility, String alternateViewText) {
            Log.i(Constants.TAG, getClass().getSimpleName() + " -featurePanelHandler-");
            if(menuVisibility) {
                bottomMenuAlternateView.setVisibility(View.GONE);
                studentLessonBottomMenu.setVisibility(View.VISIBLE);
                featureActivator(DAO.isUserAttendingLesson(lesson.lessonID, Session.getUserID()));
            } else {
                studentLessonBottomMenu.setVisibility(View.GONE);
                setTextViewContent(bottomMenuAlternateView, alternateViewText);
            }
        }
        private final View.OnClickListener evaluateButtonListener = v -> {
            Log.i(Constants.TAG, getClass().getSimpleName() + " -evaluateButtonListener-");
            if(buttonReview.isChecked()) {
                showEvaluationUI(lesson.lessonID);
            } else {
                hideEvaluationUI(lesson.lessonID);
            }
        };

        private void hideEvaluationUI(int lessonReviewID) {
            Log.i(Constants.TAG, getClass().getSimpleName() + " -hideEvaluationUI-lessonID " + lessonReviewID);
            Fragment fragment = parent.getSupportFragmentManager().findFragmentByTag(String.valueOf(lessonReviewID));
            currentLessonReview = 0;
            if(fragment != null){
                parent.getSupportFragmentManager().beginTransaction().remove(fragment).commit();
            }
        }

        private void showEvaluationUI(int lessonReviewID) {
            Log.i(Constants.TAG, getClass().getSimpleName() + " -showEvaluationUI-lessonID " + lessonReviewID);
            JSONArray existingEvaluation = DAO.getExistingEvaluation(Session.getUserID(), lessonReviewID);
            if(currentLessonReview != 0) hideEvaluationUI(currentLessonReview);
            StudentReviewFragment evaluateFragment = null;
            if(DAO.hasEvaluation(existingEvaluation)){
                JSONObject objResponse;
                try {
                    Log.i(Constants.TAG, getClass().getSimpleName() + " -showEvaluationUI-lessonID not null");
                    objResponse = existingEvaluation.getJSONObject(0);
                    evaluateFragment = StudentReviewFragment.newInstance(lessonReviewID, objResponse.optString("summary"), objResponse.optString("review"), (float) objResponse.optDouble("rating"));
                } catch (JSONException ignored) {
                }
            } else {
                Log.i(Constants.TAG, getClass().getSimpleName() + " -showEvaluationUI-lessonID null");
                evaluateFragment = StudentReviewFragment.newInstance(lessonReviewID);
            }
            evaluateFragment.setStudentEvaluateFragmentInterfaceCallback(this);
            currentLessonReview = lessonReviewID;

            parent.getSupportFragmentManager().beginTransaction().replace(reviewContainer.getId(), evaluateFragment, String.valueOf(lessonReviewID)).commit();
        }
/*
        private LinearLayout getNewLayout(int lessonID) {
            LinearLayout linearLayout = new LinearLayout(holder.getContext());
            linearLayout.setId(lessonID);
            lessonContainer.addV
            return linearLayout;
        }
*/
        private final View.OnClickListener questionButtonListener = v -> {
            Log.i(Constants.TAG, getClass().getSimpleName() + " -questionButtonListener-");
            showQuestionUI();
        };

        private void showQuestionUI() {
            Log.i(Constants.TAG, getClass().getSimpleName() + " -showQuestionUI-");
      /*    Intent questionIntent = new Intent(Constants.GET_LESSON_QUESTIONS);
            questionIntent.addFlags(parent, QuestionListActivity.class);
            questionIntent.putExtra(Constants.KEY_LESSON_ID, lesson.lessonID);
            parent.startActivity(questionIntent);
       */ }

        @Override
        public void sendQuestion(int lessonID, String input) {
            Log.i(Constants.TAG, getClass().getSimpleName() + " -sendQuestion-");
            if(DAO.sendQuestion(lessonID, input)) {
                Toast.makeText(parent, parent.getString(R.string.question_sent), Toast.LENGTH_SHORT).show();
                Log.i(Constants.TAG, getClass().getSimpleName() + " -sendQuestion-question sent-");
            } else {
                Toast.makeText(parent, parent.getResources().getString(R.string.question_not_sent), Toast.LENGTH_SHORT).show();
                Log.i(Constants.TAG, getClass().getSimpleName() + " -sendQuestion-question not sent-");
            }
        }

        @Override
        public void setReview(int lessonID, String summary, String review, int rating) {
            Log.i(Constants.TAG, getClass().getSimpleName() + " -setReview-");
            if(DAO.setReview(lessonID,summary,review,rating))  {
                buttonReview.setChecked(false);
                Toast.makeText(parent, parent.getString(R.string.review_sent), Toast.LENGTH_SHORT).show();
                Log.i(Constants.TAG, getClass().getSimpleName() + " -setReview-question sent-");
            } else {
                Toast.makeText(parent, parent.getString(R.string.review_not_sent), Toast.LENGTH_SHORT).show();
                Log.i(Constants.TAG, getClass().getSimpleName() + " -setReview-question not sent-");
            }
        }

        @Override
        public void onGeofenceTransitionAction(int geofenceReceiverAction) {
            Log.i(Constants.TAG, getClass().getSimpleName() + " -onGeofenceTransitionAction-"+ geofenceReceiverAction + " lesson in progress " + lesson.isInProgress() );
            if(lesson.isInProgress()) {
                setGeofenceResponse(geofenceReceiverAction);
            } else {
                featurePanelHandler(false, parent.getResources().getString(R.string.lesson_not_in_progress_text));
            }
        }

        void setGeofenceResponse(int geofenceTransitionAction) {
            Log.i(Constants.TAG, getClass().getSimpleName() + " -getBottomFragment-"+ geofenceTransitionAction);
            if (geofenceTransitionAction == Geofence.GEOFENCE_TRANSITION_DWELL) {
                String message = parent.getResources().getString(R.string.geofence_transition_dwelling);
                Log.i(Constants.TAG, getClass().getSimpleName() + " " + message);
                Toast.makeText(parent, message, Toast.LENGTH_SHORT).show();
                featurePanelHandler(true, "");
            } else {
                Log.i(Constants.TAG, getClass().getSimpleName() + " " + parent.getResources().getString(R.string.geofence_transition_left));
                featurePanelHandler(false, parent.getResources().getString(R.string.geofence_transition_left));
            }
        }


    }
}