package com.testtask.vadim.virtualbank.common;

public class ErrorMessages {
    public final static String USER_NOT_EXIST = "Wrong email or password";
    public static final String USER_EXIST_ERROR = "User with this email already exist";

    public static final String WRONG_PIN = "Wrong pin";
    public static final String CARD_NOT_EXIST = "Card doesn't exist";
    public static final String INSUFFICIENT_FUNDS = "Insufficient funds";
    public static final String NO_TRANS_YET = "No transactions yet";
    public static final String EMPTY_VALUE = "Empty value";

    public static final String EMPTY_FIELD = "Empty field";
    public static final String LENGTH_ERROR = "Max length is 30 symbols";
    public static final String PIN_LENGTH_ERROR = "Too short";
    public static final String EMAIL_ERROR = "Wrong email format";
    public static final String PASSWORD_CONFIRM_ERROR = "Passwords don't match";
    public static final String MAX_AMOUNT_ERROR = "Max amount is 100000";
    public static final String CARD_LENGTH_ERROR = "Length must be 16 symbols";
}
