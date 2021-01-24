package com.barclays.stock.provider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.barclays.stock.R;
import com.barclays.stock.model.Stock;

import java.util.ArrayList;
import java.util.List;


public class StockAdapter extends RecyclerView.Adapter<StockAdapter.StockViewHolder>{

    private List<Stock> mQuoteList;
    private ItemClickListener mClickListener;
    private Boolean changeInPercent = false;
    public StockAdapter() {
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
        final Stock quote = mQuoteList.get(position);

        holder.tv_stock_symbol.setText(quote.getSymbol());
        holder.tv_bid_price.setText(quote.getRegularMarketPrice()+ " " +quote.getCurrency());

        if (changeInPercent) {
            holder.tv_change.setText(quote.getRegularMarketPercent());
        } else {
            holder.tv_change.setText(String.valueOf(quote.getRegularMarketPercent()));
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



    public void setStock(Stock quote) {
        mQuoteList.add(quote);
        notifyDataSetChanged();
    }

    public List<Stock> getStocks() {
        return mQuoteList;
    }

    public String getStockSymbol(int position) {
        return mQuoteList.get(position).getSymbol();
    }

    public void setStocks(List<Stock> quotes) {
        mQuoteList = quotes;
    }


    public void setChangeInPercent(Boolean changeInPercent) {
        this.changeInPercent = changeInPercent;
        notifyDataSetChanged();
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
