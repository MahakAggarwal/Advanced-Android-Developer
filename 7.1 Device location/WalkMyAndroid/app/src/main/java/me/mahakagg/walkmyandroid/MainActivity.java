package me.mahakagg.walkmyandroid;

import android.Manifest;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

public class MainActivity extends AppCompatActivity implements FetchAddressTask.OnTaskCompleted {
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private static final String TRACKING_LOCATION_KEY = "tracking_location_key";
    //    private Location mLastLocation;
    private TextView mLocationTextView;
    private Button mButton;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private ImageView mAndroidImageView;
    private AnimatorSet mRotateAnim;
    private boolean mTrackingLocation;
    private LocationCallback mLocationCallback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // find views
        mButton = findViewById(R.id.button_location);
        mLocationTextView = findViewById(R.id.textview_location);
        mAndroidImageView = findViewById(R.id.imageview_android);
        // animator setup
        mRotateAnim = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.rotate);
        mRotateAnim.setTarget(mAndroidImageView);
        // set on click listener for button
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mTrackingLocation) {
                    startTrackingLocation();
                } else {
                    stopTrackingLocation();
                }
            }
        });
        // restore savedInstanceState if possible
        if (savedInstanceState != null) {
            mTrackingLocation = savedInstanceState.getBoolean(TRACKING_LOCATION_KEY);
        }
        // initialize fusedLocationProviderClient object
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        // fusedLocationProviderClient invokes the onLocationResult() method when location update is requested
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                // if tracking is on, start reverse geo-coding
                if (mTrackingLocation) {
                    new FetchAddressTask(MainActivity.this, MainActivity.this).execute(locationResult.getLastLocation());
                }
            }
        };
    }

    private void startTrackingLocation() {
        // check for permission
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // if not granted, ask for permission
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
        } else {
            // permission already granted
            // request periodic location updates
            mFusedLocationProviderClient.requestLocationUpdates(getLocationRequest(), mLocationCallback, null);
        }
        // show loading text while asyncTask runs
        mLocationTextView.setText(getString(R.string.address_text, getString(R.string.loading), System.currentTimeMillis()));
        // start animation, change boolean value, and change button text
        mRotateAnim.start();
        mTrackingLocation = true;
        mButton.setText(R.string.stop_tracking_location);
    }

    // this method executes when ActivityCompat requests permission from user
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            // if permission granted, get location
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startTrackingLocation();
            } else {
                // if permission not granted, show toast
                Toast.makeText(this, getString(R.string.location_permission_denied), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void stopTrackingLocation() {
        if (mTrackingLocation) {
            mTrackingLocation = false;
            mButton.setText(R.string.get_location);
            mLocationTextView.setText(R.string.textview_hint);
            mRotateAnim.end();
            mFusedLocationProviderClient.removeLocationUpdates(mLocationCallback);
        }
    }

    // LocationRequest object has setter methods which determine frequency and accuracy of location updates
    private LocationRequest getLocationRequest() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000)
                .setFastestInterval(5000)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return locationRequest;
    }

    // update textView with result from AsyncTask's interface
    @Override
    public void onTaskCompleted(String result) {
        if (mTrackingLocation) {
            mLocationTextView.setText(getString(R.string.address_text, result, System.currentTimeMillis()));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mTrackingLocation){
            startTrackingLocation();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mTrackingLocation){
            stopTrackingLocation();
            mTrackingLocation = true;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(TRACKING_LOCATION_KEY, mTrackingLocation);
        super.onSaveInstanceState(outState);
    }
}
