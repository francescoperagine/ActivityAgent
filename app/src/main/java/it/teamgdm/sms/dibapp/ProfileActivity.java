package it.teamgdm.sms.dibapp;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

import org.json.JSONException;

import java.util.Objects;

public class ProfileActivity extends BaseActivity {

   private TextView name, surname, role, degreeCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onCreate-");
        super.onCreate(savedInstanceState);

        name = findViewById(R.id.name);
        surname = findViewById(R.id.surname);
        role = findViewById(R.id.userRole);
        degreeCourse = findViewById(R.id.degreeCourse);

        Objects.requireNonNull(getSupportActionBar()).setTitle(getString(R.string.profileActivityTitle));
    }

    @Override
    protected void onStart() {
        super.onStart();
        Session session = new Session(getApplicationContext());

        String nameStr, surnameStr, roleStr, degreeStr;

        nameStr = getString(R.string.name) + " "+ Session.getUserName();
        name.setText(nameStr);

        surnameStr =getString(R.string.surname) + " "+ Session.getUserSurname();
        surname.setText(surnameStr);


        roleStr = getString(R.string.role) + " " + Session.getUserRole();
        role.setText(roleStr);

        if(!session.userIsProfessor()) {
            degreeStr = getString(R.string.degreeCourse);
            try {
                degreeStr += DAO.getDegreeCourseName(Session.getUserID()).getJSONObject(0).getString("name");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            degreeCourse.setText(degreeStr);
        }

    }

    @Override
    int getLayoutResource() {
        return R.layout.activity_profile;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onCreateOptionsMenu-");
        getMenuInflater().inflate(R.menu.profile_menu, menu);
        return true;
    }

}
