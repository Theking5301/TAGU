package com.suburbandigital.amine.tagu;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.suburbandigital.amine.tagu.Tags.Tag;
import com.suburbandigital.amine.tagu.Tags.TagType;

public class MainMap extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_map);
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
        LatLng stonybrook = new LatLng(40.9142, -73.1162);
        MarkerOptions marker = new MarkerOptions().position(stonybrook).title("Marker in Stony Brook");
        marker.isDraggable();
        mMap.addMarker(marker);

        Tag wangTag = new Tag("Wang Center", "Asian things", "SBU", TagType.BUILDING, 40.9159, -73.1197);
        addTag(wangTag);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(stonybrook));
    }
    public void addTag(Tag tag) {
        LatLng tagLL = new LatLng(tag.getLat(), tag.getLong());
        mMap.addMarker(new MarkerOptions().position(tagLL).title(tag.getNAME()));
    }
}
