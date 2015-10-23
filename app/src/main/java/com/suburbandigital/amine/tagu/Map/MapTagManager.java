package com.suburbandigital.amine.tagu.Map;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.suburbandigital.amine.tagu.TagDatabaseHandler;
import com.suburbandigital.amine.tagu.Tags.Tag;
import com.suburbandigital.amine.tagu.Tags.TagType;

import java.util.HashMap;
import java.util.List;

/**
 * TagU Closed Source
 * Created by Amine on 10/22/2015.
 */
public class MapTagManager {

    private GoogleMap MAP;
    private TagDatabaseHandler HANDLER;
    private HashMap<Marker, Tag> HASHMAP = new HashMap<Marker, Tag>();

    public MapTagManager(Context context, GoogleMap map) {
        MAP = map;
        HANDLER = new TagDatabaseHandler(context);
    }
    public void addMarkersToMap() {
        List<Tag> tags = HANDLER.getAllTags();
        Tag[] tagArray = new Tag[tags.size()];
        tags.toArray(tagArray);
        for(int i=0; i<tagArray.length; i++) {
            HASHMAP.put(addMarkerFromTag(tagArray[i]), tagArray[i]);
        }
        //Marker wangMarker = MAP.addMarker(wangTag.toMarkerOptions());
        //MAP.moveCamera(CameraUpdateFactory.newLatLngZoom(wangMarker.getPosition(), 16));
        //MAP.setMyLocationEnabled(true);
    }
    public Marker addMarkerFromTag(Tag tag) {
        LatLng tempPos = new LatLng(tag.getLat(), tag.getLong());
        MarkerOptions tempMarkerOptions = new MarkerOptions().position(tempPos).title(tag.getNAME()).snippet(tag.getDESCRIPTION());
        return MAP.addMarker(tempMarkerOptions);
    }
    public void addTagToDB(Tag tag) {
        HANDLER.addTag(tag);
    }
    public boolean removeMarkerFromDB(Marker marker) {
        if(HASHMAP.containsKey(marker)) {
            HANDLER.deleteTag(HASHMAP.get(marker));
            Log.d("DeleteTag", String.valueOf(marker.getPosition().latitude));
            return true;
        }
        Log.d("DeleteTag", "ERROR");
        return false;
    }
    public Tag tagFromMarker(Marker marker) {
        if(HASHMAP.containsKey(marker)) {
            return HASHMAP.get(marker);
        }
        return null;
    }
}
