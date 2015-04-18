package com.teamidentiti.parichay2015.Database;

import android.provider.BaseColumns;

/**
 * Created by Sravan on 3/29/2015.
 */
public class TableContract {
    public static final class MessagesContract implements BaseColumns {
        public static final String TABLE_NAME = "MessagesTable";

        public static final String COLUMN_TIME = "Time";
        public static final String COLUMN_DATE = "Date";
        public static final String COLUMN_MESSAGE = "Message";
    }

    public static final class ResultsContract implements BaseColumns {
        public static final String TABLE_NAME = "ResultsTable";

        public static final String COLUMN_EVENT = "Event";
        public static final String COLUMN_WINNER = "Winner";
    }

    public static final class PointsContract implements BaseColumns {
        public static final String TABLE_NAME = "PointsTable";

        public static final String COLUMN_EVENT = "Event";
        public static final String COLUMN_POINTS = "Points";
    }
}