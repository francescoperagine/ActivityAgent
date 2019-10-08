package it.teamgdm.sms.dibapp;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

class EmptyTextButtonDisabler implements TextWatcher {

    private Button button;
    private TextView textView;

    EmptyTextButtonDisabler(Button button, TextView textView){
        Log.i(Constants.TAG, getClass().getSimpleName() + " -EmptyTextButtonDisabler-");
        this.button = button;
        this.textView = textView;
        button.setEnabled(false);
    }
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -beforeTextChanged-");
        button.setEnabled(false);
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onTextChanged-");
        if(textView.getText().length()==0){
            button.setEnabled(false);
        } else {
            button.setEnabled(true);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {
    }
}