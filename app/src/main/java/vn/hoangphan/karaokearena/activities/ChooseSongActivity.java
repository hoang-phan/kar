package vn.hoangphan.karaokearena.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import vn.hoangphan.karaokearena.R;
import vn.hoangphan.karaokearena.adapters.SongsAdapter;
import vn.hoangphan.karaokearena.db.DatabaseUtils;

public class ChooseSongActivity extends AppCompatActivity {
    private SongsAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private RecyclerView mSongsRv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_song);

        initComponents();
        bindComponents();
    }

    private void initComponents() {
        mAdapter = new SongsAdapter();
        mLayoutManager = new LinearLayoutManager(this);

        mSongsRv = (RecyclerView) findViewById(R.id.rv_songs);
    }

    private void bindComponents() {
        mSongsRv.setAdapter(mAdapter);
        mSongsRv.setLayoutManager(mLayoutManager);

        mAdapter.setSongs(DatabaseUtils.getAllSongs(this));
        mAdapter.notifyDataSetChanged();
    }
}
