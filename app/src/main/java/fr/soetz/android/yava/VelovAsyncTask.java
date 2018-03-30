package fr.soetz.android.yava;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Thiago on 23/03/2018.
 */

public class VelovAsyncTask extends AsyncTask<Object, Void, String> {
    
    @Override
    protected String doInBackground(Object... params) {
        BufferedReader in;
        String urlString = (String) params[0];
        StringBuilder builder = new StringBuilder();
        String oneLine;
        String response = null;
        URL url;
        HttpsURLConnection connection;

        try {
            url = new URL(urlString);
            connection = (HttpsURLConnection) url.openConnection();
            if(connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while((oneLine = in.readLine()) != null){
                    builder.append(oneLine);
                }
                response = builder.toString();
                in.close();
            }
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
