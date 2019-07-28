package it.teamgdm.sms.dibapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class RegisterActivity extends AppCompatActivity {

    private final String TAG = "dibApp.RegisterActivity";

    private static final String REQUEST_METHOD = "POST";
    private static final String KEY_ACTION = "action";
    private static final String ACTION = "registration";
    private static final String KEY_CODE = "code";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_NAME = "name";
    private static final String KEY_SURNAME = "surname";
    private static final String KEY_SERIAL_NUMBER = "serialNumber";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_EMPTY = "";
    private static final int REGISTRATION_OK_CODE = 202;

    private EditText editTextName, editTextSurname, editTextSerialNumber, editTextEmail, editTextPassword, editTextConfirmPassword;
    private String name;
    private String surname;
    private String serialNumber;
    private String email;
    private String password;
    private String confirmPassword;

    private SessionHandler session;
    boolean registrationComplete;

    private Button buttonRegister;
    private Button buttonSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, getClass().getSimpleName() + " -onCreate-");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);

        session = new SessionHandler(getApplicationContext());
        registrationComplete = false;

        editTextName = findViewById(R.id.name);
        editTextSurname = findViewById(R.id.surname);
        editTextSerialNumber = findViewById(R.id.ssn);
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        editTextConfirmPassword = findViewById(R.id.confirmPassword);

        buttonRegister = findViewById(R.id.registerButton);
        buttonSignIn = findViewById(R.id.sign_inButton);
    }

    protected void onStart() {
        Log.i(TAG, getClass().getSimpleName() + " -onStart-");
        super.onStart();

        buttonSignIn.setOnClickListener(buttonSignInListener);
        buttonRegister.setOnClickListener(buttonRegisterListener);
    }

    @Override
    protected void onResume() {
        Log.i(TAG, getClass().getSimpleName() + " -onResume-");
        super.onResume();
    }
    @Override
    protected void onPause() {
        Log.i(TAG, getClass().getSimpleName() + " -onPause-");
        super.onPause();
    }
    @Override
    protected void onStop() {
        Log.i(TAG, getClass().getSimpleName() + " -onStop-");
        super.onStop();
    }
    @Override
    protected void onDestroy() {
        Log.i(TAG, getClass().getSimpleName() + " -onDestroy-");
        super.onDestroy();
    }

    private final View.OnClickListener buttonSignInListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Log.i(TAG, getClass().getSimpleName() + " -OnClickListener-buttonSignInListener-onClick-");
            Intent signInIntent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(signInIntent);
        }
    };

    private final View.OnClickListener buttonRegisterListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Log.i(TAG, getClass().getSimpleName() + " -OnClickListener-buttonRegisterListener-onClick-");
            name = editTextName.getText().toString().trim();
            surname = editTextSurname.getText().toString().trim();
            serialNumber = editTextSerialNumber.getText().toString().trim();
            email = editTextEmail.getText().toString().toLowerCase().trim();
            password = editTextPassword.getText().toString().trim();
            confirmPassword = editTextConfirmPassword.getText().toString().trim();
            registrationInit();
        }
    };

    private void registrationInit() {
        Log.i(TAG, getClass().getSimpleName() + " -registrationInit-");
        if(validateInputs()) {
            register();
        }
        if(registrationComplete == true ) {
            Log.i(TAG, getClass().getSimpleName() + " -registrationInit-registrationComplete-TRUE");
            session.loginUser(email);
            loadDashboard();
        }
    }

    /**
     * Validates inputs and shows error if any
     *
     */
    private boolean validateInputs() {
        Log.i(TAG, getClass().getSimpleName() + " -validateInputs-");
        if (KEY_EMPTY.equals(name)) {
            editTextName.setError(getResources().getString(R.string.namePromptHint) + " " + getResources().getString(R.string.inputCannotBeEmpty));
            editTextName.requestFocus();
            return false;
        }
        if (KEY_EMPTY.equals(surname)) {
            editTextSurname.setError(getResources().getString(R.string.surnamePromptHint) + " " + getResources().getString(R.string.inputCannotBeEmpty));
            editTextSurname.requestFocus();
            return false;
        }
        if (KEY_EMPTY.equals(serialNumber)) {
            editTextSerialNumber.setError(getResources().getString(R.string.ssnPromptHint) + " " + getResources().getString(R.string.inputCannotBeEmpty));
            editTextSerialNumber.requestFocus();
            return false;
        }
        if (KEY_EMPTY.equals(password)) {
            editTextPassword.setError(getResources().getString(R.string.passwordPromptHint) + " " + getResources().getString(R.string.inputCannotBeEmpty));
            editTextPassword.requestFocus();
            return false;
        }
        if (KEY_EMPTY.equals(confirmPassword)) {
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

    private void register() {
        Log.i(TAG, getClass().getSimpleName() + " -register-");
        JSONObject data = new JSONObject();

        //Populate the data parameters
        try {
            data.put(KEY_ACTION, ACTION);
            data.put(KEY_SERIAL_NUMBER, serialNumber);
            data.put(KEY_NAME, name);
            data.put(KEY_SURNAME, surname);
            data.put(KEY_EMAIL, email);
            data.put(KEY_PASSWORD, password);
            Connection connection = new Connection(data, REQUEST_METHOD);
            connection.execute();
            JSONObject response = connection.get();
            int codeResult = (int) response.get(KEY_CODE);
            String message = response.getString(KEY_MESSAGE);
            Log.i(TAG, getClass().getSimpleName() + " -register- Code: " + codeResult + " \tMessage: " + message);
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            if (codeResult == REGISTRATION_OK_CODE) {
                Log.i(TAG, getClass().getSimpleName() + " -register-REGISTRATION_OK_CODE-");
                registrationComplete = true;
            } else {
                Log.i(TAG, getClass().getSimpleName() + " -register-REGISTRATION_CODE_NOT_OK-");
            }

        } catch (JSONException | ExecutionException | InterruptedException e) {
            Log.i(TAG, getClass().getSimpleName() + " -register-Exception-");
            System.out.println("\n\t" + data + "\n\t" + e.getMessage());
            e.printStackTrace();
        }

    }

    /**
     * Launch Dashboard Activity on Successful Sign Up
     */
    private void loadDashboard() {
        Log.i(TAG, getClass().getSimpleName() + " -loadDashboard-");
        Intent i = new Intent(getApplicationContext(), DashboardActivity.class);
        startActivity(i);
    }
}
