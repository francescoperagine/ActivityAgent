package it.teamgdm.sms.dibapp;

import java.util.Comparator;

public class ComparatorReviewMaxToMin implements Comparator<Review> {

    @Override
    public int compare(Review a, Review b) {
        if(a.getRate()>b.getRate()){
            return -1;
        }
        else if(a.getRate()<b.getRate()){
            return 1;
        }
        else{
            return 0;
        }
    }
}
