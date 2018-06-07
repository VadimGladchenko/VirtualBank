package com.testtask.vadim.virtualbank.pojo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class User {
    private int id;
    private String email;
    private String name;
    private String password;
    private ArrayList<Card> cardsArray;

    public User() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<Card> getCardsArray() {
        return cardsArray;
    }

    public void setCardsArray(ArrayList<Card> cardsArray) {
        this.cardsArray = cardsArray;
    }

    public void setCards(Collection<Card> collection) {
        cardsArray.addAll(collection);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
