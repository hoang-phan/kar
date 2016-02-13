package vn.hoangphan.karaokearena.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hoang Phan on 2/10/2016.
 */
public class Line {
    private List<Word> words;
    public Line() {
        words = new ArrayList<>();
    }

    public String toString() {
        return toString(0, words.size() - 1);
    }

    public String toString(int firstIndex) {
        return toString(firstIndex, words.size() - 1);
    }

    public String toString(int firstIndex, int lastIndex) {
        String result = "";
        for (int i = firstIndex; i <= lastIndex; i++) {
            result += words.get(i).getContent() + " ";
        }
        return result;
    }

    public List<Word> getWords() {
        return words;
    }

    public void addWord(Word word) {
        words.add(word);
    }

    public WordWithIndex getWordAt(int millis) {
        return getWordAt(millis, 0, words.size() - 1);
    }

    public boolean isLastIndex(int index) {
        return index == words.size() - 1;
    }

    private WordWithIndex getWordAt(int millis, int start, int end) {
        if (start >= end) {
            return new WordWithIndex(words.get(start), start);
        }
        int mid = (start + end + 1) / 2;
        Word word = words.get(mid);
        if (millis < word.getProcessedAt()) {
            return getWordAt(millis, start, mid - 1);
        } else {
            return getWordAt(millis, mid, end);
        }
    }
}
