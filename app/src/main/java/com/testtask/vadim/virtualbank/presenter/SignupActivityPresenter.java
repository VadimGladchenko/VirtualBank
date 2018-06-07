package com.testtask.vadim.virtualbank.presenter;

import com.testtask.vadim.virtualbank.common.ErrorMessages;
import com.testtask.vadim.virtualbank.database.DbHelper;
import com.testtask.vadim.virtualbank.model.ClientsModel;
import com.testtask.vadim.virtualbank.model.IClientsModel;
import com.testtask.vadim.virtualbank.pojo.RegistrationData;
import com.testtask.vadim.virtualbank.pojo.User;
import com.testtask.vadim.virtualbank.ui.activityInterface.ISignupActivity;
import com.testtask.vadim.virtualbank.util.InputValidator;

public class SignupActivityPresenter {

    private ISignupActivity activity;
    private InputValidator validator;
    private IClientsModel model;

    public SignupActivityPresenter(ISignupActivity activity) {
        this.activity = activity;

        validator = new InputValidator();
        model = new ClientsModel();
    }

    public void createUserClick() {
        RegistrationData registrationData = activity.getRegistrationData();
        String name = registrationData.getName();
        String email = registrationData.getEmail();
        String password = registrationData.getPassword();
        String confirmPassword = registrationData.getConfirmPassword();

        boolean correctData;
        correctData = validateEmail(email);
        correctData &= validateName(name);
        correctData &= validatePasswords(password,confirmPassword);

        if(correctData) {
            User user = new User();
            user.setEmail(email);
            user.setName(name);
            user.setPassword(password);

            if(model.isEmailExist(email)) {
                activity.setEmailError(ErrorMessages.USER_EXIST_ERROR);
            } else {
                if (model.addUser(user)) {
                    activity.registrationComplete();
                }
            }
        }
    }

    private boolean validateName(String name) {
        if(validator.checkNameFilled(name)) {
            activity.setNameError(null);
            return true;
        } else {
            activity.setNameError(validator.getErrorMessage());
            return false;
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

    private boolean validatePasswords(String password1, String confirmPassword) {
        if(validatePassword(password1) && validatePassword(confirmPassword)) {
            if(validator.checkPasswordEquals(password1, confirmPassword)){
                return true;
            } else {
                activity.setConfirmPasswordError(validator.getErrorMessage());
            }
        }
        return false;
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

    public void detachView() {
        activity = null;
    }

    public void attachDbHelper(DbHelper dbHelper){
        model.attachDbHelper(dbHelper);
    }
}
