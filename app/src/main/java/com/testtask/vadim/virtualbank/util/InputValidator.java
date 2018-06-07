package com.testtask.vadim.virtualbank.util;

import android.util.Patterns;

import com.testtask.vadim.virtualbank.pojo.Card;

import static com.testtask.vadim.virtualbank.common.ErrorMessages.*;

public class InputValidator {
    private static int MAX_LENGTH = 30;

    private String errorMessage;

    public String getErrorMessage(){
        return errorMessage;
    }

    public boolean checkNameFilled(String value){
        if(value.isEmpty()){
            errorMessage = EMPTY_FIELD;
            return false;
        } else if(value.length() > MAX_LENGTH) {
            errorMessage = LENGTH_ERROR;
            return false;
        } else {
            return true;
        }
    }

    public boolean checkEmailFilled(String value){
        if(value.isEmpty()){
            errorMessage = EMPTY_FIELD;
            return false;
        } else if(value.length() > MAX_LENGTH) {
            errorMessage = LENGTH_ERROR;
            return false;
        } else if(!Patterns.EMAIL_ADDRESS.matcher(value).matches()){
            errorMessage = EMAIL_ERROR;
            return false;
        } else {
            return true;
        }
    }

    public boolean checkPasswordFilled(String value){
        if(value.isEmpty()){
            errorMessage = EMPTY_FIELD;
            return false;
        } else if(value.length() > MAX_LENGTH) {
            errorMessage = LENGTH_ERROR;
            return false;
        } else {
            return true;
        }
    }

    public boolean checkPasswordEquals(String value1, String value2){
        if(!value1.contentEquals(value2)){
            errorMessage = PASSWORD_CONFIRM_ERROR;
            return false;
        } else {
            return true;
        }
    }

    public boolean checkCardName(String value){
        if(value.isEmpty()){
            errorMessage = EMPTY_FIELD;
            return false;
        } else if(value.length() > MAX_LENGTH) {
            errorMessage = LENGTH_ERROR;
            return false;
        } else {
            return true;
        }
    }

    public boolean checkCardPin(String value){
        if(value.isEmpty()){
            errorMessage = EMPTY_FIELD;
            return false;
        } else if(value.length() != 4) {
            errorMessage = PIN_LENGTH_ERROR;
            return false;
        } else {
            return true;
        }
    }

    public boolean checkAmount(double value){
        if(value <= 0){
            errorMessage = EMPTY_FIELD;
            return false;
        } else if(value > Card.MAX_TRANSACTION_VALUE) {
            errorMessage = MAX_AMOUNT_ERROR;
            return false;
        } else {
            return true;
        }
    }

    public boolean checkCardNumber(String value){
        if(value.isEmpty()){
            errorMessage = EMPTY_FIELD;
            return false;
        } else if(value.length() != 16) {
            errorMessage = CARD_LENGTH_ERROR;
            return false;
        } else {
            return true;
        }
    }
}
