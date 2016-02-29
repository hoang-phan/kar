package vn.hoangphan.karaokearena.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import vn.hoangphan.karaokearena.R;
import vn.hoangphan.karaokearena.models.Song;

/**
 * Created by Hoang Phan on 2/27/2016.
 */
public class SongsAdapter extends RecyclerView.Adapter<SongsAdapter.SongHolder> {
    private List<Song> mSongList = new ArrayList<>();

    @Override
    public SongHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_song, parent, false);
        return new SongHolder(view);
    }

    @Override
    public void onBindViewHolder(SongHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mSongList.size();
    }

    public void setSongs(List<Song> songList) {
        mSongList = songList;
    }

    public static class SongHolder extends RecyclerView.ViewHolder {

        public SongHolder(View itemView) {
            super(itemView);
        }
    }
}
