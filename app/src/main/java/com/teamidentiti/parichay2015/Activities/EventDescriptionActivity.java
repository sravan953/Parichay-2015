package com.teamidentiti.parichay2015.Activities;

import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.teamidentiti.parichay2015.R;

/**
 * Created by Sravan on 4/15/2015.
 */
public class EventDescriptionActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Enabling Lollipop Activity transitions, and applying colors to nav bar, status bar
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.colorLightBlueDark));
            window.setNavigationBarColor(getResources().getColor(R.color.colorLightBlueDark));
        }
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_events_desc);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorLightBlue)));

        String eventText = getIntent().getStringExtra("EVENT");

        TextView event = (TextView)findViewById(R.id.event);
        TextView venue = (TextView)findViewById(R.id.venue);
        TextView time = (TextView)findViewById(R.id.time);
        TextView desc = (TextView)findViewById(R.id.desc);

        event.setText(eventText);
        venue.setText(MainActivity.eventsData.get(MainActivity.eventsData.indexOf(eventText)+1));
        time.setText(MainActivity.eventsData.get(MainActivity.eventsData.indexOf(eventText)+3));
        desc.setText(MainActivity.eventsData.get(MainActivity.eventsData.indexOf(eventText)+2));
    }
}
