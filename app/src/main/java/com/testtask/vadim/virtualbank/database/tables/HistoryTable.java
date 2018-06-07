package com.testtask.vadim.virtualbank.database.tables;

import static com.testtask.vadim.virtualbank.database.tables.HistoryTable.COLUMN.*;

public class HistoryTable {
    public static final String TABLE = "history";

    public static class COLUMN {
        public static final String ID = "_id";
        public static final String CARD_ID = "card_id";
        public static final String TYPE = "type";
        public static final String AMOUNT = "amount";
    }

    public static final String CREATE_SCRIPT =
            "CREATE TABLE " + TABLE + " ("
                    + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + CARD_ID + " INTEGER, "
                    + TYPE + " text, "
                    + AMOUNT + " REAL, "
                    + "FOREIGN KEY(" + CARD_ID + ") REFERENCES "
                    + CardsTable.TABLE + "(" + CardsTable.COLUMN.ID + ")"
                    + ")";

    public static final String DELETE_SCRIPT =
            "DROP TABLE IF EXISTS " + TABLE;
}
