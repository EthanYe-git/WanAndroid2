package com.ethan.wanandroid2.ui.notifications;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ethan.wanandroid2.R;
import com.ethan.wanandroid2.data.SquareBean;
import com.ethan.wanandroid2.data.SquareMessageListBean;
import com.ethan.wanandroid2.adapter.ShareAdapter;
import com.ethan.wanandroid2.data.util.FragmentJsonBeanUtil;
import com.ethan.wanandroid2.http.HttpUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 应用主页Activity显示
 * 广场Fragment，用于显示广场相关的文章信息
 *
 * @author Ethan Ye
 */
public class NotificationsFragment extends Fragment {
    public static final String TAG = "广场 Fragment：";
    public static final boolean DEBUG = true;

    //应用于MVVM模式下的ViewModel类
    private NotificationsViewModel notificationsViewModel;
    private Context context;
    private RecyclerView mList;

    //广场数据，从WanAndroid接口获取的Javabean类
    private SquareMessageListBean squareData = null;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);

        context = getActivity();

        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        mList = root.findViewById(R.id.wan_android_square);
        mList.setLayoutManager(new LinearLayoutManager(context));

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (squareData == null){
            squareData = (SquareMessageListBean) FragmentJsonBeanUtil.get(FragmentJsonBeanUtil.FRAGMENT_SQUARE);
            if (squareData == null) {
                initSquareData();
            }else {
                initAdapter();
            }
        }
    }

    void initSquareData(){
        Call<SquareBean> squareBeanCall = HttpUtil.doSquareConnect();
        squareBeanCall.enqueue(new Callback<SquareBean>() {
            @Override
            public void onResponse(Call<SquareBean> call, Response<SquareBean> response) {
                //网络连接成功
                squareData = response.body().getData();
                FragmentJsonBeanUtil.put(FragmentJsonBeanUtil.FRAGMENT_SQUARE, squareData);
                if (DEBUG){
                    Log.d(TAG, "网络连接成功，广场信息数量："+squareData.getSize());
                }
                initAdapter();
            }

            @Override
            public void onFailure(Call<SquareBean> call, Throwable t) {
                //网络连接失败
            }
        });
    }

    void initAdapter(){
        ShareAdapter adapter = new ShareAdapter(context, squareData.getDatas());
        mList.setAdapter(adapter);
    }
}