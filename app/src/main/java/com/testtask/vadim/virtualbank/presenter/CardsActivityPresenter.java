package com.testtask.vadim.virtualbank.presenter;

import com.testtask.vadim.virtualbank.database.DbHelper;
import com.testtask.vadim.virtualbank.model.ClientsModel;
import com.testtask.vadim.virtualbank.model.IClientsModel;
import com.testtask.vadim.virtualbank.pojo.Card;
import com.testtask.vadim.virtualbank.ui.activityInterface.ICardsActivity;
import com.testtask.vadim.virtualbank.ui.adapter.ItemClickListener;
import com.testtask.vadim.virtualbank.util.CardNumberGenerator;
import com.testtask.vadim.virtualbank.util.InputValidator;

import java.util.ArrayList;
import java.util.List;

public class CardsActivityPresenter implements ItemClickListener{
    private InputValidator validator;
    private ICardsActivity activity;
    private IClientsModel model;

    private final int userId;
    private List<Card> cardsList;

    public CardsActivityPresenter(ICardsActivity activity, int userId) {
        this.activity = activity;
        this.userId = userId;
        model = new ClientsModel();
        validator = new InputValidator();
    }

    @Override
    public void onCardClick(int cardId) {
        activity.openCardDetails(cardId);
    }

    public void addNewCardClick(String name, String pin){
        boolean correctData;
        correctData = validateName(name);
        correctData &= validatePin(pin);

        if(correctData) {
            if(addNewCard(name, pin)) {
                activity.completeCreatingCard();
                cardsList.clear();
                cardsList.addAll(model.getUserCards(userId));
                activity.displayCards(cardsList);
            }
        }
    }

    private boolean validateName(String name) {
        if(validator.checkCardName(name)) {
            activity.setCardNameError(null);
            return true;
        } else {
            activity.setCardNameError(validator.getErrorMessage());
            return false;
        }
    }

    private boolean validatePin(String pin) {
        if(validator.checkCardPin(pin)) {
            activity.setCardPinError(null);
            return true;
        } else {
            activity.setCardPinError(validator.getErrorMessage());
            return false;
        }
    }

    private boolean addNewCard(String name, String pin) {
        Card card = new Card();
        card.setName(name);
        card.setPin(pin);

        String newCardNumber;
        do {
            newCardNumber = CardNumberGenerator.generate();
        } while (model.getCard(newCardNumber) != null);

        card.setNumber(newCardNumber);

        return model.addCard(card, userId);
    }



    public void detachView() {
        activity = null;
    }

    public void attachDbHelper(DbHelper dbHelper){
        model.attachDbHelper(dbHelper);

        cardsList = new ArrayList<>();
        cardsList.addAll(model.getUserCards(userId));
        activity.displayCards(cardsList);
    }
}
