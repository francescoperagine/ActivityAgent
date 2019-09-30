package it.teamgdm.sms.dibapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public abstract class BaseActivity extends AppCompatActivity {

    Toolbar toolbar;

    protected void onCreate(Bundle savedInstanceState) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onCreate-");
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
    }

    abstract int getLayoutResource();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onCreateOptionsMenu-");
        getMenuInflater().inflate(R.menu.app_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onOptionsItemSelected-");
        int id = item.getItemId();
        switch (id) {
            case R.id.profileButton:
                Intent profileIntent = new Intent(this, ProfileActivity.class);
                startActivity(profileIntent);
                return true;
            case R.id.settingsButton:
                Intent settingIntent = new Intent(this, SettingsActivity.class);
                startActivity(settingIntent);
                return true;
            case R.id.logoutButton:
                FragmentManager fragmentManager = this.getSupportFragmentManager();
                LogoutDialogFragment logoutDialogFragment = new LogoutDialogFragment(this);
                logoutDialogFragment.show(fragmentManager, "logout_fragment");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    static JSONArray getFromDB(Map params) {
        Log.i(Constants.TAG, ClassListActivity.class.getSimpleName() + " -getFromDB-");
        JSONObject data = new JSONObject();
        JSONArray response = null;
        try {
            for (Object o : params.entrySet()) {
                Map.Entry entry = (Map.Entry) o;
                data.put((String) entry.getKey(), entry.getValue());
            }
            Log.i(Constants.TAG, ClassListActivity.class.getSimpleName() + " -getFromDB-data "+data);
            AsyncTaskConnection asyncTaskConnection = new AsyncTaskConnection();
            asyncTaskConnection.execute(data);
            response = asyncTaskConnection.get();
            Log.i(Constants.TAG, ClassListActivity.class.getSimpleName() + " -getFromDB-response "+response);
        } catch (JSONException | ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }

    static boolean isDataSent(HashMap params, int expectedResultCode) {
        Log.i(Constants.TAG, BaseActivity.class.getSimpleName() + " -isDataSent-");
        JSONArray response = getFromDB(params);
        int codeResult = 0;
        try {
            codeResult = response.getJSONObject(0).getInt(Constants.KEY_CODE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return codeResult == expectedResultCode;
    }
}
