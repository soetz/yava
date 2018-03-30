package fr.soetz.android.yava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listeV;
    private Button loadButton;
    private List<Station> stationList;
    private ArrayAdapter<Station> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*String testStation = "{\n" +
                "  \"number\": 123,\n" +
                "  \"contract_name\" : \"Paris\",\n" +
                "  \"name\": \"stations name\",\n" +
                "  \"address\": \"address of the station\",\n" +
                "  \"position\": {\n" +
                "    \"lat\": 48.862993,\n" +
                "    \"lng\": 2.344294\n" +
                "  },\n" +
                "  \"banking\": true,\n" +
                "  \"bonus\": false,\n" +
                "  \"status\": \"OPEN\",\n" +
                "  \"bike_stands\": 20,\n" +
                "  \"available_bike_stands\": 15,\n" +
                "  \"available_bikes\": 5,\n" +
                "  \"last_update\": 1521820413000\n" +
                "}";
        JsonParser.parseStation(testStation);*/

        stationList = new ArrayList<>();

        listeV = (ListView)findViewById(R.id.liste);
        loadButton = (Button) findViewById(R.id.loadbtn);

        adapter = new ArrayAdapter<Station>(MainActivity.this,
                android.R.layout.simple_list_item_1, stationList);
        listeV.setAdapter(adapter);

        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VelovAsyncTask task = new VelovAsyncTask();
                task.execute("https://api.jcdecaux.com/vls/v1/stations?contract=Lyon&apiKey=e89c4f407c7947e3be8c0f80de5252c69c3c38ad", stationList, adapter);
            }
        });
    }
}
