package it.teamgdm.sms.dibapp;

import android.util.Log;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ClassLesson extends Exam implements Serializable {

    int classID;
    int lessonID;
    int year;
    int semester;
    LocalDate date;
    LocalTime timeStart;
    LocalTime timeEnd;
    String lessonSummary;
    String lessonDescription;

    ClassLesson() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -ClassLesson-");
    }

    @NonNull
    public String toString() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -toString-");
        return "\nClassID \t " + classID + "\nLessonID \t " + lessonID + "\nName \t" + name + "\ncode \t" + code + "\nclassDescription \t" + classDescription + "\nyear \t" + year +
                "\nsemester \t" + semester + "\ndate \t" + getDate(date) + "\ntimeStart \t" + timeStart + "\ntimeEnd \t" + timeEnd +
                "\nlessonSummary \t" + lessonSummary + "\nlessonDescription \t" + lessonDescription;
    }

    String getDate(LocalDate date) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -getDate-");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT);
        return date.format(formatter);
    }

    LocalDate getDate(String date) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -getDate-" + date);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT);
        return LocalDate.parse(date, formatter);
    }

    String getTime(LocalTime time) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -getTime-");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.TIME_FORMAT);
        return time.format(formatter);
    }

    LocalTime getTime(String time) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -getTime-");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.TIME_FORMAT);
        return LocalTime.parse(time, formatter);
    }

    boolean isInProgress() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -isInProgress-");
        LocalTime now = LocalTime.now();
        return LocalDate.now().equals(date) && now.isAfter(timeStart) && now.isBefore(timeEnd);
    }

}