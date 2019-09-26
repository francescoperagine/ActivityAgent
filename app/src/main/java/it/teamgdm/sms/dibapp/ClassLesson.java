package it.teamgdm.sms.dibapp;

import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.Instant;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class ClassLesson extends Exam {

    int year;
    int semester;
    Date date;
    Date timeStart;
    Date timeEnd;
    String lessonSummary;
    String lessonDescription;

    ClassLesson() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -ClassLesson-");
    }

    void setClassLesson(JSONObject classLesson) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -setClassLesson-");
        try {
            ID = classLesson.getInt(Constants.KEY_CLASS_ID);
            name = classLesson.getString(Constants.KEY_CLASS_NAME);
            code = classLesson.getInt(Constants.KEY_CLASS_CODE);
            classDescription = classLesson.getString(Constants.KEY_CLASS_DESCRIPTION);
            year = classLesson.getInt(Constants.KEY_CLASS_LESSON_YEAR);
            semester = classLesson.getInt(Constants.KEY_CLASS_LESSON_SEMESTER);
            date = setDate((classLesson.getString(Constants.KEY_CLASS_LESSON_DATE)));
            timeStart = setTime(classLesson.getString(Constants.KEY_CLASS_LESSON_TIME_START));
            timeEnd =  setTime(classLesson.getString(Constants.KEY_CLASS_LESSON_TIME_END));
            lessonSummary = classLesson.getString(Constants.KEY_CLASS_LESSON_SUMMARY);
            lessonDescription = classLesson.getString(Constants.KEY_CLASS_LESSON_DESCRIPTION);
        } catch (JSONException ignored) {
        }
    }
    @NonNull
    public String toString() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -toString-");
        return "\nName \t" +name + "\ncode \t" + code + "\nclassDescription \t" +classDescription + "\nyear \t" + year +
                "\nsemester \t" + semester + "\ndate \t" +date + "\ntimeStart \t" + timeStart + "\ntimeEnd \t" + timeEnd +
                "\nlessonSummary \t" + lessonSummary + "\nlessonDescription \t" + lessonDescription;
    }

    String getDateString() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -getDateString-");
        return String.valueOf(date);
    }

    String getTimeStartString() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -getTimeStartString-");
        return String.valueOf(timeStart);
    }

    Date setTime(String time) {
        Date sdf = null;
        try {
            sdf = new SimpleDateFormat("hh:mm:ss", Locale.getDefault()).parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sdf;
    }
    private Date setDate(String time) {
        Date sdf = null;
        try {
            sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sdf;
    }

    boolean isInProgress() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -isInProgress-");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            return isInProgressOreo();
        } else {
            return isInProgressDefault();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean isInProgressOreo() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -isInProgressOreo-timestart " + timeStart + " timeEnd " + timeEnd);

        Date now = Date.from(Instant.now());
        return now.after(timeStart) && now.before(timeEnd);
    }

    private boolean isInProgressDefault() {
        return false;
    }

}
