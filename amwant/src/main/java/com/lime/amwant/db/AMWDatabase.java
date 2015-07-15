package com.lime.amwant.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.lime.amwant.result.CheckTagResult;


/**
 * Created by Administrator on 2015-06-11.
 */
public class AMWDatabase {
    /**
     * TAG for debugging
     */
    private final String TAG = "WatchAssemblyDatabase";

    /**
     * Singleton instance
     */
    private static AMWDatabase database;

    /**
     * database name
     */
    public static String DATABASE_NAME = "wa.db";

    /**
     * version
     */
    public static int DATABASE_VERSION = 1;

    /**
     * Helper class defined
     */
    private DatabaseHelper dbHelper;

    /**
     * Database object
     */
    private SQLiteDatabase db;

    private Context context;

    /**
     * Singleton Constructor
     *
     * @param context
     */
    private AMWDatabase(Context context) {
        this.context = context;
    }

    public static AMWDatabase getInstance(Context context) {
        if (database == null) {
            database = new AMWDatabase(context);
        }

        return database;
    }

    /**
     * open database
     *
     * @return
     */
    public boolean open() {
        Log.d(TAG, "opening database [" + DATABASE_NAME + "].");

        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();

        return true;
    }

    /**
     * close database
     */
    public void close() {
        Log.d(TAG, "closing database [" + DATABASE_NAME + "].");

        db.close();
        database = null;
    }

    /**
     * execute raw query using the input SQL
     * close the cursor after fetching any result;
     *
     * @param SQL
     * @return
     */
    public Cursor rawQuery(String SQL) {
        Log.d(TAG, "nexecuteQuery called.");

        Cursor c1 = null;
        try {
            c1 = db.rawQuery(SQL, null);
            Log.d(TAG, "cursor count : " + c1.getCount());
        } catch (Exception ex) {
            Log.d(TAG, " : Exception in rawQuery" + ex);
        }

        return c1;
    }

    /**
     * @param SQL
     * @return
     */
    public boolean execSQL(String SQL) {
        Log.d(TAG, "nexecuteQuery called.");

        try {
            Log.d(TAG, " : SQL : " + SQL);
            db.execSQL(SQL);
        } catch (Exception ex) {
            Log.d(TAG, " : Exception in execSQL " + ex);
            return false;
        }

        return true;
    }

    public CheckTagResult checkTag() {
        CheckTagResult result = new CheckTagResult(0,0,0,0,0,0);

        return result;
    }


    private class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String query;
            query = "CREATE TABLE member_info ( member_id VARCHAR(45) PRIMARY KEY, logon_type_id INTEGER PRIMARY KEY, " +
                    "member_nickname VARCHAR(45), address VARCHAR(100), birth_date DATE, gender CHAR(1));";
            query += "CREATE TABLE assemblyman ( assemblyman_id varchar(30) PRIMARY KEY, assemblyman_name varchar(30) Not null, " +
                    "image_url varchar(60), org_image_url varchar(60), mod_dttm varchar(60), party_id int, " +
                    "party_name varchar(60), local_constituency varchar(60), update_tag int);";
            database.execSQL(query);
            Log.d(TAG, "onCreated database [" + DATABASE_NAME + "].");
        }

        @Override
        public void onOpen(SQLiteDatabase db) {
            super.onOpen(db);
            Log.d(TAG, "opened database [" + DATABASE_NAME + "].");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            String query;
            query = "DROP TABLE IF EXISTS member_info";
            db.execSQL(query);
            onCreate(db);
            Log.d(TAG, "onUpgraded database [" + DATABASE_NAME + "].");
        }
    }
}
