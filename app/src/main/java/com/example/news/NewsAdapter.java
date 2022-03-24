package com.example.news;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NewsAdapter  extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private final List<News> news;

    NewsAdapter(Context context, List<News> news) {
        this.news = news;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.recycycler_view_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsAdapter.ViewHolder holder, int position) {
        News article = news.get(position);
        holder.sourceView.setText(article.getSource());
        holder.authorView.setText(article.getAuthor());
        holder.titleView.setText(article.getTitle());
        holder.descriptionView.setText(article.getDescription());
        holder.dateView.setText(article.getDate());
        Picasso.get().load(article.getImageUrl()).error(R.drawable.error_imag).into(holder.imageView);
        int[] imageList = new int[]{R.drawable.image_avatar0,
                R.drawable.image_avatar2,R.drawable.image_avatar3,R.drawable.image_avatar4,
                R.drawable.image_avatar5,R.drawable.image_avatar6,R.drawable.image_avatar7,
                R.drawable.image_avatar8,R.drawable.image_avatar9};
        int random = imageList[new Random().nextInt(imageList.length)];
        holder.randImageView.setImageResource(random);
    }

    @Override
    public int getItemCount() {
        return news.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView imageView, randImageView;
        final TextView sourceView, authorView, titleView, descriptionView, dateView;
        ViewHolder(View view){
            super(view);
            sourceView = view.findViewById(R.id.source);
            authorView = view.findViewById(R.id.author);
            titleView = view.findViewById(R.id.title);
            descriptionView = view.findViewById(R.id.description);
            dateView = view.findViewById(R.id.date);
            imageView = view.findViewById(R.id.image);
            randImageView = view.findViewById(R.id.rand_image);
        }
    }
}
