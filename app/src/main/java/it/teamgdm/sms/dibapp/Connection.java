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
    private final static String serverUrl = "http://10.72.50.165:80/sms-dibapp-server/api_gateway.php";
    // private final static String serverUrl = "http://192.168.1.5:80/sms-dibapp-server/api_gateway.php";
    // private final static String serverUrl = "http://192.168.1.178:80/sms-dibapp-server/api_gateway.php";
    private URL url;
    private HttpURLConnection urlConnection;
    private final String requestMethod;

    private final JSONObject data;

    Connection(JSONObject data, String requestMethod) {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -Constructor-");
        this.data = data;
        this.requestMethod = requestMethod;
        urlBuilder();
    }

    private void urlBuilder() {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -urlBuilder-");
        try {
            url = new URL(serverUrl);
            Log.i(Settings.TAG, getClass().getSimpleName() + " -urlBuilder-URL " + url);
        } catch (MalformedURLException e) {
            Log.i(Settings.TAG, getClass().getSimpleName() + " -urlBuilder- fault");
            e.printStackTrace();
        }
    }

    @Override
    protected JSONObject doInBackground(Void... voids) {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -doInBackground-");
        JSONObject response = null;
        String text;

        try {
            setConnection();
            OutputStream outputStream = new BufferedOutputStream(urlConnection.getOutputStream());
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
            bufferedWriter.write(data.toString());
            bufferedWriter.flush();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            while ((text = bufferedReader.readLine()) != null) {
                Log.i(Settings.TAG, getClass().getSimpleName() + " -doInBackground-while"+text);
                response = getResponse(text);
            }
        } catch (IOException e) {
            Log.i(Settings.TAG, getClass().getSimpleName() + " -doInBackground-Exception");
            e.printStackTrace();
        }
        return response;
    }

    private JSONObject getResponse(String text) {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -getResponse-");
        JSONObject response = null;
        try {
            response = new JSONObject(String.valueOf(text));
        } catch (JSONException e) {
            Log.i(Settings.TAG, getClass().getSimpleName() + " -getResponse-Exception");
            e.printStackTrace();
        }
        return response;
    }

    private void setConnection() {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -setConnection-");
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestMethod(requestMethod);
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setChunkedStreamingMode(0);
            urlConnection.setRequestProperty("Connection", "close");
        } catch (IOException e) {
            Log.i(Settings.TAG, getClass().getSimpleName() + " -setConnection-cannot connect");
            e.printStackTrace();
        }
    }
}
