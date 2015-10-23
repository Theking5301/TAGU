package com.suburbandigital.amine.tagu;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.suburbandigital.amine.tagu.Tags.Tag;
import com.suburbandigital.amine.tagu.Map.MapTagManager;
import com.suburbandigital.amine.tagu.Tags.TagMarkerInfo;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;

public class MainMap extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private MapTagManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_map);

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
                            bottomFrame.animate()
                                    .alpha(1f)
                                    .setDuration(getResources().getInteger(
                                            android.R.integer.config_shortAnimTime))
                                    .setListener(null);
                        TextView name = (TextView) findViewById(R.id.TagName);
                        name.setText(marker.getTitle());
                        TextView desc = (TextView) findViewById(R.id.Desc);
                        desc.setText(marker.getSnippet());
                        TextView pos = (TextView) findViewById(R.id.Pos);
                        pos.setText(marker.getPosition().toString());
                        return false;
                    }
                });
        mMap.setOnMapClickListener(
                new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        bottomFrame.animate()
                                .alpha(0f)
                                .setDuration(1)
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

        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        mMap.getUiSettings().setMapToolbarEnabled(false);


    }
    public Marker addMarkerFromTag(Tag tag) {
        LatLng tempPos = new LatLng(tag.getLat(), tag.getLong());
        MarkerOptions tempMarkerOptions = new MarkerOptions().title(tag.getNAME()).snippet(tag.getDESCRIPTION());
        return mMap.addMarker(tempMarkerOptions);
    }
}
