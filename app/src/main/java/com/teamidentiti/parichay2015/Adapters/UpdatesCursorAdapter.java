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

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Sravan on 3/29/2015.
 */
public class UpdatesCursorAdapter extends CursorAdapter {
    public UpdatesCursorAdapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_updates, null);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ((TextView)view.findViewById(R.id.msg)).setText(cursor.getString(cursor.getColumnIndex(TableContract.MessagesContract.COLUMN_MESSAGE)));

        String time = cursor.getString(cursor.getColumnIndex(TableContract.MessagesContract.COLUMN_TIME));
        String date = cursor.getString(cursor.getColumnIndex(TableContract.MessagesContract.COLUMN_DATE));
        SimpleDateFormat df = new SimpleDateFormat("dd-MM");
        Date d = new Date();
        String today = df.format(d);
        // Date formating: hh-mm, "Yesterday" or dd-MM
        if(today.equalsIgnoreCase(date))
            ((TextView)view.findViewById(R.id.time)).setText(time);
        else {
             int todayInt = Integer.parseInt(today.split("-")[0]);
             int dateInt = Integer.parseInt(date.split("-")[0]);
            if(dateInt-todayInt==1)
                ((TextView)view.findViewById(R.id.time)).setText("Yesterday");
            else
                ((TextView)view.findViewById(R.id.time)).setText(date);
        }
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }
}
