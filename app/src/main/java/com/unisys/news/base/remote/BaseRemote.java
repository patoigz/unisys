package com.unisys.news.base.remote;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Patricio Iván Garcia <patriciogarcia1988@gmail.com> on 14/05/2017.
 */
public class BaseRemote {

    protected <T> T create(Class<T> _class, String baseUrl) {
        T service = retrofit(baseUrl).create(_class);
        return service;
    }

    private Retrofit retrofit(String baseUrl) {
        OkHttpClient okHttpClient = new OkHttpClient()
                .newBuilder()
                .addInterceptor(chain -> {
                    Request original = chain.request();
                    HttpUrl originalHttpUrl = original.url();
                    HttpUrl url = originalHttpUrl.newBuilder().addQueryParameter("apiKey", RemoteConfiguration.API_KEY).build();

                    Log.d("Retrofit", url.toString());

                    Request.Builder requestBuilder = original.newBuilder().url(url);
                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                })
                .build();

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson customGson = gsonBuilder.create();

        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(customGson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

}
