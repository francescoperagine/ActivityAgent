package it.teamgdm.sms.dibapp;

import android.os.Bundle;
import android.util.Log;

public class ProfileActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -onCreate-");
        super.onCreate(savedInstanceState);
    }

    @Override
    int getLayoutResource() {
        return R.layout.activity_profile;
    }

    @Override
    String setActivityTitle() {
        return getResources().getString(R.string.title_activity_profile);
    }

}
