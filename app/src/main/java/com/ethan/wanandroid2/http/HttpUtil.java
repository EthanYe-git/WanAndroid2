package com.ethan.wanandroid2.http;

import com.ethan.wanandroid2.data.SquareBean;
import com.ethan.wanandroid2.data.WanBean;
import com.ethan.wanandroid2.data.image.HomeImageBean;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 封装Retrofit框架链接网络
 *
 * @author Ethan Ye
 */
public class HttpUtil {
    public static final String WAN_ANDROID_URL = "https://www.wanandroid.com/";

    private static Retrofit getRetrofit(){
        return new Retrofit.Builder()
                .baseUrl(WAN_ANDROID_URL) // 设置网络请求的公共Url地址
                .addConverterFactory(GsonConverterFactory.create()) // 设置数据解析器
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()) // 支持RxJava平台
                .build();
    }

    public static Call<WanBean> doHomeConnect() {
        GetHmeArticlesInterface homeArticlesInterface = getRetrofit().create(GetHmeArticlesInterface.class);
        return homeArticlesInterface.getCall();
    }

    public static Call<SquareBean> doSquareConnect(){
        GetSquareArticlesInterface squareArticlesInterface = getRetrofit().create(GetSquareArticlesInterface.class);
        return squareArticlesInterface.getCall();
    }

    public static Call<HomeImageBean> doHomeHeaderImage(){
        GetHomeHeaderImagesInterface imageInterface = getRetrofit().create(GetHomeHeaderImagesInterface.class);
        return imageInterface.getCall();
    }
}
