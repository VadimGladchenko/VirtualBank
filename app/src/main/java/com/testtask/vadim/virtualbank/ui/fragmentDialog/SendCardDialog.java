package com.testtask.vadim.virtualbank.ui.fragmentDialog;

import android.app.Activity;
import android.app.DialogFragment;
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

public class SendCardDialog extends DialogFragment implements View.OnClickListener {
    public static final String TAG = "SendCardDialog";

    public interface EventListener {
        void SendCardClick(String cardNumber, String amount, String pin);
    }

    EventListener listener;

    @BindView(R.id.et_send_card_recipient)
    EditText etSendRecipient;

    @BindView(R.id.send_card_recipient_container)
    TextInputLayout recipientContainer;

    @BindView(R.id.et_send_card_amount)
    EditText etAmount;

    @BindView(R.id.et_send_card_pin)
    EditText etPin;

    @BindView(R.id.send_card_amount_container)
    TextInputLayout amountContainer;

    @BindView(R.id.send_card_pin_container)
    TextInputLayout sendPinContainer;

    @BindView(R.id.btn_send_card)
    Button btnSend;

    @BindView(R.id.btn_send_card_cancel)
    Button btnSendCancel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().setTitle(Messages.SEND_CARD);

        View view = inflater.inflate(R.layout.dialog_send_card, container, false);
        ButterKnife.bind(this, view);

        btnSend.setOnClickListener(this);
        btnSendCancel.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_send_card:
                listener.SendCardClick(
                        etSendRecipient.getText().toString(),
                        etAmount.getText().toString(),
                        etPin.getText().toString());
                break;
            case R.id.btn_send_card_cancel:
                clearFields();
                dismiss();
                break;
        }
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

    public void setRecipientErrorSend(String message) {
        recipientContainer.setError(message);
    }

    public void setAmountErrorSend(String message) {
        amountContainer.setError(message);
    }

    public void setPinErrorSend(String message) {
        sendPinContainer.setError(message);
    }

    public void clearFields() {
        etSendRecipient.setText("");
        etAmount.setText("");
        etPin.setText("");
    }
}
