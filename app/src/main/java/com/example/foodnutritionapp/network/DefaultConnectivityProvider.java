package com.example.foodnutritionapp.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

//Implementation of Connectivity Provider that queries the network status on the real device.
public class DefaultConnectivityProvider implements ConnectivityProvider {
    private final ConnectivityManager cm;

    public DefaultConnectivityProvider(Context ctx) {
        cm = (ConnectivityManager)
                ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    @Override
    public boolean isNetworkAvailable() {
        NetworkInfo info = cm.getActiveNetworkInfo();
        return info != null && info.isConnected();
    }
}
