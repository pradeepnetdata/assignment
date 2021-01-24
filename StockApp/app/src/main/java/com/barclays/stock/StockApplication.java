package com.barclays.stock;

import android.app.Application;

import androidx.room.Room;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.interceptors.HttpLoggingInterceptor;
import com.barclays.stock.data.AppDataManager;
import com.barclays.stock.data.remote.ApiHelper;
import com.barclays.stock.dbhelper.AppDatabase;
import com.barclays.stock.provider.AppSchedulerProvider;
import com.barclays.stock.provider.SchedulerProvider;
import com.barclays.stock.utils.AppUtils;

public class StockApplication extends Application {

    private static StockApplication singleton;

    public static StockApplication getInstance() {
        return singleton;
    }

    private AppDataManager appDataManager;
    private ApiHelper apiHelper;
    private SchedulerProvider schedulerProvider;
    private AppDatabase appDatabase;
    @Override
    public void onCreate() {

        super.onCreate();
        singleton =this;
       AndroidNetworking.initialize(getApplicationContext());
        if (BuildConfig.DEBUG) {
            AndroidNetworking.enableLogging(HttpLoggingInterceptor.Level.BODY);
        }
        apiHelper = new ApiHelper();
        appDataManager = new AppDataManager(getApplicationContext(),apiHelper);
        schedulerProvider = new AppSchedulerProvider();
        createAppDatabase();
    }

    public AppDataManager getDataManager() {
        return appDataManager;
    }
    public SchedulerProvider getSchedulerProvider(){
        return schedulerProvider;
    }
    public AppDatabase getAppDatabase() {
        return appDatabase;
    }
    private void createAppDatabase() {
        appDatabase= Room.databaseBuilder(getApplicationContext(), AppDatabase.class, AppUtils.DB_NAME).build();
    }
}
