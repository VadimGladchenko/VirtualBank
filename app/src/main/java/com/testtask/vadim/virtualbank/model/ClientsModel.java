package com.testtask.vadim.virtualbank.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.testtask.vadim.virtualbank.database.DbHelper;
import com.testtask.vadim.virtualbank.database.tables.CardsTable;
import com.testtask.vadim.virtualbank.database.tables.HistoryTable;
import com.testtask.vadim.virtualbank.database.tables.UsersTable;
import com.testtask.vadim.virtualbank.pojo.Card;
import com.testtask.vadim.virtualbank.pojo.Transaction;
import com.testtask.vadim.virtualbank.pojo.User;

import java.util.ArrayList;
import java.util.List;

public class ClientsModel implements IClientsModel {
    private DbHelper dbHelper;

    public ClientsModel() {
    }

    public boolean addUser(User user){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UsersTable.COLUMN.NAME, user.getName());
        values.put(UsersTable.COLUMN.EMAIL, user.getEmail());
        values.put(UsersTable.COLUMN.PASSWORD, user.getPassword());

        db.insert(UsersTable.TABLE, null, values);
        db.close();

        return true;
    }

    @Override
    public void attachDbHelper(DbHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public boolean isEmailExist(String email){
        String[] columns = {
                UsersTable.COLUMN.ID
        };
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selection = UsersTable.COLUMN.EMAIL + " = ?";
        String[] selectionArgs = { email };

        Cursor cursor = db.query(UsersTable.TABLE,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        return cursorCount > 0;
    }

    public User getUser(String email, String password) {
        String[] columns = {
                UsersTable.COLUMN.ID,
                UsersTable.COLUMN.NAME,
                UsersTable.COLUMN.EMAIL
        };
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selection = UsersTable.COLUMN.EMAIL + " = ?" + " AND " + UsersTable.COLUMN.PASSWORD + " =?";
        String[] selectionArgs = { email, password };

        Cursor cursor = db.query(UsersTable.TABLE,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        int cursorCount = cursor.getCount();

        if(cursorCount > 0){
            cursor.moveToNext();

            User user = new User();
            user.setId(cursor.getInt(cursor.getColumnIndex(UsersTable.COLUMN.ID)));
            user.setName(cursor.getString(cursor.getColumnIndex(UsersTable.COLUMN.NAME)));
            user.setEmail(cursor.getString(cursor.getColumnIndex(UsersTable.COLUMN.EMAIL)));

            cursor.close();
            db.close();
            return user;
        }
        cursor.close();
        db.close();

        return null;
    }

    public boolean addCard(Card card, int userId){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(CardsTable.COLUMN.NUMBER, card.getNumber());
        values.put(CardsTable.COLUMN.NAME, card.getName());
        values.put(CardsTable.COLUMN.PIN, card.getPin());
        values.put(CardsTable.COLUMN.USER_ID, userId);

        db.insert(CardsTable.TABLE, null, values);
        db.close();

        return true;
    }

    @Override
    public boolean addTransaction(int cardId, Transaction transaction) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(HistoryTable.COLUMN.CARD_ID, cardId);
        values.put(HistoryTable.COLUMN.TYPE, transaction.getType());
        values.put(HistoryTable.COLUMN.AMOUNT, transaction.getAmount());

        db.insert(HistoryTable.TABLE, null, values);
        db.close();

        return true;
    }

    @Override
    public List<Transaction> getTransactions(int cardId) {
        List<Transaction> transactionList = new ArrayList<>();

        String[] columns = {
                HistoryTable.COLUMN.ID,
                HistoryTable.COLUMN.TYPE,
                HistoryTable.COLUMN.AMOUNT,
        };
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selection = HistoryTable.COLUMN.CARD_ID + " = ?";
        String[] selectionArgs = { String.valueOf(cardId) };

        Cursor cursor = db.query(HistoryTable.TABLE,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);

        while (cursor.moveToNext()) {
            Transaction transaction = new Transaction();

            transaction.setId(cursor.getInt(cursor.getColumnIndex(HistoryTable.COLUMN.ID)));
            transaction.setType(cursor.getString(cursor.getColumnIndex(HistoryTable.COLUMN.TYPE)));
            transaction.setAmount(cursor.getDouble(cursor.getColumnIndex(HistoryTable.COLUMN.AMOUNT)));

            transactionList.add(transaction);
        }
        cursor.close();
        db.close();

        return transactionList;
    }

    @Override
    public List<Card> getUserCards(int userId) {
        List<Card> cardsList = new ArrayList<>();

        String[] columns = {
                CardsTable.COLUMN.ID,
                CardsTable.COLUMN.NUMBER,
                CardsTable.COLUMN.NAME,
                CardsTable.COLUMN.BALANCE,
                CardsTable.COLUMN.PIN
        };
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selection = CardsTable.COLUMN.USER_ID + " = ?";
        String[] selectionArgs = { String.valueOf(userId) };

        Cursor cursor = db.query(CardsTable.TABLE,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);

        while (cursor.moveToNext()) {
            Card card = new Card();

            card.setId(cursor.getInt(cursor.getColumnIndex(CardsTable.COLUMN.ID)));
            card.setNumber(cursor.getString(cursor.getColumnIndex(CardsTable.COLUMN.NUMBER)));
            card.setName(cursor.getString(cursor.getColumnIndex(CardsTable.COLUMN.NAME)));
            card.setBalance(cursor.getDouble(cursor.getColumnIndex(CardsTable.COLUMN.BALANCE)));
            card.setPin(cursor.getString(cursor.getColumnIndex(CardsTable.COLUMN.PIN)));

            cardsList.add(card);
        }
        cursor.close();
        db.close();

        return cardsList;
    }

    @Override
    public Card getCard(int cardId) {
        Card card = new Card();

        String[] columns = {
                CardsTable.COLUMN.ID,
                CardsTable.COLUMN.NUMBER,
                CardsTable.COLUMN.NAME,
                CardsTable.COLUMN.BALANCE,
                CardsTable.COLUMN.PIN
        };
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selection = CardsTable.COLUMN.ID + " = ?";
        String[] selectionArgs = { String.valueOf(cardId) };

        Cursor cursor = db.query(CardsTable.TABLE,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);

        cursor.moveToNext();

        card.setId(cursor.getInt(cursor.getColumnIndex(CardsTable.COLUMN.ID)));
        card.setNumber(cursor.getString(cursor.getColumnIndex(CardsTable.COLUMN.NUMBER)));
        card.setName(cursor.getString(cursor.getColumnIndex(CardsTable.COLUMN.NAME)));
        card.setBalance(cursor.getDouble(cursor.getColumnIndex(CardsTable.COLUMN.BALANCE)));
        card.setPin(cursor.getString(cursor.getColumnIndex(CardsTable.COLUMN.PIN)));

        cursor.close();
        db.close();

        return card;
    }

    @Override
    public Card getCard(String cardNumber) {
        Card card = new Card();

        String[] columns = {
                CardsTable.COLUMN.ID,
                CardsTable.COLUMN.NUMBER,
                CardsTable.COLUMN.NAME,
                CardsTable.COLUMN.BALANCE,
                CardsTable.COLUMN.PIN
        };
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selection = CardsTable.COLUMN.NUMBER + " = ?";
        String[] selectionArgs = { String.valueOf(cardNumber) };

        Cursor cursor = db.query(CardsTable.TABLE,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);

        if(cursor.moveToNext()) {
            card.setId(cursor.getInt(cursor.getColumnIndex(CardsTable.COLUMN.ID)));
            card.setNumber(cursor.getString(cursor.getColumnIndex(CardsTable.COLUMN.NUMBER)));
            card.setName(cursor.getString(cursor.getColumnIndex(CardsTable.COLUMN.NAME)));
            card.setBalance(cursor.getDouble(cursor.getColumnIndex(CardsTable.COLUMN.BALANCE)));
            card.setPin(cursor.getString(cursor.getColumnIndex(CardsTable.COLUMN.PIN)));
        } else {
            cursor.close();
            db.close();

            return null;
        }

        cursor.close();
        db.close();

        return card;
    }

    @Override
    public boolean updateCard(Card card) {

        ContentValues cv = new ContentValues();
        cv.put(CardsTable.COLUMN.BALANCE, card.getBalance());

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] selectionArgs = { String.valueOf(card.getId()) };

        int updCount = db.update(CardsTable.TABLE, cv, CardsTable.COLUMN.ID + " = ?", selectionArgs);

        db.close();

        return updCount == 1;
    }
}
