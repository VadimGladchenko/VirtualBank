package com.testtask.vadim.virtualbank.ui.activity;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.testtask.vadim.virtualbank.R;
import com.testtask.vadim.virtualbank.common.AppConstants;
import com.testtask.vadim.virtualbank.common.Messages;
import com.testtask.vadim.virtualbank.database.DbHelper;
import com.testtask.vadim.virtualbank.pojo.Card;
import com.testtask.vadim.virtualbank.presenter.CardViewActivityPresenter;
import com.testtask.vadim.virtualbank.ui.activityInterface.ICardViewActivity;
import com.testtask.vadim.virtualbank.ui.fragmentDialog.HistoryDialog;
import com.testtask.vadim.virtualbank.ui.fragmentDialog.ReplenishCardDialog;
import com.testtask.vadim.virtualbank.ui.fragmentDialog.SendCardDialog;
import com.testtask.vadim.virtualbank.ui.fragmentDialog.WithdrawCardDialog;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CardViewActivity extends AppCompatActivity
        implements ICardViewActivity, View.OnClickListener, SendCardDialog.EventListener,
        ReplenishCardDialog.EventListener, WithdrawCardDialog.EventListener {

    @BindView(R.id.btn_card_replenish)
    LinearLayout btnCardReplenish;

    @BindView(R.id.btn_card_send)
    LinearLayout btnCardSend;

    @BindView(R.id.btn_card_withdraw)
    LinearLayout btnCardWithdraw;

    @BindView(R.id.btn_card_history)
    LinearLayout btnCardHistory;

    @BindView(R.id.tv_card_balance)
    TextView tvCardBalance;

    private CardViewActivityPresenter presenter;

    private String currentDialog;

    private SendCardDialog sendCardDialog;
    private ReplenishCardDialog replenishCardDialog;
    private WithdrawCardDialog withdrawCardDialog;
    private HistoryDialog historyDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_view);

        int cardId = getIntent().getIntExtra(AppConstants.CARD_ID, -1);

        ButterKnife.bind(this);
        presenter = new CardViewActivityPresenter(this, cardId);

        //// Need to replace by Dagger
        DbHelper dbHelper = new DbHelper(this);
        presenter.attachDbHelper(dbHelper);
        ////

        initListeners();
        initDialogs();

        Objects.requireNonNull(getSupportActionBar()).setElevation(0);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
        }
        return true;
    }

    @Override
    public boolean onNavigateUp(){
        finish();
        return true;
    }

    private void initDialogs() {
        FragmentManager fm = getFragmentManager();

        DialogFragment dialogFragmentSend = (DialogFragment) fm.findFragmentByTag(SendCardDialog.TAG);
        DialogFragment dialogFragmentReplenish = (DialogFragment) fm.findFragmentByTag(ReplenishCardDialog.TAG);
        DialogFragment dialogFragmentWithdraw = (DialogFragment) fm.findFragmentByTag(WithdrawCardDialog.TAG);
        DialogFragment dialogFragmentHistory = (DialogFragment) fm.findFragmentByTag(HistoryDialog.TAG);

        if(dialogFragmentSend != null) {
            if(dialogFragmentSend instanceof SendCardDialog) {
                sendCardDialog = (SendCardDialog) dialogFragmentSend;
                currentDialog = SendCardDialog.TAG;
            }
        } else {
            sendCardDialog = new SendCardDialog();
        }

        if(dialogFragmentReplenish != null) {
            if(dialogFragmentReplenish instanceof ReplenishCardDialog) {
                replenishCardDialog = (ReplenishCardDialog) dialogFragmentReplenish;
                currentDialog = ReplenishCardDialog.TAG;
            }
        } else {
            replenishCardDialog = new ReplenishCardDialog();
        }

        if(dialogFragmentWithdraw != null) {
            if(dialogFragmentWithdraw instanceof WithdrawCardDialog) {
                withdrawCardDialog = (WithdrawCardDialog) dialogFragmentWithdraw;
                currentDialog = WithdrawCardDialog.TAG;
            }
        } else {
            withdrawCardDialog = new WithdrawCardDialog();
        }

        if(dialogFragmentHistory != null) {
            if(dialogFragmentHistory instanceof HistoryDialog) {
                historyDialog = (HistoryDialog) dialogFragmentHistory;
                currentDialog = HistoryDialog.TAG;
            }
        } else {
            historyDialog = new HistoryDialog();
        }
    }

    private void initListeners() {
        btnCardReplenish.setOnClickListener(this);
        btnCardSend.setOnClickListener(this);
        btnCardWithdraw.setOnClickListener(this);
        btnCardHistory.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.btn_card_send:
                if(getFragmentManager().findFragmentByTag(SendCardDialog.TAG) == null) {
                    sendCardDialog.show(getFragmentManager(), SendCardDialog.TAG);
                    currentDialog = SendCardDialog.TAG;
                }
                break;
            case R.id.btn_card_replenish:
                if(getFragmentManager().findFragmentByTag(ReplenishCardDialog.TAG) == null) {
                    replenishCardDialog.show(getFragmentManager(), ReplenishCardDialog.TAG);
                    currentDialog = ReplenishCardDialog.TAG;
                }
                break;
            case R.id.btn_card_withdraw:
                if(getFragmentManager().findFragmentByTag(WithdrawCardDialog.TAG) == null) {
                    withdrawCardDialog.show(getFragmentManager(), WithdrawCardDialog.TAG);
                    currentDialog = WithdrawCardDialog.TAG;
                }
                break;
            case R.id.btn_card_history:
                if(getFragmentManager().findFragmentByTag(HistoryDialog.TAG) == null) {
                    historyDialog.show(getFragmentManager(), HistoryDialog.TAG);
                    currentDialog = HistoryDialog.TAG;
                    presenter.showHistoryClick();
                }
                break;
        }
    }

    @Override
    public void setActionBarTitle(String title) {
        Objects.requireNonNull(getSupportActionBar()).setTitle(title);
    }

    @Override
    public void setCardPinError(String message) {
        switch(currentDialog) {
            case SendCardDialog.TAG:
                sendCardDialog.setPinErrorSend(message);
                break;
            case ReplenishCardDialog.TAG:
                replenishCardDialog.setPinErrorReplenish(message);
                break;
            case WithdrawCardDialog.TAG:
                withdrawCardDialog.setPinErrorReplenish(message);
                break;
        }
    }

    @Override
    public void setRecipientError(String message) {
        sendCardDialog.setRecipientErrorSend(message);
    }

    @Override
    public void setAmountError(String message) {
        switch(currentDialog) {
            case SendCardDialog.TAG:
                sendCardDialog.setAmountErrorSend(message);
                break;
            case ReplenishCardDialog.TAG:
                replenishCardDialog.setAmountErrorReplenish(message);
                break;
            case WithdrawCardDialog.TAG:
                withdrawCardDialog.setAmountErrorReplenish(message);
                break;
        }
    }

    @Override
    public void displayCardInfo(Card card) {
        tvCardBalance.setText(getString(R.string.dollar_sign) + String.valueOf(card.getBalance()));
    }

    @Override
    public void showHistory(String message) {
        historyDialog.setHistory(message);
    }

    @Override
    public void completeSendCard() {
        sendCardDialog.dismiss();
        showToast(Messages.MONEY_HAS_SENT);
    }

    @Override
    public void completeReplenishCard() {
        replenishCardDialog.dismiss();
        showToast(Messages.CARD_HAS_REPLENISHED);
    }

    @Override
    public void completeWithdrawCard() {
        withdrawCardDialog.dismiss();
        showToast(Messages.CARD_HAS_WITHDRAWN);

    }

    @Override
    public void completeHistoryCard() {
        historyDialog.dismiss();
    }

    @Override
    public void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void replenishCardClick(String amount, String pin) {
        presenter.replenishCardClick(amount, pin);
    }

    @Override
    public void SendCardClick(String cardNumber, String amount, String pin) {
        presenter.sendCardClick(cardNumber, amount, pin);
    }

    @Override
    public void withdrawCardClick(String amount, String pin) {
        presenter.withdrawCardClick(amount, pin);
    }
}
