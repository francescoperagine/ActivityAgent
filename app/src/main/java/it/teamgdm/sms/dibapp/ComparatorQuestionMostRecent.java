package it.teamgdm.sms.dibapp;

import java.util.Comparator;

public class ComparatorQuestionMostRecent implements Comparator<Question> {

    @Override
    public int compare(Question a, Question b) {
        if(a.getDate().after(b.getDate())){
            return -1;
        }
        else if(a.getDate().before(b.getDate())){
            return 1;
        }
        else{
            return 0;
        }
    }

}
