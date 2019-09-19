package it.teamgdm.sms.dibapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {


    private EditText editTextEmail, editTextPassword;
    private Button buttonSignIn;
    private Button buttonRegister;
    private String email;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onCreate-");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);

        buttonSignIn = findViewById(R.id.sign_inButton);
        buttonRegister = findViewById(R.id.registerButton);
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
        Intent mainActivityIntent = new Intent();
        mainActivityIntent.putExtra(Constants.KEY_USER_EMAIL, email);
        if(validateInputs() & login()) {
            setResult(Activity.RESULT_OK, mainActivityIntent);
        } else {
            setResult(Activity.RESULT_CANCELED, mainActivityIntent);
        }
        finish();
    }

    private final OnClickListener buttonRegisterListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            Log.i(Constants.TAG, getClass().getSimpleName() + " -OnClickListener-buttonRegisterListener-onClick-");
            Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(registerIntent);
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
        Log.i(Constants.TAG, getClass().getSimpleName() + " -setAccess-");
        JSONObject data = new JSONObject();

        //Populate the data parameters
        try {
            data.put(Constants.KEY_ACTION, Constants.USER_LOGIN);
            data.put(Constants.KEY_USER_EMAIL, email);
            data.put(Constants.KEY_USER_PASSWORD, password);
            AsyncTaskConnection asyncTaskConnection = new AsyncTaskConnection();
            asyncTaskConnection.execute(data);
            JSONArray response = asyncTaskConnection.get();
            Log.i(Constants.TAG, getClass().getSimpleName() + " -setAccess- response:" + response.toString());
            String message = response.getJSONObject(0).getString(Constants.KEY_MESSAGE);
            int codeResult = response.getJSONObject(0).getInt(Constants.KEY_CODE);

            Log.i(Constants.TAG, getClass().getSimpleName() + " -setAccess- Code: " + codeResult + " \tMessage: " + message);
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            if (codeResult == Constants.LOGIN_OK_CODE) {
                Log.i(Constants.TAG, getClass().getSimpleName() + " -setAccess-Constants.LOGIN_OK_CODE-");
                return true;
            } else {
                Log.i(Constants.TAG, getClass().getSimpleName() + " -setAccess-LOGIN_CODE_NOT_OK-");
            }
        } catch (Exception e) {
            Log.i(Constants.TAG, getClass().getSimpleName() + " -setAccess- Exception-");
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return false;
    }
}
