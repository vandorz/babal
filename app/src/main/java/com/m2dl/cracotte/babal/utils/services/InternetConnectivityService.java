package com.m2dl.cracotte.babal.utils.services;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;

public class InternetConnectivityService {

    public static boolean isInternetReachable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean isWifiConnected = false;
        boolean isMobileConnected = false;
        boolean isInternetReachable = false;
        for (Network network : connectivityManager.getAllNetworks()) {
            NetworkInfo networkInfo = connectivityManager.getNetworkInfo(network);
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                isWifiConnected = networkInfo.isConnected();
            }
            if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                isMobileConnected = networkInfo.isConnected();
            }
            if (isWifiConnected || isMobileConnected) {
                NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(network);
                if (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)) {
                    isInternetReachable = true;
                }
            }
        }
        return isInternetReachable ;
    }
}
