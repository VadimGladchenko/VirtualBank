package com.testtask.vadim.virtualbank.ui.adapter;

import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.testtask.vadim.virtualbank.pojo.Card;
import com.testtask.vadim.virtualbank.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CardsAdapter extends RecyclerView.Adapter<CardsAdapter.ViewHolder> {
    private List<Card> cardList = new ArrayList<>();
    private ItemClickListener itemClickListener;

    public CardsAdapter(ItemClickListener itemClickListener) {
        super();
        this.itemClickListener = itemClickListener;
    }

    public void setItems(Collection<Card> collection) {
        cardList.clear();
        cardList.addAll(collection);
        notifyDataSetChanged();
    }

    public void addItem(Card card) {
        cardList.add(card);
        notifyDataSetChanged();
    }

    public void clearItems() {
        cardList.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        boolean portOrientation = true;

        if(parent.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            portOrientation = false;
        }

        View view = LayoutInflater.from(parent.getContext())
                .inflate(portOrientation ? R.layout.card_item : R.layout.card_item_land, parent,false);
        return new ViewHolder(view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(cardList.get(position));
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.tv_item_card_name)
        TextView tvCardName;

        @BindView(R.id.tv_item_card_number)
        TextView tvCardNumber;

        @BindView(R.id.tv_item_card_id)
        TextView tvCardId;

        private ItemClickListener itemClickListener;

        ViewHolder(View itemView, ItemClickListener itemClickListener) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            this.itemClickListener = itemClickListener;
            itemView.setOnClickListener(this);
        }

        public void bind(Card card) {
            tvCardNumber.setText(card.getFormattedNumber());
            tvCardName.setText(card.getName());
            tvCardId.setText("#" + card.getId());
        }

        @Override
        public void onClick(View v) {
            if(itemClickListener != null)
                itemClickListener.onCardClick(cardList.get(getAdapterPosition()).getId());
        }
    }
}
