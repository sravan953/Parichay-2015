package com.teamidentiti.parichay2015.GCM;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.teamidentiti.parichay2015.Activities.MainActivity;
import com.teamidentiti.parichay2015.Database.Provider;
import com.teamidentiti.parichay2015.Database.TableContract;
import com.teamidentiti.parichay2015.R;

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
            addToDb(message);
            makeNotif(message);
        }
    }

    private void addToDb(String msg) {
        SimpleDateFormat df = new SimpleDateFormat("dd-MM");
        Date d = new Date();
        String date = df.format(d);
        df = new SimpleDateFormat("HH:mm");
        String time = df.format(d);

        /*
        Message format:
        [UPDATE]Message
        OR
        [RESULTS]Event~Winner
        OR
        [POINTS]Branch~Points
         */
        ContentValues values = new ContentValues();
        if(msg.contains("[UPDATE]")) {
            msg = msg.replace("[UPDATE]", "");
            if(msg.length()!=0) {
                values.put(TableContract.MessagesContract.COLUMN_MESSAGE, msg);
                values.put(TableContract.MessagesContract.COLUMN_DATE, date);
                values.put(TableContract.MessagesContract.COLUMN_TIME, time);
                getContentResolver().insert(Provider.MESSAGES_CONTENT_URI, values);
            }
        }
        else if(msg.contains("[RESULTS]")) {
            msg = msg.replace("[RESULTS]", "");
            if(msg.length()!=1) {
                String event = msg.split("~")[0];
                String winner = msg.split("~")[1];
                values.put(TableContract.ResultsContract.COLUMN_EVENT, event);
                values.put(TableContract.ResultsContract.COLUMN_WINNER, winner);
                getContentResolver().insert(Provider.RESULTS_CONTENT_URI, values);
            }
        }
        else if(msg.contains("[POINTS]")) {
            msg = msg.replace("[POINTS]", "");
            if(msg.length()!=1) {
                String branch = msg.split("~")[0];
                String points = msg.split("~")[1];
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
            }
        }
    }

    private void makeNotif(String msg) {
        String contentText = "";
        if(msg.contains("[UPDATE]")) {
            msg = msg.replace("[UPDATE]", "");
            if(msg.length()!=0) {
                contentText = msg;
                showNotif(contentText);
            }
        }
        else if(msg.contains("[RESULTS]")) {
            msg = msg.replace("[RESULTS]", "");
            if(msg.length()!=1) {
                String event = msg.split("~")[0];
                contentText = "Results for " + event + " have been announced! Click here to see who the winner is!";
                showNotif(contentText);
            }
        }
        else if(msg.contains("[POINTS]")) {
            msg = msg.replace("[POINTS]", "");
            if(msg.length()!=1) {
                String branch = msg.split("~")[0];
                contentText = branch + " just scored some points! Click here to see who's leading the scoreboard!";
                showNotif(contentText);
            }
        }
    }

    private void showNotif(String contentText) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("Parichay 2015");
        builder.setContentText(contentText);
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pIntent);
        builder.setStyle(new NotificationCompat.BigTextStyle().bigText(contentText));
        builder.setDefaults(Notification.DEFAULT_VIBRATE);
        builder.setAutoCancel(true);

        NotificationManager nm = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(1, builder.build());
    }
}
