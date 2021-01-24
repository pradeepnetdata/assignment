package com.barclays.stock.viewmodel;


public interface IBaseManager {

    void loadWatchList();
    void loadStocks(String symbol);
    void loadStocks();
    void loadGraphSeriesDetails(String interval, String symbol,String range);
}
