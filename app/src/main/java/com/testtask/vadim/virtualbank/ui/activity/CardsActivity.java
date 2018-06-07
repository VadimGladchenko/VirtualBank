package com.testtask.vadim.virtualbank.ui.activity;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.testtask.vadim.virtualbank.R;
import com.testtask.vadim.virtualbank.common.AppConstants;
import com.testtask.vadim.virtualbank.common.Messages;
import com.testtask.vadim.virtualbank.database.DbHelper;
import com.testtask.vadim.virtualbank.pojo.Card;
import com.testtask.vadim.virtualbank.presenter.CardsActivityPresenter;
import com.testtask.vadim.virtualbank.ui.activityInterface.ICardsActivity;
import com.testtask.vadim.virtualbank.ui.adapter.CardsAdapter;
import com.testtask.vadim.virtualbank.ui.fragmentDialog.CreateCardDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CardsActivity extends AppCompatActivity implements ICardsActivity, CreateCardDialog.EventListener {

    @BindView(R.id.recycle_view_cards)
    RecyclerView recyclerView;

    @BindView(R.id.fab_cards)
    FloatingActionButton fab;

    private CardsActivityPresenter presenter;
    private CardsAdapter adapter;
    private CreateCardDialog createCardDialog;

    private boolean doubleBackToExitPressedOnce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cards);

        Intent intent = getIntent();
        int userId = intent.getIntExtra(AppConstants.USER_ID_EXTRA, -1);

        ButterKnife.bind(this);
        presenter = new CardsActivityPresenter(this, userId);

        initRecycleView();
        initListeners();

        //// Need to replace by Dagger
        DbHelper dbHelper = new DbHelper(this);
        presenter.attachDbHelper(dbHelper);
        ////

        initDialogs();
    }

    private void initRecycleView() {
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        } else {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }

        adapter = new CardsAdapter(presenter);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && fab.getVisibility() == View.VISIBLE) {
                    fab.hide();
                } else if (dy < 0 && fab.getVisibility() != View.VISIBLE) {
                    fab.show();
                }
            }
        });
    }

    private void initListeners() {
        fab.setOnClickListener((View view) -> openCreateCardDialog());
    }

    private void initDialogs() {
        FragmentManager fm = getFragmentManager();

        DialogFragment dialogFragment = (DialogFragment) fm.findFragmentByTag(CreateCardDialog.TAG);

        if(dialogFragment != null) {
            if(dialogFragment instanceof CreateCardDialog) {
                createCardDialog = (CreateCardDialog) dialogFragment;
            }
        } else {
            createCardDialog = new CreateCardDialog();
        }
    }

    @Override
    public void openCardDetails(int cardId) {
        Intent intent = new Intent(this, CardViewActivity.class);
        intent.putExtra(AppConstants.CARD_ID, cardId);
        startActivity(intent);
    }

    @Override
    public void displayCards(List<Card> carsList) {
        adapter.setItems(carsList);
    }

    @Override
    public void openCreateCardDialog() {
        createCardDialog.show(getFragmentManager(), CreateCardDialog.TAG);
    }

    @Override
    public void completeCreatingCard() {
        createCardDialog.dismiss();
        showToast(Messages.NEW_CARD_HAS_ADDED);
    }

    @Override
    public void setCardNameError(String message) {
        createCardDialog.setNameError(message);
    }

    @Override
    public void setCardPinError(String message) {
        createCardDialog.setPinError(message);
    }

    @Override
    public void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    public void createCardClick(String name, String pin) {
        presenter.addNewCardClick(name, pin);
    }
}
