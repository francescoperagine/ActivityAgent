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

import java.util.Date;

public class RegisterActivity extends BaseActivity {

    private EditText editTextName, editTextSurname, editTextSerialNumber, editTextEmail, editTextPassword, editTextConfirmPassword;

    private Spinner spinnerRole, spinnerDegreecourse;
    private Button buttonRegister, buttonSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onCreate-");
        super.onCreate(savedInstanceState);

        disableBackButton();
        disableToolbar();

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

    @Override
    int getLayoutResource() {
        return R.layout.activity_register;
    }

    protected void onStart() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onStart-");
        super.onStart();
        setSpinners();
        buttonRegister.setOnClickListener(buttonRegisterListener);
        buttonSignIn.setOnClickListener(buttonSignInListener);
    }

    /**
     * Sets role & degreecourse spinners
     */

    private void setSpinners() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -setSpinners-");
        SpinnerListAdapter spinnerDegreecourseAdapter = new SpinnerListAdapter(this, R.string.selectDegreecourseText, android.R.layout.simple_spinner_dropdown_item);
        spinnerDegreecourse.setAdapter(spinnerDegreecourseAdapter.setInputList(Constants.GET_DEGREECOURSE_LIST));
        spinnerDegreecourse.setSelection(0);
        SpinnerListAdapter spinnerRoleAdapter = new SpinnerListAdapter(this, R.string.selectRoleText, android.R.layout.simple_spinner_dropdown_item);
        spinnerRole.setAdapter(spinnerRoleAdapter.setInputList(Constants.GET_ROLE_LIST));
        spinnerRole.setSelection(0);
    }

    private final View.OnClickListener buttonRegisterListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Log.i(Constants.TAG, getClass().getSimpleName() + " -OnClickListener-buttonRegisterListener-onClick-");
            registrationInit();
        }
    };

    private final View.OnClickListener buttonSignInListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Log.i(Constants.TAG, getClass().getSimpleName() + " -OnClickListener-buttonSignInListener-onClick-");
            toLogin();
        }
    };

    /**
     * Starts the registration process
     */

    private void registrationInit() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -registrationInit-");

        User tmpUser = new User();

        tmpUser.setName(editTextName.getText().toString().trim());
        tmpUser.setSurname(editTextSurname.getText().toString().trim());
        tmpUser.setSsn(editTextSerialNumber.getText().toString().trim()) ;
        tmpUser.setDegreeCourse(spinnerDegreecourse.getSelectedItem().toString());
        tmpUser.setRole(spinnerRole.getSelectedItem().toString());
        tmpUser.setEmail(editTextEmail.getText().toString().toLowerCase().trim());
        tmpUser.setPassword(editTextPassword.getText().toString().trim());
        tmpUser.setConfirmPassword(editTextConfirmPassword.getText().toString().trim());
        tmpUser.setRegistrationDate(new Date());

        if(validateInputs(tmpUser) && register(tmpUser)) {
            Log.i(Constants.TAG, getClass().getSimpleName() + " -registrationInit-registrationComplete-"+tmpUser);
            toLogin();
        }
    }

    private void toLogin() {
        Intent loginIntent = new Intent(this, LoginActivity.class);
        startActivity(loginIntent);
        finish();
    }

    /**
     * Validates inputs and shows error if any
     *
     */
    private boolean validateInputs(User tmpUser) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -validateInputs-");

        if (Constants.KEY_EMPTY.equals(tmpUser.getName())) {
            editTextName.setError(getResources().getString(R.string.namePromptHint) + " " + getResources().getString(R.string.inputCannotBeEmpty));
            editTextName.requestFocus();
            return false;
        }
        if (Constants.KEY_EMPTY.equals(tmpUser.getSurname())) {
            editTextSurname.setError(getResources().getString(R.string.surnamePromptHint) + " " + getResources().getString(R.string.inputCannotBeEmpty));
            editTextSurname.requestFocus();
            return false;
        }
        if (Constants.KEY_EMPTY.equals(tmpUser.getSsn())) {
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
            Log.i(Constants.TAG, getClass().getSimpleName() + " -validateInputs-!isEmailValid" + tmpUser.getEmail());
            editTextEmail.setError(getResources().getString(R.string.emailNotValid));
            editTextEmail.requestFocus();
            return false;
        }
        if (Constants.KEY_EMPTY.equals(tmpUser.getPassword())) {
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
     *
     */

    private boolean register(User tmpUser) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -register-");
        if(DAO.registerUser(tmpUser)) {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.user_registration_ok), Toast.LENGTH_SHORT).show();
            return true;
        } else {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.user_registration_not_ok), Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
