package it.teamgdm.sms.dibapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextName, editTextSurname, editTextSerialNumber, editTextEmail, editTextPassword, editTextConfirmPassword;

    private Spinner spinnerRole, spinnerDegreecourse;
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
        spinnerDegreecourse = findViewById(R.id.degreecourse);
        buttonRegister = findViewById(R.id.registerButton);
        buttonSignIn = findViewById(R.id.sign_inButton);
    }

    protected void onStart() {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -onStart-");
        super.onStart();
        setSpinners();
        buttonSignIn.setOnClickListener(buttonSignInListener);
        buttonRegister.setOnClickListener(buttonRegisterListener);
    }

    /**
     * Sets role & degreecourse spinners
     */

    private void setSpinners() {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -setSpinners-");
        SpinnerListAdapter spinnerDegreecourseAdapter = new SpinnerListAdapter(this, Settings.GET_DEGREECOURSE_LIST, R.string.selectDegreecourseText, android.R.layout.simple_spinner_dropdown_item);
        spinnerDegreecourse.setAdapter(spinnerDegreecourseAdapter.setInputList());
        spinnerDegreecourse.setSelection(0);
        SpinnerListAdapter spinnerRoleAdapter = new SpinnerListAdapter(this, Settings.GET_ROLE_LIST, R.string.selectRoleText, android.R.layout.simple_spinner_dropdown_item);
        spinnerRole.setAdapter(spinnerRoleAdapter.setInputList());
        spinnerRole.setSelection(0);
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
            registrationInit();
        }
    };

    /**
     * Starts the registration process
     */

    private void registrationInit() {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -registrationInit-");

        User tmpUser = new User();

        tmpUser.setName(editTextName.getText().toString().trim());
        tmpUser.setSurname(editTextSurname.getText().toString().trim());
        tmpUser.setSsn(editTextSerialNumber.getText().toString().trim()) ;
        tmpUser.setDegreeCourse(spinnerDegreecourse.getSelectedItem().toString());
        tmpUser.setRole(spinnerRole.getSelectedItem().toString());
        tmpUser.setEmail(editTextEmail.getText().toString().toLowerCase().trim());
        tmpUser.setPassword(editTextPassword.getText().toString().trim());
        tmpUser.setConfirmPassword(editTextConfirmPassword.getText().toString().trim());

        if(validateInputs(tmpUser) && register(tmpUser)) {
            Log.i(Settings.TAG, getClass().getSimpleName() + " -registrationInit-registrationComplete-"+tmpUser);
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(loginIntent);
        }
    }

    /**
     * Validates inputs and shows error if any
     *
     * @param tmpUser
     */
    private boolean validateInputs(User tmpUser) {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -validateInputs-");

        if (Settings.KEY_EMPTY.equals(tmpUser.getName())) {
            editTextName.setError(getResources().getString(R.string.namePromptHint) + " " + getResources().getString(R.string.inputCannotBeEmpty));
            editTextName.requestFocus();
            return false;
        }
        if (Settings.KEY_EMPTY.equals(tmpUser.getSurname())) {
            editTextSurname.setError(getResources().getString(R.string.surnamePromptHint) + " " + getResources().getString(R.string.inputCannotBeEmpty));
            editTextSurname.requestFocus();
            return false;
        }
        if (Settings.KEY_EMPTY.equals(tmpUser.getSsn())) {
            editTextSerialNumber.setError(getResources().getString(R.string.ssnPromptHint) + " " + getResources().getString(R.string.inputCannotBeEmpty));
            editTextSerialNumber.requestFocus();
            return false;
        }
        if (tmpUser.getDegreeCourse().equals(getResources().getString(R.string.selectDegreecourseText))) {
            TextView errorText = (TextView) spinnerDegreecourse.getSelectedView();
            errorText.setError("");
            errorText.setTextColor(Color.RED);
            errorText.setText(getResources().getString(R.string.degreecourseSelectionErrorText));
            return false;
        }
        if (tmpUser.getRoleName().equals(getResources().getString(R.string.selectRoleText))) {
            TextView errorText = (TextView) spinnerRole.getSelectedView();
            errorText.setError("");
            errorText.setTextColor(Color.RED);
            errorText.setText(getResources().getString(R.string.roleSelectionErrorText));
            return false;
        }
        if(!tmpUser.isEmailValid()) {
            Log.i(Settings.TAG, getClass().getSimpleName() + " -validateInputs-!isEmailValid" + tmpUser.getEmail());
            editTextEmail.setError(getResources().getString(R.string.emailNotValid));
            editTextEmail.requestFocus();
            return false;
        }
        if (Settings.KEY_EMPTY.equals(tmpUser.getPassword())) {
            editTextPassword.setError(getResources().getString(R.string.passwordPromptHint) + " " + getResources().getString(R.string.inputCannotBeEmpty));
            editTextPassword.requestFocus();
            return false;
        }
        if (!tmpUser.getPassword().equals(tmpUser.getConfirmPassword())) {
            editTextConfirmPassword.setError(getResources().getString(R.string.passwordAndConfirmDoesNotMatch));
            editTextConfirmPassword.requestFocus();
            return false;
        }
        return true;
    }

    /**
     * Sends form data to the webserver with the default request method
     * @return true if the server returns USER_CREATED_CODE
     * @param tmpUser
     */

    private boolean register(User tmpUser) {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -register-");
        JSONObject data = new JSONObject();

        //Populate the data parameters
        try {
            data.put(Settings.KEY_ACTION, Settings.USER_REGISTRATION);
            data.put(Settings.KEY_USER_NAME, tmpUser.getName());
            data.put(Settings.KEY_USER_SURNAME, tmpUser.getSurname());
            data.put(Settings.KEY_SERIAL_NUMBER, tmpUser.getSsn());
            data.put(Settings.KEY_DEGREECOURSE, tmpUser.getDegreeCourse());
            data.put(Settings.KEY_USER_ROLE_NAME, tmpUser.getRoleName());
            data.put(Settings.KEY_USER_EMAIL, tmpUser.getEmail());
            data.put(Settings.KEY_PASSWORD, tmpUser.getPassword());
            AsyncTaskConnection asyncTaskConnection = new AsyncTaskConnection();
            asyncTaskConnection.execute(data);
            JSONArray response = asyncTaskConnection.get();
            Log.i(Settings.TAG, getClass().getSimpleName() + " -register- Response: " + response.toString());

            String message = response.getJSONObject(0).getString(Settings.KEY_MESSAGE);
            int codeResult = (int) response.getJSONObject(0).get(Settings.KEY_CODE);

            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            if (codeResult == Settings.USER_CREATED_CODE) {
                Log.i(Settings.TAG, getClass().getSimpleName() + " -register-"+message);
                return true;
            } else {
                Log.i(Settings.TAG, getClass().getSimpleName() + " -register-REGISTRATION_NOT_OK");
                return false;
            }
        } catch (JSONException | ExecutionException | InterruptedException e) {
            Log.i(Settings.TAG, getClass().getSimpleName() + " -register-Exception-");
            System.out.println("\n\t" + "\n\t" + e.getMessage());
            e.printStackTrace();
        }
        return false;
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

}
