package fr.soetz.android.yava;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Adapter;
import android.widget.ArrayAdapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Thiago on 23/03/2018.
 */

public class VelovAsyncTask extends AsyncTask<Object, Void, String> {
    private ArrayAdapter myAdapter;
    private ArrayList<Station> stationsList;
    @Override
    protected String doInBackground(Object... params) {
        BufferedReader in;
        String urlString;
        StringBuilder builder;
        String oneLine;
        String response = "";
        URL url;
        HttpsURLConnection connection;

        urlString = (String) params[0];
        stationsList = (ArrayList<Station>) params[1];
        myAdapter = (ArrayAdapter) params[2];
        builder = new StringBuilder();

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

        stationsList = JsonParser.parseStations(response);

        for(Station station : stationsList){
            Log.d("magrosseteub", station.toString());
        }

        return response;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        myAdapter.notifyDataSetChanged();

    }
}
