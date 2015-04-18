package com.teamidentiti.parichay2015.Fragments;


import android.app.ActivityOptions;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.teamidentiti.parichay2015.Activities.EventDescriptionActivity;
import com.teamidentiti.parichay2015.Activities.MainActivity;
import com.teamidentiti.parichay2015.Adapters.EventsArrayAdapter;
import com.teamidentiti.parichay2015.R;


public class EventsFragment extends Fragment implements ListView.OnItemClickListener {
    private ListView listView;
    private EventsArrayAdapter eventsAdapter;
    private ProgressBar progress;

    private BroadcastReceiver events = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            progress.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            eventsAdapter = new EventsArrayAdapter(getActivity(), R.layout.item_events, MainActivity.eventsData);
            listView.setAdapter(eventsAdapter);
        }
    };

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_events, container, false);
        view.setBackgroundColor(getActivity().getResources().getColor(R.color.colorEventsFragment));

        progress = (ProgressBar)view.findViewById(R.id.progress);
        listView = (ListView)view.findViewById(R.id.list);
        listView.setVisibility(View.GONE);
        listView.setOnItemClickListener(this);

        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(events, new IntentFilter("com.teamidentiti.parichay2015.UPDATE_EVENTS"));

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), EventDescriptionActivity.class);
        intent.putExtra("EVENT", MainActivity.eventsData.get(position*4));
        getActivity().startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
    }

    @Override
    public void onDetach () {
        super.onDetach();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(events);
    }
}