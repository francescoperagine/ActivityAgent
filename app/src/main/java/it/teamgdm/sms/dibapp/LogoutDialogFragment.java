package it.teamgdm.sms.dibapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

public class LogoutDialogFragment extends DialogFragment {

    Context applicationContext;

    public LogoutDialogFragment(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.logout_dialog_confirm)
                .setPositiveButton(R.string.logout_dialog_confirm_yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                SessionHandler session = new SessionHandler(applicationContext);
                session.logoutUser();
            }
        })
                .setNegativeButton(R.string.logout_dialog_confirm_no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }


}
