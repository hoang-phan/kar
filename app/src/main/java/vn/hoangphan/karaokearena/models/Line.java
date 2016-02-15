package vn.hoangphan.karaokearena.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hoang Phan on 2/10/2016.
 */
public class Line {
    private String content;
    private List<WordWithPosition> words;

    public Line() {
        words = new ArrayList<>();
        content = "";
    }

    public int length() {
        return content.length();
    }

    public List<WordWithPosition> getWords() {
        return words;
    }

    public void addWord(Word word) {
        content += word.getContent() + " ";
        words.add(new WordWithPosition(word, content.length()));
    }

    public String getContent() {
        return content;
    }

    public int getProcessedAt() {
        return words.isEmpty() ? 0 : words.get(0).getWord().getProcessedAt();
    }

    //    public WordWithIndex getWordAt(int millis) {
//        return getWordAt(millis, 0, words.size() - 1);
//    }
//
//    public boolean isLastIndex(int index) {
//        return index == words.size() - 1;
//    }

//    private WordWithIndex getWordAt(int millis, int start, int end) {
//        if (start >= end) {
//            return new WordWithIndex(words.get(start), start);
//        }
//        int mid = (start + end + 1) / 2;
//        Word word = words.get(mid);
//        if (millis < word.getProcessedAt()) {
//            return getWordAt(millis, start, mid - 1);
//        } else {
//            return getWordAt(millis, mid, end);
//        }
//    }
}
