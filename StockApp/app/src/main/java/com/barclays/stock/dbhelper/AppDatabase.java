package com.barclays.stock.dbhelper;

import androidx.room.Database;
import androidx.room.RoomDatabase;


import com.barclays.stock.dbhelper.dao.StockDao;
import com.barclays.stock.model.db.StockData;

@Database(entities = { StockData.class}, exportSchema = true,version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract StockDao stockDb();
}