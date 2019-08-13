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

    private EditText editTextName, editTextSurname, editTextSerialNumber, editTextEmail, editTextPassword, editTextConfirmPassword;
    private String name;
    private String surname;
    private String serialNumber;
    private String email;
    private String password;
    private String confirmPassword;

    private Button buttonRegister;
    private Button buttonSignIn;

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

        buttonRegister = findViewById(R.id.registerButton);
        buttonSignIn = findViewById(R.id.sign_inButton);
    }

    protected void onStart() {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -onStart-");
        super.onStart();

        buttonSignIn.setOnClickListener(buttonSignInListener);
        buttonRegister.setOnClickListener(buttonRegisterListener);
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
            email = editTextEmail.getText().toString().toLowerCase().trim();
            password = editTextPassword.getText().toString().trim();
            confirmPassword = editTextConfirmPassword.getText().toString().trim();
            registrationInit();
        }
    };

    /**
     * Validate the input, records the user then loads the login activity
     */

    private void registrationInit() {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -registrationInit-");
        boolean registrationComplete = false;
        if(validateInputs()) {
            registrationComplete = register();
        }
        if(registrationComplete) {
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
            Connection connection = new Connection(data, Settings.REQUEST_METHOD);
            connection.execute();
            JSONObject response = connection.get();
            Log.i(Settings.TAG, getClass().getSimpleName() + " -register- Response: " + response.toString());
            String message = response.getString(Settings.KEY_MESSAGE);
            int codeResult = (int) response.get(Settings.KEY_CODE);

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
