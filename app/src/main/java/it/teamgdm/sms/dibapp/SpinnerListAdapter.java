package it.teamgdm.sms.dibapp;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Retrieves the spinner data from the DB and returns the ArrayAdapter with setInputList()
 */

class SpinnerListAdapter {
    private Context context;
    private String spinnerArgument;
    private int spinnerSelectItemText;
    private int rowItemLayout;

    SpinnerListAdapter(Context context, String spinnerArgument, int spinnerSelectItemText, int rowItemLayout) {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -SpinnerListAdapter-");
        this.context = context;
        this.spinnerArgument = spinnerArgument;
        this.spinnerSelectItemText = spinnerSelectItemText;
        this.rowItemLayout = rowItemLayout;
    }

    ArrayAdapter<SpinnerElement> setInputList() {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -setInputList-");

        Log.i(Settings.TAG, getClass().getSimpleName() + " -setInputList- "+spinnerArgument);
        final JSONArray spinnerListData = loadSpinnerData(spinnerArgument);
        assert spinnerListData != null;
        ArrayList<SpinnerElement> spinnerElementList = setSpinnerList(spinnerListData, context.getResources().getString(spinnerSelectItemText));
        ArrayAdapter<SpinnerElement> spinnerAdapter = new ArrayAdapter<>(context, rowItemLayout, spinnerElementList);
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_row_item);
        return spinnerAdapter;
    }

    private JSONArray loadSpinnerData(String spinnerDataList) {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -loadSpinnerData-" + spinnerDataList);
        JSONObject data = new JSONObject();
        JSONArray response = null;
        try {
            data.put(Settings.KEY_ACTION, spinnerDataList);
            Log.i(Settings.TAG, getClass().getSimpleName() + " -loadSpinnerData-data: " + data);
            AsyncTaskConnection asyncTaskConnection = new AsyncTaskConnection();
            asyncTaskConnection.execute(data);
            response = asyncTaskConnection.get();
            Log.i(Settings.TAG, getClass().getSimpleName() + " -loadSpinnerData-response: " + response.toString());
        } catch (ExecutionException | InterruptedException | JSONException e) {
            Log.i(Settings.TAG, getClass().getSimpleName() + " -loadSpinnerData-Exception");
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            return null;
        }
        return response;
    }

    private ArrayList<SpinnerElement> setSpinnerList(JSONArray spinnerListData, String selectSpinnerValueText) {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -setSpinnerList-");
        ArrayList<SpinnerElement> spinnerElementList = new ArrayList<>();
        SpinnerElement selectSpinnerElement = new SpinnerElement(selectSpinnerValueText);
        spinnerElementList.add(selectSpinnerElement);
        for(int i = 0; i<spinnerListData.length(); i++) {
            try {
                SpinnerElement r = new SpinnerElement(spinnerListData.getJSONObject(i).optString(Settings.KEY_USER_NAME));
                spinnerElementList.add(r);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Log.i(Settings.TAG, getClass().getSimpleName() + " -setSpinnerList-spinnerElementList " + spinnerElementList);
        return spinnerElementList;
    }
}
