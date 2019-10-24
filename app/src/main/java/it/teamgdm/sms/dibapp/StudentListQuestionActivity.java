package it.teamgdm.sms.dibapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
                JSONObject obj = response.getJSONObject(i);
                String qst = obj.optString(Constants.KEY_QUESTION);
                int id = obj.optInt(Constants.KEY_QUESTION_ID);
                int rate = obj.optInt(Constants.KEY_QUESTION_RATE);
                String dateStr = obj.getString(Constants.KEY_QUESTION_TIME);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date dateQst = sdf.parse(dateStr);
                Question question = new Question(id, qst, rate, dateQst);
                questionArray.add(question);
                Log.i(Constants.TAG, getClass().getSimpleName() + " -onCreate-Question " + question.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ParseException e) {
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
