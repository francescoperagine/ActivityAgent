package it.teamgdm.sms.dibapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;

public class LoginActivity extends BaseActivity {


    private EditText editTextEmail, editTextPassword;
    private Button buttonSignIn;
    private Button buttonRegister;
    private String email;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onCreate-");
        super.onCreate(savedInstanceState);

        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);

        buttonSignIn = findViewById(R.id.sign_inButton);
        buttonRegister = findViewById(R.id.registerButton);
    }

    @Override
    int getLayoutResource() {
        return R.layout.activity_login;
    }

    @Override
    protected void onStart() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onStart-");
        super.onStart();

        buttonSignIn.setOnClickListener(buttonSignInListener);
        buttonRegister.setOnClickListener(buttonRegisterListener);
    }

    private final OnClickListener buttonSignInListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            Log.i(Constants.TAG, getClass().getSimpleName() + " -OnClickListener-buttonSignInListener-onClick-");
            email = editTextEmail.getText().toString().toLowerCase().trim();
            password = editTextPassword.getText().toString().trim();
            loginInit();
        }
    };

    public void loginInit() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -loginInit-");
        Intent mainActivityIntent = new Intent(this, MainActivity.class);
        if(validateInputs() & login()) {
            Log.i(Constants.TAG, getClass().getSimpleName() + " -loginInit-RESULT_OK");
            mainActivityIntent.putExtra(Constants.USER_LOGIN, Constants.LOGIN_OK_CODE);
            mainActivityIntent.putExtra(Constants.KEY_USER_EMAIL, email);
        } else {
            Log.i(Constants.TAG, getClass().getSimpleName() + " -loginInit-RESULT_CANCELED");
            mainActivityIntent.putExtra(Constants.USER_LOGIN, Constants.LOGIN_FAILED_CODE);
            finish();
        }
        startActivity(mainActivityIntent);
        finish();
    }

    private final OnClickListener buttonRegisterListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            Log.i(Constants.TAG, getClass().getSimpleName() + " -OnClickListener-buttonRegisterListener-onClick-");
            Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(registerIntent);
            finish();
        }
    };

    private boolean validateInputs() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -validateInputs-");
        if(email.equals(Constants.KEY_EMPTY) ){
            editTextEmail.setError(getResources().getString(R.string.emailPromptHint) + " " + getResources().getString(R.string.inputCannotBeEmpty));
            editTextEmail.requestFocus();
            return false;
        }
        if(password.equals(Constants.KEY_EMPTY)){
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
        Log.i(Constants.TAG, getClass().getSimpleName() + " -isEmailValid-");
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean login() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -login-");
        HashMap<String, String> params = new HashMap<>();
        params.put(Constants.KEY_ACTION, Constants.USER_LOGIN);
        params.put(Constants.KEY_USER_EMAIL, email);
        params.put(Constants.KEY_USER_PASSWORD, password);
        JSONArray response = DAO.getFromDB(params);

        try {
            String message = response.getJSONObject(0).getString(Constants.KEY_MESSAGE);
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            int codeResult = response.getJSONObject(0).getInt(Constants.KEY_CODE);
            if (codeResult == Constants.LOGIN_OK_CODE) {
                Log.i(Constants.TAG, getClass().getSimpleName() + " -setAccess-Constants.LOGIN_OK_CODE-");
                return true;
            } else {
                Log.i(Constants.TAG, getClass().getSimpleName() + " -setAccess-LOGIN_CODE_NOT_OK-");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }
}
