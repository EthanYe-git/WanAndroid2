package com.ethan.wanandroid2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ethan.wanandroid2.R;
import com.ethan.wanandroid2.data.ArticleBean;
import com.ethan.wanandroid2.data.image.ImageBean;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {
    Context activity;
    List<ArticleBean> articles;
    List<ImageBean> images;

    final static int HEADER = 10000;
    final static int MESSAGE = 20000;

    final static int HEADER_SIZE = 2;//fragment的个数

    public HomeAdapter(Context context, List<ArticleBean> articles, List<ImageBean> list) {
        activity = context;
        this.articles = articles;
        images = list;
    }

    @NonNull
    @Override
    public HomeAdapter.HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutId;
        switch (viewType) {
            case HEADER:
                layoutId = R.layout.layout_item_header;
                break;
            case MESSAGE:
                layoutId = R.layout.layout_item_message;
                break;
            default:
                layoutId = R.layout.layout_item_article;
                break;
        }
        View item = LayoutInflater.from(activity).inflate(layoutId, parent, false);
        return new HomeViewHolder(item, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        switch (holder.viewType) {
            case HEADER:
                if (images == null) break;
                int i = 0;
                Glide.with(activity)
                        .load(images.get(i++).getImagePath())
                        .centerCrop()
                        .placeholder(R.drawable.loading_spinner)
                        .into(holder.img1);

                Glide.with(activity)
                        .load(images.get(i++).getImagePath())
                        .centerCrop()
                        .placeholder(R.drawable.loading_spinner)
                        .into(holder.img2);

                Glide.with(activity)
                        .load(images.get(i++).getImagePath())
                        .centerCrop()
                        .placeholder(R.drawable.loading_spinner)
                        .into(holder.img3);
                break;
            case MESSAGE:
                //TODO　公告消息提醒
                break;
            default:
                ArticleBean article = articles.get(getPosition(position));
                holder.title.setText(article.getTitle());
                String author = article.getAuthor().equals("") ? article.getShareUser() : article.getAuthor();
                holder.author.setText(author);
                holder.category.setText(article.getChapterName());
                holder.time.setText(article.getNiceDate());
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return HEADER;
            case 1:
                return MESSAGE;
            default:
                return super.getItemViewType(position);
        }
    }

    int getPosition(int position) {
        return position - HEADER_SIZE;
    }

    @Override
    public int getItemCount() {
        return articles.size() + 2;
    }

    class HomeViewHolder extends RecyclerView.ViewHolder {
        //ViewHolder本身属性
        int viewType;
        //首页文章显示组件
        TextView title, author, category, time;
        //首页轮播图消息推送
        ImageView img1, img2, img3;

        public HomeViewHolder(@NonNull View itemView, int viewType) {
            super(itemView);
            this.viewType = viewType;
            switch (itemView.getId()) {
                case R.id.adapter_header:
                    img1 = itemView.findViewById(R.id.header_img1);
                    img2 = itemView.findViewById(R.id.header_img2);
                    img3 = itemView.findViewById(R.id.header_img3);
                    break;
                case R.id.adapter_item:
                    title = itemView.findViewById(R.id.article_title);
                    author = itemView.findViewById(R.id.article_author);
                    category = itemView.findViewById(R.id.article_category);
                    time = itemView.findViewById(R.id.article_time);
                    break;
            }
        }
    }
}
