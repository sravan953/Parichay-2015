package com.teamidentiti.parichay2015.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.teamidentiti.parichay2015.Database.TableContract;
import com.teamidentiti.parichay2015.R;

/**
 * Created by Sravan on 3/29/2015.
 */
public class ResultsCursorAdapter extends CursorAdapter {
    public ResultsCursorAdapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_results, null);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ((TextView)view.findViewById(R.id.event)).setText(cursor.getString(cursor.getColumnIndex(TableContract.ResultsContract.COLUMN_EVENT)));
        ((TextView)view.findViewById(R.id.winner)).setText(cursor.getString(cursor.getColumnIndex(TableContract.ResultsContract.COLUMN_WINNER)));
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }
}
