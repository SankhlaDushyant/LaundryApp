package xyz.dushyant31.laundroapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap mMap;
    private ArrayList<MyDataModel> list;
    Button Refreshdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        list = new ArrayList<>();
        Refreshdata = findViewById(R.id.refresh_btn);
        Refreshdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ScanData().execute();
            }
        });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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

        // Add a marker in Sydney and move the camera
        LatLng CurrentLocation = new LatLng(40.7426440, -74.0400090);
        mMap.addMarker(new MarkerOptions().position(CurrentLocation).title("Marker at Orange laundrowash "));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(CurrentLocation));
        mMap.animateCamera(CameraUpdateFactory.zoomTo( 13.0f ) );

    }

    private class ScanData extends AsyncTask<Void, Void, Void> {

        int jIndex;
        int x;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            x = list.size();
            if (x == 0)
                jIndex = 0;
            else
                jIndex = x;

        }

        @Nullable
        @Override
        protected Void doInBackground(Void... params) {
            JSONObject jsonObject = Controller.readAllData();
            try {
                if (jsonObject != null) {
                    if (jsonObject.length() > 0) {
                        JSONArray array = jsonObject.getJSONArray("records");
                        int lenArray = array.length();
                        if (lenArray > 0) {
                            for (; jIndex < lenArray; jIndex++) {
                                MyDataModel model = new MyDataModel();
                                JSONObject innerObject = array.getJSONObject(jIndex);
                                String CompanyName = innerObject.getString("CompanyName");
                                String Date = innerObject.getString("Date");
                                model.setName(CompanyName);
                                model.setId(Date);
                                list.add(model);
                            }
                        }
                    }
                }
            } catch (JSONException je) {
                Log.i(Controller.TAG, "" + je.getLocalizedMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (list.size() > 0) {
                for(int i = 0;i<list.size();i++) {

                    LatLng CurrentLocation = new LatLng(40.7382160, -74.0340590);
                    mMap.addMarker(new MarkerOptions().position(CurrentLocation).title("new"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(CurrentLocation));
                }

            } else {
                Toast.makeText(getApplicationContext(), "No data found", Toast.LENGTH_LONG).show();
            }
        }
    }
}
