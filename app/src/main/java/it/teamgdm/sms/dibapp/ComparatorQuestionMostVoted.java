package it.teamgdm.sms.dibapp;

import java.util.Comparator;

public class ComparatorQuestionMostVoted implements Comparator<Question> {

    @Override
    public int compare(Question a, Question b) {
        return Integer.compare(b.rate, a.rate);
    }
}
