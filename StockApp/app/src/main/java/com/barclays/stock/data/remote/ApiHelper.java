package com.barclays.stock.data.remote;

import android.util.Log;


import com.barclays.stock.BuildConfig;
import com.barclays.stock.data.IDataManager;
import com.barclays.stock.model.Stock;
import com.barclays.stock.remote.ApiEndPoint;
import com.rx2androidnetworking.Rx2AndroidNetworking;
import io.reactivex.Single;


public class ApiHelper implements IDataManager {

    private static final String TAG = ApiHelper.class.getName();


    @Override
    public Single<Stock> getStockDetailApiCall(String symbol) {
        Log.i(TAG, "getStockDetailApiCall :: called ");
        return Rx2AndroidNetworking.get(ApiEndPoint.OPEN_STOCK_API+"get-summary")
                .addQueryParameter("symbol", symbol)
                .addQueryParameter("region","US")
                .addHeaders("x-rapidapi-key", BuildConfig.API_KEY)
                .addHeaders("x-rapidapi-host", "apidojo-yahoo-finance-v1.p.rapidapi.com")
                .build()
                .getObjectSingle(Stock.class);

    }

    @Override
    public Single<Stock> getGraphSeriesDetailApiCall(String interval, String symbol, String range) {
        Log.i(TAG, "getStockDetailApiCall :: called ");

        return Rx2AndroidNetworking.get(ApiEndPoint.OPEN_STOCK_API+"get-chart")
                .addQueryParameter("interval", interval)
                .addQueryParameter("symbol", symbol)
                .addQueryParameter("range", range)
                .addQueryParameter("region","US")
                .addHeaders("x-rapidapi-key", BuildConfig.API_KEY)
                .addHeaders("x-rapidapi-host", "apidojo-yahoo-finance-v1.p.rapidapi.com")
                .build()
                .getObjectSingle(Stock.class);
    }

}
