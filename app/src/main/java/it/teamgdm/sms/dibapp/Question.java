package it.teamgdm.sms.dibapp;

import java.util.Date;

public class Question {

    private int id;
    private String question;
    private int rate;
    private Date date;

    public Question(int id, String question, int rate, Date date) {
        this.id = id;
        this.question = question;
        this.rate = rate;
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    int getRate() {
        return rate;
    }

    void setRate(int rate) {
        this.rate = rate;
    }

    String getQuestion() {
        return question;
    }

    void setQuestion(String question) {
        this.question = question;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ID = " + id + " - Question = " +question + " - rate = " + rate + " - time = " + date;
    }


}
