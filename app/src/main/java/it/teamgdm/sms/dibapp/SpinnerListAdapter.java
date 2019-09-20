package it.teamgdm.sms.dibapp;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Retrieves the spinner data from the DB and returns the ArrayAdapter with setInputList()
 */

class SpinnerListAdapter {
    private Context context;
    private int spinnerSelectItemText;
    private int rowItemLayout;

    SpinnerListAdapter(Context context, int spinnerSelectItemText, int rowItemLayout) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -SpinnerListAdapter-");
        this.context = context;
        this.spinnerSelectItemText = spinnerSelectItemText;
        this.rowItemLayout = rowItemLayout;
    }

    ArrayAdapter<SpinnerElement> setInputList(String spinnerArgument) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -setInputList- "+spinnerArgument);
        HashMap<String, String> params = new HashMap<>();
        params.put(Constants.KEY_ACTION, spinnerArgument);
        JSONArray spinnerListData = BaseActivity.getFromDB(params);
        ArrayList<SpinnerElement> spinnerElementList = setSpinnerList(spinnerListData, context.getResources().getString(spinnerSelectItemText));
        ArrayAdapter<SpinnerElement> spinnerAdapter = new ArrayAdapter<>(context, rowItemLayout, spinnerElementList);
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_row_item);
        return spinnerAdapter;
    }

    private ArrayList<SpinnerElement> setSpinnerList(JSONArray spinnerListData, String selectSpinnerValueText) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -setSpinnerList-");
        ArrayList<SpinnerElement> spinnerElementList = new ArrayList<>();
        SpinnerElement selectSpinnerElement = new SpinnerElement(selectSpinnerValueText);
        spinnerElementList.add(selectSpinnerElement);
        for(int i = 0; i<spinnerListData.length(); i++) {
            try {
                SpinnerElement r = new SpinnerElement(spinnerListData.getJSONObject(i).optString(Constants.KEY_USER_NAME));
                spinnerElementList.add(r);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Log.i(Constants.TAG, getClass().getSimpleName() + " -setSpinnerList-spinnerElementList " + spinnerElementList);
        return spinnerElementList;
    }
}
