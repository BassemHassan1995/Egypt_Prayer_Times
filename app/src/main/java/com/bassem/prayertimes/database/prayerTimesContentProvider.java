package com.bassem.prayertimes.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

/**
 * Created by Bassem on 6/27/2015.
 */
public class prayerTimesContentProvider extends ContentProvider {

	private DatabaseHelper database;

	private static final String AUTHORITY = "com.bassem.prayertimes.contentprovider";
	private static final String BASE_PATH = "prayerTimes";

	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);

	@Override
	public boolean onCreate() {
		database = new DatabaseHelper(getContext());
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		// Using SQLiteQueryBuilder instead of query() method
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

		// Set the table
		queryBuilder.setTables(PrayersTable.PRAYERS_TABLE);

		SQLiteDatabase db = database.getWritableDatabase();
		Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);

		// make sure that potential listeners are getting notified
		cursor.setNotificationUri(getContext().getContentResolver(), uri);

		return cursor;

	}

	@Override
	public String getType(Uri uri) {
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		SQLiteDatabase sqlDB = database.getWritableDatabase();
		long id = sqlDB.insertWithOnConflict(PrayersTable.PRAYERS_TABLE, null, values, SQLiteDatabase.CONFLICT_IGNORE);
		getContext().getContentResolver().notifyChange(uri, null);

		return Uri.parse(BASE_PATH + "/" + id);
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		SQLiteDatabase sqlDB = database.getWritableDatabase();
		int rowsDeleted = 0;
		rowsDeleted = sqlDB.delete(PrayersTable.PRAYERS_TABLE, selection, selectionArgs);
		getContext().getContentResolver().notifyChange(uri, null);
		return rowsDeleted;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		SQLiteDatabase sqlDB = database.getWritableDatabase();
		int rowsUpdated = 0;
		rowsUpdated = sqlDB.update(PrayersTable.PRAYERS_TABLE, values, selection, selectionArgs);
		getContext().getContentResolver().notifyChange(uri, null);
		return rowsUpdated;
	}

}
