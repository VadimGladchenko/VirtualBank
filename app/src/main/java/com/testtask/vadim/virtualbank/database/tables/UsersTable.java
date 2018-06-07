package com.testtask.vadim.virtualbank.database.tables;

import static com.testtask.vadim.virtualbank.database.tables.UsersTable.COLUMN.*;

public class UsersTable {

    public static final String TABLE = "users";

    public static class COLUMN {
        public static final String ID = "_id";
        public static final String NAME = "name";
        public static final String EMAIL = "email";
        public static final String PASSWORD = "password";

    }

    public static final String CREATE_SCRIPT =
            "CREATE TABLE " + TABLE + " ("
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + NAME + " text, "
            + EMAIL + " text, "
            + PASSWORD + " text "
            + ")";

    public static final String DELETE_SCRIPT =
            "DROP TABLE IF EXISTS " + TABLE;
}
