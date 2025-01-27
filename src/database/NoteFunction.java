package database;

import structure.Note;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NoteFunction {
    //note function
    public void saveNote(Note note) throws SQLException {
        String insertSQL = "INSERT INTO notes (title, content) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            pstmt.setString(1, note.getTitle());
            pstmt.setString(2, note.getContent());
            pstmt.executeUpdate();
        }
    }

    public List<Note> getAllNotes() throws SQLException {
        String fetchSQL = "SELECT id, title, content FROM notes";
        List<Note> notes = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(fetchSQL)) {
            while (rs.next()) {
                notes.add(new Note(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("content")
                ));
            }
        }
        return notes;
    }

    public void updateNote(Note note) throws SQLException {
        String updateSQL = "UPDATE notes SET title = ?, content = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {
            pstmt.setString(1, note.getTitle());
            pstmt.setString(2, note.getContent());
            pstmt.setInt(3, note.getId());
            pstmt.executeUpdate();
        }
    }

    public void deleteNoteById(int id) throws SQLException {
        String deleteSQL = "DELETE FROM notes WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(deleteSQL)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    public List<Note> searchNotes(String keyword) throws SQLException {
        String searchSQL = "SELECT id, title, content FROM notes WHERE title LIKE ? OR content LIKE ?";
        List<Note> notes = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(searchSQL)) {
            pstmt.setString(1, "%" + keyword + "%");
            pstmt.setString(2, "%" + keyword + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    notes.add(new Note(
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getString("content")
                    ));
                }
            }
        }
        return notes;
    }
}
