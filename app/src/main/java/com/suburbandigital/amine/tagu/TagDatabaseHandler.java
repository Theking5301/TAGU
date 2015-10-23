package com.suburbandigital.amine.tagu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.suburbandigital.amine.tagu.Tags.Tag;
import com.suburbandigital.amine.tagu.Tags.TagType;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

/**
 * TagU Closed Source
 * Created by Amine on 10/20/2015.
 */
public class TagDatabaseHandler extends SQLiteOpenHelper {
    private static final String DATABASENAME = "TagDatabase";
    private static final int DATABASEVERSION = 1;

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_DESC = "desc";
    private static final String KEY_TYPE = "type";
    private static final String KEY_ENT = "ent";
    private static final String KEY_XPOS = "posx";
    private static final String KEY_YPOS = "posy";

    public TagDatabaseHandler(Context context) {
        super(context, DATABASENAME, null, DATABASEVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + DATABASENAME + " ( " + "id INTEGER, " + "name TEXT, " +
                "desc TEXT, " + "ent TEXT, " + "type TEXT, " + "posx INTEGER, " + "posy INTEGER )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public List<Tag> getAllTags() {
        List<Tag> tags = new LinkedList<Tag>();
        addTag(new Tag("Wang Center", "Asian food and culture", "SBU", TagType.BUILDING, 40.9159, -73.1197));
        String query = "SELECT  * FROM " + DATABASENAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        Tag tag = null;
        if (cursor.moveToFirst()) {
            do {
                tag = new Tag(cursor.getString(1), cursor.getString(2), cursor.getString(3), TagType.valueOf(cursor.getString(4)),
                        Double.parseDouble(cursor.getString(5)), Double.parseDouble(cursor.getString(6)));

                // Add book to books
                tags.add(tag);
            } while (cursor.moveToNext());
        }
        //Log.d("getAllBooks()", tag.toString());
        return tags;
    }

    public void addTag(Tag tag) {
        Log.d("addTag", tag.toString());
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, tag.getID());
        values.put(KEY_NAME, tag.getNAME());
        values.put(KEY_DESC, tag.getDESCRIPTION());
        values.put(KEY_TYPE, tag.getTYPE().toString());
        values.put(KEY_ENT, tag.getENTITY());
        values.put(KEY_XPOS, tag.getLat());
        values.put(KEY_YPOS, tag.getLong());

        database.insert(DATABASENAME, null, values);
        database.close();
    }
}