package com.testtask.vadim.virtualbank.ui.fragmentDialog;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.testtask.vadim.virtualbank.R;
import com.testtask.vadim.virtualbank.common.Messages;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WithdrawCardDialog extends DialogFragment implements View.OnClickListener {
    public static final String TAG = "WithdrawCardDialog";

    public interface EventListener {
        void withdrawCardClick(String amount, String pin);
    }

    EventListener listener;

    @BindView(R.id.et_withdraw_card_amount)
    EditText etAmount;

    @BindView(R.id.et_withdraw_card_pin)
    EditText etPin;

    @BindView(R.id.withdraw_card_amount_container)
    TextInputLayout amountContainer;

    @BindView(R.id.withdraw_card_pin_container)
    TextInputLayout withdrawPinContainer;

    @BindView(R.id.btn_withdraw_card)
    Button btnWithdraw;

    @BindView(R.id.btn_withdraw_card_cancel)
    Button btnWithdrawCancel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().setTitle(Messages.WITHDRAW_CARD);

        View view = inflater.inflate(R.layout.dialog_withdraw_card, container, false);
        ButterKnife.bind(this, view);

        btnWithdraw.setOnClickListener(this);
        btnWithdrawCancel.setOnClickListener(this);

        return view;
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (EventListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_withdraw_card:
                listener.withdrawCardClick(
                        etAmount.getText().toString(),
                        etPin.getText().toString());
                break;
            case R.id.btn_withdraw_card_cancel:
                dismiss();
                break;
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        clearFields();
    }

    public void setAmountErrorReplenish(String message) {
        amountContainer.setError(message);
    }

    public void setPinErrorReplenish(String message) {
        withdrawPinContainer.setError(message);
    }

    public void clearFields() {
        etAmount.setText("");
        etPin.setText("");
    }
}
