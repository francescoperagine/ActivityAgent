package it.teamgdm.sms.dibapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    private final String TAG = "dibApp.RegisterActivity";

    private static final String REGISTRATION_URL = "registration.php";
    private static final String REQUEST_METHOD = "POST";
    private static final String REGISTRATION_OK = "Registration is complete.";

    private static final String KEY_NAME = "name";
    private static final String KEY_SURNAME = "surname";
    private static final String KEY_SERIAL_NUMBER = "serialNumber";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_EMPTY = "";
    private static final String KEY_STATUS = "status";
    private static final String KEY_MESSAGE = "message";

    private SessionHandler session;

    private EditText editTextName, editTextSurname, editTextSerialNumber, editTextEmail, editTextPassword, editTextConfirmPassword;
    private String name;
    private String surname;
    private String serialNumber;
    private String email;
    private String password;
    private String confirmPassword;

    Button buttonRegister, buttonSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, getClass().getSimpleName() + " -onCreate-");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);
        session = new SessionHandler(getApplicationContext());

        editTextName = findViewById(R.id.name);
        editTextSurname = findViewById(R.id.surname);
        editTextSerialNumber = findViewById(R.id.ssn);
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        editTextConfirmPassword = findViewById(R.id.confirmPassword);

        buttonRegister = findViewById(R.id.register);
        buttonSignIn = findViewById(R.id.sign_in);
    }

    protected void onStart() {
        Log.i(TAG, getClass().getSimpleName() + " -onStart- The activity is getting started.");
        super.onStart();

        buttonSignIn.setOnClickListener(buttonSignInListener);
        buttonRegister.setOnClickListener(buttonRegisterListener);
    }

    @Override
    protected void onResume() {
        Log.i(TAG, getClass().getSimpleName() + " -onResume- The activity is being resumed.");
        super.onResume();
    }
    @Override
    protected void onPause() {
        Log.i(TAG, getClass().getSimpleName() + " -onPause- The activity is being paused.");
        super.onPause();
    }
    @Override
    protected void onStop() {
        Log.i(TAG, getClass().getSimpleName() + " -onStop- The activity is being stopped.");
        super.onStop();
    }
    @Override
    protected void onDestroy() {
        Log.i(TAG, getClass().getSimpleName() + " -onDestroy- The activity is being destroyed.");
        super.onDestroy();
    }

    public View.OnClickListener buttonSignInListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent signInIntent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(signInIntent);
        }
    };

    public View.OnClickListener buttonRegisterListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            name = editTextName.getText().toString().trim();
            surname = editTextSurname.getText().toString().trim();
            serialNumber = editTextSerialNumber.getText().toString().trim();
            email = editTextEmail.getText().toString().toLowerCase().trim();
            password = editTextPassword.getText().toString().trim();
            confirmPassword = editTextConfirmPassword.getText().toString().trim();
            if(validateInputs()) {
                register();
            }
        }
    };

    /**
     * Validates inputs and shows error if any
     * @return
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
        try {
            //Populate the data parameters
            data.put(KEY_SERIAL_NUMBER, serialNumber);
            data.put(KEY_NAME, name);
            data.put(KEY_SURNAME, surname);
            data.put(KEY_EMAIL, email);
            data.put(KEY_PASSWORD, password);
        } catch (JSONException e) {
            System.out.println("\n\t" + data + "\n\t" + e.getMessage());
            e.printStackTrace();
        }
        Connection connection = new Connection(data, REGISTRATION_URL, REQUEST_METHOD);
        connection.execute();
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
