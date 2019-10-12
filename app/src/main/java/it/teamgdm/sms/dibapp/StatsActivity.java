package it.teamgdm.sms.dibapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static it.teamgdm.sms.dibapp.Constants.KEY_CLASS_ID;

public class StatsActivity extends BaseActivity {

    BarChart barChart;
    RatingBar rateBar;
    TextView totalNumText, rateValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        rateBar = findViewById(R.id.averageRate);
        rateValue = findViewById(R.id.ratingValueProfStats);
        totalNumText = findViewById(R.id.totalNumber);
        barChart = findViewById(R.id.barGraph);

        //QUERY

        int classID = getIntent().getIntExtra(Constants.KEY_CLASS_ID, 0);
        String className = getIntent().getStringExtra(Constants.KEY_CLASS_NAME);

        getSupportActionBar().setTitle(className);

        HashMap<String, String> paramsAverage = new HashMap<>();
        paramsAverage.put(Constants.KEY_ACTION, Constants.GET_AVERAGE_RATING);
        paramsAverage.put(KEY_CLASS_ID, String.valueOf(classID));
        JSONArray responseAverage = DAO.getFromDB(paramsAverage);

        try {
            float rate = (float) responseAverage.getJSONObject(0).optDouble("avgrating");
            rateBar.setRating(rate);
            if((int) rate > 0) {
                String rating = Float.toString(rate);
                rateValue.setText(rating);
            } else {
                rateValue.setText("0.0");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        HashMap<String, String> paramsTotal = new HashMap<>();
        paramsTotal.put(Constants.KEY_ACTION, Constants.GET_TOTAL_MEMBERS);
        paramsTotal.put(KEY_CLASS_ID, String.valueOf(classID));
        JSONArray responseTotal = DAO.getFromDB(paramsTotal);

        try {
            int total = responseTotal.getJSONObject(0).optInt("count");
            totalNumText.setText(String.valueOf(total));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        List<BarEntry> entries = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<String> ();

        HashMap<String, String> paramsAttendance = new HashMap<>();
        paramsAttendance.put(Constants.KEY_ACTION, Constants.GET_ATTENDANCE_CHART);
        paramsAttendance.put(KEY_CLASS_ID, String.valueOf(classID));
        JSONArray responseAttendance = DAO.getFromDB(paramsAttendance);

        //Add elements to arraylist
        int totalLesson = responseAttendance.length();
        for (int i = 0; i < totalLesson; i++) {
            try {
                int attendance = responseAttendance.getJSONObject(i).optInt("attendance");
                String date = responseAttendance.getJSONObject(i).optString("date");
                entries.add(new BarEntry(i, attendance));
                labels.add(date);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }



        //HYSTOGRAM SETTINGS

        BarDataSet barDataSet = new BarDataSet(entries, "Daily attendance");
        barDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        barDataSet.setBarBorderColor(Color.BLACK);
        barDataSet.setBarBorderWidth(2.0f);
        barDataSet.setColor(Color.parseColor("#008577"));
        barDataSet.setValueTextSize(12);
        barDataSet.setValueTextColor(Color.BLACK);

        //FORMATTER (value on bars from float to integer)
        barDataSet.setValueFormatter(new BarChartIntergerFormatter());

        BarData barData = new BarData(barDataSet);

        barData.setHighlightEnabled(false);

        barChart.getDescription().setText("");
        barChart.getDescription().setTextSize(12);
        barChart.getAxisLeft().setAxisMinimum(0);
        //value on the left vertical axe from float to integer
        barChart.getAxisLeft().setGranularity(1.0f);
        barChart.getAxisRight().setAxisMinimum(0);
        //value on the right vertical axe from float to integer
        barChart.getAxisRight().setGranularity(1.0f);
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);

        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
        barChart.animateY(1000);

        barChart.getXAxis().setGranularityEnabled(true);
        barChart.getXAxis().setGranularity(1.0f);
        barChart.getXAxis().setLabelRotationAngle(45);
        barChart.getXAxis().setLabelCount(barDataSet.getEntryCount());

        barChart.setData(barData);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onCreateOptionsMenu-");
        getMenuInflater().inflate(R.menu.app_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onOptionsItemSelected-");
        int id = item.getItemId();
        switch (id) {
            case R.id.profileButton:
                Intent profileIntent = new Intent(this, ProfileActivity.class);
                startActivity(profileIntent);
                return true;
            case R.id.settingsButton:
                Intent settingIntent = new Intent(this, SettingsActivity.class);
                startActivity(settingIntent);
                return true;
            case R.id.logoutButton:
                FragmentManager fragmentManager = this.getSupportFragmentManager();
                LogoutDialogFragment logoutDialogFragment = new LogoutDialogFragment(this);
                logoutDialogFragment.show(fragmentManager, "logout_fragment");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected int getLayoutResource() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -getLayoutResource-");
        return R.layout.stats_activity;
    }

}
