package it.teamgdm.sms.dibapp;

import java.util.Comparator;

public class ComparatorQuestionMostRecent implements Comparator<Question> {

    @Override
    public int compare(Question a, Question b) {
        if(a.date.after(b.date)){
            return -1;
        }
        else if(a.date.before(b.date)){
            return 1;
        }
        else{
            return 0;
        }
    }

}
