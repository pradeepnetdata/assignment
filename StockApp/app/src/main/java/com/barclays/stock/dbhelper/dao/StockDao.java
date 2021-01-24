package com.barclays.stock.dbhelper.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.barclays.stock.model.db.StockData;
import java.util.List;
import io.reactivex.Single;


@Dao
public interface StockDao {

    @Delete
    void update(StockData stock);

    @Delete
    void delete(StockData stock);

    @Query("SELECT * FROM stocks WHERE symbol LIKE :symbol")
    Single<List<StockData>> findStockByName(String symbol);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(StockData stock);


    @Query("SELECT * FROM stocks")
    Single<List<StockData>> loadAll();

}
