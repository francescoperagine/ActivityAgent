package it.teamgdm.sms.dibapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class StudentListQuestionActivity extends BaseActivity {

    //create ArrayList of String
    private ArrayList<Question> questionArray = new ArrayList<>();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //create object of listview
        ListView listView= findViewById(R.id.listviewstudent);

        int lessonID = getIntent().getIntExtra(Constants.KEY_LESSON_ID, 0);
        String className = getIntent().getStringExtra(Constants.KEY_CLASS_NAME);
        String date = getIntent().getStringExtra(Constants.KEY_LESSON_DATE);

        getSupportActionBar().setTitle(className + " - " + date);

        //query
        JSONArray response = DAO.getLessonQuestion(lessonID);

        Log.i(Constants.TAG, "QUESTION RESPONSE = " + response.toString() + " lenght " + response.length());

        //Add elements to arraylist
        int totalQuestion = response.length();
        for (int i = 0; i < totalQuestion; i++) {
            try {
                Question question = new Question();
                question.setQuestion(response.getJSONObject(i).optString(Constants.KEY_QUESTION));
                question.setId(response.getJSONObject(i).optInt(Constants.KEY_QUESTION_ID, 0));
                question.setRate(response.getJSONObject(i).optInt(Constants.KEY_QUESTION_RATE, 0 ));
                questionArray.add(question);
                Log.i(Constants.TAG, getClass().getSimpleName() + " -onCreate-Question " + question.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        StudentQuestionAdapter studentQuestionAdapter = new StudentQuestionAdapter(this, R.layout.question_item, questionArray);
        //Create Adapter
        //ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, questionArray);



        //assign adapter to listview
        listView.setAdapter(studentQuestionAdapter);

    }

    @Override
    int getLayoutResource() {
        return R.layout.student_question_list_activity;
    }

}
