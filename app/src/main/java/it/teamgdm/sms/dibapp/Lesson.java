package it.teamgdm.sms.dibapp;

import android.util.Log;

import androidx.annotation.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Lesson extends Exam  {

    private boolean expanded;
    int lessonID;
    int calendarID;
    float rating;
    int attendance;
    Date timeStart;
    Date timeEnd;
    String lessonSummary;
    String lessonDescription;

    boolean isExpanded() {
        return expanded;
    }

    void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    Lesson() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -Lesson-");
    }

    @NonNull
    public String toString() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -toString-");
        return "\nClassID \t " + classID + "\nLessonID \t " + lessonID + "\nName \t" + className + "\nclassCode \t" + classCode + "\nclassDescription \t" + classDescription + "\nclassYear \t" + classYear +
                "\nclassSemester \t" + classSemester + "\ndate \t" + getDate() + "\ntimeStart \t" + timeStart + "\ntimeEnd \t" + timeEnd +
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

    Date getTimestampFromString(String time) {
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
        TimeZone tz = TimeZone.getDefault();
        if(tz.inDaylightTime(new Date())) {
            now = new Date(System.currentTimeMillis() + 3600 * 1000);
        }
        Log.i(Constants.TAG, getClass().getSimpleName() + " -isInProgress- now " + now);
        return now.after(timeStart) && now.before(timeEnd);
    }

}