package vn.hoangphan.karaokearena.models;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Hoang Phan on 2/27/2016.
 */

public class Song extends RealmObject {
    @PrimaryKey
    private long id;

    private String beatLink;
    private String lyricLink;
    private String beatFile;
    private String lyricFile;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBeatLink() {
        return beatLink;
    }

    public void setBeatLink(String beatLink) {
        this.beatLink = beatLink;
    }

    public String getLyricLink() {
        return lyricLink;
    }

    public void setLyricLink(String lyricLink) {
        this.lyricLink = lyricLink;
    }

    public String getBeatFile() {
        return beatFile;
    }

    public void setBeatFile(String beatFile) {
        this.beatFile = beatFile;
    }

    public String getLyricFile() {
        return lyricFile;
    }

    public void setLyricFile(String lyricFile) {
        this.lyricFile = lyricFile;
    }
}
