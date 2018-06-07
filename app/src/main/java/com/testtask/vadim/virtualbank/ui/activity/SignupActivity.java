package com.testtask.vadim.virtualbank.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.testtask.vadim.virtualbank.R;
import com.testtask.vadim.virtualbank.common.AppConstants;
import com.testtask.vadim.virtualbank.database.DbHelper;
import com.testtask.vadim.virtualbank.pojo.RegistrationData;
import com.testtask.vadim.virtualbank.presenter.SignupActivityPresenter;
import com.testtask.vadim.virtualbank.ui.activityInterface.ISignupActivity;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignupActivity extends AppCompatActivity implements ISignupActivity, View.OnClickListener {

    @BindView(R.id.et_signup_email)
    EditText etEmail;

    @BindView(R.id.et_signup_name)
    EditText etName;

    @BindView(R.id.et_signup_password)
    EditText etPassword;

    @BindView(R.id.et_signup_confirm_password)
    EditText etConfirmPassword;

    @BindView(R.id.signup_email_container)
    TextInputLayout containerEmail;

    @BindView(R.id.signup_name_container)
    TextInputLayout containerName;

    @BindView(R.id.signup_password_container)
    TextInputLayout containerPassword;

    @BindView(R.id.signup_confirm_password_container)
    TextInputLayout containerConfirmPassword;

    @BindView(R.id.btn_signup_create)
    Button btnCreateUser;

    @BindView(R.id.tv_signup_login)
    TextView tvGoLogin;

    private SignupActivityPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        ButterKnife.bind(this);
        initListeners();
        presenter = new SignupActivityPresenter(this);

        //// Need to replace by Dagger
        DbHelper dbHelper = new DbHelper(this);
        presenter.attachDbHelper(dbHelper);
        ////
        Objects.requireNonNull(getSupportActionBar()).hide();
    }

    private void initListeners() {
        btnCreateUser.setOnClickListener(this);
        tvGoLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_signup_create:
                presenter.createUserClick();
                break;
            case R.id.tv_signup_login:
                finish();
                break;
        }
    }

    @Override
    public RegistrationData getRegistrationData() {
        return new RegistrationData(
            etName.getText().toString(),
            etEmail.getText().toString(),
            etPassword.getText().toString(),
            etConfirmPassword.getText().toString()
        );
    }

    @Override
    public void setNameError(String message) {
        containerName.setError(message);
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
    public void setConfirmPasswordError(String message) {
        containerConfirmPassword.setError(message);
    }

    @Override
    public void registrationComplete() {
        Intent intent = new Intent();
        intent.putExtra(AppConstants.EMAIL_REQUEST, etEmail.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}
