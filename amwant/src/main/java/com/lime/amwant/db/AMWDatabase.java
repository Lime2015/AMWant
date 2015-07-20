package com.lime.amwant.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.lime.amwant.listitem.AssemblymanListItem;
import com.lime.amwant.result.CheckTagResult;
import com.lime.amwant.vo.Assemblyman;

import java.util.ArrayList;
import java.util.List;


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
    public static int DATABASE_VERSION = 5;

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
        CheckTagResult result = new CheckTagResult();
        List<Integer> list = new ArrayList<Integer>();

        Cursor cursor = rawQuery("select max(update_tag) from assemblyman");
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            list.add(cursor.getInt(0));
        } else {
            list.add(0);
        }
        cursor = rawQuery("select max(update_tag) from bill");
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            list.add(cursor.getInt(0));
        } else {
            list.add(0);
        }
        cursor = rawQuery("select max(update_tag) from committee_meeting");
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            list.add(cursor.getInt(0));
        } else {
            list.add(0);
        }
        cursor = rawQuery("select max(update_tag) from general_meeting");
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            list.add(cursor.getInt(0));
        } else {
            list.add(0);
        }
        cursor = rawQuery("select max(update_tag) from party_history");
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            list.add(cursor.getInt(0));
        } else {
            list.add(0);
        }
        cursor = rawQuery("select max(update_tag) from vote");
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            list.add(cursor.getInt(0));
        } else {
            list.add(0);
        }

        result.setTagList(list);
        return result;
    }

    public boolean insertAssemblymenList(List<Assemblyman> assList) {

        try {
            for (Assemblyman ass : assList) {
                String sql = "insert into assemblyman values('" + ass.getAssemblymanId() +
                        "','" + ass.getAssemblymanName() + "'," + ass.getUpdateTag() + ",'" + ass.getImageUrl() +
                        "','" + ass.getOrgImageUrl() + "','" + ass.getModDttm() + "'," + ass.getPartyId() +
                        ",'" + ass.getPartyName() + "','" + ass.getLocalConstituency() + "');";
                execSQL(sql);
            }
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    public List<AssemblymanListItem> selectAssemblymenList(int type) {
        List<AssemblymanListItem> list = null;
        String sql="";

        switch (type){
            case 0:     // mod_dttm
                sql="select * from assemblyman order by mod_dttm";
                break;
            case 1:     // favorite
                break;
            case 2:     // naming
                sql="select * from assemblyman order by assemblyman_name";
                break;
        }

        Cursor cursor = rawQuery(sql);

        if(cursor.moveToFirst()){
            list = new ArrayList<>();
            while (cursor.moveToNext()){
                AssemblymanListItem item = new AssemblymanListItem();
                item.setAssemblymanName(cursor.getString(1));
                item.setCountDislike(0);
                item.setCountLike(0);
                item.setUrlPhoto(cursor.getString(3));
                item.setLocalConstituency(cursor.getString(8));
                item.setPartyName(cursor.getString(7));

                list.add(item);
            }
        }

        return list;
    }


    private class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String query;
            query = "CREATE TABLE member_info(" +
                    "member_id VARCHAR(45)," +
                    "logon_type_id int," +
                    "member_nickname VARCHAR(45)," +
                    "address VARCHAR(100)," +
                    "birth_date DATE, gender CHAR(1)," +
                    "CONSTRAINT tab_pk PRIMARY KEY (member_id, logon_type_id));";
            db.execSQL(query);
            query = "create table assemblyman(" +
                    "assemblyman_id VARCHAR(30) NOT NULL primary key," +
                    "assemblyman_name VARCHAR(30) NOT NULL," +
                    "update_tag INT(10) NOT NULL," +
                    "image_url VARCHAR(60)," +
                    "org_image_url VARCHAR(60)," +
                    "mod_dttm VARCHAR(60)," +
                    "party_id INT(10)," +
                    "party_name VARCHAR(60)," +
                    "local_constituency VARCHAR(60));";
            db.execSQL(query);
            query = "create table bill(" +
                    "assemblyman_id INT(10) NOT NULL," +
                    "update_tag INT(10) NOT NULL," +
                    "bill_seq INT(10)," +
                    "bill_no VARCHAR(60) NOT NULL primary key," +
                    "bill_status VARCHAR(60)," +
                    "bill_title VARCHAR(300)," +
                    "proposer_info VARCHAR(60)," +
                    "bill_class VARCHAR(60)," +
                    "receive_date VARCHAR(60)," +
                    "refer_date VARCHAR(60)," +
                    "bill_date3 VARCHAR(60)," +
                    "committee_name VARCHAR(60)," +
                    "committee_id INT(10)," +
                    "committee_class INT(10)," +
                    "bill_result VARCHAR(60)," +
                    "bill_target_url VARCHAR(300));";
            db.execSQL(query);
            query = "create table committee_meeting(" +
                    "assemblyman_id INT(10) NOT NULL, " +
                    "update_tag INT(10) NOT NULL," +
                    "meeting_id INT(10)," +
                    "meeting_name VARCHAR(100) NOT NULL," +
                    "meeting_order VARCHAR(60) NOT NULL," +
                    "meeting_date VARCHAR(60)," +
                    "attend_status VARCHAR(60)," +
                    "CONSTRAINT tab_pk PRIMARY KEY (assemblyman_id, meeting_name, meeting_order));";
            db.execSQL(query);
            query = "create table general_meeting(" +
                    "assemblyman_id INT(10) NOT NULL," +
                    "update_tag INT(10) NOT NULL, " +
                    "meeting_id INT(10) NOT NULL," +
                    "meeting_order VARCHAR(60)," +
                    "meeting_dttm VARCHAR(60)," +
                    "attend_status VARCHAR(60)," +
                    "CONSTRAINT tab_pk PRIMARY KEY (assemblyman_id, meeting_id));";
            db.execSQL(query);
            query = "create table party_history(" +
                    "update_tag INT(10) NOT NULL," +
                    "member_seq INT(10) NOT NULL," +
                    "party_name VARCHAR(60) NOT NULL," +
                    "in_date VARCHAR(60) NOT NULL," +
                    "out_date VARCHAR(60)," +
                    "note VARCHAR(60)," +
                    "CONSTRAINT tab_pk PRIMARY KEY (party_name, member_seq, in_date));";
            db.execSQL(query);
            query = "create table vote(" +
                    "assemblyman_id INT(10) NOT NULL, " +
                    "update_tag INT(10) NOT NULL," +
                    "vote_id VARCHAR(60)," +
                    "bill_name VARCHAR(300)," +
                    "bill_no INT(10) NOT NULL," +
                    "vote_dttm VARCHAR(60)," +
                    "bill_target_url VARCHAR(300)," +
                    "result VARCHAR(60)," +
                    "assemblyman_vote VARCHAR(60)," +
                    "CONSTRAINT tab_pk PRIMARY KEY (assemblyman_id, bill_no));";
            db.execSQL(query);
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
            query = "DROP TABLE IF EXISTS member_info;";
            db.execSQL(query);
            query = "DROP TABLE IF EXISTS assemblyman;";
            db.execSQL(query);
            query = "DROP TABLE IF EXISTS committee_meeting;";
            db.execSQL(query);
            query = "DROP TABLE IF EXISTS general_meeting;";
            db.execSQL(query);
            query = "DROP TABLE IF EXISTS party_history;";
            db.execSQL(query);
            query = "DROP TABLE IF EXISTS vote;";
            db.execSQL(query);
            onCreate(db);
            Log.d(TAG, "onUpgraded database [" + DATABASE_NAME + "].");
        }
    }
}
