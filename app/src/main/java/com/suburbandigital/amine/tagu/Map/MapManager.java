package com.suburbandigital.amine.tagu.Map;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
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
public class MapManager {

    private GoogleMap MAP;
    private TagDatabaseHandler HANDLER;
    private Marker SELECTEDMARKER;
    private HashMap<Marker, Tag> HASHMAP = new HashMap<Marker, Tag>();

    public MapManager(Context context, GoogleMap map) {
        MAP = map;
        HANDLER = new TagDatabaseHandler(context);
        onCreate();
    }
    public void onCreate() {
        HANDLER.addTag(new Tag("Wang Center", "Asian food and culture", "SBU", TagType.FOOD, 40.9159, -73.1197));
    }
    public void refreshMap() {
        List<Tag> tags = HANDLER.getAllTags();
        Tag[] tagArray = new Tag[tags.size()];
        tags.toArray(tagArray);
        for(int i=0; i<tagArray.length; i++) {
            HASHMAP.put(addMarkerToMap(tagArray[i]), tagArray[i]);
        }
        MAP.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(tagArray[0].getLat(), tagArray[0].getLong())));
    }
    public Marker addMarkerToMap(Tag tag) {
        LatLng tempPos = new LatLng(tag.getLat(), tag.getLong());
        MarkerOptions tempMarkerOptions = new MarkerOptions().position(tempPos).title(tag.getNAME()).snippet(tag.getDESCRIPTION());

        Marker marker = MAP.addMarker(tempMarkerOptions);
        marker.setIcon(tag.getIcon());
        marker.setDraggable(true);
        return marker;
    }
    public void addTagToDB(Tag tag) {
        HANDLER.addTag(tag);
    }
    public boolean removeMarker(Marker marker) {
        if (marker != null && HASHMAP.containsKey(marker)) {
            HANDLER.deleteTag(HASHMAP.get(marker));
            marker.remove();
            return true;
        }
        return false;
    }
    public Tag tagFromMarker(Marker marker) {
        if(HASHMAP.containsKey(marker)) {
            return HASHMAP.get(marker);
        }
        return null;
    }
    public void clearDB() {
        HANDLER.clearDB();
    }
    public void setSelectedMarker(Marker marker) {
        SELECTEDMARKER = marker;
    }
    public Marker getSelectedMarker() {
        return SELECTEDMARKER;
    }
}
