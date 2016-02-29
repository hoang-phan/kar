package vn.hoangphan.karaokearena.net;

import retrofit.Call;
import retrofit.http.GET;
import vn.hoangphan.karaokearena.models.net.PatchesResponse;

/**
 * Created by Hoang Phan on 2/27/2016.
 */
public interface KaraokeArenaAPI {
    @GET("/patches")
    Call<PatchesResponse> getPatches();
}
