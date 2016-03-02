package vn.hoangphan.karaokearena.models;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Hoang Phan on 2/27/2016.
 */

public class Song extends RealmObject {
    @PrimaryKey
    private long id;

    @Index
    private long patchId;

    private String name;
    private String author;
    private String singer;
    private String beatLink;
    private String lyricLink;
    private String wordsLink;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public long getPatchId() {
        return patchId;
    }

    public void setPatchId(long patchId) {
        this.patchId = patchId;
    }

    public String getWordsLink() {
        return wordsLink;
    }

    public void setWordsLink(String wordsLink) {
        this.wordsLink = wordsLink;
    }
}
