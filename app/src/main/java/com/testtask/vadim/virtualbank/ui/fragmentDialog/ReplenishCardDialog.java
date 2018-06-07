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

public class ReplenishCardDialog extends DialogFragment implements View.OnClickListener {
    public static final String TAG = "ReplenishCardDialog";

    public interface EventListener {
        void replenishCardClick(String amount, String pin);
    }

    EventListener listener;

    @BindView(R.id.et_replenish_card_amount)
    EditText etAmount;

    @BindView(R.id.et_replenish_card_pin)
    EditText etPin;

    @BindView(R.id.replenish_card_amount_container)
    TextInputLayout amountContainer;

    @BindView(R.id.replenish_card_pin_container)
    TextInputLayout replenishPinContainer;

    @BindView(R.id.btn_replenish_card)
    Button btnReplenish;

    @BindView(R.id.btn_replenish_card_cancel)
    Button btnReplenishCancel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().setTitle(Messages.REPLENISH_CARD);

        View view = inflater.inflate(R.layout.dialog_replenish_card, container, false);
        ButterKnife.bind(this, view);

        btnReplenish.setOnClickListener(this);
        btnReplenishCancel.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_replenish_card:
                listener.replenishCardClick(
                        etAmount.getText().toString(),
                        etPin.getText().toString());
                break;
            case R.id.btn_replenish_card_cancel:
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

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        clearFields();
    }

    public void setAmountErrorReplenish(String message) {
        amountContainer.setError(message);
    }

    public void setPinErrorReplenish(String message) {
        replenishPinContainer.setError(message);
    }

    public void clearFields() {
        etAmount.setText("");
        etPin.setText("");
    }
}
