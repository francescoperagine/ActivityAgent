package it.teamgdm.sms.dibapp;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class StudentListQuestionActivity extends BaseActivity {

    //create ArrayList of String
    private ArrayList<Question> questionArray = new ArrayList<>();
    private static boolean addQuestionButtonStatus;
    View newQuestionLayout;
    TextView question_list_empty;
    EditText questionText;
    Button addQuestionButton, submitQuestion, cancelQuestion;
    int lessonID;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //create object of listview
        ListView listView= findViewById(R.id.listviewstudent);
        newQuestionLayout = findViewById(R.id.addQuestionContainer);
        question_list_empty = findViewById(R.id.question_list_empty);
        addQuestionButton = findViewById(R.id.addQuestionButton);
        questionText = findViewById(R.id.questionText);
        submitQuestion = findViewById(R.id.questionSubmitButton);
        cancelQuestion = findViewById(R.id.questionCancelButton);

        lessonID = getIntent().getIntExtra(Constants.KEY_LESSON_ID, 0);
        String className = getIntent().getStringExtra(Constants.KEY_CLASS_NAME);
        String date = getIntent().getStringExtra(Constants.KEY_LESSON_DATE);

        getSupportActionBar().setTitle(className + " - " + date);

        //query
        JSONArray response = DAO.getLessonQuestion(lessonID);

        Log.i(Constants.TAG, "QUESTION RESPONSE = " + response.toString() + " lenght " + response.length());

        //Add elements to arraylist
        int totalQuestion = response.length();
        if(totalQuestion != 0) {
            question_list_empty.setVisibility(View.GONE);
            for (int i = 0; i < totalQuestion; i++) {
                try {
                    JSONObject obj = response.getJSONObject(i);
                    String qst = obj.optString(Constants.KEY_QUESTION);
                    int id = obj.optInt(Constants.KEY_QUESTION_ID);
                    int rate = obj.optInt(Constants.KEY_QUESTION_RATE);
                    String dateStr = obj.getString(Constants.KEY_QUESTION_TIME);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                    Date dateQst = sdf.parse(dateStr);
                    Question question = new Question(id, qst, rate, dateQst);
                    questionArray.add(question);
                    Log.i(Constants.TAG, getClass().getSimpleName() + " -onCreate-Question " + question.toString());
                } catch (JSONException | ParseException e) {
                    e.printStackTrace();
                }
            }
        } else {
            question_list_empty.setVisibility(View.VISIBLE);
        }

        cancelQuestion.setOnClickListener(cancelQuestionListener);
        submitQuestion.setOnClickListener(submitQuestionListener);

        StudentQuestionAdapter studentQuestionAdapter = new StudentQuestionAdapter(this, R.layout.question_item, questionArray);
        //Create Adapter
        //ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, questionArray);



        //assign adapter to listview
        listView.setAdapter(studentQuestionAdapter);

    }

    private final View.OnClickListener cancelQuestionListener = v -> {
        String input = questionText.getText().toString();
        Log.i(Constants.TAG, getClass().getSimpleName() + " -submitButtonListener-classLessonID input" + input);
        newQuestionLayout.setVisibility(View.GONE);
    };

    private final View.OnClickListener submitQuestionListener = v -> {
        String input = questionText.getText().toString();
        Log.i(Constants.TAG, getClass().getSimpleName() + " -submitButtonListener-classLessonID input" + input);

        sendQuestion(lessonID, input);
        newQuestionLayout.setVisibility(View.GONE);
    };

    @Override
    protected void onStart() {
        super.onStart();
        question_list_empty.setVisibility(View.GONE);
    }

    public void sendQuestion(int lessonID, String input) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -sendQuestion-");
        if(DAO.sendQuestion(lessonID, input)) {
            Toast.makeText(this, getString(R.string.question_sent), Toast.LENGTH_SHORT).show();
            Log.i(Constants.TAG, getClass().getSimpleName() + " -sendQuestion-question sent-");
        } else {
            Toast.makeText(this, getResources().getString(R.string.question_not_sent), Toast.LENGTH_SHORT).show();
            Log.i(Constants.TAG, getClass().getSimpleName() + " -sendQuestion-question not sent-");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onOptionsItemSelected-");
        int id = item.getItemId();
        if (id == R.id.addQuestionButton) {
            if(!addQuestionButtonStatus) {
                newQuestionLayout.setVisibility(View.VISIBLE);
                item.setIcon(R.drawable.ic_cancel_24dp);
            } else {
                newQuestionLayout.setVisibility(View.GONE);
                item.setIcon(R.drawable.ic_add_24dp);
            }
            addQuestionButtonStatus = !addQuestionButtonStatus;
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onCreateOptionsMenu-");
        getMenuInflater().inflate(R.menu.question_menu, menu);
        return true;
    }

    @Override
    int getLayoutResource() {
        return R.layout.student_question_list_activity;
    }

}
