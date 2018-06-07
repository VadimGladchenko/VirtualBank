package com.testtask.vadim.virtualbank.database.tables;

import static com.testtask.vadim.virtualbank.database.tables.CardsTable.COLUMN.*;

public class CardsTable {
    public static final String TABLE = "cards";

    public static class COLUMN {
        public static final String ID = "_id";
        public static final String USER_ID = "user_id";
        public static final String NUMBER = "number";
        public static final String NAME = "name";
        public static final String BALANCE = "balance";
        public static final String PIN = "pin";

    }

    public static final String CREATE_SCRIPT =
            "CREATE TABLE " + TABLE + " ("
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + USER_ID + " INTEGER, "
            + NUMBER + " text, "
            + NAME + " text, "
            + BALANCE + " REAL, "
            + PIN + " text, "
            + "FOREIGN KEY(" + USER_ID + ") REFERENCES "
                + UsersTable.TABLE + "(" + UsersTable.COLUMN.ID + ")"
            + ")";

    public static final String DELETE_SCRIPT =
            "DROP TABLE IF EXISTS " + TABLE;
}
