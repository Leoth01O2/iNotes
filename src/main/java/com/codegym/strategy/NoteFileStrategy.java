package com.codegym.strategy;

import com.codegym.model.Note;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class NoteFileStrategy implements NoteStrategy {
    private final Path filePath;

    public NoteFileStrategy() {
        this("notes.txt");
    }

    public NoteFileStrategy(String fileName) {
        filePath = Paths.get(fileName);
    }

    @Override
    public void save(Note note) {
        List<Note> notes = findAll();
        if (note.getId() == 0) {
            int maxId = 0;
            for (Note item : notes) if (item.getId() > maxId) maxId = item.getId();
            note.setId(maxId + 1);
            notes.add(note);
        } else {
            boolean found = false;
            for (int i = 0; i < notes.size(); i++) {
                if (notes.get(i).getId() == note.getId()) {
                    notes.set(i, note);
                    found = true;
                    break;
                }
            }
            if (!found) notes.add(note);
        }
        writeAll(notes);
    }

    @Override
    public void delete(int id) {
        List<Note> notes = findAll();
        notes.removeIf(note -> note.getId() == id);
        writeAll(notes);
    }

    @Override
    public List<Note> findAll() {
        List<Note> notes = new ArrayList<>();
        if (!Files.exists(filePath)) return notes;
        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split("\\|", -1);
                if (data.length == 4) {
                    notes.add(new Note(Integer.parseInt(data[0]), data[1], data[2], Integer.parseInt(data[3])));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return notes;
    }

    @Override
    public List<Note> search(String keyword) {
        String key = keyword == null ? "" : keyword.toLowerCase();
        List<Note> result = new ArrayList<>();
        for (Note note : findAll()) {
            if (note.getTitle().toLowerCase().contains(key) || note.getContent().toLowerCase().contains(key)) {
                result.add(note);
            }
        }
        return result;
    }

    public Note findById(int id) {
        for (Note note : findAll()) if (note.getId() == id) return note;
        return null;
    }

    private void writeAll(List<Note> notes) {
        try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
            for (Note note : notes) {
                String title = note.getTitle() == null ? "" : note.getTitle().replace("|", "/").replace("\n", " ");
                String content = note.getContent() == null ? "" : note.getContent().replace("|", "/").replace("\n", " ");
                writer.write(note.getId() + "|" + title + "|" + content + "|" + note.getTypeId());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
