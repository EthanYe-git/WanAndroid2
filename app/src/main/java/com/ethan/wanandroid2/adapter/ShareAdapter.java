package com.ethan.wanandroid2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ethan.wanandroid2.R;
import com.ethan.wanandroid2.data.SquareMessageBean;

import java.util.List;

public class ShareAdapter extends RecyclerView.Adapter<ShareAdapter.ShareViewHolder> {
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

