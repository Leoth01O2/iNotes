package com.codegym.dao;

import com.codegym.model.NoteType;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NoteTypeDAO {
    private final String jdbcURL = "jdbc:mysql://localhost:3306/inotes_db?useSSL=false&serverTimezone=UTC";
    private final String jdbcUsername = "root";
    private final String jdbcPassword = "123456";

    public List<NoteType> findAll() {
        List<NoteType> types = new ArrayList<>();
        String sql = "SELECT id,name,description FROM note_type ORDER BY id";
        try (Connection conn = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                types.add(new NoteType(rs.getInt("id"), rs.getString("name"), rs.getString("description")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return types;
    }
}
