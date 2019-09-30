package it.teamgdm.sms.dibapp;

import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Locale;

public class ClassLesson extends Exam implements Serializable {

    int classID;
    int lessonID;
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

    @NonNull
    public String toString() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -toString-");
        return "\nClassID \t " + classID + "\nLessonID \t " + lessonID + "\nName \t" +name + "\ncode \t" + code + "\nclassDescription \t" +classDescription + "\nyear \t" + year +
                "\nsemester \t" + semester + "\ndate \t" +getDateString() + "\ntimeStart \t" + getTimeString(timeStart) + "\ntimeEnd \t" + getTimeString(timeEnd) +
                "\nlessonSummary \t" + lessonSummary + "\nlessonDescription \t" + lessonDescription;
    }

    String getDateString() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -getDateString-");
        return new SimpleDateFormat(Constants.DATE_FORMAT, Locale.getDefault()).format(date);
    }

    String getTimeString(Date time) {
        Log.i(Constants.TAG, ClassLesson.class.getSimpleName() + " -getTimeStartString-");
        return new SimpleDateFormat(Constants.TIME_FORMAT, Locale.getDefault()).format(time);
    }

    Date setTime(String time) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -setTime-");
        Date sdf = null;
        try {
            sdf = new SimpleDateFormat(Constants.TIME_FORMAT, Locale.getDefault()).parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sdf;
    }
    Date setDate(String date) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -setDate-"+date);
        Date sdf = null;
        try {
            sdf = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.getDefault()).parse(date);
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
        Log.i(Constants.TAG, getClass().getSimpleName() + " -isInProgressOreo-");
        Date now = Date.from(Instant.now());
        return now.after(timeStart) && now.before(timeEnd);
    }

    private boolean isInProgressDefault() {
        return false;
    }

}
