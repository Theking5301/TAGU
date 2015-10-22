package com.suburbandigital.amine.tagu.Tags;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import com.suburbandigital.amine.tagu.R;

/**
 * TagU Closed Source
 * Created by Amine on 10/21/2015.
 */
public class TagInfoView extends View {
    public TagInfoView(Context context) {
        super(context);
    }

    public TagInfoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.TagInfo,
                0, 0);

        try {

        } finally {
            a.recycle();
        }
    }
}
