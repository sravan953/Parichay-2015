package com.biryanistudios.Parichay2015;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.biryanistudios.Parichay2015.Activities.MainActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Sravan on 3/29/2015.
 */
public class EventsIntentService extends IntentService {
    Elements rows;

    public EventsIntentService() {
        super("Parichay 2015");
    }

    public EventsIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        getEvents();
    }

    private void getEvents() {
        try {
            String url = "http://pantheon.0fees.net/parichay2015/events.html";
            Document doc = Jsoup.connect(url).userAgent("Chrome/41.0.2228.0").get();
            Elements elements = doc.getElementsByClass("events");
            rows = elements.select("tr");
            addToList(rows);
        } catch(IOException e) {
        } catch(Exception e) {}
    }

    private void addToList(Elements events) {
        MainActivity.eventsData = new ArrayList<String>();
        for(Element temp:events) {
            MainActivity.eventsData.add(temp.text());
        }
        sendLocalBroadcast();
    }

    private void sendLocalBroadcast() {
        Intent intent = new Intent();
        intent.setAction("com.teamidentiti.parichay2015.UPDATE_EVENTS");
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}
