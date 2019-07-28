package it.teamgdm.sms.dibapp;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

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

class Connection extends AsyncTask<Void, Void, JSONObject> {
    private final static String TAG = "dibApp.Connection";

    // private final static String serverUrl = "http://10.72.50.165:80/sms-dibapp-server/api_gateway.php";
    final static String serverUrl = "http://192.168.1.5:80/sms-dibapp-server/api_gateway.php";
    private URL url;
    private HttpURLConnection urlConnection;
    private final String requestMethod;

    private final JSONObject data;
    private JSONObject response;

    Connection(JSONObject data, String requestMethod) {
        Log.i(TAG, getClass().getSimpleName() + " -Constructor-");
        this.data = data;
        this.requestMethod = requestMethod;
        urlBuilder();
    }

    private void urlBuilder() {
        Log.i(TAG, getClass().getSimpleName() + " -urlBuilder-");
        try {
            url = new URL(serverUrl);
            Log.i(TAG, getClass().getSimpleName() + " -urlBuilder-URL " + url);
        } catch (MalformedURLException e) {
            Log.i(TAG, getClass().getSimpleName() + " -urlBuilder- fault");
            e.printStackTrace();
        }
    }

    @Override
    protected JSONObject doInBackground(Void... voids) {
        Log.i(TAG, getClass().getSimpleName() + " -doInBackground-");
        try {
            setConnection();
            OutputStream outputStream = new BufferedOutputStream(urlConnection.getOutputStream());
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
            bufferedWriter.write(data.toString());
            bufferedWriter.flush();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                Log.i(TAG, getClass().getSimpleName() + " -doInBackground-while"+line);
                readResponse(line);
            }
        } catch (IOException e) {
            Log.i(TAG, getClass().getSimpleName() + " -doInBackground-Exception");
            e.printStackTrace();
        }
        return response;
    }

    private void setConnection() {
        Log.i(TAG, getClass().getSimpleName() + " -setConnection-");
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestMethod(requestMethod);
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setChunkedStreamingMode(0);
            urlConnection.setRequestProperty("Connection", "close");
        } catch (IOException e) {
            Log.i(TAG, getClass().getSimpleName() + " -setConnection-cannot connect");
            e.printStackTrace();
        }
    }

    private void readResponse(String line) {
        Log.i(TAG, getClass().getSimpleName() + " -readResponse-");
        try {
            response = new JSONObject(line);
            Log.i(TAG, "\nResponse: "+ response);
            Log.i(TAG, "\nResponse: "+ line);
       } catch (JSONException e) {
            Log.i(TAG,response.toString());
            e.printStackTrace();
        }
    }
}
