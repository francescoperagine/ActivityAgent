package it.teamgdm.sms.dibapp;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ProfessorListQuestionActivity extends BaseActivity {

    //create ArrayList of String
    ArrayList<Question> questionArray = new ArrayList<>();

    //the listview
    ListView listView;

    QuestionListAdapter adapterTime;
    QuestionListAdapter adapterRate;

    Boolean filterFlag = false;

    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onCreate-");
        super.onCreate(savedInstanceState);

        //create object of listview
        listView= findViewById(R.id.listview);

        int lessonID = getIntent().getIntExtra(Constants.KEY_LESSON_ID, 0);
        String date = getIntent().getStringExtra(Constants.KEY_LESSON_DATE);

        getSupportActionBar().setTitle(getResources().getString(R.string.question_title) + " - " + date);

        //query
        JSONArray response = DAO.getLessonQuestion(lessonID);

        Log.i(Constants.TAG, "QUESTION RESPONSE = " + response.toString() + response.length());

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
                questionArray.add(new Question(id, qst, rate, dateQst));

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        //creating sorted lists
        questionArray.sort(new ComparatorQuestionMostRecent());
        ArrayList<Question> questionListTime = (ArrayList<Question>) questionArray.clone();
        questionArray.sort(new ComparatorQuestionMostVoted());
        ArrayList<Question> questionListRate = (ArrayList<Question>) questionArray.clone();

        //creating the adapters
        adapterTime = new QuestionListAdapter(this, R.layout.professor_question_item, questionListTime);
        adapterRate = new QuestionListAdapter(this, R.layout.professor_question_item, questionListRate);

        //assign adapter to listview
        listView.setAdapter(adapterRate);

    }

    @Override
    int getLayoutResource() {
        return R.layout.professor_list_questions;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onCreateOptionsMenu-");
        getMenuInflater().inflate(R.menu.filter_question_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onOptionsItemSelected-");
        int id = item.getItemId();
        switch (id) {
            case R.id.filterButton:

                String toastSortText;

                if(filterFlag == false){
                    listView.setAdapter(adapterTime);
                    item.setIcon(R.drawable.ic_rate_filter);
                    filterFlag = true;
                    toastSortText = getString(R.string.sort_time);
                }
                else{
                    listView.setAdapter(adapterRate);
                    item.setIcon(R.drawable.ic_clock_filter);
                    filterFlag = false;
                    toastSortText = getString(R.string.sort_rate);
                }

                //if the filter button is pressed again before the previous toast is dismissed, this control dismiss it immediately
                if (toast != null) {
                    toast.cancel();
                }

                toast = Toast.makeText(getApplicationContext(), toastSortText, Toast.LENGTH_SHORT);
                toast.show();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
