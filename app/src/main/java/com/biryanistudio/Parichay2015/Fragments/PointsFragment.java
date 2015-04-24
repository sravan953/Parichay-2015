package com.biryanistudio.Parichay2015.Fragments;


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
import android.widget.TextView;

import com.biryanistudio.Parichay2015.Adapters.PointsCursorAdapter;
import com.biryanistudio.Parichay2015.Database.Provider;
import com.biryanistudio.Parichay2015.Database.TableContract;
import com.biryanistudio.Parichay2015.R;


public class PointsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>  {
    private ListView listView;
    private PointsCursorAdapter pointsAdapter;
    private TextView checkBackLater;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_updates_results_points, container, false);
        view.setBackgroundColor(getActivity().getResources().getColor(R.color.colorPointsFragment));

        checkBackLater = (TextView)view.findViewById(R.id.check_back_later);
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
        if(data.getCount()==0)
            checkBackLater.setVisibility(View.VISIBLE);
        else
            checkBackLater.setVisibility(View.GONE);
        pointsAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        pointsAdapter.swapCursor(null);
    }
}