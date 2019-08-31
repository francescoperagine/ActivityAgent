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

class Connection extends AsyncTask<Void, Void, JSONArray> {
    // private final static String serverUrl = "http://10.72.50.165:80/sms-dibapp-server/api_gateway.php";
    private final static String serverUrl = "http://192.168.1.2:80/sms-dibapp-server/api_gateway.php";
    // private final static String serverUrl = "http://civicsensebari.altervista.org/api_gateway.php";
    // private final static String serverUrl = "http://192.168.1.178:80/sms-dibapp-server/api_gateway.php";
    private URL url;
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
    protected JSONArray doInBackground(Void... voids) {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -doInBackground-");
        HttpURLConnection urlConnection = null;
        BufferedReader bufferedReader = null;
        JSONArray response = null;
        String text;

        try {
            urlConnection = setConnection();
            bufferedReader = setBufferedReader(urlConnection);

            while ((text = bufferedReader.readLine()) != null) {
                Log.i(Settings.TAG, getClass().getSimpleName() + " -doInBackground-while"+text);
                Object json = new JSONTokener(text).nextValue();
                if(json instanceof JSONObject) {
                    Log.i(Settings.TAG, getClass().getSimpleName() + " -doInBackground-while-JSONObject");
                    // convert to JSONArray
                    response = new JSONArray();
                    response.put(json);
                    Log.i(Settings.TAG, getClass().getSimpleName() + " -doInBackground-while-JSONObject-response"+ response.toString());
                } else if (json instanceof JSONArray) {
                    Log.i(Settings.TAG, getClass().getSimpleName() + " -doInBackground-while-JSONArray");
                    // acquire the JSONArray
                    response = new JSONArray(text);
                } else {
                    Log.i(Settings.TAG, getClass().getSimpleName() + " -doInBackground-while-WTFisThis");
                }
            }
        } catch (IOException | JSONException e) {
            Log.i(Settings.TAG, getClass().getSimpleName() + " -doInBackground-IOException");
            e.printStackTrace();
        }
        return response;
    }

    private HttpURLConnection setConnection() {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -setConnection-");
        HttpURLConnection urlConnection = null;
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
        return urlConnection;
    }

    private BufferedReader setBufferedReader(HttpURLConnection urlConnection) {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -setBufferedReader-");
        BufferedReader bufferedReader = null;
        try {
            OutputStream outputStream = new BufferedOutputStream(urlConnection.getOutputStream());
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
            bufferedWriter.write(data.toString());
            bufferedWriter.flush();
            bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        } catch (IOException e) {
            Log.i(Settings.TAG, getClass().getSimpleName() + " -setBufferedReader-not working properly");
            e.printStackTrace();
        }
        return  bufferedReader;
    }
}