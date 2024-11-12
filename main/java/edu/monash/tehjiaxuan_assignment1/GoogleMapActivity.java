package edu.monash.tehjiaxuan_assignment1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Locale;

import edu.monash.tehjiaxuan_assignment1.databinding.ActivityGoogleMapBinding;

public class GoogleMapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityGoogleMapBinding binding;

    private String country;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        binding = ActivityGoogleMapBinding.inflate(getLayoutInflater());
        setContentView(R.layout.google_map_app_bar);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        country = getIntent().getExtras().getString("Country", "Default");
        Toolbar myToolbar = (Toolbar) findViewById(R.id.googlemaptoolbar);
        setSupportActionBar(myToolbar);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.goback);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){

        if(item.getItemId() == android.R.id.home){
            Intent intentNewEvent = new Intent(getApplicationContext(), AddNewEvent.class);
            startActivity(intentNewEvent);

        }
        return true;
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

        /**
         * Change map display type, feel free to explore other available map type:
         * MAP_TYPE_NORMAL: Basic map.
         * MAP_TYPE_SATELLITE: Satellite imagery.
         * MAP_TYPE_HYBRID: Satellite imagery with roads and labels.
         * MAP_TYPE_TERRAIN: Topographic data.
         * MAP_TYPE_NONE: No base map tiles
         */
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        // place latitude-longitude values in the order specified
        LatLng monashMsia = new LatLng(3.0652404638624455, 101.60094259630084);

        // adds a marker to the specified latitude-longitude
        mMap.addMarker(new MarkerOptions().position(monashMsia).title("Default"));

        // use moveCamera method to move current map viewing angle to Malaysia campus
        mMap.moveCamera(CameraUpdateFactory.newLatLng(monashMsia));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10.0f));

        findCountryMoveCamera();
    }

    private void findCountryMoveCamera(){
        if(TextUtils.isEmpty(country)){
            LatLng monashMsia = new LatLng(3.0652404638624455, 101.60094259630084);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(monashMsia));
            // adds a marker to the specified latitude-longitude
            mMap.addMarker(new MarkerOptions().position(monashMsia).title("Default"));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(10.0f));
            Toast.makeText(getApplicationContext(), "Category address not found", Toast.LENGTH_SHORT).show();
            return;
        }
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

            /**
             * countryToFocus: String value, any string we want to search
             * maxResults: how many results to return if search was successful
             * successCallback method: if results are found, this method will be executed
             *                          runs in a background thread
             */
            geocoder.getFromLocationName(country, 1, addresses -> {

                // if there are results, this condition would return true
                if (!addresses.isEmpty()) {
                    // run on UI thread as the user interface will update once set map location
                    runOnUiThread(() -> {
                        // define new LatLng variable using the first address from list of addresses
                        LatLng newAddressLocation = new LatLng(
                                addresses.get(0).getLatitude(),
                                addresses.get(0).getLongitude()
                        );

                        // repositions the camera according to newAddressLocation
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(newAddressLocation));

                        // just for reference add a new Marker
                        mMap.addMarker(
                                new MarkerOptions()
                                        .position(newAddressLocation)
                                        .title(country)
                        );

                        mMap.animateCamera(CameraUpdateFactory.zoomTo(10.0f));
                    });
                }
                else {
                    runOnUiThread(() -> {
                        LatLng monashMsia = new LatLng(3.0652404638624455, 101.60094259630084);
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(monashMsia));
                        // adds a marker to the specified latitude-longitude
                        mMap.addMarker(new MarkerOptions().position(monashMsia).title("Default"));
                        mMap.animateCamera(CameraUpdateFactory.zoomTo(10.0f));
                        // use moveCamera method to move current map viewing angle to Malaysia campus
                        Toast.makeText(getApplicationContext(), "Category address not found", Toast.LENGTH_SHORT).show();
                    });
                }

            });
        }
    }

}