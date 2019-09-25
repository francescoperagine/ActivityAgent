package it.teamgdm.sms.dibapp;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

class AsyncTaskConnection extends AsyncTask<JSONObject, Void, JSONArray> {
    private final static String serverUrl = "http://10.72.50.176:80/sms-dibapp-server/api_gateway.php";
    //private final static String serverUrl = "http://192.168.1.110:80/sms-dibapp-server/api_gateway.php";
    //private final static String serverUrl = "http://www.civicsensebari.altervista.org/api_gateway.php";
    //private final static String serverUrl = "http://192.168.1.58:80/sms-dibapp-server/api_gateway.php";

    private URL url;

    AsyncTaskConnection() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -Constructor-");
        urlBuilder();
    }

    private void urlBuilder() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -urlBuilder-");
        try {
            url = new URL(serverUrl);
            Log.i(Constants.TAG, getClass().getSimpleName() + " -urlBuilder-URL " + url);
        } catch (MalformedURLException e) {
            Log.i(Constants.TAG, getClass().getSimpleName() + " -urlBuilder- fault");
            e.printStackTrace();
        }
    }

    @Override
    protected JSONArray doInBackground(JSONObject... dataArray) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -doInBackground-");
        HttpURLConnection urlConnection;
        BufferedReader bufferedReader;
        JSONArray response = null;
        String text;
        urlConnection = setConnection();
        bufferedReader = setBufferedReader(dataArray[0], urlConnection);
        try {
            while ((text = bufferedReader.readLine()) != null) {
                Log.i(Constants.TAG, getClass().getSimpleName() + " -doInBackground-while"+text);
                Object json = new JSONTokener(text).nextValue();
                if(json instanceof JSONObject) {
                    Log.i(Constants.TAG, getClass().getSimpleName() + " -doInBackground-while-JSONObject");
                    // convert to JSONArray
                    response = new JSONArray();
                    response.put(json);
                    Log.i(Constants.TAG, getClass().getSimpleName() + " -doInBackground-while-JSONObject-response"+ response.toString());
                } else if (json instanceof JSONArray) {
                    Log.i(Constants.TAG, getClass().getSimpleName() + " -doInBackground-while-JSONArray");
                    // acquire the JSONArray
                    response = new JSONArray(text);
                } else {
                    Log.i(Constants.TAG, getClass().getSimpleName() + " -doInBackground-while-WTFisThis"+json);
                }
            }
        } catch (IOException | JSONException e) {
            Log.i(Constants.TAG, getClass().getSimpleName() + " -doInBackground-IOException");
            e.printStackTrace();
        }
        return response;
    }

    private HttpURLConnection setConnection() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -setConnection-");
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestMethod(Constants.REQUEST_METHOD);
            urlConnection.setDoOutput(true);
        } catch (IOException e) {
            Log.i(Constants.TAG, getClass().getSimpleName() + " -setConnection-cannot connect");
            e.printStackTrace();
        }
        return urlConnection;
    }

    private BufferedReader setBufferedReader(JSONObject data, HttpURLConnection urlConnection) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -setBufferedReader-");
        BufferedReader bufferedReader = null;
        try {
            OutputStream outputStream = new BufferedOutputStream(urlConnection.getOutputStream());
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
            bufferedWriter.write(data.toString());
            bufferedWriter.flush();
            bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        } catch (IOException e) {
            Log.i(Constants.TAG, getClass().getSimpleName() + " -setBufferedReader-not working properly");
            e.printStackTrace();
        }
        return  bufferedReader;
    }

    protected void onPostExecute(JSONArray result) {
        super.onPostExecute(result);
    }
}