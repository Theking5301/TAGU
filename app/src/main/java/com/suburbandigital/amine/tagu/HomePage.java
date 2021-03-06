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
import com.suburbandigital.amine.tagu.Map.MapManager;
import com.suburbandigital.amine.tagu.Tags.Tag;
import com.suburbandigital.amine.tagu.Tags.TagMarkerInfo;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.suburbandigital.amine.tagu.Tags.TagType;


public class HomePage extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private GoogleMap MAP;
    private MapManager MANAGER;
    private GoogleApiClient API_CLIENT;
    public static final String TAG = HomePage.class.getSimpleName();
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private LocationRequest LOCATION_REQUEST;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        LOCATION_REQUEST = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 100)        // 1 seconds, in milliseconds
                .setFastestInterval(1000); // 1 second, in milliseconds

        API_CLIENT = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        Button button = (Button)findViewById(R.id.AddTag);
        button.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Location location = LocationServices.FusedLocationApi.getLastLocation(API_CLIENT);
                        Tag tag;
                        TextView text = (TextView)findViewById(R.id.editText);
                        if(location != null) {
                            tag = new Tag(text.getText().toString(), "Custom Tag", "SBU", TagType.BUILDING, location.getLatitude(), location.getLongitude());
                        }else{
                            tag = new Tag(text.getText().toString(), "Custom Tag", "SBU", TagType.BUILDING, 0, 0);
                        }
                        MANAGER.addTagToDB(tag);
                        MAP.clear();
                        MANAGER.refreshMap();
                    }
                }
        );
        Button button2 = (Button)findViewById(R.id.DeleteTag);
        button2.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        MANAGER.removeMarker(MANAGER.getSelectedMarker());
                    }
                }
        );
        Button button3 = (Button)findViewById(R.id.ClearMap);
        button3.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        MANAGER.clearDB();
                        MAP.clear();
                    }
                }
        );
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        MAP = googleMap;
        MANAGER = new MapManager(getApplicationContext(), MAP);
        final View bottomFrame = findViewById(R.id.BottomFrame);
        bottomFrame.setVisibility(View.GONE);
        bottomFrame.setAlpha(0f);
        //bottomFrame.setTranslationY(300);
        MAP.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        MAP.setOnMarkerClickListener(
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
                        MANAGER.setSelectedMarker(marker);
                        return false;
                    }
                });
        MAP.setOnMapClickListener(
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
                        MANAGER.setSelectedMarker(null);
                    }
                }
        );
        MAP.setInfoWindowAdapter(new TagMarkerInfo(getApplicationContext()));
        MANAGER.refreshMap();
        MAP.setMyLocationEnabled(true);
        MAP.getUiSettings().setMyLocationButtonEnabled(false);
        MAP.getUiSettings().setMapToolbarEnabled(false);
    }
    @Override
    protected void onResume() {
        super.onResume();
        API_CLIENT.connect();
    }
    @Override
    protected void onPause() {
        super.onPause();
        if (API_CLIENT.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(API_CLIENT, this);
            API_CLIENT.disconnect();
        }
    }
    @Override
    public void onConnected(Bundle bundle) {
        Location location = LocationServices.FusedLocationApi.getLastLocation(API_CLIENT);
        Log.i(TAG, "Connection has been established");
        if (location == null) {
            LocationServices.FusedLocationApi.requestLocationUpdates(API_CLIENT, LOCATION_REQUEST, this);
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
//        MAP.addMarker(options);

        MAP.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
    }
    @Override
    public void onLocationChanged(Location location) {
        handleNewLocation(location);
    }
}
