package vn.hoangphan.karaokearena;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.MediaController;
import android.widget.VideoView;

import com.marvinlabs.widget.slideshow.SlideShowView;
import com.marvinlabs.widget.slideshow.adapter.ResourceBitmapAdapter;
import com.marvinlabs.widget.slideshow.playlist.RandomPlayList;
import com.marvinlabs.widget.slideshow.transition.FadeTransitionFactory;
import com.marvinlabs.widget.slideshow.transition.SlideAndZoomTransitionFactory;

import java.io.IOException;

import vn.hoangphan.karaokearena.playlists.CustomRandomPlaylist;
import vn.hoangphan.karaokearena.tasks.DownloadFileTask;
import vn.hoangphan.karaokearena.callbacks.OnPostExecute;
import vn.hoangphan.karaokearena.callbacks.OnProgressUpdate;
import vn.hoangphan.karaokearena.transitions.CustomTransitionFactory;

public class KaraokeActivity extends Activity {
    public static final String URL = "https://www.dropbox.com/s/dnrop9jldn61zem/%5BKaraoke%20beat%5D%20Xe%20%C4%91%E1%BA%A1p%20-%20Th%C3%B9y%20Chi%20ft.%20M4U%20%28h%E1%BA%A1%201%20note%29.mp4?dl=1";
    public static final String FILE_NAME = "sample.mp4";
    public static final String FILE_PATH = "/sdcard/" + FILE_NAME;

    private SlideShowView mMainSlides;
    private MediaPlayer mBeatPlayer;
//    private ProgressDialog mProgressDialog;
    private Handler mHandler;
    private Runnable mScoringRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_karaoke);
        initComponents();
        bindComponents();
    }

    private void bindComponents() {
        playFile();
//        mProgressDialog.setTitle("Downloading...");
//        mProgressDialog.setMessage("Downloading media...");
//        mProgressDialog.setCancelable(false);
//        mProgressDialog.setIndeterminate(false);
//        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//        mProgressDialog.setMax(100);
//        mProgressDialog.show();

//        DownloadFileTask task = new DownloadFileTask(FILE_PATH);
//        task.setOnProgressUpdate(new OnProgressUpdate() {
//            @Override
//            public void update(int progress) {
//                mProgressDialog.setProgress(progress);
//            }
//        });
//        task.setOnPostExecute(new OnPostExecute() {
//            @Override
//            public void execute() {
//                playFile();
//            }
//        });
//        task.execute(URL);
    }

    private void initComponents() {
        mHandler = new Handler();
//        mProgressDialog = new ProgressDialog(this);
        mMainSlides = (SlideShowView) findViewById(R.id.slides_main);
        mBeatPlayer = new MediaPlayer();

//        mScoringRunnable = new Runnable() {
//            @Override
//            public void run() {
//                while (true) {
//                    int position = mMainVid.getCurrentPosition();
//                    Log.e("Position", position + "");
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        };
    }

    private void playFile() {
//        mMainVid.setVideoPath(FILE_PATH);
//        mMainVid.setMediaController(new MediaController(this));
//        mMainVid.start();
//        mHandler.postDelayed(mScoringRunnable, 1000);
//        mProgressDialog.dismiss();
        mMainSlides.setAdapter(new ResourceBitmapAdapter(this, new int[] { R.drawable.schoolhood1, R.drawable.schoolhood2, R.drawable.schoolhood3, R.drawable.schoolhood4, R.drawable.schoolhood5, R.drawable.schoolhood6, R.drawable.schoolhood7, R.drawable.schoolhood8, R.drawable.schoolhood9, R.drawable.schoolhood10 }));
        mMainSlides.setPlaylist(new CustomRandomPlaylist());
        mMainSlides.setTransitionFactory(new CustomTransitionFactory(2000));
        mMainSlides.play();
        try {
            AssetFileDescriptor descriptor = getAssets().openFd("Xedap-beat.mp3");
            mBeatPlayer.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
            mBeatPlayer.prepare();
            mBeatPlayer.setVolume(1f, 1f);
            mBeatPlayer.setLooping(false);
            mBeatPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
