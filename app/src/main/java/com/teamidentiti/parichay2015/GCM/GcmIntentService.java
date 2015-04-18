package com.teamidentiti.parichay2015.GCM;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

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
            values.put(TableContract.MessagesContract.COLUMN_MESSAGE, msg);
            values.put(TableContract.MessagesContract.COLUMN_DATE, date);
            values.put(TableContract.MessagesContract.COLUMN_TIME, time);
            getContentResolver().insert(Provider.MESSAGES_CONTENT_URI, values);
        }
        else if(msg.contains("[RESULTS]")) {
            msg = msg.replace("[RESULTS]", "");
            String event = msg.split("~")[0];
            String winner = msg.split("~")[1];
            values.put(TableContract.ResultsContract.COLUMN_EVENT, event);
            values.put(TableContract.ResultsContract.COLUMN_WINNER, winner);
            getContentResolver().insert(Provider.RESULTS_CONTENT_URI, values);
        }
        else if(msg.contains("[POINTS]")) {
            msg = msg.replace("[POINTS]", "");
            String branch = msg.split("~")[0];
            String points = msg.split("~")[1];
            values.put(TableContract.PointsContract.COLUMN_EVENT, branch);
            values.put(TableContract.PointsContract.COLUMN_POINTS, points);
            getContentResolver().update(Provider.POINTS_CONTENT_URI, values, null, null);
        }
    }

    private void makeNotif(String msg) {
        String contentText = "";
        if(msg.contains("[UPDATE]")) {
            msg = msg.replace("[UPDATE]", "");
            contentText = msg;
        }
        else if(msg.contains("[RESULTS]")) {
            msg = msg.replace("[RESULTS]", "");
            String event = msg.split("~")[0];
            contentText = "Results for "+event+" have been announced! Click here to see who the winner is!";
        }
        else if(msg.contains("[POINTS]")) {
            msg = msg.replace("[POINTS]", "");
            String branch = msg.split("~")[0];
            contentText = branch+" just scored some points! Click here to see who's leading the scoreboard!";
        }
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
