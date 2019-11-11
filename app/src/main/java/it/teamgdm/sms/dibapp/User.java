package it.teamgdm.sms.dibapp;

import android.util.Log;

import androidx.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

class User {

    final String name;
    final String surname;
    final String ssn;
    final String degreeCourse;
    final String roleName;
    final String email;
    final Date registrationDate;
    final String password;
    final String confirmPassword;

    private User(final String name, final String surname, final String ssn, final String degreeCourse, final String roleName,
                 final String email, final Date registrationDate, final String password, final String confirmPassword) {
        this.name = name;
        this.surname = surname;
        this.ssn = ssn;
        this.degreeCourse = degreeCourse;
        this.roleName = roleName;
        this.email = email;
        this.registrationDate = registrationDate;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    boolean isEmailValid() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -isEmailValid-");
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    @NonNull
    public String toString() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -toString-");
        return name + Constants.KEY_BLANK + surname + Constants.KEY_BLANK + ssn + Constants.KEY_BLANK + roleName + Constants.KEY_BLANK + email + Constants.KEY_BLANK + password;
    }

    String getRegistrationDate() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -getRegistrationDate-");
        return new SimpleDateFormat(Constants.DATETIME_FORMAT, Locale.getDefault()).format(registrationDate);
    }

    static class Builder {
        private String name;
        private String surname;
        private String ssn;
        private String degreeCourse;
        private String roleName;
        private String email;
        private Date registrationDate;
        private String password;
        private String confirmPassword;

        private Builder() {}

        Builder setName(final String name) {
            this.name = name;
            return this;
        }

        Builder setSurname(final String surname) {
            this.surname = surname;
            return this;
        }

        Builder setSsn(final String ssn) {
            this.ssn = ssn;
            return this;
        }

        Builder setDegreeCourse(final String degreeCourse) {
            this.degreeCourse = degreeCourse;
            return this;
        }

        Builder setRoleName(final String roleName) {
            this.roleName = roleName;
            return this;
        }

        Builder setEmail(final String email) {
            this.email = email;
            return this;
        }

        Builder setRegistrationDate(final Date registrationDate) {
            this.registrationDate = registrationDate;
            return this;
        }

        Builder setPassword(final String password) {
            this.password = password;
            return this;
        }

        Builder setConfirmPassword(final String confirmPassword) {
            this.confirmPassword = confirmPassword;
            return this;
        }

        static Builder create() {
            return new Builder();
        }

        User build() {
            return new User(name, surname, ssn, degreeCourse, roleName, email, registrationDate, password, confirmPassword);
        }
    }
}