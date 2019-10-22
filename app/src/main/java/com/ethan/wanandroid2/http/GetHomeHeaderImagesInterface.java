package com.ethan.wanandroid2.http;

import com.ethan.wanandroid2.data.WanBean;
import com.ethan.wanandroid2.data.image.HomeImageBean;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetHomeHeaderImagesInterface {
    @GET("banner/json")
    Call<HomeImageBean> getCall();
}
