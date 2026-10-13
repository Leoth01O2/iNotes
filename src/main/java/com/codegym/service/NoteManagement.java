package com.codegym.service;

import com.codegym.model.Note;
import com.codegym.strategy.NoteDBStrategy;
import com.codegym.strategy.NoteFileStrategy;
import com.codegym.strategy.NoteStrategy;

import java.util.List;

public class NoteManagement {
    private NoteStrategy strategy;

    public NoteManagement(NoteStrategy strategy) {
        setStrategy(strategy);
    }

    public void setStrategy(NoteStrategy strategy) {
        this.strategy = strategy;
        Note.setStrategy(strategy);
    }

    public List<Note> searchNotes(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) return strategy.findAll();
        return strategy.search(keyword.trim());
    }

    public void addNote(String title, String content, int typeId) {
        new Note(title, content, typeId).save();
    }

    public void updateNote(int id, String title, String content, int typeId) {
        new Note(id, title, content, typeId).save();
    }

    public void deleteNote(int id) {
        new Note().delete(id);
    }

    public List<Note> findAll() {
        return strategy.findAll();
    }

    public Note findById(int id) {
        if (strategy instanceof NoteDBStrategy) return ((NoteDBStrategy) strategy).findById(id);
        if (strategy instanceof NoteFileStrategy) return ((NoteFileStrategy) strategy).findById(id);
        return null;
    }
}
