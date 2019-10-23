package it.teamgdm.sms.dibapp;

public class Question {

    private int id;
    private String question;
    private int rate;

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
        return "ID\t" + id + "\tQuestion\t" +question + "\trate\t" + rate;
    }


}
