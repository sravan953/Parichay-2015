package com.biryanistudios.Parichay2015.GCM;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.biryanistudios.Parichay2015.Activities.MainActivity;
import com.biryanistudios.Parichay2015.Database.Provider;
import com.biryanistudios.Parichay2015.Database.TableContract;
import com.biryanistudios.Parichay2015.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Sravan on 3/29/2015.
 */
public class GcmIntentService extends IntentService {
    public GcmIntentService() {
        super("Parichay 2015");
    }

    public GcmIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        String messageType = gcm.getMessageType(intent);
        String message = intent.getExtras().get("m").toString();
        if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
            Log.i("DATA", "RAW - " + message);
            String type = message.contains("UPDATE")?"UPDATE":message.contains("RESULTS")?"RESULTS":message.contains("POINTS")?"POINTS":"";
            message = message.replace("["+type+"]", "");
            addToDb(message, type);
        }
    }

    private void addToDb(String message, String type) {
        /*
        Message format:
        [UPDATE]Message
        OR
        [RESULTS]Event~Winner
        OR
        [POINTS]Branch~Points
         */
        if(type.equals("UPDATE")&&message.length()!=0)
            doUpdateWork(message, type);
        else if(type.equals("RESULTS")&&message.length()!=1)
            doResultsWork(message, type);
        else if(type.equals("POINTS")&&message.length()!=1)
            doPointsWork(message, type);
    }

    private void doUpdateWork(String message, String type) {
        SimpleDateFormat df = new SimpleDateFormat("dd-MM");
        Date d = new Date();
        String date = df.format(d);
        df = new SimpleDateFormat("HH:mm");
        String time = df.format(d);

        ContentValues values = new ContentValues();
        values.put(TableContract.MessagesContract.COLUMN_MESSAGE, message);
        values.put(TableContract.MessagesContract.COLUMN_DATE, date);
        values.put(TableContract.MessagesContract.COLUMN_TIME, time);
        getContentResolver().insert(Provider.MESSAGES_CONTENT_URI, values);

        makeNotif(message, type, "");
    }

    private void doResultsWork(String message, String type) {
        String event = message.split("~")[0];
        String winner = message.split("~")[1];
        ContentValues values = new ContentValues();
        values.put(TableContract.ResultsContract.COLUMN_EVENT, event);
        values.put(TableContract.ResultsContract.COLUMN_WINNER, winner);
        getContentResolver().insert(Provider.RESULTS_CONTENT_URI, values);

        makeNotif(message, type, event);
    }

    private void doPointsWork(String message, String type) {
        String branch = message.split("~")[0];
        String points = message.split("~")[1];
        ContentValues values = new ContentValues();
        values.put(TableContract.PointsContract.COLUMN_BRANCH, branch);
        values.put(TableContract.PointsContract.COLUMN_POINTS, points);
        String WHERE = "Branch=?";
        Cursor c = getContentResolver().query(Provider.POINTS_CONTENT_URI,
                new String[]{TableContract.PointsContract.COLUMN_BRANCH},
                WHERE, new String[]{branch}, null);
        if (c.getCount() != 0)
            getContentResolver().update(Provider.POINTS_CONTENT_URI, values, WHERE, new String[]{branch});
        else
            getContentResolver().insert(Provider.POINTS_CONTENT_URI, values);

        makeNotif(message, type, branch);
    }

    private void makeNotif(String message, String type, String s1) {
        String contentText = "";
        if(type.equals("UPDATE"))
            contentText = message;
        else if(type.equals("RESULTS"))
            contentText = "Results for " + s1 + " have been announced! Click here to see who the winner is!";
        else if(type.equals("POINTS"))
            contentText = s1 + " just scored some points! Click here to see who's leading the scoreboard!";

        showNotif(contentText);
    }

    private void showNotif(String contentText) {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setSmallIcon(R.drawable.ic_launcher);
        builder.setContentTitle("Parichay 2015");
        builder.setContentText(contentText);
        builder.setStyle(new Notification.BigTextStyle().bigText(contentText));
        builder.setDefaults(Notification.DEFAULT_VIBRATE);
        builder.setAutoCancel(true);

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pIntent);

        NotificationManager nm = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(1, builder.build());
    }
}
