package com.example.pvlasic.petarvlasic;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by pvlasic on 1/26/18.
 */

public class DBAdapter {
    static final String KEY_LIKID = "_likid";
    static final String KEY_FILMID = "_filmid";
    static final String KEY_IME = "ime";
    static final String KEY_AUTOR = "autor";
    static final String TAG = "DBAdapter";

    static final String DATABASE_NAME = "MyDB";
    static final String DATABASE_TABLE1 = "likovi";
    static final String DATABASE_TABLE2 = "filmovi";
    static final int DATABASE_VERSION = 2;

    static final String DATABASE_CREATE1 =
            "create table likovi (_likid integer primary key autoincrement, "
                    + "ime text not null, autor text not null);";

    static final String DATABASE_CREATE2 =
            "create table filmovi (_filmid integer primary key autoincrement, "
                    + "_likid integer not null);";

    final Context context;

    DatabaseHelper DBHelper;
    SQLiteDatabase db;

    public DBAdapter(Context ctx)
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            try {
                db.execSQL(DATABASE_CREATE1);
                db.execSQL(DATABASE_CREATE2);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            Log.w(TAG, "Upgrading db from" + oldVersion + "to"
                    + newVersion );
            db.execSQL("DROP TABLE IF EXISTS contacts");
            onCreate(db);
        }
    }

    //---opens the database---
    public DBAdapter open() throws SQLException
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    //---closes the database---
    public void close()
    {
        DBHelper.close();
    }


    public long insertLik(String ime, String autor)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_IME, ime);
        initialValues.put(KEY_AUTOR,autor);
        return db.insert(DATABASE_TABLE1, null, initialValues);
    }

    //---deletes a particular contact---
    public boolean deleteLik(long Id)
    {
        return db.delete(DATABASE_TABLE1, KEY_LIKID + "=" + Id, null) > 0;
    }

    public long insertFilm(String idLik)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_LIKID, idLik);

        return db.insert(DATABASE_TABLE2, null, initialValues);
    }

    //---deletes a particular contact---

    public boolean deleteFilm(long Id)
    {
        return db.delete(DATABASE_TABLE2, KEY_FILMID + "=" + Id, null) > 0;
    }

    //---retrieves all the contacts---
    public Cursor GetAllCharacters()
    {
        return db.query(DATABASE_TABLE1, new String[] {KEY_LIKID, KEY_IME,
                KEY_AUTOR}, null, null, null, null, null);
    }

    public Cursor GetAllFilm()
    {
        return db.query(DATABASE_TABLE2,
                new String[] {KEY_FILMID, KEY_LIKID}, null, null, null, null, null);
    }
    //MOŽDANEĆE TREBAT
    //---retrieves a particular contact---


}
