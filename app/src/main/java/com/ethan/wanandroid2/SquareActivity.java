package com.ethan.wanandroid2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ethan.wanandroid2.data.ArticleBean;
import com.ethan.wanandroid2.data.ArticleListBean;
import com.ethan.wanandroid2.data.SquareBean;
import com.ethan.wanandroid2.data.SquareMessageBean;
import com.ethan.wanandroid2.data.SquareMessageListBean;
import com.ethan.wanandroid2.data.WanBean;
import com.ethan.wanandroid2.http.GetHmeArticlesInterface;
import com.ethan.wanandroid2.http.GetSquareArticlesInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class SquareActivity extends AppCompatActivity {
    public static final String TAG = "WanAndroid广场";
    public static final Boolean DEBUG = true;
    RecyclerView mView;

    public static Intent getNewIntent(Context context) {
        Intent intent = new Intent(context, SquareActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_square);
        mView = findViewById(R.id.share);
        mView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.wanandroid.com/") // 设置网络请求的公共Url地址
                .addConverterFactory(GsonConverterFactory.create()) // 设置数据解析器
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()) // 支持RxJava平台
                .build();

        GetSquareArticlesInterface squareArticlesInterface = retrofit.create(GetSquareArticlesInterface.class);
        Call<SquareBean> homeArticles = squareArticlesInterface.getCall();

        //发起异步网络请求
        homeArticles.enqueue(new Callback<SquareBean>() {
            @Override
            public void onResponse(Call<SquareBean> call, Response<SquareBean> response) {
                //执行成功,执行于UI线程
                SquareMessageListBean list = response.body().getData();
                if (DEBUG) {
                    Log.d(TAG, "Retrofot网络请求成功！广场获取到的文章数量为:" );
                }
                ShareAdapter adapter = new ShareAdapter(SquareActivity.this, list.getDatas());
                mView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<SquareBean> call, Throwable t) {
                //执行失败
                if (DEBUG) {
                    Log.d(TAG, "Retrofit链接失败，原因为：" + t.toString());
                }
            }
        });
    }

    class ShareAdapter extends RecyclerView.Adapter<ShareAdapter.ShareViewHolder> {
        Context activity;
        List<SquareMessageBean> articles;

        final static int HEADER = 10000;

        public ShareAdapter(Context context, List<SquareMessageBean> articles) {
            activity = context;
            this.articles = articles;
        }

        @NonNull
        @Override
        public ShareViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            int layoutId;
            switch (viewType) {
                case HEADER:
                    layoutId = R.layout.layout_item_header_square;
                    break;
                default:
                    layoutId = R.layout.layout_item_article_square;
                    break;
            }
            View item = LayoutInflater.from(activity).inflate(layoutId, parent, false);
            return new ShareViewHolder(item);
        }

        @Override
        public void onBindViewHolder(@NonNull ShareViewHolder holder, int position) {
            if (position == 0) {

            } else {
                SquareMessageBean article = articles.get(position - 1);
                holder.title.setText(article.getTitle());
                String author = article.getAuthor().equals("") ? article.getShareUser() : article.getAuthor();
                holder.author.setText(author);
                holder.time.setText(article.getNiceDate());
            }
        }

        @Override
        public int getItemViewType(int position) {
            switch (position) {
                case 0:
                    return HEADER;
                default:
                    return super.getItemViewType(position);
            }
        }

        @Override
        public int getItemCount() {
            return articles.size() + 1;
        }

        class ShareViewHolder extends RecyclerView.ViewHolder {
            //首页文章显示组件
            TextView title, author, time;

            public ShareViewHolder(@NonNull View itemView) {
                super(itemView);
                title = itemView.findViewById(R.id.article_title);
                author = itemView.findViewById(R.id.article_author);
                time = itemView.findViewById(R.id.article_time);
            }
        }
    }
}

