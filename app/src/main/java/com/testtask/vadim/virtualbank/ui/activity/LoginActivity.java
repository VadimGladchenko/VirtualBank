package com.testtask.vadim.virtualbank.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.testtask.vadim.virtualbank.R;
import com.testtask.vadim.virtualbank.common.AppConstants;
import com.testtask.vadim.virtualbank.common.Messages;
import com.testtask.vadim.virtualbank.database.DbHelper;
import com.testtask.vadim.virtualbank.pojo.LoginData;
import com.testtask.vadim.virtualbank.pojo.User;
import com.testtask.vadim.virtualbank.presenter.LoginActivityPresenter;
import com.testtask.vadim.virtualbank.ui.activityInterface.ILoginActivity;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements ILoginActivity, View.OnClickListener {

    @BindView(R.id.et_login_email)
    EditText etEmail;

    @BindView(R.id.et_login_password)
    EditText etPassword;

    @BindView(R.id.login_email_container)
    TextInputLayout containerEmail;

    @BindView(R.id.login_password_container)
    TextInputLayout containerPassword;

    @BindView(R.id.btn_login_login)
    Button btnLogin;

    @BindView(R.id.tv_login_signup)
    TextView tvGoSignup;

    private LoginActivityPresenter presenter;
    private boolean doubleBackToExitPressedOnce;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);
        presenter = new LoginActivityPresenter(this);

        //// Need to replace by Dagger
        DbHelper dbHelper = new DbHelper(this);
        presenter.attachDbHelper(dbHelper);
        ////

        initListeners();
        Objects.requireNonNull(getSupportActionBar()).hide();
    }

    private void initListeners() {
        btnLogin.setOnClickListener(this);
        tvGoSignup.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login_login:
                presenter.loginClick();
                break;
            case R.id.tv_login_signup:
                startActivityForResult(new Intent(this, SignupActivity.class), AppConstants.REGISTRATION_REQUEST);
                break;
        }
    }

    @Override
    public LoginData getLoginData() {
        return new LoginData(etEmail.getText().toString(), etPassword.getText().toString());
    }

    @Override
    public void setEmailError(String message) {
        containerEmail.setError(message);
    }

    @Override
    public void setPasswordError(String message) {
        containerPassword.setError(message);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void loginComplete(int userId) {
        Intent intent = new Intent(this, CardsActivity.class);
        intent.putExtra(AppConstants.USER_ID_EXTRA, userId);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK && requestCode == AppConstants.REGISTRATION_REQUEST) {
            String email = data.getStringExtra(AppConstants.EMAIL_REQUEST);
            etEmail.setText(email);
            etPassword.setText("");
            showToast(Messages.REGISTRATION_COMPLETE);
        }
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        showToast(Messages.EXIT_HINT);

        new Handler().postDelayed(() -> doubleBackToExitPressedOnce=false, 2000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}
