package com.biryanistudio.Parichay2015.Database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * Created by Sravan on 3/29/2015.
 */
public class Provider extends ContentProvider {
    private static final String AUTHORITIES = "com.biryanistudio.Parichay2015";
    private static final Uri BASE_CONTENT_URI = Uri.parse("content://"+AUTHORITIES);
    public static final Uri MESSAGES_CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath("Messages").build();
    public static final Uri RESULTS_CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath("Results").build();
    public static final Uri POINTS_CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath("Points").build();
    private static UriMatcher uriMatcher;

    private DbHelper dbHelper;

    @Override
    public boolean onCreate() {
        dbHelper = new DbHelper(getContext());

        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITIES, "Messages", 1);
        uriMatcher.addURI(AUTHORITIES, "Results", 2);
        uriMatcher.addURI(AUTHORITIES, "Points", 3);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor c;
        if(uriMatcher.match(uri)==1)
            c = dbHelper.getReadableDatabase().query(TableContract.MessagesContract.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
        else if(uriMatcher.match(uri)==2)
            c = dbHelper.getReadableDatabase().query(TableContract.ResultsContract.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
        else
            c = dbHelper.getReadableDatabase().query(TableContract.PointsContract.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db;
        db = dbHelper.getWritableDatabase();
        long id = 0;
        if(uriMatcher.match(uri)==1)
            id = db.insert(TableContract.MessagesContract.TABLE_NAME, null, values);
        else if(uriMatcher.match(uri)==2)
            db.insert(TableContract.ResultsContract.TABLE_NAME, null, values);
        else
            db.insert(TableContract.PointsContract.TABLE_NAME, null, values);
        getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db;
        db = dbHelper.getWritableDatabase();
        int id = 0;
        if(uriMatcher.match(uri)==1)
            id = db.update(TableContract.MessagesContract.TABLE_NAME, values, selection, selectionArgs);
        else if(uriMatcher.match(uri)==2)
            id = db.update(TableContract.ResultsContract.TABLE_NAME, values, selection, selectionArgs);
        else
            id = db.update(TableContract.PointsContract.TABLE_NAME, values, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return id;
    }
}