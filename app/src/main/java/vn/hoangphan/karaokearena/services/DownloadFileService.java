package vn.hoangphan.karaokearena.services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import vn.hoangphan.karaokearena.models.Song;

/**
 * Created by Hoang Phan on 2/10/2016.
 */
public class DownloadFileService extends IntentService {
    private static Queue<Song> mSongs = new ConcurrentLinkedQueue<>();
    private static boolean mIsRunning = false;

    public DownloadFileService() {
        super("DownloadFileService");
    }

    public static void addToDownload(Song song) {
        mSongs.add(song);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (mIsRunning) {
            return;
        }
        mIsRunning = true;
        Song song;
        while (mSongs.size() > 0) {
            song = mSongs.remove();

            File parent = new File("/sdcard/KaraokeArena");
            parent.mkdirs();
            File file = new File(parent, song.getId() + "_beat.mp3");
            if (file.exists()) {
                Log.d("Download", "Downloaded");
            } else {
                int count;

                URL url;
                try {
                    url = new URL(song.getBeatLink());
                    URLConnection connection = url.openConnection();
                    connection.connect();

                    InputStream input = new BufferedInputStream(url.openStream(), 8192);

                    OutputStream output = new FileOutputStream(file);

                    byte data[] = new byte[1024];

                    while ((count = input.read(data)) != -1) {
                        output.write(data, 0, count);
                    }

                    output.flush();

                    output.close();
                    input.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            mIsRunning = false;
        }
    }
}