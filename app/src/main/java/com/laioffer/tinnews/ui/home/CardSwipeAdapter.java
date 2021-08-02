package com.laioffer.tinnews.ui.home;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.laioffer.tinnews.R;
import com.laioffer.tinnews.databinding.SwipeNewsCardBinding;
import com.laioffer.tinnews.model.Article;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CardSwipeAdapter extends RecyclerView.Adapter<CardSwipeAdapter.CardSwipeViewHolder> {


    // 1. Supporting data:
    private List<Article> articles = new ArrayList<>();

    public void setArticles(List<Article> newsList) {
        articles.clear();
        articles.addAll(newsList);
        notifyDataSetChanged();
    }


    // 2. Adapter overrides:


    @Override
    public CardSwipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.swipe_news_card

                , parent, false);
        return new CardSwipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CardSwipeViewHolder holder, int position) {

        if (position < 19) {

            Article article = articles.get(position);
            holder.titleTextView.setText(article.title);
            holder.descriptionTextView.setText(article.description);
            if (article.urlToImage != null && !article.urlToImage.isEmpty()) {
                Picasso.get().load(article.urlToImage).into(holder.imageView);
            }


        }else  {
            holder.titleTextView.setText("No more news! Rewind, plz!");
            holder.descriptionTextView.setText("");
            holder.imageView.setImageResource(R.drawable.nodata);
        }


    }

    @Override
    public int getItemCount() {
        return articles.size();
    }


    // 3. CardSwipeViewHolder:
    public static class CardSwipeViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView titleTextView;
        TextView descriptionTextView;

        public CardSwipeViewHolder(View itemView) {
            super(itemView);

            SwipeNewsCardBinding binding = SwipeNewsCardBinding.bind(itemView);
            imageView = binding.swipeCardImageView;
            titleTextView = binding.swipeCardTitle;
            descriptionTextView = binding.swipeCardDescription;
        }
    }


}
