package com.testtask.vadim.virtualbank.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.testtask.vadim.virtualbank.database.tables.CardsTable;
import com.testtask.vadim.virtualbank.database.tables.HistoryTable;
import com.testtask.vadim.virtualbank.database.tables.UsersTable;

public class DbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Clients.db";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(UsersTable.CREATE_SCRIPT);
        sqLiteDatabase.execSQL(CardsTable.CREATE_SCRIPT);
        sqLiteDatabase.execSQL(HistoryTable.CREATE_SCRIPT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
