package vn.hoangphan.karaokearena;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.MediaController;
import android.widget.VideoView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import vn.hoangphan.karaokearena.async.DownloadFileFromURL;
import vn.hoangphan.karaokearena.async.OnPostExecute;
import vn.hoangphan.karaokearena.async.OnProgressUpdate;
import vn.hoangphan.karaokearena.utils.Constants;

public class KaraokeActivity extends Activity {
    public static final String URL = "https://www.dropbox.com/s/dnrop9jldn61zem/%5BKaraoke%20beat%5D%20Xe%20%C4%91%E1%BA%A1p%20-%20Th%C3%B9y%20Chi%20ft.%20M4U%20%28h%E1%BA%A1%201%20note%29.mp4?dl=1";
    public static final String FILE_NAME = "sample.mp4";
    public static final String FILE_PATH = "/sdcard/" + FILE_NAME;

    private VideoView mMainVid;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_karaoke);
        mMainVid = (VideoView) findViewById(R.id.vid_main);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("Downloading...");
        mProgressDialog.setMessage("Downloading media...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setMax(100);
        mProgressDialog.show();

        DownloadFileFromURL task = new DownloadFileFromURL(FILE_PATH);
        task.setOnProgressUpdate(new OnProgressUpdate() {
            @Override
            public void update(int progress) {
                mProgressDialog.setProgress(progress);
            }
        });
        task.setOnPostExecute(new OnPostExecute() {
            @Override
            public void execute() {
                playFile();
            }
        });
        task.execute(URL);

    }

    private void playFile() {
        mMainVid.setVideoPath(FILE_PATH);
        mMainVid.setMediaController(new MediaController(this));
        mMainVid.start();
        mProgressDialog.dismiss();
    }
}
