package vn.hoangphan.karaokearena.db;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import vn.hoangphan.karaokearena.models.Patch;
import vn.hoangphan.karaokearena.models.Song;
import vn.hoangphan.karaokearena.models.Word;

/**
 * Created by Hoang Phan on 2/27/2016.
 */
public class DatabaseUtils {
    public static void save(Context context, List objects) {
        Realm realm = Realm.getInstance(context);
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(objects);
        realm.commitTransaction();
    }

    public static List<Song> getAllSongs(Context context) {
        Realm realm = Realm.getInstance(context);
        List<Song> songs = new ArrayList<>();
        songs.addAll(realm.allObjects(Song.class));
        return songs;
    }
}
