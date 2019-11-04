package it.teamgdm.sms.dibapp;

import java.util.Comparator;

public class ComparatorQuestionMostVoted implements Comparator<Question> {

    @Override
    public int compare(Question a, Question b) {
        if(a.rate>b.rate){
            return -1;
        }
        else if(a.rate<b.rate){
            return 1;
        }
        else{
            return 0;
        }
    }

}
