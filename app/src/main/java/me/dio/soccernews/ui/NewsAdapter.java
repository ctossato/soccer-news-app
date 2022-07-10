package me.dio.soccernews.ui;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import me.dio.soccernews.R;
import me.dio.soccernews.data.SoccerNewsRepository;
import me.dio.soccernews.databinding.NewsItemBinding;
import me.dio.soccernews.domain.News;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private final FavoriteListener favoriteListener;
    private final List<News> localFavoriteNews;
    private List<News> news;

    // Construtor
    public NewsAdapter(List<News> news, FavoriteListener favoriteListener, List<News> localFavoriteNews) {

        this.news = news;
        this.favoriteListener = favoriteListener;
        this.localFavoriteNews = localFavoriteNews;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        NewsItemBinding binding = NewsItemBinding.inflate(layoutInflater ,parent, false );
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        News news = this.news.get(position);
        holder.binding.tvTitle.setText(news.getTitle());
        holder.binding.tvDescription.setText(news.getDescription());

        Picasso.get().load(news.getImage()).into(holder.binding.ivThumbnail);

        // https://stackoverflow.com/questions/3004515/sending-an-intent-to-browser-to-open-specific-url
        // implementação da funcionalidade de abrir link externo
        holder.binding.btopenLink.setOnClickListener(view -> {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(news.getLink()));
            holder.itemView.getContext().startActivity(i);
        });

        // implementação da funcionalidade de compartilhar
        holder.binding.ivShare.setOnClickListener(view -> {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_TEXT, news.getLink());
            holder.itemView.getContext().startActivity(Intent.createChooser(i, "Share via"));
        });

        // implementação da funcionalidade de favoritar
        holder.binding.ivFavorite.setOnClickListener(view -> {
            news.setFavorite(!news.isFavorite());
            this.favoriteListener.onFavorite(news);
            notifyItemChanged(position);
        } );
        //List<News> favoritedNewsList = localFavorites.getValue();
        List<String> favoritedNewsLink =  new ArrayList<>();
        if (this.localFavoriteNews != null) {
            for (int i = 0; i < this.localFavoriteNews.size(); i++) {

                favoritedNewsLink.add(this.localFavoriteNews.get(i).getLink());
            }
            if (favoritedNewsLink.contains(news.getLink())) {
                news.setFavorite(true);
            }
        }
        int favoriteColor = news.isFavorite() ? R.color.red : R.color.news_icon;
        holder.binding.ivFavorite.setColorFilter(holder.itemView.getContext().getResources().getColor(favoriteColor));
    }

    @Override
    public int getItemCount() {
        return this.news.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        private final NewsItemBinding binding;

        // construtor
        public ViewHolder(NewsItemBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface FavoriteListener {
        void onFavorite(News news);
    }
}
