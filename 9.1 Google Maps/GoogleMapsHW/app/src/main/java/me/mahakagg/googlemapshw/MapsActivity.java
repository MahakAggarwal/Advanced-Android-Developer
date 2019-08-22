package me.mahakagg.googlemapshw;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Locale;
/*
* Answers to homework questions
*
* Q1 - Which method is called when the map is loaded and ready to be used in the app ?
* A1 - onMapReady (GoogleMap googleMap)
*
* Q2 - Which Android components can you use to include Google Maps in your app ?
* A2 - MapView and MapFragment
*
* Q3 - What types of maps does the Google Maps Android API offer?
* A3 - Normal, hybrid, terrain, satellite, and "none"
*
* Q4 - What interface do you implement to add on-click functionality to a point of interest (POI)?
* A4 - GoogleMap.OnPoiClickListener
*
* */
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LatLng mLocation1; // first location
    private LatLng mLocation2; // second location
    private static final int ZOOM = 15;
    private static final int REQUEST_LOCATION_PERMISSION = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        mLocation1 = new LatLng(37.422089, -122.083950); // GooglePlex
        mLocation2 = new LatLng(37.415831, -122.077544); // Starbucks
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
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mLocation1, ZOOM));
        String snippet1 = String.format(Locale.getDefault(), "Lat: %1$.5f, Long: %2$.5f", mLocation1.latitude, mLocation1.longitude);
        String snippet2 = String.format(Locale.getDefault(), "Lat: %1$.5f, Long: %2$.5f", mLocation2.latitude, mLocation2.longitude);
        mMap.addMarker(new MarkerOptions()
                .position(mLocation1)
                .title(getString(R.string.googleplex))
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                .snippet(snippet1));

        mMap.addMarker(new MarkerOptions()
                .position(mLocation2)
                .title(getString(R.string.starbucks))
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                .snippet(snippet2));

        enableMyLocation();
    }

    // enable location tracking
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // Check if location permissions are granted and if so enable the location data layer.
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                enableMyLocation();
            }
        }
    }
}
