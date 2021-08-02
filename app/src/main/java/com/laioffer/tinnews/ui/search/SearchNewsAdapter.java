package com.laioffer.tinnews.ui.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.laioffer.tinnews.R;
import com.laioffer.tinnews.databinding.SearchNewsItemBinding;
import com.laioffer.tinnews.model.Article;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SearchNewsAdapter extends RecyclerView.Adapter <SearchNewsAdapter.SearchNewsViewHolder> {

    // 1. Supporting data:

    private List<Article> articles = new ArrayList<>();


    //给 SearchFragment 的返回的结果 用
    public void setArticles(List<Article> newsList) {
        articles.clear();
        articles.addAll(newsList);
        notifyDataSetChanged();//refresh; RecyclerView.Adapter的方法
    }




    // 2. Adapter overrides:

    //创建新的 view 和 viewholder
    @NonNull
    @Override
    public SearchNewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {//viewtype?
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_news_item, parent, false);
        return new SearchNewsViewHolder(view);

    }


    // reuse 的 过程
    @Override
    public void onBindViewHolder(@NonNull SearchNewsAdapter.SearchNewsViewHolder holder, int position) {
        Article article = articles.get(position);
        holder.itemTitleTextView.setText(article.title);
        Picasso.get().load(article.urlToImage).resize(200,200).into(holder.itemImageView);


    }

    @Override
    public int getItemCount() {
        return articles.size(); //adapter知道总共的数据量：index range
    }



    // 3. 自己定义一个viewholder ：SearchNewsViewHolder:
    //It’s for holding the view references
    // 使用viewholder更高效，因为节省了findviewbyid 操作，在onCreateViewHolder一次性找好所有 child views
    public static class SearchNewsViewHolder extends RecyclerView.ViewHolder {

        //child views in the item view
        ImageView itemImageView;
        TextView itemTitleTextView;

        public SearchNewsViewHolder(@NonNull View itemView) {
            super(itemView);
            //这里的bind也会call findviewbyid，但调用是在 onCreateViewHolder，不然的在onBindViewHolder 重复 view.findviewbyid

            SearchNewsItemBinding binding = SearchNewsItemBinding.bind(itemView);
            itemImageView = binding.searchItemImage;
            itemTitleTextView = binding.searchItemTitle;

        }
    }
}







