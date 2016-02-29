package vn.hoangphan.karaokearena.models;

import io.realm.RealmObject;
import io.realm.annotations.Index;

/**
 * Created by Hoang Phan on 2/10/2016.
 */
public class Word extends RealmObject {
    private String content;
    private int note;
    private int duration;

    @Index
    private int position;

    @Index
    private int processedAt;

    @Index
    private long lineId;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public int getProcessedAt() {
        return processedAt;
    }

    public void setProcessedAt(int processedAt) {
        this.processedAt = processedAt;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public long getLineId() {
        return lineId;
    }

    public void setLineId(long lineId) {
        this.lineId = lineId;
    }
}
