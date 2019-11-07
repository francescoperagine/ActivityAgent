package it.teamgdm.sms.dibapp;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileActivity extends BaseActivity {

   private TextView name, surname, role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onCreate-");
        super.onCreate(savedInstanceState);

        name = findViewById(R.id.name);
        surname = findViewById(R.id.surname);
        role = findViewById(R.id.userRole);

        getSupportActionBar().setTitle(getString(R.string.profileActivityTitle));
    }

    @Override
    protected void onStart() {
        super.onStart();

        String nameStr, surnameStr, roleStr;

        nameStr = getString(R.string.name) + " "+ Session.getUserName();
        name.setText(nameStr);

        surnameStr =getString(R.string.surname) + " "+ Session.getUserSurname();
        surname.setText(surnameStr);


        roleStr = getString(R.string.role) + " " + Session.getUserRole();
        role.setText(roleStr);




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
