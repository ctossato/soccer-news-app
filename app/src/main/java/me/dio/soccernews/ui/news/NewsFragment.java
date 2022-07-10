package me.dio.soccernews.ui.news;

import android.os.Bundle;
import android.se.omapi.SEService;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.Snackbar;

import me.dio.soccernews.R;
import me.dio.soccernews.databinding.FragmentNewsBinding;
import me.dio.soccernews.ui.NewsAdapter;

public class NewsFragment extends Fragment {

    private FragmentNewsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NewsViewModel newsViewModel =
                new ViewModelProvider(this).get(NewsViewModel.class);

        binding = FragmentNewsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.rvNews.setLayoutManager(new LinearLayoutManager(getContext()));



        findNews(newsViewModel);
        observeStates(newsViewModel);
        binding.srlNews.setOnRefreshListener(newsViewModel::findNews);
        return root;
    }



    private void findNews(NewsViewModel newsViewModel) {
        newsViewModel.getNews().observe(getViewLifecycleOwner(), news -> {
            binding.rvNews.setAdapter(new NewsAdapter(news, updatedNews -> {
                newsViewModel.saveNews(updatedNews) ;
            }, newsViewModel.getFavoriteNews()));
        });
    }

    private void observeStates(NewsViewModel newsViewModel) {
        newsViewModel.getState().observe(getViewLifecycleOwner(), state -> {
            switch (state) {
                case DOING:
                    binding.srlNews.setRefreshing(true);
                    break;
                case DONE:
                    binding.srlNews.setRefreshing(false);
                    break;
                case ERROR:
                    binding.srlNews.setRefreshing(false);
                    Snackbar.make(binding.srlNews, R.string.error_network, Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}