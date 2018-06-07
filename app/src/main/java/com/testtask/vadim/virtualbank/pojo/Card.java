package com.testtask.vadim.virtualbank.pojo;

import java.util.Objects;

public class Card {
    public final static int MAX_TRANSACTION_VALUE = 100_001;
    private int id;
    private String name;
    private String number;
    private double balance;
    private String pin;

    public Card() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getFormattedNumber() {
        StringBuilder sb = new StringBuilder(String.valueOf(number));
        for (int i = 0; i < 4; i++) {
            sb.insert(4*i+i, " ");
        }
        return sb.toString();
    }

    public double getBalance() {
        return balance;
    }

    public void replenish(double amount) {
        this.balance += amount;
    }

    public void withdraw(double amount) {
        this.balance -= amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Card)) return false;
        Card card = (Card) o;
        return id == card.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
