package me.dio.soccernews.ui.news;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import me.dio.soccernews.domain.News;

public class NewsViewModel extends ViewModel {

    private final MutableLiveData<List<News>> news;

    public NewsViewModel() {
        this.news = new MutableLiveData<>();

        //TODO remover Mock de noticias
        List<News> news = new ArrayList<>();
        news.add(new News("Ferroviária tem retorno de desfalque importante",
                "O destaque do time contra o Juventos pode voltar à jogo"));
        news.add(new News("Corinthians joga no Sábado", "dummy description"));
        news.add(new News("Timão faz último treinamento antes do jogo de sábado", "dummy description 2"));

        this.news.setValue(news);
    }

    public LiveData<List<News>> getNews() {
        return this.news;
    }
}