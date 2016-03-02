package vn.hoangphan.karaokearena.db;

import android.content.Context;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import vn.hoangphan.karaokearena.models.Patch;
import vn.hoangphan.karaokearena.models.Word;

/**
 * Created by Hoang Phan on 2/27/2016.
 */
public class DatabaseHelper {
    private static DatabaseHelper instance;
    private Realm mRealm;
    private RealmConfiguration mConfiguration;

    public static void init(Context context) {
        instance = new DatabaseHelper(context);
    }

    public static DatabaseHelper getInstance() {
        return instance;
    }

    private DatabaseHelper(Context context) {
        mConfiguration = new RealmConfiguration.Builder(context).build();
        mRealm = Realm.getInstance(context);
    }

    public void save(List objects) {
        mRealm.beginTransaction();
        mRealm.copyToRealmOrUpdate(objects);
        mRealm.commitTransaction();
    }

    public static Word newWord(String content, int duration, int note, int processedAt, int position) {
        Word word = new Word();
        word.setContent(content);
        word.setDuration(duration);
        word.setNote(note);
        word.setProcessedAt(processedAt);
        word.setPosition(position);
        return word;
    }
}
