package com.biryanistudio.Parichay2015.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.biryanistudio.Parichay2015.Database.TableContract;
import com.biryanistudio.Parichay2015.R;

/**
 * Created by Sravan on 3/29/2015.
 */
public class PointsCursorAdapter extends CursorAdapter {
    public PointsCursorAdapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_points, null);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ((TextView)view.findViewById(R.id.branch)).setText(cursor.getString(cursor.getColumnIndex(TableContract.PointsContract.COLUMN_BRANCH)));
        ((TextView)view.findViewById(R.id.points)).setText(cursor.getString(cursor.getColumnIndex(TableContract.PointsContract.COLUMN_POINTS)));
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }
}
