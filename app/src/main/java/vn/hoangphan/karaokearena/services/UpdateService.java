package vn.hoangphan.karaokearena.services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import au.com.bytecode.opencsv.CSVReader;
import vn.hoangphan.karaokearena.db.DatabaseUtils;
import vn.hoangphan.karaokearena.models.Patch;
import vn.hoangphan.karaokearena.models.Song;

/**
 * Created by Hoang Phan on 3/1/2016.
 */
public class UpdateService extends IntentService {
    private static Queue<Patch> mPatches = new ConcurrentLinkedQueue<>();
    private static boolean mIsRunning = false;

    public UpdateService() {
        super("UpdateService");
    }

    public static void addToDownload(Patch patch) {
        mPatches.add(patch);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (mIsRunning) {
            return;
        }
        mIsRunning = true;
        Patch patch;
        while (mPatches.size() > 0) {
            patch = mPatches.remove();

            long version = patch.getVersion();

            if (version >= 0) {
                try {
                    URL url = new URL(patch.getLink());

                    CSVReader reader = new CSVReader(new InputStreamReader(url.openStream()));
                    List<String> headers = Arrays.asList(reader.readNext());
                    Log.d("headers", headers.toString());

                    int columnId = headers.indexOf("id"),
                            columnName = headers.indexOf("name"),
                            columnAuthor = headers.indexOf("author"),
                            columnBeatLink = headers.indexOf("beat_link"),
                            columnLyricLink = headers.indexOf("lyric_link"),
                            columnWordsLink = headers.indexOf("words_link"),
                            columnSinger = headers.indexOf("singer");

                    String[] parts;
                    List<Song> songs = new ArrayList<>();

                    while ((parts = reader.readNext()) != null) {
                        Song song = new Song();
                        song.setId(Long.valueOf(parts[columnId]));
                        song.setName(parts[columnName]);
                        song.setAuthor(parts[columnAuthor]);
                        song.setSinger(parts[columnSinger]);
                        song.setBeatLink(parts[columnBeatLink]);
                        song.setLyricLink(parts[columnLyricLink]);
                        song.setWordsLink(parts[columnWordsLink]);
                        song.setPatchId(version);
                        songs.add(song);
                    }

                    Log.d("Read songs", "Found " + songs.size());

                    DatabaseUtils.save(this, songs);
                    DatabaseUtils.onPatchUpdated(this, patch);
                    Thread.sleep(200);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            mIsRunning = false;
        }
    }
}
