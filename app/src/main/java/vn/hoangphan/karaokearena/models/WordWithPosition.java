package vn.hoangphan.karaokearena.models;

/**
 * Created by Hoang Phan on 2/12/2016.
 */
public class WordWithPosition {
    private Word word;
    private int position;

    public WordWithPosition(Word word, int position) {
        this.word = word;
        this.position = position;
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
