package com.barclays.stock.dbhelper;

import android.util.Log;

import com.barclays.stock.model.Stock;
import com.barclays.stock.model.db.StockData;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;


public class AppDbHelper implements IDbHelper {

    private final AppDatabase mAppDatabase;

    public AppDbHelper(AppDatabase appDatabase) {
        this.mAppDatabase = appDatabase;
    }


    @Override
    public Observable<List<StockData>> loadAllStocksInfo() {
        Log.i("TAG", "loadAllStocksInfo >> ");
        return mAppDatabase.stockDb().loadAll().toObservable();
    }

    @Override
    public Observable<Boolean> saveStockInfo(Stock stock) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() {
                Log.i("TAG", "saveStockInfo :: start::");
                StockData stockData= syncLocalRepositoryStock(stock);
                mAppDatabase.stockDb().insert(stockData);
                return true;
            }
        });
    }

    @Override
    public Observable<Boolean> updateStockInfo(Stock stock) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() {
                Log.i("TAG", "updateStockInfo :: called");
                StockData stockData= syncLocalRepositoryStock(stock);
                mAppDatabase.stockDb().update(stockData);
                return true;
            }
        });
    }

    @Override
    public Observable<List<StockData>> getStockByName(String symbol) {
        Log.i("TAG", "getStockInfoByName: called ");
        return mAppDatabase.stockDb().findStockByName(symbol).toObservable();
    }

    public StockData syncLocalRepositoryStock(Stock stock) {
        StockData stockData = new StockData();
        stockData.currency = stock.getCurrency();
        stockData.currencySymbol = stock.getCurrencySymbol();
        stockData.percent = stock.getRegularMarketPercent();
        stockData.price = stock.getRegularMarketPrice();
        stockData.shortName = stock.getShortName();
        stockData.symbol = stock.getSymbol();
        stockData.regularTime = stock.getRegularMarketTime();
        stockData.createdAt= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        return stockData;

    }
}