package vn.hoangphan.karaokearena.net;

import retrofit.JacksonConverterFactory;
import retrofit.Retrofit;
import vn.hoangphan.karaokearena.utils.Constants;

/**
 * Created by Hoang Phan on 2/27/2016.
 */
public class APIService {
    private static KaraokeArenaAPI instance;

    public static KaraokeArenaAPI getInstance() {
        if (instance == null) {
            Retrofit restAdapter = new Retrofit.Builder().baseUrl(Constants.API_ENDPOINT).addConverterFactory(JacksonConverterFactory.create()).build();
            instance = restAdapter.create(KaraokeArenaAPI.class);
        }
        return instance;
    }
}