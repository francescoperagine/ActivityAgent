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

public class Connection extends AsyncTask<Void, Void, Integer> {
    private final String TAG = "dibApp.Connection";
    private String serverUrl = "http://192.168.1.5:80/sms-dibapp-server/";
 //   private String serverUrl = "http://10.72.50.165:80/sms-dibapp-server/";

    private static final String KEY_CODE = "code";
    private static final String KEY_MESSAGE = "message";

    private URL url;
    private HttpURLConnection urlConnection;
    private String requestMethod;

    private JSONObject data;
    private JSONObject response;

    private int code;
    private String message;

    Connection(JSONObject data, String targetUrl, String requestMethod) {
        Log.i(TAG, getClass().getSimpleName() + " -Constructor-");
        this.data = data;
        urlBuilder(targetUrl);
        this.requestMethod = requestMethod;
    }

    private void urlBuilder(String targetUrl) {
        Log.i(TAG, getClass().getSimpleName() + " -urlBuilder-");
        try {
            url = new URL(serverUrl + targetUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        Log.i(TAG, getClass().getSimpleName() + " -doInBackground-");
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestMethod(requestMethod);
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setChunkedStreamingMode(0);
            urlConnection.setRequestProperty("Connection", "close");

            OutputStream outputStream = new BufferedOutputStream(urlConnection.getOutputStream());
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            bufferedWriter.write(data.toString());
            bufferedWriter.flush();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                Log.i(TAG, getClass().getSimpleName() + " -bufferedReader.readLine-");
                readResponse(line);
            }
        } catch (IOException e) {
            Log.i(TAG, e.getMessage());
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
            return code;
        }
    }

    private void readResponse(String line) {
        Log.i(TAG, getClass().getSimpleName() + " -readResponse-");
        try {
            Log.i(TAG, "\n\t " + line);
            response = new JSONObject(line);
            code = (int) response.get(KEY_CODE);
            message = response.getString(KEY_MESSAGE);
            Log.i(TAG, getClass().getSimpleName() + " -readResponse-" + "\n\t"+KEY_CODE+": " + code + "\t"+KEY_MESSAGE+": " + message);
       } catch (JSONException e) {
            Log.i(TAG,response.toString());
            e.printStackTrace();
        }
    }
}
