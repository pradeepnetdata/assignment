package com.barclays.stock.data;

import android.content.Context;
import com.barclays.stock.model.Stock;
import io.reactivex.Single;


public class AppDataManager implements IDataManager {

    private static final String TAG = "AppDataManager";
    private final IDataManager mApiHelper;
    private final Context mContext;


    public AppDataManager(Context context, IDataManager apiHelper) {
        mContext = context;
        mApiHelper = apiHelper;
    }

    @Override
    public Single<Stock> getStockDetailApiCall(String symbol) {
        return mApiHelper.getStockDetailApiCall(symbol);
    }

    @Override
    public Single<Stock> getGraphSeriesDetailApiCall(String interval, String symbol, String range) {
        return mApiHelper.getGraphSeriesDetailApiCall(interval,symbol,range);
    }
}
