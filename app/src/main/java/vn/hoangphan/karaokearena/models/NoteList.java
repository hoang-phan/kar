package vn.hoangphan.karaokearena.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hoang Phan on 2/13/2016.
 */
public class NoteList {
    private List<Integer> notes;

    public List<Integer> getNotes() {
        return notes;
    }

    public NoteList() {
        notes = new ArrayList<>();
    }

    public void addNote(int note) {
        notes.add(note);
    }
}
