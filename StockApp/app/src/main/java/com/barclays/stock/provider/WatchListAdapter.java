package com.barclays.stock.provider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.barclays.stock.R;
import com.barclays.stock.model.db.StockData;

import java.util.ArrayList;
import java.util.List;


public class WatchListAdapter extends RecyclerView.Adapter<WatchListAdapter.StockViewHolder>{

    private List<StockData> mQuoteList;
    private ItemClickListener mClickListener;
    private Boolean changeInPercent = false;
    public WatchListAdapter() {
        mQuoteList = new ArrayList<>();
    }

    @Override
    public StockViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_stock, parent, false);
        return new StockViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final StockViewHolder holder, int position) {
        final StockData quote = mQuoteList.get(position);

        holder.tv_stock_symbol.setText(quote.symbol);
        holder.tv_bid_price.setText(quote.price+ " " +quote.currencySymbol);

        if (changeInPercent) {
            holder.tv_change.setText(quote.percent);
        } else {
            holder.tv_change.setText(String.valueOf(quote.percent));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mClickListener != null) mClickListener.onItemClick(view, position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mQuoteList.size();
    }



    public void setStock(StockData quote) {
        mQuoteList.add(quote);
        notifyDataSetChanged();
    }

    public List<StockData> getStocks() {
        return mQuoteList;
    }

    public String getStockSymbol(int position) {
        return mQuoteList.get(position).symbol;
    }

    public void setStocks(List<StockData> quotes) {
        mQuoteList = quotes;
    }


    public void setChangeInPercent(Boolean changeInPercent) {
        this.changeInPercent = changeInPercent;
        notifyDataSetChanged();
    }

    public interface DismissAndOnClickItemStockListener {
        void onStockDismiss(String symbol);
        void onItemClick(String symbol);
    }

    class StockViewHolder extends RecyclerView.ViewHolder  {

        TextView tv_stock_symbol;
        TextView tv_bid_price;
        TextView tv_change;

        public StockViewHolder(View itemView) {
            super(itemView);

            tv_stock_symbol = (TextView)itemView.findViewById(R.id.stock_symbol);
            tv_bid_price = (TextView)itemView.findViewById(R.id.bid_price);
            tv_change = (TextView)itemView.findViewById(R.id.change);

        }

    }
    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
