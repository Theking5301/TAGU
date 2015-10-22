package com.suburbandigital.amine.tagu;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.inputmethod.InputMethodSession;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.suburbandigital.amine.tagu.Tags.Tag;
import com.suburbandigital.amine.tagu.Tags.TagMarkerInfo;
import com.suburbandigital.amine.tagu.Tags.TagType;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;

import java.util.EventListener;

public class MainMap extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
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
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        mMap.setOnMarkerClickListener(
                new OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        TextView t = (TextView) findViewById(R.id.TagName);
                        t.setText(marker.getTitle());
                        return false;
                    }
                });
        mMap.setInfoWindowAdapter(new TagMarkerInfo(getApplicationContext()));

        Tag wangTag = new Tag("Wang Center", "Asian food and culture", "SBU", TagType.BUILDING, 40.9159, -73.1197);

        Marker wangMarker = mMap.addMarker(wangTag.toMarkerOptions());

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(wangMarker.getPosition(), 16));
        mMap.setMyLocationEnabled(true);
    }
}
