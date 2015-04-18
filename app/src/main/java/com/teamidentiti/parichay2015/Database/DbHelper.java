package com.teamidentiti.parichay2015.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Sravan on 3/29/2015.
 */
public class DbHelper extends SQLiteOpenHelper {
    private static String DATABASE_NAME = "Parichay2015.db";
    private static int DATABASE_VERSION = 1;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String MESSAGES_CREATE_STATEMENT = "CREATE TABLE "+ TableContract.MessagesContract.TABLE_NAME+" ("
                + TableContract.MessagesContract._ID+" INTEGER PRIMARY KEY, "
                + TableContract.MessagesContract.COLUMN_DATE+" TEXT NOT NULL, "
                + TableContract.MessagesContract.COLUMN_TIME+" TEXT NOT NULL, "
                + TableContract.MessagesContract.COLUMN_MESSAGE+" TEXT NOT NULL)";
        db.execSQL(MESSAGES_CREATE_STATEMENT);

        final String RESULTS_CREATE_STATEMENT = "CREATE TABLE "+ TableContract.ResultsContract.TABLE_NAME+" ("
                + TableContract.ResultsContract._ID+" INTEGER PRIMARY KEY, "
                + TableContract.ResultsContract.COLUMN_EVENT+" TEXT NOT NULL, "
                + TableContract.ResultsContract.COLUMN_WINNER+" TEXT NOT NULL)";
        db.execSQL(RESULTS_CREATE_STATEMENT);

        final String POINTS_CREATE_STATEMENT = "CREATE TABLE "+ TableContract.PointsContract.TABLE_NAME+" ("
                + TableContract.PointsContract._ID+" INTEGER PRIMARY KEY, "
                + TableContract.PointsContract.COLUMN_EVENT+" TEXT NOT NULL, "
                + TableContract.PointsContract.COLUMN_POINTS+" TEXT NOT NULL)";
        db.execSQL(POINTS_CREATE_STATEMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}
