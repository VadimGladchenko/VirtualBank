package com.testtask.vadim.virtualbank.presenter;

import com.testtask.vadim.virtualbank.common.ErrorMessages;
import com.testtask.vadim.virtualbank.database.DbHelper;
import com.testtask.vadim.virtualbank.model.ClientsModel;
import com.testtask.vadim.virtualbank.model.IClientsModel;
import com.testtask.vadim.virtualbank.pojo.Card;
import com.testtask.vadim.virtualbank.pojo.Transaction;
import com.testtask.vadim.virtualbank.ui.activityInterface.ICardViewActivity;
import com.testtask.vadim.virtualbank.util.InputValidator;

import java.util.List;

public class CardViewActivityPresenter {
    private static final String TRANS_SEND = "Transaction send";
    private static final String TRANS_INCOME = "Transaction income";
    private static final String TRANS_REPLENISH = "Transaction replenish";
    private static final String TRANS_WITHDRAW = "Transaction withdraw";

    private ICardViewActivity activity;
    private InputValidator validator;
    private IClientsModel model;

    private final int cardId;
    private Card card;

    private double amountDouble;

    public CardViewActivityPresenter(ICardViewActivity activity, int cardId) {
        this.activity = activity;
        this.cardId = cardId;

        validator = new InputValidator();
        model = new ClientsModel();
    }

    public void sendCardClick(String recipientCardNumber, String amount, String pin) {
        boolean correctData;
        correctData = validateRecipientCardNumber(recipientCardNumber);
        correctData &= validateAmount(amount);
        correctData &= validatePin(pin);

        if(correctData) {
            Card recipientCard = model.getCard(recipientCardNumber);

            if(sendCard(amountDouble, pin, recipientCard)) {
                addTransaction(TRANS_SEND, amountDouble, cardId);
                addTransaction(TRANS_INCOME, amountDouble, recipientCard.getId());
                activity.completeSendCard();
                activity.displayCardInfo(card);
            }
        }
    }

    public void replenishCardClick(String amount, String pin) {
        boolean correctData;
        correctData = validateAmount(amount);
        correctData &= validatePin(pin);

        if(correctData) {
            if(replenishCard(amountDouble, pin)) {
                addTransaction(TRANS_REPLENISH, amountDouble, cardId);
                activity.completeReplenishCard();
                activity.displayCardInfo(card);
            }
        }
    }

    public void withdrawCardClick(String amount, String pin) {
        boolean correctData;
        correctData = validateAmount(amount);
        correctData &= validatePin(pin);

        if(correctData) {
            if(withdrawCard(amountDouble, pin)) {
                addTransaction(TRANS_WITHDRAW, amountDouble, cardId);
                activity.completeWithdrawCard();
                activity.displayCardInfo(card);
            }
        }
    }

    public void showHistoryClick() {
        List<Transaction> transactions = model.getTransactions(cardId);

        StringBuilder historySb = new StringBuilder();
        for(Transaction transaction : transactions) {
            historySb
                    .append(transaction.getType())
                    .append("\t\t")
                    .append(transaction.getAmount())
                    .append("\n");
        }
        String history = historySb.toString();
        if(history.isEmpty()) {
            history = ErrorMessages.NO_TRANS_YET;
        }
        activity.showHistory(history);
    }

    private boolean sendCard(double amount, String pin, Card recipientCard) {
        if(isPinCorrect(pin)) {
            if(card.getBalance() >= amount) {
                if (recipientCard != null) {
                    recipientCard.replenish(amount);
                    card.withdraw(amount);
                    if (model.updateCard(card) && model.updateCard(recipientCard)) {
                        return true;
                    } else {
                        recipientCard.withdraw(amount);
                        card.replenish(amount);
                        return false;
                    }
                } else {
                    activity.setRecipientError(ErrorMessages.CARD_NOT_EXIST);
                    return false;
                }
            } else {
                activity.setAmountError(ErrorMessages.INSUFFICIENT_FUNDS);
            }
        }
        return false;
    }

    private boolean replenishCard(double amount, String pin) {
        if(isPinCorrect(pin)) {
            card.replenish(amount);
            if(model.updateCard(card)) {
                return true;
            } else {
                card.withdraw(amount);
                return false;
            }
        }
        return false;
    }

    private boolean withdrawCard(double amount, String pin) {
        if(isPinCorrect(pin)) {
            if(card.getBalance() >= amount) {
                card.withdraw(amount);
                if (model.updateCard(card)) {
                    return true;
                } else {
                    card.replenish(amount);
                    return false;
                }
            } else {
                activity.setAmountError(ErrorMessages.INSUFFICIENT_FUNDS);
            }
        }
        return false;
    }


    private boolean validateRecipientCardNumber(String recipientCardNumber) {
        if(validator.checkCardNumber(recipientCardNumber)) {
            activity.setRecipientError(null);
            return true;
        } else {
            activity.setRecipientError(validator.getErrorMessage());
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

    private boolean validateAmount(String amount) {
        try {
            amountDouble = Double.parseDouble(amount);
        } catch (NumberFormatException e) {
            activity.setAmountError(ErrorMessages.EMPTY_VALUE);
            return false;
        }
        if(validator.checkAmount(amountDouble)) {
            activity.setAmountError(null);
            return true;
        } else {
            activity.setAmountError(validator.getErrorMessage());
            return false;
        }
    }

    private boolean isPinCorrect(String pin) {
        if(card.getPin().equals(pin)) {
            activity.setCardPinError(null);
            return true;
        } else {
            activity.setCardPinError(ErrorMessages.WRONG_PIN);
            return false;
        }
    }

    private boolean addTransaction(String type, double amount, int TransCardId) {
        Transaction transaction = new Transaction();

        transaction.setAmount(amount);
        transaction.setType(type);

        return model.addTransaction(TransCardId, transaction);
    }

    public void detachView() {
        activity = null;
    }

    public void attachDbHelper(DbHelper dbHelper){
        model.attachDbHelper(dbHelper);

        card = model.getCard(cardId);
        activity.displayCardInfo(card);
        activity.setActionBarTitle(card.getName());
    }
}
