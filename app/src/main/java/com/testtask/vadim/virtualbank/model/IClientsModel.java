package com.testtask.vadim.virtualbank.model;

import com.testtask.vadim.virtualbank.database.DbHelper;
import com.testtask.vadim.virtualbank.pojo.Card;
import com.testtask.vadim.virtualbank.pojo.Transaction;
import com.testtask.vadim.virtualbank.pojo.User;

import java.util.ArrayList;
import java.util.List;

public interface IClientsModel {
    void attachDbHelper(DbHelper dbHelper);

    boolean isEmailExist(String email);
    boolean addUser(User user);

    User getUser(String email, String password);

    List<Card> getUserCards(int userId);

    Card getCard(int cardId);

    Card getCard(String cardNumber);

    boolean updateCard(Card card);

    boolean addCard(Card card, int userId);

    boolean addTransaction(int cardId, Transaction transaction);

    List<Transaction> getTransactions(int cardId);

}
