package com.bassem.prayertimes.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.bassem.prayertimes.model.DayPrayers;
import com.bassem.prayertimes.model.Prayer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bassem on 6/27/2015.
 */
public class DatabaseImplementation {

	static public ArrayList<DayPrayers> query(Context context, int month, int year, double lat, double lng, int gmt) {
		// Run query
		Uri uri = prayerTimesContentProvider.CONTENT_URI;
		String[] projection = new String[] { PrayersTable.PRAYERS_DAY, PrayersTable.PRAYERS_MONTH,
				PrayersTable.PRAYERS_YEAR, PrayersTable.PRAYERS_LAT, PrayersTable.PRAYERS_LNG,
				PrayersTable.PRAYERS_GMT, PrayersTable.PRAYERS_FAJR_TIME, PrayersTable.PRAYERS_SUNRISE_TIME,
				PrayersTable.PRAYERS_DHUHR_TIME, PrayersTable.PRAYERS_ASR_TIME, PrayersTable.PRAYERS_MAGHRIB_TIME,
				PrayersTable.PRAYERS_ISHA_TIME,

		};

		String selection = PrayersTable.PRAYERS_MONTH + " = ? AND " + PrayersTable.PRAYERS_YEAR + " = ? AND "
				+ PrayersTable.PRAYERS_LAT + " = ? AND " + PrayersTable.PRAYERS_LNG + " = ? AND "
				+ PrayersTable.PRAYERS_GMT + " = ?";

		String[] selectionArgs = new String[] { "" + month, "" + year, "" + lat, "" + lng, "" + gmt };

		Cursor cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);

		ArrayList<DayPrayers> dayPrayers = new ArrayList<DayPrayers>();

		if (cursor != null) {
			while (cursor.moveToNext()) {
				ArrayList<Prayer> prayers = new ArrayList<Prayer>();
				for (int i = 0; i < 6; i++) {
					Prayer prayer = new Prayer();
					prayer.index = i;
					switch (i) {
					case 0: {
						prayer.prayerTime = cursor.getString(cursor.getColumnIndex(PrayersTable.PRAYERS_FAJR_TIME));
						break;
					}

					case 1: {
						prayer.prayerTime = cursor.getString(cursor.getColumnIndex(PrayersTable.PRAYERS_SUNRISE_TIME));
						break;
					}

					case 2: {
						prayer.prayerTime = cursor.getString(cursor.getColumnIndex(PrayersTable.PRAYERS_DHUHR_TIME));
						break;
					}

					case 3: {
						prayer.prayerTime = cursor.getString(cursor.getColumnIndex(PrayersTable.PRAYERS_ASR_TIME));
						break;
					}

					case 4: {
						prayer.prayerTime = cursor.getString(cursor.getColumnIndex(PrayersTable.PRAYERS_MAGHRIB_TIME));
						break;
					}

					case 5: {
						prayer.prayerTime = cursor.getString(cursor.getColumnIndex(PrayersTable.PRAYERS_ISHA_TIME));
						break;
					}
					}
					prayers.add(prayer);
				}
				DayPrayers dayPrayer = new DayPrayers();
				dayPrayer.day = cursor.getInt(cursor.getColumnIndex(PrayersTable.PRAYERS_DAY));
				dayPrayer.month = cursor.getInt(cursor.getColumnIndex(PrayersTable.PRAYERS_MONTH));
				dayPrayer.year = cursor.getInt(cursor.getColumnIndex(PrayersTable.PRAYERS_YEAR));
				//dayPrayer.lat = Double.parseDouble(cursor.getString(cursor.getColumnIndex(PrayersTable.PRAYERS_LAT)));
				//dayPrayer.lng = Double.parseDouble(cursor.getString(cursor.getColumnIndex(PrayersTable.PRAYERS_LNG)));
				dayPrayer.lat = cursor.getDouble(cursor.getColumnIndex(PrayersTable.PRAYERS_LAT));
				dayPrayer.lng = cursor.getDouble(cursor.getColumnIndex(PrayersTable.PRAYERS_LNG));
				dayPrayer.gmt = cursor.getInt(cursor.getColumnIndex(PrayersTable.PRAYERS_GMT));
				dayPrayer.prayers = prayers;

				dayPrayers.add(dayPrayer);
			}

		}
		return dayPrayers;
	}

	public static void save(Context context, List<DayPrayers> dayPrayers) {
		Uri uri = prayerTimesContentProvider.CONTENT_URI;

		if (dayPrayers != null) {

			for (int i = 0; i < dayPrayers.size(); i++) {
				ContentValues values = new ContentValues();
				values.put(PrayersTable.PRAYERS_DAY, dayPrayers.get(i).day);
				values.put(PrayersTable.PRAYERS_MONTH, dayPrayers.get(i).month);
				values.put(PrayersTable.PRAYERS_YEAR, dayPrayers.get(i).year);
				//values.put(PrayersTable.PRAYERS_LAT, "" + dayPrayers.get(i).lat);
				//values.put(PrayersTable.PRAYERS_LNG, "" + dayPrayers.get(i).lng);
				values.put(PrayersTable.PRAYERS_LAT, dayPrayers.get(i).lat);
				values.put(PrayersTable.PRAYERS_LNG, dayPrayers.get(i).lng);
				values.put(PrayersTable.PRAYERS_GMT, dayPrayers.get(i).gmt);
				values.put(PrayersTable.PRAYERS_FAJR_TIME, dayPrayers.get(i).prayers.get(0).prayerTime);
				values.put(PrayersTable.PRAYERS_SUNRISE_TIME, dayPrayers.get(i).prayers.get(1).prayerTime);
				values.put(PrayersTable.PRAYERS_DHUHR_TIME, dayPrayers.get(i).prayers.get(2).prayerTime);
				values.put(PrayersTable.PRAYERS_ASR_TIME, dayPrayers.get(i).prayers.get(3).prayerTime);
				values.put(PrayersTable.PRAYERS_MAGHRIB_TIME, dayPrayers.get(i).prayers.get(4).prayerTime);
				values.put(PrayersTable.PRAYERS_ISHA_TIME, dayPrayers.get(i).prayers.get(5).prayerTime);

				context.getContentResolver().insert(uri, values);
			}
		}
	}
}
