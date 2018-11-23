package com.example.timva.smartlighting.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.timva.smartlighting.Lamp;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    final static String TAG = "DatabaseHandler";

    public DatabaseHandler(Context context) {
        super(context, Util.DB_NAME, null, Util.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LAMP_TABLE = "CREATE TABLE " + Util.TABLE_LAMPS + "("
                + Util.KEY_ID + " INTEGER PRIMARY KEY, " + Util.KEY_BRIGHTNES + " INTEGER, "
                + Util.KEY_HUE + " INTEGER," + Util.KEY_SATURATION + " INTEGER" + " )";

        db.execSQL(CREATE_LAMP_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Delete (drop) the old version of the table
        db.execSQL("DROP TABLE IF EXISTS " + Util.DB_NAME);

        // Create the new version of the table
        onCreate(db);
    }

    /**
     *  CRUD operations
     */

    // Add a contact
    public void addLamp(Lamp lamp) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Util.KEY_ID, lamp.getId());
        contentValues.put(Util.KEY_BRIGHTNES, lamp.getBri());
        contentValues.put(Util.KEY_HUE, lamp.getHue());
        contentValues.put(Util.KEY_SATURATION, lamp.getSat());
        if(getLamp(lamp.getId()) == null)
        {
            // Add the row to the table:
            db.insert(Util.TABLE_LAMPS, null, contentValues);
        }
        else
        {
            String UPDATE_LAMP = "UPDATE " + Util.TABLE_LAMPS + " SET " + Util.KEY_BRIGHTNES + " = '" + lamp.getBri() +
                    "', " +Util.KEY_HUE +" = '" + lamp.getHue()+"', "+ Util.KEY_SATURATION + " = '" + lamp.getSat()+"' WHERE "+Util.KEY_ID +" = '" + lamp.getId() +"'";
            // Update the row wich already exist
            //db.update(Util.TABLE_LAMPS, contentValues, String.valueOf(lamp.getId()), null);
            db.execSQL(UPDATE_LAMP);
        }


    }

    public Lamp getLamp(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                Util.TABLE_LAMPS,
                new String[]{Util.KEY_ID, Util.KEY_BRIGHTNES, Util.KEY_HUE, Util.KEY_SATURATION},
                Util.KEY_ID + "=?",
                new String[] {String.valueOf(id)},
                null,null,null,null);

        if (cursor != null) {
            if(cursor.moveToFirst()) {
                Lamp lamp = new Lamp(cursor.getInt(0), true, cursor.getInt(1), cursor.getInt(2), cursor.getInt(3));
                return lamp;
            }
        }
        return null;
    }

    public List<Lamp> getAllLamps() {
        Log.d("GET ALL LAMPS", "return from db");

        SQLiteDatabase db = this.getReadableDatabase();
        List<Lamp> lamps = new ArrayList<>();

        // select all contacts:
        String selectAll = "SELECT * FROM " + Util.TABLE_LAMPS;
        Cursor cursor = db.rawQuery(selectAll, null);
//        cursor.moveToFirst();

        // TODO: 23-Oct-17 uitzoeken wat de handigste manier is om alle rijen te lezen.
        if (cursor.moveToFirst()) {
            do {
                Lamp lamp = new Lamp(cursor.getInt(0),true, cursor.getInt(1), cursor.getInt(2), cursor.getInt(3));
                lamps.add(lamp);
            } while (cursor.moveToNext());

        }
        return lamps;
    }
}
