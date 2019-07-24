package it.teamgdm.sms.dibapp;

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

    private final String TAG = "dibApp.LoginActivity";

    private static final String login_url = "login.php";
    private static final String REQUEST_METHOD = "POST";
    private static final String LOGIN_OK = "Login is complete.";
    private static final int LOGIN_OK_CODE = 201;

    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_EMPTY = "";

    private int codeResult;

    private SessionHandler session;
    private EditText editTextEmail, editTextPassword;

    Button buttonSignIn, buttonRegister;
    String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, getClass().getSimpleName() + " -onCreate- The activity is getting created.");
        super.onCreate(savedInstanceState);

        session = new SessionHandler(getApplicationContext());
        if(session.isLoggedIn()){
            loadDashboard();
        }
        setContentView(R.layout.activity_login);

        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);

        buttonSignIn = findViewById(R.id.sign_in);
        buttonRegister = findViewById(R.id.register);
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

    public OnClickListener buttonSignInListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            email = editTextEmail.getText().toString().toLowerCase().trim();
            password = editTextPassword.getText().toString().trim();
            if(validateInputs()) login();

        }
    };

    public OnClickListener buttonRegisterListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(registerIntent);
        }
    };

    private boolean validateInputs() {
        if(KEY_EMPTY.equals(email) ){
            editTextEmail.setError(getResources().getString(R.string.emailPromptHint) + " " + getResources().getString(R.string.inputCannotBeEmpty));
            editTextEmail.requestFocus();
            return false;
        }
        if(KEY_EMPTY.equals(password)){
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

    boolean isEmailValid(CharSequence email) {
        Log.i(TAG, getClass().getSimpleName() + " -isEmailValid-");
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void login() {
        Log.i(TAG, getClass().getSimpleName() + " -login-");
        JSONObject data = new JSONObject();

        //Populate the data parameters
        try {
            data.put(KEY_EMAIL, email);
            data.put(KEY_PASSWORD, password);
            Connection connection = new Connection(data, login_url, REQUEST_METHOD);
            connection.execute();
            codeResult = connection.get();
            Log.i(TAG, getClass().getSimpleName() + " -login- execute. " + codeResult);
            if (codeResult == LOGIN_OK_CODE) {
                Log.i(TAG, getClass().getSimpleName() + " -login-LOGIN_OK_CODE-");
                Toast.makeText(getApplicationContext(), LOGIN_OK, Toast.LENGTH_SHORT).show();
                session.loginUser(email);
                loadDashboard();
            } else {
                Log.i(TAG, getClass().getSimpleName() + " -login-else-");
            }
        } catch (Exception e) {
            Log.i(TAG, getClass().getSimpleName() + " -login-Exception-");
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    /**
     * Launch Dashboard Activity on Successful Login
     */
    private void loadDashboard() {
        Log.i(TAG, getClass().getSimpleName() + " -loadDashboard-");
        Intent i = new Intent(getApplicationContext(), DashboardActivity.class);
        startActivity(i);
    }
}