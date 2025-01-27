package UI;

import database.NoteFunction;
import structure.Note;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class NoteTakingApp extends JFrame {
    private JTextArea textArea;
    private JTextField titleField;
    private final NoteFunction noteFunction;

    public NoteTakingApp() {
        noteFunction = new NoteFunction();

        // frame set up
        setTitle("Note Taking App");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // component creation
        titleField = new JTextField("Enter title here");
        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);

        // button creation
        JButton saveButton = new JButton("Save Note");
        JButton viewButton = new JButton("View Notes");
        JButton editButton = new JButton("Edit Note");
        JButton deleteButton = new JButton("Delete Note");
        JButton searchButton = new JButton("Search Notes");
        JButton clearButton = new JButton("Clear");

        // Listeners
        saveButton.addActionListener(e -> saveNote());
        viewButton.addActionListener(e -> viewNotes());
        editButton.addActionListener(e -> editNote());
        deleteButton.addActionListener(e -> deleteNote());
        searchButton.addActionListener(e -> searchNotes());
        clearButton.addActionListener(e -> clearFields());

        // Layout
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(titleField, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(clearButton);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void saveNote() {
        try {
            noteFunction.saveNote(new Note(titleField.getText().trim(), textArea.getText().trim()));
            JOptionPane.showMessageDialog(this, "Note saved!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error saving note: " + e.getMessage());
        }
    }

    private void viewNotes() {
        try {
            List<Note> notes = noteFunction.getAllNotes();
            StringBuilder result = new StringBuilder();
            for (Note note : notes) {
                result.append("ID: ").append(note.getId())
                        .append("\nTitle: ").append(note.getTitle())
                        .append("\nContent: ").append(note.getContent())
                        .append("\n\n");
            }
            JOptionPane.showMessageDialog(this, new JScrollPane(new JTextArea(result.toString())), "All Notes", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error fetching notes: " + e.getMessage());
        }
    }

    private void editNote() {
        String idStr = JOptionPane.showInputDialog(this, "Enter the note ID to edit:");
        try {
            int id = Integer.parseInt(idStr);
            String title = JOptionPane.showInputDialog(this, "New title:");
            String content = JOptionPane.showInputDialog(this, "New content:");
            noteFunction.updateNote(new Note(id, title, content));
            JOptionPane.showMessageDialog(this, "Note updated!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error updating note: " + e.getMessage());
        }
    }

    private void deleteNote() {
        String idStr = JOptionPane.showInputDialog(this, "Enter the note ID to delete:");
        try {
            noteFunction.deleteNoteById(Integer.parseInt(idStr));
            JOptionPane.showMessageDialog(this, "Note deleted!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error deleting note: " + e.getMessage());
        }
    }

    private void searchNotes() {
        String keyword = JOptionPane.showInputDialog(this, "Enter keyword to search:");
        try {
            List<Note> notes = noteFunction.searchNotes(keyword);
            StringBuilder result = new StringBuilder();
            for (Note note : notes) {
                result.append("ID: ").append(note.getId())
                        .append("\nTitle: ").append(note.getTitle())
                        .append("\nContent: ").append(note.getContent())
                        .append("\n\n");
            }
            JOptionPane.showMessageDialog(this, new JScrollPane(new JTextArea(result.toString())), "Search Results", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error searching notes: " + e.getMessage());
        }
    }

    private void clearFields() {
        titleField.setText("");
        textArea.setText("");
    }
}
