package com.testtask.vadim.virtualbank.ui.fragmentDialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.testtask.vadim.virtualbank.R;
import com.testtask.vadim.virtualbank.common.Messages;
import com.testtask.vadim.virtualbank.presenter.CardViewActivityPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoryDialog extends DialogFragment implements View.OnClickListener {
    public static final String TAG = "HistoryDialog";

    @BindView(R.id.tv_history_dialog)
    TextView tvHistory;

    @BindView(R.id.btn_history_close)
    Button btnHistoryClose;

    boolean queueData = false;
    String historyData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().setTitle(Messages.HISTORY_CARD);

        View view = inflater.inflate(R.layout.dialog_history_card, container, false);
        ButterKnife.bind(this, view);

        btnHistoryClose.setOnClickListener(this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(queueData) {
            tvHistory.setText(historyData);
            queueData = false;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("history", tvHistory.getText().toString());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState != null) {
            queueData = true;
            historyData = savedInstanceState.getString("history");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_history_close:
                dismiss();
                break;
        }
    }

    public void setHistory(String message) {
            queueData = true;
            historyData = message;
    }
}
