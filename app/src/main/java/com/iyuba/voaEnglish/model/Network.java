package com.iyuba.voaEnglish.model;

import com.iyuba.voaEnglish.model.networkCertificates.Constant;
import com.iyuba.voaEnglish.model.networkCertificates.SSLSocketFactoryUtils;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Network {

    private static Network mInstance;

    private static Retrofit retrofit;

    private static Retrofit retrofitDev;

    private static volatile ApiServer request = null;

    private static volatile DevServer devServer = null;


    //单例模式，固定写法
    public static Network getInstance() {
        if (mInstance == null) {
            synchronized (Network.class) {
                if (mInstance == null) {
                    mInstance = new Network();
                }
            }
        }
        return mInstance;
    }

    //初始化对象和参数
    public void init() {
        //初始化okhttp
        OkHttpClient client = new OkHttpClient.Builder()
                .sslSocketFactory(SSLSocketFactoryUtils.createSSLSocketFactory(), SSLSocketFactoryUtils.createTrustAllManager())
                .build();

        //初始化Retorfit
        retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(Constant.API_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static ApiServer getRequest() {
        if(request == null){
            synchronized (ApiServer.class) {
                request = retrofit.create(ApiServer.class);
            }
        }
        return request;
    }

    public void initDev() {
        // 初始化okhttp
        OkHttpClient client = new OkHttpClient.Builder()
                .sslSocketFactory(SSLSocketFactoryUtils.createSSLSocketFactory(), SSLSocketFactoryUtils.createTrustAllManager())
                .build();

        // 初始化Retrofit
        retrofitDev = new Retrofit.Builder()
                .client(client)
                .baseUrl("http://dev.qomolama.cn")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static DevServer getRequestForDev() {

        if (devServer == null) {
            synchronized (DevServer.class) {
                devServer = retrofitDev.create(DevServer.class);
            }
        }
        return devServer;
    }


}
