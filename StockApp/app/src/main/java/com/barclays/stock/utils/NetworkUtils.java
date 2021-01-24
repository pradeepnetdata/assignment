package com.barclays.stock.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public final class NetworkUtils {

    private static NetworkUtils networkUtils =null;
    private NetworkUtils() {
        // This class is not publicly instantiable
    }

    public static NetworkUtils getInstance(){

        if(networkUtils==null){
            synchronized (NetworkUtils.class){

                if(networkUtils==null){
                    networkUtils = new NetworkUtils();
                }
            }
        }

        return networkUtils;
    }
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        }
        return false;
    }
}
