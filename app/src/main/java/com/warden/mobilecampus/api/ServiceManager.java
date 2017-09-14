package com.warden.mobilecampus.api;

import com.warden.mobilecampus.util.UrlUtil;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Warden on 2017/9/14.
 */

public class ServiceManager {
    private static ServiceManager instance = null;

    public synchronized static ServiceManager getInstance() {
        return instance != null ? instance : new ServiceManager();
    }
    private OkHttpClient client = new OkHttpClient()
            .newBuilder()
            .build();
    public CareerService getCareerService() {
        return getRetrofit(UrlUtil.CARRE_BASE_URL).create(CareerService.class);
    }
    private Retrofit getRetrofit(String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit;
    }
}
