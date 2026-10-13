package com.codegym.strategy;

import com.codegym.model.Note;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NoteDBStrategy implements NoteStrategy {
    private final String jdbcURL = "jdbc:mysql://localhost:3306/inotes_db?useSSL=false&serverTimezone=UTC";
    private final String jdbcUsername = "root";
    private final String jdbcPassword = "";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
    }

    @Override
    public void save(Note note) {
        if (note.getId() == 0) {
            String sql = "INSERT INTO notes(title, content, type_id) VALUES (?, ?, ?)";
            try (Connection conn = getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, note.getTitle());
                ps.setString(2, note.getContent());
                ps.setInt(3, note.getTypeId());
                ps.executeUpdate();
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) note.setId(rs.getInt(1));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            String sql = "UPDATE notes SET title=?, content=?, type_id=? WHERE id=?";
            try (Connection conn = getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, note.getTitle());
                ps.setString(2, note.getContent());
                ps.setInt(3, note.getTypeId());
                ps.setInt(4, note.getId());
                ps.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM notes WHERE id=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Note> findAll() {
        String sql = "SELECT n.id,n.title,n.content,n.type_id,t.name type_name FROM notes n LEFT JOIN note_type t ON n.type_id=t.id ORDER BY n.id";
        return getNotes(sql, null);
    }

    @Override
    public List<Note> search(String keyword) {
        String sql = "SELECT n.id,n.title,n.content,n.type_id,t.name type_name FROM notes n LEFT JOIN note_type t ON n.type_id=t.id WHERE n.title LIKE ? OR n.content LIKE ? ORDER BY n.id";
        return getNotes(sql, keyword);
    }

    public Note findById(int id) {
        String sql = "SELECT n.id,n.title,n.content,n.type_id,t.name type_name FROM notes n LEFT JOIN note_type t ON n.type_id=t.id WHERE n.id=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapNote(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    private List<Note> getNotes(String sql, String keyword) {
        List<Note> notes = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            if (keyword != null) {
                String value = "%" + keyword + "%";
                ps.setString(1, value);
                ps.setString(2, value);
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) notes.add(mapNote(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return notes;
    }

    private Note mapNote(ResultSet rs) throws SQLException {
        return new Note(rs.getInt("id"), rs.getString("title"), rs.getString("content"),
                rs.getInt("type_id"), rs.getString("type_name"));
    }
}
