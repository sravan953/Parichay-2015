package com.biryanistudio.Parichay2015.GCM;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sravan on 3/29/2015.
 * Register with GCM, and save registration ID
 * with server.
 */
public class RegistrationsIntentService extends IntentService {
    public RegistrationsIntentService() {
        super("Parichay 2015");
    }

    public RegistrationsIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            GoogleCloudMessaging gcm=GoogleCloudMessaging.getInstance(this);
            String regID = gcm.register("730126292261");
            Log.i("DATA", "Reg ID - "+regID);
            saveServer(regID);
        } catch(Exception e) {}
    }

    private void saveServer(String regID) {
        String URL = "http://pantheon.0fees.net/parichay2015/index.php?push=1";
        try {
            HttpClient client=new DefaultHttpClient();
            HttpPost post=new HttpPost(URL);
            List<NameValuePair> nameValue=new ArrayList<NameValuePair>();
            nameValue.add(new BasicNameValuePair("regId", regID));
            post.setEntity(new UrlEncodedFormEntity(nameValue));
            client.execute(post);

            persistRegistration(regID);
        } catch (UnsupportedEncodingException e) {
        } catch (ClientProtocolException e) {
        } catch (IOException e) {}
    }

    private void persistRegistration(String regID) {
        SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(this);
        p.edit().putBoolean("GCM_REGISTERED", true).commit();
    }
}
