package it.teamgdm.sms.dibapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

class LogoutDialogFragment extends DialogFragment {

    private final Context applicationContext;

    LogoutDialogFragment(Context applicationContext) {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -LogoutDialogFragment-");
        this.applicationContext = applicationContext;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -onCreateDialog-");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.logout_dialog_confirm)
                .setPositiveButton(R.string.logout_dialog_confirm_yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SessionHandler session = new SessionHandler(applicationContext);
                        session.logoutUser();
                    }})
                .setNegativeButton(R.string.logout_dialog_confirm_no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}