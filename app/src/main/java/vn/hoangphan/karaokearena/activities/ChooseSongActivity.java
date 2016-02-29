package vn.hoangphan.karaokearena.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import vn.hoangphan.karaokearena.R;
import vn.hoangphan.karaokearena.adapters.SongsAdapter;

public class ChooseSongActivity extends AppCompatActivity {
    private SongsAdapter mAdapter;
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
        mSongsRv = (RecyclerView) findViewById(R.id.rv_songs);
    }

    private void bindComponents() {
        mSongsRv.setAdapter(mAdapter);
    }
}
