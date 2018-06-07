package com.testtask.vadim.virtualbank.ui.activityInterface;

import com.testtask.vadim.virtualbank.pojo.RegistrationData;

public interface ISignupActivity {

    RegistrationData getRegistrationData();

    void setNameError(String message);

    void setEmailError(String message);

    void setPasswordError(String message);

    void setConfirmPasswordError(String message);

    void registrationComplete();
}
