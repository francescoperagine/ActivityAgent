package it.teamgdm.sms.dibapp;

public class Review {

    private float rate;
    private String summary;
    private String description;

    Review(float rate, String summary, String description){
        this.rate = rate;
        this.summary = summary;
        this.description = description;
    }


    public float getRate() {
        return rate;
    }

    String getSummary() {
        return summary;
    }

    String getDescription() {
        return description;
    }
}
