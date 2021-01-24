package com.barclays.stock.model.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "stocks")
public class StockData {

    @ColumnInfo(name = "created_at")
    public String createdAt;

    @PrimaryKey(autoGenerate = true)
    public Long id;

    @ColumnInfo(name = "symbol")
    public String symbol;

    @ColumnInfo(name = "shortName")
    public String shortName;

    @ColumnInfo(name = "currency")
    public String currency;

    @ColumnInfo(name = "regularMarketTime")
    public String regularTime;

    @ColumnInfo(name = "currencySymbol")
    public String currencySymbol;

    @ColumnInfo(name = "price")
    public String price;

    @ColumnInfo(name = "percent")
    public String percent;

}
