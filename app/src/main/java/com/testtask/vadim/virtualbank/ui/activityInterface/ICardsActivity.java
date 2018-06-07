package com.testtask.vadim.virtualbank.ui.activityInterface;

import com.testtask.vadim.virtualbank.pojo.Card;

import java.util.List;

public interface ICardsActivity {
    void openCardDetails(int cardId);

    void displayCards(List<Card> carsList);

    void openCreateCardDialog();

    void completeCreatingCard();

    void setCardNameError(String message);

    void setCardPinError(String message);

    void showToast(String text);
}
