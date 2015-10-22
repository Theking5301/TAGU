package com.suburbandigital.amine.tagu.Tags;

import android.content.Context;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.suburbandigital.amine.tagu.R;

/**
 * TagU Closed Source
 * Created by Amine on 10/21/2015.
 */
public class TagMarkerInfo implements GoogleMap.InfoWindowAdapter {

    Context CONTEXT;
    public TagMarkerInfo(Context context) {
        CONTEXT = context;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return new TagInfoView(CONTEXT);
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}
