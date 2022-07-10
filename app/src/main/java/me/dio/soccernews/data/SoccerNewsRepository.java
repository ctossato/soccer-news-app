package me.dio.soccernews.data;

import androidx.room.Room;

import me.dio.soccernews.App;
import me.dio.soccernews.data.local.SoccerNewsDatabase;
import me.dio.soccernews.data.remote.SoccerNewsApi;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SoccerNewsRepository {

    //region Constantes
    private static final String REMOTE_API_URL = "https://digitalinnovationone.github.io/soccer-news-api/";
    private static final String LOCAL_DB_NAME = "soccer-news";
    //endregion

    //region Retrofit setup
    private SoccerNewsApi remoteApi;
    private SoccerNewsDatabase localDb;

    public SoccerNewsApi getRemoteApi() {
        return remoteApi;
    }

    public SoccerNewsDatabase getLocalDb() {
        return localDb;
    }
    //endregion

    //region Singleton SoccerNewsRepository
    private SoccerNewsRepository () {
        remoteApi = new Retrofit.Builder()
                .baseUrl(REMOTE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(SoccerNewsApi.class);

        localDb = Room.databaseBuilder( App.getInstance(), SoccerNewsDatabase.class, LOCAL_DB_NAME)
                .allowMainThreadQueries()
                .build();
    }

    private static class LazyHolder {
        private static final SoccerNewsRepository INSTANCE = new SoccerNewsRepository();
    }

    public static SoccerNewsRepository getInstance() { return LazyHolder.INSTANCE; }

    //endregion
}
