package com.testtask.vadim.virtualbank.ui.activityInterface;

import com.testtask.vadim.virtualbank.pojo.Card;

public interface ICardViewActivity {
    void setActionBarTitle(String title);

    void setRecipientError(String message);
    void setAmountError(String message);
    void setCardPinError(String message);

    void displayCardInfo(Card card);

    void showHistory(String message);

    void completeSendCard();
    void completeReplenishCard();
    void completeWithdrawCard();
    void completeHistoryCard();

    void showToast(String message);
}
