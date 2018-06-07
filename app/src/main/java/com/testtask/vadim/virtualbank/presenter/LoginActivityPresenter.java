package com.testtask.vadim.virtualbank.presenter;

import com.testtask.vadim.virtualbank.common.ErrorMessages;
import com.testtask.vadim.virtualbank.database.DbHelper;
import com.testtask.vadim.virtualbank.model.IClientsModel;
import com.testtask.vadim.virtualbank.model.ClientsModel;
import com.testtask.vadim.virtualbank.pojo.LoginData;
import com.testtask.vadim.virtualbank.pojo.User;
import com.testtask.vadim.virtualbank.ui.activityInterface.ILoginActivity;
import com.testtask.vadim.virtualbank.util.InputValidator;

public class LoginActivityPresenter {

    private ILoginActivity activity;
    private InputValidator validator;
    private IClientsModel model;

    private User user;

    public LoginActivityPresenter(ILoginActivity activity) {
        this.activity = activity;

        validator = new InputValidator();
        model = new ClientsModel();
    }

    public void loginClick() {
        LoginData loginData = activity.getLoginData();
        String email = loginData.getEmail();
        String password = loginData.getPassword();

        boolean correctData;
        correctData = validateEmail(email);
        correctData &= validatePassword(password);

        if(correctData) {
            if(loginUser(email, password)) {
                activity.loginComplete(user.getId());
            }
        }
    }

    private boolean validateEmail(String email) {
        if(validator.checkEmailFilled(email)) {
            activity.setEmailError(null);
            return true;
        } else {
            activity.setEmailError(validator.getErrorMessage());
            return false;
        }
    }

    private boolean validatePassword(String password) {
        if(validator.checkPasswordFilled(password)) {
            activity.setPasswordError(null);
            return true;
        } else {
            activity.setPasswordError(validator.getErrorMessage());
            return false;
        }
    }

    private boolean loginUser(String email, String password) {
        user =  model.getUser(email, password);

        if(user == null) {
            activity.showToast(ErrorMessages.USER_NOT_EXIST);
            return false;
        } else {
            return true;
        }
    }

    public void detachView() {
        activity = null;
    }

    public void attachDbHelper(DbHelper dbHelper){
        model.attachDbHelper(dbHelper);
    }
}
