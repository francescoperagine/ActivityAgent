package it.teamgdm.sms.dibapp;

import java.util.Comparator;

public class ComparatorReviewMaxToMin implements Comparator<Review> {

    @Override
    public int compare(Review a, Review b) {
        return Float.compare(b.getRate(), a.getRate());
    }
}
