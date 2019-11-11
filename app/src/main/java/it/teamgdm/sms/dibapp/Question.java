package it.teamgdm.sms.dibapp;

import java.util.Date;

public final class Question {

    public final int id;
    public final String question;
    public final int rate;
    public final Date date;

    private Question(final int id, final String question, final int rate, final Date date) {
        this.id = id;
        this.question = question;
        this.rate = rate;
        this.date = date;
    }

    public static class Builder {
        private int id;
        private String question;
        private int rate;
        private Date date;

        private Builder(final int id) {
            this.id = id;
        }

        public Builder setQuestion(final String question) {
            this.question = question;
            return this;
        }

        public Builder setRate(final int rate) {
            this.rate = rate;
            return this;
        }

        public Builder setDate(final Date date) {
            this.date = date;
            return this;
        }

        static Builder create(final int id) {
            return new Builder(id);
        }

        Question build() {
            return new Question(id, question, rate, date);
        }
    }

    @Override
    public String toString() {
        return "ID = " + id + " - Question = " +question + " - rate = " + rate + " - time = " + date;
    }


}
