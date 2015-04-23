package com.biryanistudios.Parichay2015.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.biryanistudios.Parichay2015.R;

import java.util.List;

/**
 * Created by Sravan on 4/15/2015.
 */
public class EventsArrayAdapter<E> extends ArrayAdapter<String> {
    List<String> mData;

    public EventsArrayAdapter(Context context, int resource, List<String> data) {
        super(context, resource, data);
        mData = data;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        /*
        eventsData structure:
        1. Event
        2. Venue
        3. Description
        4. Time
         */
        position = position*4;
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_events, parent, false);
        ((TextView) convertView.findViewById(R.id.event)).setText(mData.get(position));
        ((TextView) convertView.findViewById(R.id.venue)).setText(mData.get(position+1));
        ((TextView) convertView.findViewById(R.id.time)).setText(mData.get(position+3));

        convertView.setAlpha(0f);
        convertView.animate().alpha(1f).start();
        return convertView;
    }

    public int getCount() {
        return mData.size()/4;
    }
}
