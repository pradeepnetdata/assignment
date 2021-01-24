package com.barclays.stock.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.barclays.stock.StockApplication;
import com.barclays.stock.data.IDataManager;
import com.barclays.stock.dbhelper.AppDbHelper;
import com.barclays.stock.model.Stock;
import com.barclays.stock.model.db.StockData;
import com.barclays.stock.model.graph.Series;
import com.barclays.stock.provider.SchedulerProvider;
import com.barclays.stock.viewmodel.base.BaseViewModel;

import java.util.ArrayList;
import java.util.List;


public class StockViewModel extends BaseViewModel implements IBaseManager {

    private static final String TAG = StockViewModel.class.getName();
    private final MutableLiveData<List<StockData>> watchListLiveData;
    private final MutableLiveData<Stock> stockLiveData;
    private final MutableLiveData<Boolean> errorLiveData;

    private AppDbHelper appDbHelper;
    public StockViewModel(IDataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        watchListLiveData = new MutableLiveData<>();
        stockLiveData = new MutableLiveData<>();
        errorLiveData = new MutableLiveData<>();
        appDbHelper = new AppDbHelper(StockApplication.getInstance().getAppDatabase());
    }

    @Override
    public void loadStocks(String symbol) {
        Log.d(TAG, "loadStocks :: start");
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .getStockDetailApiCall(symbol)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .doOnSuccess(response-> setStockResult(response))
                .subscribe(response -> {
                    setIsLoading(false);

                }, throwable -> {
                    setIsLoading(false);
                    errorLiveData.setValue(true);
                    Log.d(TAG, "setStockResult :: error>>"+throwable.toString());
                }));
    }

    @Override
    public void loadStocks() {
        loadStocks("AMRN");
    }


    public void setStockResult(Stock stock){
        stockLiveData.setValue(stock);
    }

    public LiveData<Stock> observerLiveData() {
        return stockLiveData;
    }

    public LiveData<Boolean> observerErrorLiveData() {
        return errorLiveData;
    }

    public void setWatchListData(List<StockData> watchListData){
        Log.d(TAG, "setWatchListData :: called");
        watchListLiveData.setValue(watchListData);
    }
    public LiveData<List<StockData> > observerWatchListData() {
        return watchListLiveData;
    }
    @Override
    public void loadWatchList() {

        getCompositeDisposable().add(appDbHelper.loadAllStocksInfo()

                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    Log.d(TAG, "saveStockInfo :: size-- " +response);
                    setWatchListData(response);
                }, throwable -> {
                    errorLiveData.setValue(true);
                    Log.d(TAG, "saveStockInfo :: error "+throwable.toString() );
                }));
    }

    public void saveStockInfo(Stock stock) {
        Log.d(TAG, "saveStockInfo :: symbol::" +stock.getSymbol());

        getCompositeDisposable().add(appDbHelper.saveStockInfo(stock)

                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    Log.d(TAG, "saveStockInfo :: size-- " +response);

                }, throwable -> {

                    Log.d(TAG, "saveStockInfo :: error "+throwable.toString() );
                }));
    }

    public Boolean checkStocksExistOrNot(final String symbol, List<Stock> quoteList) {
       boolean stockExist = false;

       if(quoteList==null ){
           return false;
       }
        if(quoteList!=null && quoteList.size() ==0){
            return false;
        }
       for(Stock stock :quoteList){
           if(stock.getSymbol().equals(symbol)){
               stockExist = true;
           }
       }

        return stockExist;
    }

    @Override
    public void loadGraphSeriesDetails(String interval, String symbol,String range) {
        Log.d(TAG, "loadStocks :: start");
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .getGraphSeriesDetailApiCall(interval,symbol,range)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .doOnSuccess(response-> setStockResult(response))
                .subscribe(response -> {
                    setIsLoading(false);

                }, throwable -> {
                    setIsLoading(false);
                    errorLiveData.setValue(true);
                    Log.d(TAG, "setStockResult :: error>>"+throwable.toString());
                }));
    }

    public List<Series> getSeries(){

        List<Series> seriesList = new ArrayList<>();
        Series series1 = new Series();
        series1.setDate("2020-10-01");
        series1.setClose(10.30f);
        seriesList.add(series1);

        Series series2 = new Series();
        series2.setDate("2020-10-10");
        series2.setClose(04.30f);
        seriesList.add(series2);

        Series series3 = new Series();
        series3.setDate("2020-10-20");
        series3.setClose(22.30f);
        seriesList.add(series3);

        Series series4 = new Series();
        series4.setDate("2020-10-30");
        series4.setClose(13.30f);
        seriesList.add(series4);

        return seriesList;
    }
}
