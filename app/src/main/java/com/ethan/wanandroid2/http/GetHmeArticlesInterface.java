package com.ethan.wanandroid2.http;

import com.ethan.wanandroid2.data.WanBean;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * 主页接收文章列表JSON Bean类
 */
public interface GetHmeArticlesInterface {
    @GET("article/list/1/json")
    Call<WanBean> getCall();
}
