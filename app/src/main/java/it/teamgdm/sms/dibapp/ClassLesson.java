package it.teamgdm.sms.dibapp;

import android.util.Log;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.sql.Time;

public class ClassLesson extends Exam {

    int year;
    int semester;
    Date date;
    Time timeStart;
    Time timeEnd;
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
            date = Date.valueOf((classLesson.getString(Constants.KEY_CLASS_LESSON_DATE)));
            timeStart = Time.valueOf(classLesson.getString(Constants.KEY_CLASS_LESSON_TIME_START));
            timeEnd =  Time.valueOf(classLesson.getString(Constants.KEY_CLASS_LESSON_TIME_END));
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

    void setYear(int year) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -setYear-");
        this.year = year;
    }

    void setSemester(int semester) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -setSemester-");
        this.semester = semester;
    }

    String getDateString() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -getDateString-");
        return String.valueOf(date);
    }

    String getTimeStartString() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -getTimeStartString-");
        return String.valueOf(timeStart);
    }

    String getTimeEndString() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -getTimeEndString-");
        return String.valueOf(timeEnd);
    }
}
