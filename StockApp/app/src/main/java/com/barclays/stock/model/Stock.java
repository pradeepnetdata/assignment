package com.barclays.stock.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Stock implements Serializable {

    @Expose
    @SerializedName("price")
    public Price price ;

    public static class Price {

        @Expose
        @SerializedName("shortName")
        public String shortName ;

        @Expose
        @SerializedName("currency")
        public String currency ;

        @Expose
        @SerializedName("symbol")
        public String symbol ;
        @Expose
        @SerializedName("regularMarketTime")
        public String regularMarketTime ;

        @Expose
        @SerializedName("currencySymbol")
        public String currencySymbol;

        @Expose
        @SerializedName("regularMarketOpen")
        public RegularMarketOpen regularMarketOpen;

        @Expose
        @SerializedName("regularMarketChangePercent")
        public RegularMarketChangePercent regularMarketChangePercent;

    }
    public static class RegularMarketOpen {
        @Expose
        @SerializedName("raw")
        public String price;
    }
    public static class RegularMarketChangePercent {
        @Expose
        @SerializedName("fmt")
        public String percent;
    }
    public String getCurrency(){
        return price.currency;
    }
    public String getShortName(){
        return price.shortName;
    }
    public String getSymbol(){
        return price.symbol;
    }
    public String getRegularMarketTime(){
        return price.regularMarketTime;
    }
    public String getCurrencySymbol(){
        return price.currencySymbol;
    }
    public String getRegularMarketPrice(){
        return price.regularMarketOpen.price;
    }
    public String getRegularMarketPercent(){
        return price.regularMarketChangePercent.percent;
    }


}
