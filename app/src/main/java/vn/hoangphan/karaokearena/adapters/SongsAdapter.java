package vn.hoangphan.karaokearena.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
        Song song = mSongList.get(position);
        holder.mNameTv.setText(song.getName());
        holder.mAuthorTv.setText(song.getAuthor());
        holder.mSingerTv.setText(song.getSinger());
    }

    @Override
    public int getItemCount() {
        return mSongList.size();
    }

    public void setSongs(List<Song> songList) {
        mSongList = songList;
    }

    public static class SongHolder extends RecyclerView.ViewHolder {
        private TextView mNameTv, mAuthorTv, mSingerTv;

        public SongHolder(View view) {
            super(view);
            mNameTv = (TextView) view.findViewById(R.id.tv_song_name);
            mAuthorTv = (TextView) view.findViewById(R.id.tv_song_author);
            mSingerTv = (TextView) view.findViewById(R.id.tv_song_singer);
        }
    }
}
