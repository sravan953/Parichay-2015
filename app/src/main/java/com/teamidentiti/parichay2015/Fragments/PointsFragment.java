package com.teamidentiti.parichay2015.Fragments;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.teamidentiti.parichay2015.Adapters.PointsCursorAdapter;
import com.teamidentiti.parichay2015.Database.Provider;
import com.teamidentiti.parichay2015.Database.TableContract;
import com.teamidentiti.parichay2015.R;


public class PointsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>  {
    private ListView listView;
    private PointsCursorAdapter pointsAdapter;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_updates_results_points, container, false);
        view.setBackgroundColor(getActivity().getResources().getColor(R.color.colorPointsFragment));

        listView = (ListView)view.findViewById(R.id.list);
        pointsAdapter = new PointsCursorAdapter(getActivity(), null, 0);
        listView.setAdapter(pointsAdapter);
        getLoaderManager().initLoader(3, null, this);

        return view;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(), Provider.POINTS_CONTENT_URI, null, null, null, TableContract.PointsContract.COLUMN_POINTS+" DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        pointsAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        pointsAdapter.swapCursor(null);
    }
}