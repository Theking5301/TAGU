package com.suburbandigital.amine.tagu;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.suburbandigital.amine.tagu.Tags.Tag;

/**
 * TagU Closed Source
 * Created by Amine on 10/20/2015.
 */
public class TagListHandler extends SQLiteOpenHelper {
    private static final String DATABASENAME = "TagDatabase";
    private static final int DATABASEVERSION = 1;
    public SQLiteDatabase DATABASE;

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_DESC = "desc";
    private static final String KEY_TYPE = "type";
    private static final String KEY_ENT = "ent";
    private static final String KEY_XPOS = "xpos";
    private static final String KEY_YPOS = "ypos";

    public TagListHandler(Context context) {
        super(context, DATABASENAME, null, DATABASEVERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + DATABASENAME + " ( " + "id INTEGER, " + "name TEXT, " +
                "desc TEXT, " + "ent TEXT, " + "type TEXT, " + "posx INTEGER, " + "posy INTEGER )");
        DATABASE = getWritableDatabase();
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void addTag(Tag tag){
        Log.d("addTag", tag.toString());

        ContentValues values = new ContentValues();
        values.put(KEY_ID, tag.getID()); // get title
        values.put(KEY_NAME, tag.getNAME()); // get author
        values.put(KEY_DESC, tag.getDESCRIPTION()); // get title
        values.put(KEY_TYPE, tag.getTYPE().toString()); // get author
        values.put(KEY_ENT, tag.getENTITY()); // get title
        values.put(KEY_XPOS, tag.getX()); // get author
        values.put(KEY_YPOS, tag.getY()); // get title

        DATABASE.insert(DATABASENAME, null, values);
    }
}
