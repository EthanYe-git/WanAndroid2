package com.ethan.wanandroid2.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ethan.wanandroid2.R;
import com.ethan.wanandroid2.adapter.HomeAdapter;
import com.ethan.wanandroid2.data.ArticleListBean;
import com.ethan.wanandroid2.data.WanBean;
import com.ethan.wanandroid2.data.image.HomeImageBean;
import com.ethan.wanandroid2.data.image.ImageBean;
import com.ethan.wanandroid2.data.util.FragmentJsonBeanUtil;
import com.ethan.wanandroid2.http.HttpUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 应用主页Activity显示
 * 首页Fragment，用于显示首页相关的文章信息
 *
 * @author Ethan Ye
 */
public class HomeFragment extends Fragment {
    public static final String TAG = "主页 Fragment：";
    public static final boolean DEBUG = true;

    private HomeViewModel homeViewModel;
    private RecyclerView mList;//显示首页文章列表
    private Context context;
    //主页显示数据，通过WanAndroid获取的Javabean类
    private ArticleListBean homeData;
    List<ImageBean> headerImages;

    public static HomeFragment newInstance() {

        Bundle args = new Bundle();

        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        context = getActivity();

        initLayoutView(root);
        return root;
    }

    void initLayoutView(View root) {

        mList = root.findViewById(R.id.wan_android_home);
        mList.setLayoutManager(new LinearLayoutManager(context));
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (headerImages == null) {
            if (FragmentJsonBeanUtil.getImage(FragmentJsonBeanUtil.IMAGE_HEADER_HOME) != null){
                headerImages = FragmentJsonBeanUtil.getImage(FragmentJsonBeanUtil.IMAGE_HEADER_HOME);
            }else {
                initHeaderImage();
            }
        }

        if (homeData == null) {
            homeData = (ArticleListBean) FragmentJsonBeanUtil.get(FragmentJsonBeanUtil.FRAGMENT_HOME);
            if (homeData == null) {
                initHomeData();
            } else {
                initAdapter();
            }
        }
    }

    //加载首页
    void initHeaderImage() {
        Call<HomeImageBean> homeImageBeanCall = HttpUtil.doHomeHeaderImage();
        homeImageBeanCall.enqueue(new Callback<HomeImageBean>() {
            @Override
            public void onResponse(Call<HomeImageBean> call, Response<HomeImageBean> response) {
                headerImages = response.body().getData();
                FragmentJsonBeanUtil.putImage(FragmentJsonBeanUtil.IMAGE_HEADER_HOME, headerImages);
                if (DEBUG){
                    Log.d(TAG, "网络连接成功，获取首页banner数据");
                }
            }

            @Override
            public void onFailure(Call<HomeImageBean> call, Throwable t) {
                //网络连接失败
                Log.d(TAG, "网络连接失败");
            }
        });
    }

    //加载主页文章列表数据
    void initHomeData() {
        Call<WanBean> homeJsonBean = HttpUtil.doHomeConnect();
        homeJsonBean.enqueue(new Callback<WanBean>() {
            @Override
            public void onResponse(Call<WanBean> call, Response<WanBean> response) {
                //网络连接成功
                homeData = response.body().getData();
                FragmentJsonBeanUtil.put(FragmentJsonBeanUtil.FRAGMENT_HOME, homeData);
                if (DEBUG) {
                    Log.d(TAG, "homeData存入FragmentUtil中");
                    Log.d(TAG, "网络连接成功，首页文章数：" + homeData.getPageCount());
                }
                initAdapter();
            }

            @Override
            public void onFailure(Call<WanBean> call, Throwable t) {
                //网络连接失败
                Log.d(TAG, "网络连接失败");
            }
        });
    }

    //加载首页Recycler布局
    void initAdapter() {
        HomeAdapter adapter = new HomeAdapter(context, homeData.getDatas(), headerImages);
        mList.setAdapter(adapter);
    }
}