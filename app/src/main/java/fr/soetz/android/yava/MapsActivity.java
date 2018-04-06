package fr.soetz.android.yava;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static GoogleMap mMap;
    private static List<Station> STATIONS_LIST;
    private static List<Marker> MARKERS_LIST = new ArrayList<Marker>();
    private static String mode = "pickup";
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        new VelovAsyncTask().execute("https://api.jcdecaux.com/vls/v1/stations?contract=Lyon&apiKey=e89c4f407c7947e3be8c0f80de5252c69c3c38ad");

        ImageView refresh = (ImageView) findViewById(R.id.toolbar_right_icon);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new VelovAsyncTask().execute("https://api.jcdecaux.com/vls/v1/stations?contract=Lyon&apiKey=e89c4f407c7947e3be8c0f80de5252c69c3c38ad");
            }
        });

        ToggleButton modeButton = (ToggleButton) findViewById(R.id.modeToggle);
        modeButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                changeMode(isChecked);
            }
        });
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View v = inflater.inflate(R.layout.info_window, null);

                TextView title = (TextView) v.findViewById(R.id.title);
                title.setText(marker.getTitle());

                try {
                    JSONObject data = new JSONObject(marker.getSnippet());

                    TextView status = (TextView) v.findViewById(R.id.status);
                    status.setText("Statut : " + data.getString("status"));

                    TextView address = (TextView) v.findViewById(R.id.address);
                    address.setText("Adresse : " + data.getString("address"));

                    TextView banking = (TextView) v.findViewById(R.id.banking);
                    banking.setText("Borne de paiement : " + data.getString("banking"));

                    TextView capacity = (TextView) v.findViewById(R.id.capacity);
                    capacity.setText("Capacité : " + data.getString("capacity"));

                    TextView bikes = (TextView) v.findViewById(R.id.bikes);
                    bikes.setText("Vélos : " + data.getString("bikes"));

                    TextView bikestands = (TextView) v.findViewById(R.id.bikestands);
                    bikestands.setText("Places libres : " + data.getString("available"));
                }
                catch(Exception e){
                }

                return(v);
            }
        });

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                marker.showInfoWindow();
                return false;
            }
        });

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if(isGPSEnabled){
            try {
                Location loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                LatLng actualPosition = new LatLng(loc.getLatitude(), loc.getLongitude());
                mMap.moveCamera(CameraUpdateFactory.newLatLng(actualPosition));
                mMap.moveCamera(CameraUpdateFactory.zoomTo(16.0f));
            }
            catch(SecurityException e){
                LatLng lyon = new LatLng(45.75, 4.85);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(lyon));
                mMap.moveCamera(CameraUpdateFactory.zoomTo(12.0f));
            }
        }
    }

    static protected void updateStationsList(List<Station> list){
        STATIONS_LIST = list;
        updateMap();
    }

    static protected void updateMap(){
        for(Marker marker : MARKERS_LIST){
            marker.remove();
        }
        MARKERS_LIST.clear();

        for(Station station : STATIONS_LIST){

            int count = 0;

            if(mode.equals("pickup")){
                count = station.getAvailableBikes();
            }
            else if(mode.equals("deposit")){
                count = station.getAvailableBikeStands();
            }

            Bitmap.Config conf = Bitmap.Config.ARGB_8888;
            Bitmap bmp = Bitmap.createBitmap(80, 80, conf);
            Canvas canvas1 = new Canvas(bmp);

            // paint defines the text color, stroke width and size
            Paint textColor = new Paint();
            textColor.setTextSize(40);
            textColor.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            textColor.setColor(Color.WHITE);

            Paint backgroundColor = new Paint();

            if(station.getStatus().equals("OPEN")){
                if(count == 0){
                    backgroundColor.setColor(Color.rgb(173, 32, 32));
                }
                else if(count == 1){
                    backgroundColor.setColor(Color.rgb(175, 119, 28));
                }
                else if(count == 2){
                    backgroundColor.setColor(Color.rgb(175, 148, 28));
                }
                else if(count == 3){
                    backgroundColor.setColor(Color.rgb(129, 140, 30));
                }
                else {
                    backgroundColor.setColor(Color.rgb(95, 175, 77));
                }
            }
            else if(station.getStatus().equals("CLOSED")){
                backgroundColor.setColor(Color.rgb(173, 32, 32));
            }
            else {
                backgroundColor.setColor(Color.GRAY);
            }

            canvas1.drawCircle(40, 40, 40, backgroundColor);

            int xPos = 40 - (int)(textColor.measureText(new Integer(count).toString())/2);
            int yPos = (int) ((canvas1.getHeight() / 2) - ((textColor.descent() + textColor.ascent()) / 2)) ;

            canvas1.drawText(new Integer(count).toString(), xPos, yPos, textColor);

            LatLng coords = new LatLng(station.getPosition().getLatitude(), station.getPosition().getLongitude());
            String banking = "Non";
            if(station.isBanking()){
                banking = "Oui";
            }
            String statut = "Inconnu";
            if(station.getStatus().equals("OPEN")){
                statut = "Ouvert";
            }
            else if(station.getStatus().equals("CLOSED")){
                statut = "Fermé";
            }

            JSONObject data = new JSONObject();
            try {
                data.put("status", statut);
                data.put("address", station.getAddress());
                data.put("banking", banking);
                data.put("capacity", station.getBikeStands());
                data.put("bikes", station.getAvailableBikes());
                data.put("available", station.getAvailableBikeStands());
            }
            catch(Exception e){

            }

            Marker marker = mMap.addMarker(new MarkerOptions()
                    .position(coords)
                    .title(station.getName())
                    .snippet(data.toString())
                    .icon(BitmapDescriptorFactory.fromBitmap(bmp)));

            MARKERS_LIST.add(marker);
        }
    }

    static protected void changeMode(boolean checked){
        if(checked){
            mode = "deposit";
        }
        else {
            mode = "pickup";
        }

        updateMap();
    }
}
