package vn.hoangphan.karaokearena.models;

/**
 * Created by Hoang Phan on 2/12/2016.
 */
public class WordWithIndex {
    private Word word;
    private int index;

    public WordWithIndex(Word word, int index) {
        this.word = word;
        this.index = index;
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
