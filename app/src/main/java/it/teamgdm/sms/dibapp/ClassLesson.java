package it.teamgdm.sms.dibapp;

import android.util.Log;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ClassLesson extends Exam implements Serializable {

    int classID;
    int lessonID;
    int year;
    int semester;
    float rating;
    int attendance;
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
        return "\nClassID \t " + classID + "\nLessonID \t " + lessonID + "\nName \t" + name + "\ncode \t" + code + "\nclassDescription \t" + classDescription + "\nyear \t" + year +
                "\nsemester \t" + semester + "\ndate \t" + getDate() + "\ntimeStart \t" + timeStart + "\ntimeEnd \t" + timeEnd +
                "\nlessonSummary \t" + lessonSummary + "\nlessonDescription \t" + lessonDescription;
    }

    String getDate() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -getDate-");
        return new SimpleDateFormat(Constants.DATE_FORMAT, Locale.getDefault()).format(timeStart);
    }

    String getTimeStringFromDate(Date date) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -getTimestampStringFromDate-" + date);
        return new SimpleDateFormat(Constants.TIME_FORMAT, Locale.getDefault()).format(date);
    }

    Date getTimestampDateFromString(String time) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -getTimeFromDatetimeString-"+ time);
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATETIME_FORMAT, Locale.getDefault());
        Date d = null;
        try {
            d = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d;
    }

    boolean isInProgress() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -isInProgress-");
        Date now = new Date();
        return now.after(timeStart) && now.before(timeEnd);
    }

}