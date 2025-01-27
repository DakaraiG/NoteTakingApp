package main;

import UI.NoteTakingApp;
import database.DatabaseConnection;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            NoteTakingApp app = new NoteTakingApp();
            app.setVisible(true);

            //shutdown hook to close the database connection
            Runtime.getRuntime().addShutdownHook(new Thread(DatabaseConnection::closeConnection));
        });
    }
}
