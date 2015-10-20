package com.suburbandigital.amine.tagu;

import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * TagU Closed Source
 * Created by Amine on 10/20/2015.
 */
public class TagList extends SQLiteOpenHelper {
    private static final String DATABASENAME = "TagDatabase";
    private static final int DATABASEVERSION = 1;
    public SQLiteDatabase WRITABLEDB;
    public SQLiteDatabase READABLEDB;

    public TagList(Context context) {
        super(context, DATABASENAME, null, DATABASEVERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASENAME);
        WRITABLEDB = getWritableDatabase();
        READABLEDB = getReadableDatabase();
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
