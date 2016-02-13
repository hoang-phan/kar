package vn.hoangphan.karaokearena.models;

/**
 * Created by Hoang Phan on 2/10/2016.
 */
public class Word {
    private String content;
    private String note;
    private int duration;
    private int processedAt;

    public Word(String content, String note, int processedAt, int duration) {
        this.content = content;
        this.note = note;
        this.processedAt = processedAt;
        this.duration = duration;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
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
}
