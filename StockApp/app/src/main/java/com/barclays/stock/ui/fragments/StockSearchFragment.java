package com.barclays.stock.ui.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.barclays.stock.R;
import com.barclays.stock.StockApplication;
import com.barclays.stock.graph.StockGraphFragment;
import com.barclays.stock.model.Stock;
import com.barclays.stock.provider.StockAdapter;
import com.barclays.stock.ui.StockActivity;
import com.barclays.stock.utils.AppUtils;
import com.barclays.stock.viewmodel.ViewModelProviderFactory;
import com.barclays.stock.viewmodel.StockViewModel;
import com.barclays.stock.viewmodel.base.BaseViewModel;

import org.jetbrains.annotations.Nullable;


public class StockSearchFragment extends BaseFragment implements StockAdapter.ItemClickListener{

  private static final String TAG= StockSearchFragment.class.getName();
  private StockAdapter mStockAdapter;
  private RecyclerView recyclerView;
  private TextView textViewMessage;
  private ViewModelProviderFactory factory;
  private StockViewModel stockViewModel;
  private StockActivity stockActivity;

  public static StockSearchFragment newInstance() {
    StockSearchFragment fragment = new StockSearchFragment();
    return fragment;
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
    subscribeStockData();
    subscribeErrorData();
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    Log.i(TAG, "onViewCreated() :: called");
    recyclerView = view.findViewById(R.id.stock_list);
    textViewMessage = view.findViewById(R.id.message);
    stockActivity.showSearchMenu();
    setUp();
    loadStocksInfo();

  }
  private void setUp() {
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    recyclerView.setItemAnimator(new DefaultItemAnimator());
    mStockAdapter = new StockAdapter();
    recyclerView.setAdapter(mStockAdapter);
    mStockAdapter.setClickListener(this::onItemClick);

  }


  public void hideTextMessage() {
    if (textViewMessage.getVisibility() == View.VISIBLE) {
      // Its visible
      textViewMessage.setVisibility(View.GONE);
    }
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
  }


  @Override
  public void onItemClick(View view, int position) {
    Log.i(TAG, "onItemClick ::  called>>>> " );
    showAlertDialogButtonClicked(view,position);

  }
  private void subscribeStockData() {

    stockViewModel.observerLiveData().observe(this, new Observer<Stock>() {
      @Override
      public void onChanged(Stock stock) {
        Log.i(TAG, "subscribeToLiveData ::  start " );
        stockActivity.hideLoading();
        mStockAdapter.setStock(stock);
      }

    });
  }

  private void subscribeErrorData() {
    stockViewModel.observerErrorLiveData().observe(this, aBoolean -> {
      stockActivity.hideLoading();
      Toast.makeText(getContext(),"Error in loading the data",Toast.LENGTH_LONG).show();
    });

  }
  public void loadStocksInfo(final String symbol){
     hideKeyboard();
     hideTextMessage();
     boolean stockExist =stockViewModel.checkStocksExistOrNot(symbol,mStockAdapter.getStocks());
     if(stockExist){
       Toast.makeText(getContext(),"Stock is already there",Toast.LENGTH_LONG).show();
     }else {
        stockViewModel.loadStocks(symbol);
     }
  }

  private void loadStocksInfo(){
    Log.i(TAG, "loadStocksInfo ::  called " );
    hideTextMessage();
    stockActivity.showLoading();
    stockViewModel.loadStocks();
  }
  public void showAlertDialogButtonClicked(View view, int position) {
    // setup the alert builder
    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
    builder.setTitle("");
    builder.setMessage("Would you like to add in watch list?");
    // add the buttons
    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int id) {
        Stock stock = mStockAdapter.getStocks().get(position);
        stockViewModel.saveStockInfo(stock);
      }

      });
    builder.setNegativeButton("Cancel", null);
    // create and show the alert dialog
    AlertDialog dialog = builder.create();
    dialog.show();
  }
}
