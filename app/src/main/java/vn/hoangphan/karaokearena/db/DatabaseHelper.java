package vn.hoangphan.karaokearena.db;

import java.util.List;

import io.realm.Realm;
import vn.hoangphan.karaokearena.models.Patch;
import vn.hoangphan.karaokearena.models.Word;

/**
 * Created by Hoang Phan on 2/27/2016.
 */
public class DatabaseHelper {
    public static void save(Realm realm, List objects) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(objects);
        realm.commitTransaction();
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
