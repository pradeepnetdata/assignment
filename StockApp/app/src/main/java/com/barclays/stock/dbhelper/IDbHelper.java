package com.barclays.stock.dbhelper;

import com.barclays.stock.model.Stock;
import com.barclays.stock.model.db.StockData;

import java.util.List;
import io.reactivex.Observable;



public interface IDbHelper {

    Observable<List<StockData>> loadAllStocksInfo();
    Observable<Boolean> saveStockInfo(final Stock stock);
    Observable<Boolean> updateStockInfo(final Stock stock);
    Observable<List<StockData>> getStockByName(String symbol);
}
