package com.barclays.stock.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.barclays.stock.R;
import com.barclays.stock.StockApplication;
import com.barclays.stock.model.db.StockData;
import com.barclays.stock.provider.WatchListAdapter;
import com.barclays.stock.ui.StockActivity;
import com.barclays.stock.viewmodel.StockViewModel;
import com.barclays.stock.viewmodel.ViewModelProviderFactory;
import com.barclays.stock.viewmodel.base.BaseViewModel;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WatchListFragment extends BaseFragment {
    private static final String TAG = WatchListFragment.class.getName();

    private WatchListAdapter mStockAdapter;
    private RecyclerView recyclerView;
    private TextView textViewMessage;
    private ViewModelProviderFactory factory;
    private StockViewModel stockViewModel;

    private StockActivity stockActivity;

    public static WatchListFragment newInstance() {
        WatchListFragment fragment = new WatchListFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof StockActivity) {
            stockActivity = (StockActivity) context;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        subscribeWatchListData();
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.stock_list);
        textViewMessage = view.findViewById(R.id.message);
        stockActivity.showSearchMenu();
        setUp();
        loadWatchList();

    }
    private void setUp() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mStockAdapter = new WatchListAdapter();
        recyclerView.setAdapter(mStockAdapter);
      //  mStockAdapter.setClickListener(this::onItemClick);

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_stock;
    }

    @Override
    public BaseViewModel getViewModel() {
        Log.d(TAG, "getViewModel :: " + ((StockApplication)getActivity().getApplicationContext()).getDataManager());
        factory = new ViewModelProviderFactory(StockApplication.getInstance().getDataManager(), StockApplication.getInstance().getSchedulerProvider());
        stockViewModel = ViewModelProviders.of(this,factory).get(StockViewModel.class);
        return stockViewModel;
    }
    private void loadWatchList(){
        Log.i(TAG, "loadWatchList ::  called " );
        hideTextMessage();
        stockActivity.showLoading();
        stockActivity.hideWatchListButton();
        stockViewModel.loadWatchList();
    }
    private void subscribeWatchListData() {
        stockViewModel.observerWatchListData().observe(this, new Observer<List<StockData>>() {
            @Override
            public void onChanged(List<StockData> stockData) {
                stockActivity.hideLoading();

                mStockAdapter.setStocks(stockData);
                mStockAdapter.notifyDataSetChanged();
            }
        });
    }
    public void hideTextMessage() {
        if (textViewMessage.getVisibility() == View.VISIBLE) {
            // Its visible
            textViewMessage.setVisibility(View.GONE);
        }
    }
}