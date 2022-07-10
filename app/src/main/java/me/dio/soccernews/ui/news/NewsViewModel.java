package me.dio.soccernews.ui.news;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import me.dio.soccernews.data.ApiState;
import me.dio.soccernews.data.SoccerNewsRepository;
import me.dio.soccernews.data.remote.SoccerNewsApi;
import me.dio.soccernews.domain.News;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsViewModel extends ViewModel {

    private final MutableLiveData<List<News>> news = new MutableLiveData<>();
    private final MutableLiveData<ApiState> state = new MutableLiveData<>();

    public NewsViewModel() {
                this.findNews();
    }

    public void findNews() {
        state.setValue(ApiState.DOING);
        SoccerNewsRepository.getInstance().getRemoteApi().getNews().
                enqueue(new Callback<List<News>>(){
            @Override
            public void onResponse(Call<List<News>> call, Response<List<News>> response) {
                if (response.isSuccessful()){
                    news.setValue(response.body());
                    state.setValue(ApiState.DONE);
                } else {
                    state.setValue(ApiState.ERROR);
                }
            }
            @Override
            public void onFailure(Call<List<News>> call, Throwable t) {
                state.setValue(ApiState.ERROR);
            }
        });
    }

    public void saveNews(News news) {
        AsyncTask.execute(() -> SoccerNewsRepository.getInstance().getLocalDb().newsDao().save(news));
    }
    public List<News> getFavoriteNews() {
        return SoccerNewsRepository.getInstance().getLocalDb().newsDao().loadFavoriteNews_non();
    }

    public LiveData<List<News>> getNews() {
        return this.news;
    }

    public MutableLiveData<ApiState> getState() {
        return state;
    }
}