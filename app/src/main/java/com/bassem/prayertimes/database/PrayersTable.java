package com.bassem.prayertimes.database;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Bassem on 6/27/2015.
 */
public class PrayersTable {
    public static final String PRAYERS_TABLE = "prayers";
    public static final String PRAYERS_ID = "id";
    public static final String PRAYERS_DAY = "day";
    public static final String PRAYERS_MONTH = "month";
    public static final String PRAYERS_YEAR = "year";
    public static final String PRAYERS_LAT = "lat";
    public static final String PRAYERS_LNG = "lng";
    public static final String PRAYERS_GMT = "gmt";
    public static final String PRAYERS_FAJR_TIME = "fajr";
    public static final String PRAYERS_SUNRISE_TIME = "sunrise";
    public static final String PRAYERS_DHUHR_TIME = "dhuhr";
    public static final String PRAYERS_ASR_TIME = "asr";
    public static final String PRAYERS_MAGHRIB_TIME = "maghrib";
    public static final String PRAYERS_ISHA_TIME = "isha";

    private static final String DATABASE_CREATE = "create table "
            + PRAYERS_TABLE
            + "("
            + PRAYERS_DAY + " integer,"
            + PRAYERS_MONTH + " integer,"
            + PRAYERS_YEAR + " integer,"
            + PRAYERS_LAT + " text,"
            + PRAYERS_LNG + " text,"
            + PRAYERS_GMT + " integer,"
            + PRAYERS_FAJR_TIME + " text,"
            + PRAYERS_SUNRISE_TIME + " text,"
            + PRAYERS_DHUHR_TIME + " text,"
            + PRAYERS_ASR_TIME + " text,"
            + PRAYERS_MAGHRIB_TIME + " text,"
            + PRAYERS_ISHA_TIME + " text,"
            + "UNIQUE (" + PRAYERS_DAY + ", " 
            + PRAYERS_MONTH + ", " 
            + PRAYERS_YEAR + ", " 
            + PRAYERS_LAT + ", " 
            + PRAYERS_LNG + ", " 
            + PRAYERS_GMT + ")"

            + ");";


    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

}
