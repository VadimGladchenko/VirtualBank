package com.testtask.vadim.virtualbank.ui.activityInterface;

import com.testtask.vadim.virtualbank.pojo.LoginData;

public interface ILoginActivity {

    LoginData getLoginData();

    void setEmailError(String message);

    void setPasswordError(String message);

    void showToast(String message);

    void loginComplete(int userId);
}
