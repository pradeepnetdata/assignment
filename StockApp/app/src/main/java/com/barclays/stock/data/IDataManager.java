package com.barclays.stock.data;


import com.barclays.stock.model.Stock;

import io.reactivex.Single;


public interface IDataManager {

    Single<Stock> getStockDetailApiCall(String symbol);
    Single<Stock> getGraphSeriesDetailApiCall(String interval, String symbol,String range);
}
