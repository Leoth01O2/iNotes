package com.codegym.model;

import com.codegym.strategy.NoteStrategy;

public class Note {
    private int id;
    private String title;
    private String content;
    private int typeId;
    private String typeName;
    private static NoteStrategy strategy;

    public Note() {
    }

    public Note(int id, String title, String content, int typeId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.typeId = typeId;
    }

    public Note(String title, String content, int typeId) {
        this.title = title;
        this.content = content;
        this.typeId = typeId;
    }

    public Note(int id, String title, String content, int typeId, String typeName) {
        this(id, title, content, typeId);
        this.typeName = typeName;
    }

    public static void setStrategy(NoteStrategy noteStrategy) {
        strategy = noteStrategy;
    }

    public void save() {
        if (strategy == null) throw new IllegalStateException("NoteStrategy chưa được thiết lập");
        strategy.save(this);
    }

    public void delete(int id) {
        if (strategy == null) throw new IllegalStateException("NoteStrategy chưa được thiết lập");
        strategy.delete(id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getTypeId() {
        return typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
