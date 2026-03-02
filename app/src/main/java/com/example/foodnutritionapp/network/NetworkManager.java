package com.example.foodnutritionapp.network;

import android.content.Context;
import androidx.annotation.VisibleForTesting;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkManager {
    private static NetworkManager INSTANCE;
    private final ConnectivityProvider provider;
    private UsdaAPI usdaAPI;

    private NetworkManager(Context ctx) {
        this.provider = new DefaultConnectivityProvider(ctx);
    }

    public static NetworkManager getInstance(Context ctx) {
        if (INSTANCE == null) {
            INSTANCE = new NetworkManager(ctx);
        }
        return INSTANCE;
    }

    @VisibleForTesting
    NetworkManager(ConnectivityProvider provider) {
        this.provider = provider;
    }

    public boolean isNetworkAvailable() {
        return provider.isNetworkAvailable();
    }

    // USDA API'yi döndür (YENİ)
    public UsdaAPI getUsdaApi() {
        if (usdaAPI == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(UsdaAPI.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            usdaAPI = retrofit.create(UsdaAPI.class);
        }
        return usdaAPI;
    }
}