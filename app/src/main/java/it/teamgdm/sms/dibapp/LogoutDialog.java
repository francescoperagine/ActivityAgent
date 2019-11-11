package it.teamgdm.sms.dibapp;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.annotation.NonNull;

public class LogoutDialog extends Dialog implements View.OnClickListener {

    private Button confirmButton;

    LogoutDialog(@NonNull Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.logout_dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Button cancelButton = findViewById(R.id.logoutCancelButton);
        confirmButton = findViewById(R.id.logoutConfirmButton);
        cancelButton.setOnClickListener(this);
        confirmButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onClick-");
        if(v == confirmButton) {
            Session.logout();
            dismiss();
        } else {
            dismiss();
        }
    }
}
