package it.teamgdm.sms.dibapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private SessionHandler session;
    private EditText editTextEmail, editTextPassword;
    private Button buttonSignIn;
    private Button buttonRegister;
    private String email;
    private String password;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -onCreate-");
        super.onCreate(savedInstanceState);

        session = new SessionHandler(getApplicationContext());
        if(session.isLoggedIn()){
            session.setUserWithSharedPreferences();
            loadDashboard();
        }
        setContentView(R.layout.activity_login);

        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);

        buttonSignIn = findViewById(R.id.sign_inButton);
        buttonRegister = findViewById(R.id.registerButton);
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

    private final OnClickListener buttonSignInListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            Log.i(Settings.TAG, getClass().getSimpleName() + " -OnClickListener-buttonSignInListener-onClick-");
            email = editTextEmail.getText().toString().toLowerCase().trim();
            password = editTextPassword.getText().toString().trim();
            loginInit();
        }
    };

    private void loginInit() {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -loginInit-");
        boolean loginComplete = false;
        if(validateInputs()) {
            loginComplete = login();
        }
        if(loginComplete) {
            Log.i(Settings.TAG, getClass().getSimpleName() + " -loginInit-loginComplete-TRUE");
            session.login(email);
            loadDashboard();
        }
    }

    private final OnClickListener buttonRegisterListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            Log.i(Settings.TAG, getClass().getSimpleName() + " -OnClickListener-buttonRegisterListener-onClick-");
            Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(registerIntent);
        }
    };

    private boolean validateInputs() {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -validateInputs-");
        if(email.equals(Settings.KEY_EMPTY) ){
            editTextEmail.setError(getResources().getString(R.string.emailPromptHint) + " " + getResources().getString(R.string.inputCannotBeEmpty));
            editTextEmail.requestFocus();
            return false;
        }
        if(password.equals(Settings.KEY_EMPTY)){
            editTextPassword.setError(getResources().getString(R.string.passwordPromptHint) + " " + getResources().getString(R.string.inputCannotBeEmpty));
            editTextPassword.requestFocus();
            return false;
        }
        if(!isEmailValid(email)) {
            editTextEmail.setError(getResources().getString(R.string.emailNotValid));
            editTextEmail.requestFocus();
            return false;
        }
        return true;
    }

    private boolean isEmailValid(CharSequence email) {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -isEmailValid-");
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean login() {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -login-");
        JSONObject data = new JSONObject();

        //Populate the data parameters
        try {
            data.put(Settings.KEY_ACTION, Settings.ACTION_LOGIN);
            data.put(Settings.KEY_EMAIL, email);
            data.put(Settings.KEY_PASSWORD, password);
            Connection connection = new Connection(data, Settings.REQUEST_METHOD);
            connection.execute();
            JSONObject response = connection.get();

            String message = response.getString(Settings.KEY_MESSAGE);
            int codeResult = (int) response.get(Settings.KEY_CODE);

            Log.i(Settings.TAG, getClass().getSimpleName() + " -login- Code: " + codeResult + " \tMessage: " + message);
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            if (codeResult == Settings.LOGIN_OK_CODE) {
                Log.i(Settings.TAG, getClass().getSimpleName() + " -login-Settings.LOGIN_OK_CODE-");
                return true;
            } else {
                Log.i(Settings.TAG, getClass().getSimpleName() + " -login-LOGIN_CODE_NOT_OK-");
            }
        } catch (Exception e) {
            Log.i(Settings.TAG, getClass().getSimpleName() + " -login- Exception-");
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Launch Dashboard Activity on Successful Login
     *
     */
    private void loadDashboard() {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -loadDashboard-");
        Intent i;
        if(session.isStudent()) {
            i = new Intent(this, StudentDashboardActivity.class);
        } else {
            i = new Intent(this, TeacherDashboardActivity.class);
        }
        startActivity(i);
    }
}
