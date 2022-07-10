package me.dio.soccernews.ui.favorites;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import me.dio.soccernews.databinding.FragmentFavoritesBinding;
import me.dio.soccernews.ui.NewsAdapter;

public class FavoritesFragment extends Fragment {

    private FragmentFavoritesBinding binding;
    FavoritesViewModel favoritesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        favoritesViewModel =
                new ViewModelProvider(this).get(FavoritesViewModel.class);

        binding = FragmentFavoritesBinding.inflate(inflater, container, false);

        loadFavoriteNews();
        return binding.getRoot();
    }

    private void loadFavoriteNews() {
        this.favoritesViewModel.loadFavoriteNews().observe(getViewLifecycleOwner(), localnews -> {
            binding.rvFavorites.setLayoutManager(new LinearLayoutManager(getContext()));
            binding.rvFavorites.setAdapter(new NewsAdapter(localnews, updatedNews -> {
                favoritesViewModel.saveNews(updatedNews);
                loadFavoriteNews(); // Usado para atualizar a lista após uma alteração
            }, null));

        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}