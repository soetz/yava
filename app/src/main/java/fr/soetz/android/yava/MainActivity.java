package fr.soetz.android.yava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private ListView liste;
    private Button loadButton;
    private String[] objets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String testStation = "{\n" +
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
        JsonParser.parseStation(testStation);

        liste = (ListView)findViewById(R.id.liste);
        loadButton = (Button) findViewById(R.id.loadbtn);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1, objets);
        liste.setAdapter(adapter);

        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new VelovAsyncTask().execute("");
            }
        });
    }
}
