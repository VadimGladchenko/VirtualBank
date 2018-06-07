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

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateCardDialog extends DialogFragment implements View.OnClickListener {
    public static final String TAG = "CreateCardDialog";

    public interface EventListener {
        void createCardClick(String name, String pin);
    }

    EventListener listener;

    @BindView(R.id.et_create_card_name)
    EditText etCardName;

    @BindView(R.id.et_create_card_pin)
    EditText etPin;

    @BindView(R.id.create_card_name_container)
    TextInputLayout cardNameContainer;

    @BindView(R.id.create_card_pin_container)
    TextInputLayout cardPinContainer;

    @BindView(R.id.btn_create_card)
    Button btnCreateCard;

    @BindView(R.id.btn_create_card_cancel)
    Button btnCreateCardCancel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().setTitle("Create new card");

        View view = inflater.inflate(R.layout.dialog_create_card, container, false);
        ButterKnife.bind(this, view);

        btnCreateCard.setOnClickListener(this);
        btnCreateCardCancel.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_create_card:
                listener.createCardClick(
                        etCardName.getText().toString(),
                        etPin.getText().toString());
                break;
            case R.id.btn_create_card_cancel:
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

    private void clearFields() {
        etCardName.setText("");
        etPin.setText("");
    }

    public void setNameError(String message) {
        cardNameContainer.setError(message);
    }

    public void setPinError(String message) {
        cardPinContainer.setError(message);
    }
}


