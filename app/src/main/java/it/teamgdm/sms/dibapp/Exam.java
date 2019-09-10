package it.teamgdm.sms.dibapp;

import android.util.Log;

import java.io.Serializable;

class Exam implements Serializable {
    private int ID;
    private String name;
    private int year;
    private int semester;
    private boolean passed;
    private int vote;
    private boolean praise;
    private String passedDate;

    public int getID() {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -getID-");
        return ID;
    }

    public String getName() {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -getName-");
        return name;
    }

    public boolean isPassed() {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -isPassed-");
        return passed;
    }

    public int getVote() {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -getVote-");
        return vote;
    }

    public boolean hasPraise() {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -hasPraise-");
        return praise;
    }

    public String getPassedDate() {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -getPassedDate-");
        return passedDate;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }

    public void setPraise(boolean praise) {
        this.praise = praise;
    }

    public void setPassedDate(String passedDate) {
        this.passedDate = passedDate;
    }

    public String toString() {
        return name;
    }
}