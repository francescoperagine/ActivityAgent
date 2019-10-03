package it.teamgdm.sms.dibapp;

import android.util.Log;

import java.io.Serializable;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

class Exam implements Serializable {
    int ID;
    String name;
    int code;
    String classDescription;
    boolean passed;
    int vote;
    boolean praise;
    Date passedDate;

    void setPassedDate(String passedDate) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -setPassedDate-");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
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