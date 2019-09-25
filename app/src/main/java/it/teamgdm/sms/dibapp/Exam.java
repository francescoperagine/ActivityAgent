package it.teamgdm.sms.dibapp;

import android.annotation.SuppressLint;
import android.util.Log;

import java.io.Serializable;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

class Exam implements Serializable {
    int ID;
    String name;
    int code;
    String classDescription;
    boolean passed;
    int vote;
    boolean praise;
    Date passedDate;

    int getID() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -getID-");
        return ID;
    }

    String getName() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -getName-");
        return name;
    }

    void setID(int ID) {
        this.ID = ID;
    }

    void setName(String name) {
        this.name = name;
    }

    void setPassed(boolean passed) {
        this.passed = passed;
    }

    void setVote(int vote) {
        this.vote = vote;
    }

    void setPraise(boolean praise) {
        this.praise = praise;
    }

    void setPassedDate(String passedDate) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = (Date) simpleDateFormat.parse(passedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.passedDate = date;
    }

    public String toString() {
        return name;
    }
}