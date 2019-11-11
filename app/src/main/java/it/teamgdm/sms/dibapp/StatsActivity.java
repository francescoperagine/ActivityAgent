package it.teamgdm.sms.dibapp;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

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
import java.util.Locale;
import java.util.Objects;

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

        Objects.requireNonNull(getSupportActionBar()).setTitle(className);

        HashMap<String, String> paramsAverage = new HashMap<>();
        paramsAverage.put(Constants.KEY_ACTION, Constants.GET_AVERAGE_RATING);
        paramsAverage.put(KEY_CLASS_ID, String.valueOf(classID));
        JSONArray responseAverage = DAO.getFromDB(paramsAverage);

        try {
            float rate = (float) responseAverage.getJSONObject(0).optDouble("avgrating");
            rateBar.setRating(rate);
            if((int) rate > 0) {
                String ratingStr = String.format(Locale.getDefault(),"%.1f", rate);
                rateValue.setText(ratingStr);
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

        //START HYSTOGRAM SETTINGS

        //BarChart entries declaration
        ArrayList<BarEntry> attendanceEntries = new ArrayList<>();
        ArrayList<BarEntry> reviewEntries = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();

        //query
        HashMap<String, String> paramsAttendance = new HashMap<>();
        paramsAttendance.put(Constants.KEY_ACTION, Constants.GET_ATTENDANCE_CHART);
        paramsAttendance.put(KEY_CLASS_ID, String.valueOf(classID));
        JSONArray responseAttendance = DAO.getFromDB(paramsAttendance);

        //Add elements to ArrayLists
        int totalLesson = responseAttendance.length();
        for (int i = 0; i < totalLesson; i++) {
            try {
                int attendance = responseAttendance.getJSONObject(i).optInt("attendance");
                int review = responseAttendance.getJSONObject(i).optInt("review");
                String date = responseAttendance.getJSONObject(i).optString("date");
                attendanceEntries.add(new BarEntry(i, attendance));
                reviewEntries.add(new BarEntry(i, review));
                labels.add(date);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //HYSTOGRAM SETTINGS

        BarDataSet barDataSet = new BarDataSet(attendanceEntries, getString(R.string.daily_attendance));
        barDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        barDataSet.setBarBorderColor(Color.BLACK);
        barDataSet.setBarBorderWidth(2.0f);
        barDataSet.setColor(getColor(R.color.primaryColor));
        barDataSet.setValueTextSize(12);
        barDataSet.setValueTextColor(Color.BLACK);

        BarDataSet barDataSet2 = new BarDataSet(reviewEntries, getString(R.string.review_per_lesson));
        barDataSet2.setAxisDependency(YAxis.AxisDependency.LEFT);
        barDataSet2.setBarBorderColor(Color.BLACK);
        barDataSet2.setBarBorderWidth(2.0f);
        barDataSet2.setColor(getColor(R.color.secondaryColor));
        barDataSet2.setValueTextSize(12);
        barDataSet2.setValueTextColor(Color.BLACK);

        //FORMATTER (value on bars from float to integer)
        barDataSet.setValueFormatter(new BarChartIntergerFormatter());
        barDataSet2.setValueFormatter(new BarChartIntergerFormatter());

        BarData barData = new BarData(barDataSet, barDataSet2);

        barData.setHighlightEnabled(false);

        barChart.getDescription().setText("");
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

        //barChart.setFitBars(true);

        barData.setBarWidth(0.4f); // set the width of each bar
        barChart.setData(barData);
        barChart.groupBars(-0.5f, 0.2f, 0f); // perform the "explicit" grouping
        barChart.setVisibleXRangeMaximum(6); // allow 6 values to be displayed at once on the x-axis, not more
        barChart.moveViewToX(-0.5f); //start at the beginning of the chart
        barChart.invalidate(); // refresh

    }

    @Override
    protected int getLayoutResource() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -getLayoutResource-");
        return R.layout.stats_activity;
    }

}
