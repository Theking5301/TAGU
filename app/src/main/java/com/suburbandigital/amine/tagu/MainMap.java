package com.suburbandigital.amine.tagu;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.IntentSender;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.suburbandigital.amine.tagu.Map.MapTagManager;
import com.suburbandigital.amine.tagu.Tags.Tag;
import com.suburbandigital.amine.tagu.Tags.TagMarkerInfo;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.suburbandigital.amine.tagu.Tags.TagType;


public class MainMap extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private GoogleMap mMap;
    private MapTagManager manager;
    private GoogleApiClient mGoogleApiClient;
    public static final String TAG = MainMap.class.getSimpleName();
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private LocationRequest mLocationRequest;
    private Marker selectedMarker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 100)        // 1 seconds, in milliseconds
                .setFastestInterval(1000); // 1 second, in milliseconds

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        Button button = (Button)findViewById(R.id.AddTag);
        button.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                        Tag tag = new Tag("Hello", "Custom Tag", "SBU", TagType.BUILDING, location.getLatitude(), location.getLongitude());
                        manager.addTagToDB(tag);
                        mMap.clear();
                        manager.addMarkersToMap();
                    }
                }
        );
        Button button2 = (Button)findViewById(R.id.DeleteTag);
        button2.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        manager.removeMarkerFromDB(selectedMarker);
                        selectedMarker.remove();
                    }
                }
        );
        Button button3 = (Button)findViewById(R.id.ClearMap);
        button3.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        manager.clearDB();
                        mMap.clear();
                    }
                }
        );
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        manager = new MapTagManager(getApplicationContext(), mMap);
        final View bottomFrame = findViewById(R.id.BottomFrame);
        bottomFrame.setVisibility(View.GONE);
        bottomFrame.setAlpha(0f);
        //bottomFrame.setTranslationY(300);
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        mMap.setOnMarkerClickListener(
                new OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        bottomFrame.setVisibility(View.VISIBLE);
                        bottomFrame.animate().alpha(1f).setDuration(getResources().getInteger(android.R.integer.config_shortAnimTime)).setListener(null);
                        TextView name = (TextView) findViewById(R.id.TagName);
                        name.setText(marker.getTitle());
                        TextView desc = (TextView) findViewById(R.id.Desc);
                        desc.setText(marker.getSnippet());
                        TextView pos = (TextView) findViewById(R.id.Pos);
                        pos.setText(marker.getPosition().toString());
                        selectedMarker = marker;
                        return false;
                    }
                });
        mMap.setOnMapClickListener(
                new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        bottomFrame.animate()
                                .alpha(0f)
                                .setDuration(getResources().getInteger(
                                        android.R.integer.config_shortAnimTime))
                                .setListener(new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        bottomFrame.setVisibility(View.GONE);
                                    }
                                });
                    }
                }
        );
        mMap.setInfoWindowAdapter(new TagMarkerInfo(getApplicationContext()));
        manager.addMarkersToMap();
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        mMap.getUiSettings().setMapToolbarEnabled(false);
    }


    @Override
    protected void onResume() {
        super.onResume();
        mGoogleApiClient.connect();
    }
    @Override
    protected void onPause() {
        super.onPause();
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
    }
    @Override
    public void onConnected(Bundle bundle) {
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        Log.i(TAG, "Connection has been established");
        if (location == null) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        } else {
            handleNewLocation(location);
        }
    }
    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Connection has been suspended");

    }
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        } else {
            Log.i(TAG, "Location services connection failed with code " + connectionResult.getErrorCode());
        }

    }
    private void handleNewLocation(Location location) {
        Log.d(TAG, location.toString());
        double currentLatitude = location.getLatitude();
        double currentLongitude = location.getLongitude();

        LatLng latLng = new LatLng(currentLatitude, currentLongitude);


//        MarkerOptions options = new MarkerOptions()
//                .position(latLng)
//                .title("I am here!");
//        mMap.addMarker(options);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
    }
    @Override
    public void onLocationChanged(Location location) {
        handleNewLocation(location);
    }
}
