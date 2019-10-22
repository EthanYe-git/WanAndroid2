package com.ethan.wanandroid2.http;

import com.ethan.wanandroid2.data.SquareBean;
import com.ethan.wanandroid2.data.WanBean;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * 主页接收广场分享JSON Bean类
 */
public interface GetSquareArticlesInterface {
    @GET("user_article/list/1/json")
    Call<SquareBean> getCall();
}
