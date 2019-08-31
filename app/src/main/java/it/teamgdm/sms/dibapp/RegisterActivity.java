package it.teamgdm.sms.dibapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextName, editTextSurname, editTextSerialNumber, editTextEmail, editTextPassword, editTextConfirmPassword;
    private String name, surname, serialNumber, role, email, password, confirmPassword;

    private Spinner spinnerRole;
    private Button buttonRegister, buttonSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -onCreate-");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);

        editTextName = findViewById(R.id.name);
        editTextSurname = findViewById(R.id.surname);
        editTextSerialNumber = findViewById(R.id.ssn);
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        editTextConfirmPassword = findViewById(R.id.confirmPassword);

        spinnerRole = findViewById(R.id.role);
        buttonRegister = findViewById(R.id.registerButton);
        buttonSignIn = findViewById(R.id.sign_inButton);
    }

    protected void onStart() {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -onStart-");
        super.onStart();
        loadRoleSpinner();
        buttonSignIn.setOnClickListener(buttonSignInListener);
        buttonRegister.setOnClickListener(buttonRegisterListener);
    }

    private void loadRoleSpinner() {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -loadRoleSpinner-");
        final JSONArray roleListData = loadRoleListData();
        final ArrayList<Role> roleList = setRoleList(roleListData);

        ArrayAdapter spinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, roleList);
        spinnerRole.setAdapter(spinnerAdapter);
    }

    private JSONArray loadRoleListData() {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -loadRoleListData-");
        JSONObject data = new JSONObject();
        JSONArray response = null;
        try {
            data.put(Settings.KEY_ACTION, Settings.ACTION_GET_ROLE_LIST);
            Log.i(Settings.TAG, getClass().getSimpleName() + " -loadRoleListData-data: " + data.toString());
            Connection connection = new Connection(data, Settings.REQUEST_METHOD);
            connection.execute();
            response = connection.get();
            Log.i(Settings.TAG, getClass().getSimpleName() + " -loadRoleListData-response: " + response.toString());
        } catch (ExecutionException | InterruptedException | JSONException e) {
            Log.i(Settings.TAG, getClass().getSimpleName() + " -loadRoleListData-Exception");
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            return null;
        }
        return response;
    }

    private ArrayList setRoleList(JSONArray roleListData) {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -setRoleList-");
        ArrayList roleList = new ArrayList<>();
        for(int i = 0; i<roleListData.length(); i++) {
            try {
                Role r = new Role(roleListData.getJSONObject(i).optString(Settings.KEY_ROLE_NAME));
                roleList.add(r.getName());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Log.i(Settings.TAG, getClass().getSimpleName() + " -setRoleList-roleList " + roleList.toString());
        return roleList;
    }

    @Override
    protected void onResume() {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -onResume-");
        super.onResume();
    }
    @Override
    protected void onPause() {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -onPause-");
        super.onPause();
    }
    @Override
    protected void onStop() {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -onStop-");
        super.onStop();
    }
    @Override
    protected void onDestroy() {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -onDestroy-");
        super.onDestroy();
    }

    private final View.OnClickListener buttonSignInListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Log.i(Settings.TAG, getClass().getSimpleName() + " -OnClickListener-buttonSignInListener-onClick-");
            Intent signInIntent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(signInIntent);
        }
    };

    private final View.OnClickListener buttonRegisterListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Log.i(Settings.TAG, getClass().getSimpleName() + " -OnClickListener-buttonRegisterListener-onClick-");
            name = editTextName.getText().toString().trim();
            surname = editTextSurname.getText().toString().trim();
            serialNumber = editTextSerialNumber.getText().toString().trim();
            role = spinnerRole.toString().trim();
            email = editTextEmail.getText().toString().toLowerCase().trim();
            password = editTextPassword.getText().toString().trim();
            confirmPassword = editTextConfirmPassword.getText().toString().trim();
            registrationInit();
        }
    };

    /**
     * Starts the registration process
     */

    private void registrationInit() {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -registrationInit-");
        if(validateInputs() & register()) {
            Log.i(Settings.TAG, getClass().getSimpleName() + " -registrationInit-registrationComplete-");
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(loginIntent);
        }
    }

    /**
     * Validates inputs and shows error if any
     *
     */
    private boolean validateInputs() {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -validateInputs-");
        if (Settings.KEY_EMPTY.equals(name)) {
            editTextName.setError(getResources().getString(R.string.namePromptHint) + " " + getResources().getString(R.string.inputCannotBeEmpty));
            editTextName.requestFocus();
            return false;
        }
        if (Settings.KEY_EMPTY.equals(surname)) {
            editTextSurname.setError(getResources().getString(R.string.surnamePromptHint) + " " + getResources().getString(R.string.inputCannotBeEmpty));
            editTextSurname.requestFocus();
            return false;
        }
        if (Settings.KEY_EMPTY.equals(serialNumber)) {
            editTextSerialNumber.setError(getResources().getString(R.string.ssnPromptHint) + " " + getResources().getString(R.string.inputCannotBeEmpty));
            editTextSerialNumber.requestFocus();
            return false;
        }
        if (Settings.KEY_EMPTY.equals(password)) {
            editTextPassword.setError(getResources().getString(R.string.passwordPromptHint) + " " + getResources().getString(R.string.inputCannotBeEmpty));
            editTextPassword.requestFocus();
            return false;
        }
        if (Settings.KEY_EMPTY.equals(confirmPassword)) {
            editTextConfirmPassword.setError(getResources().getString(R.string.confirmPasswordPromptHint) + " " + getResources().getString(R.string.inputCannotBeEmpty));
            editTextConfirmPassword.requestFocus();
            return false;
        }
        if (!password.equals(confirmPassword)) {
            editTextConfirmPassword.setError(getResources().getString(R.string.passwordAndConfirmDoesNotMatch));
            editTextConfirmPassword.requestFocus();
            return false;
        }
        return true;
    }

    /**
     * Sends form data to the webserver with the default request method
     * @return true if the server returns USER_CREATED_CODE
     */

    private boolean register() {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -register-");
        JSONObject data = new JSONObject();

        //Populate the data parameters
        try {
            data.put(Settings.KEY_ACTION, Settings.ACTION_REGISTRATION);
            data.put(Settings.KEY_SERIAL_NUMBER, serialNumber);
            data.put(Settings.KEY_NAME, name);
            data.put(Settings.KEY_SURNAME, surname);
            data.put(Settings.KEY_EMAIL, email);
            data.put(Settings.KEY_PASSWORD, password);
            data.put(Settings.KEY_ROLE_ID, role);
            Connection connection = new Connection(data, Settings.REQUEST_METHOD);
            connection.execute();
            JSONArray response = connection.get();
            Log.i(Settings.TAG, getClass().getSimpleName() + " -register- Response: " + response.toString());

            String message = response.getJSONObject(0).getString(Settings.KEY_MESSAGE);
            int codeResult = (int) response.getJSONObject(0).get(Settings.KEY_CODE);

            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            if (codeResult == Settings.USER_CREATED_CODE) {
                Log.i(Settings.TAG, getClass().getSimpleName() + " -register-Settings.USER_CREATED-");
                return true;
            } else {
                Log.i(Settings.TAG, getClass().getSimpleName() + " -register-REGISTRATION_NOT_OK-");
                return false;
            }
        } catch (JSONException | ExecutionException | InterruptedException e) {
            Log.i(Settings.TAG, getClass().getSimpleName() + " -register-Exception-");
            System.out.println("\n\t" + "\n\t" + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
}
