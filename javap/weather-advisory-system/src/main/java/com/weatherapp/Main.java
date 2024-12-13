package com.weatherapp;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.weatherapp.ui.WeatherFrame;

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        // Set the look and feel to the system's default
        setLookAndFeel();

        // Show a splash screen while initializing
        showSplashScreen();

        // Initialize the main frame
        SwingUtilities.invokeLater(() -> {
            try {
                WeatherFrame frame = new WeatherFrame();
                frame.setVisible(true);
            } catch (RuntimeException e) {
                LOGGER.log(Level.SEVERE, "Failed to initialize weather frame", e);
                showErrorDialog(e);
            }
        });
    }

    private static void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            LOGGER.log(Level.SEVERE, "Failed to set look and feel", e);
            showErrorDialog(e);
        }
    }

    private static void showSplashScreen() {
        SwingUtilities.invokeLater(() -> {
            // Create a simple splash screen with a gradient background
            javax.swing.JWindow splashScreen = new javax.swing.JWindow();
            javax.swing.JPanel panel = new javax.swing.JPanel() {
                @Override
                protected void paintComponent(java.awt.Graphics g) {
                    super.paintComponent(g);
                    java.awt.Graphics2D g2d = (java.awt.Graphics2D) g;
                    java.awt.GradientPaint gradient = new java.awt.GradientPaint(
                        0, 0, new java.awt.Color(135, 206, 250),
                        getWidth(), 0, new java.awt.Color(0, 191, 255)
                    );
                    g2d.setPaint(gradient);
                    g2d.fillRect(0, 0, getWidth(), getHeight());
                }
            };
            panel.setOpaque(false); // Make the panel transparent
            javax.swing.JLabel label = new javax.swing.JLabel("Loading Weather Advisory System...", javax.swing.SwingConstants.CENTER);
            label.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 20));
            label.setForeground(java.awt.Color.WHITE);
            panel.add(label);
            splashScreen.add(panel);
            splashScreen.setSize(400, 200);
            splashScreen.setLocationRelativeTo(null);
            splashScreen.setVisible(true);
    
            // Simulate loading time
            try {
                Thread.sleep(2000); // 2 seconds
            } catch (InterruptedException e) {
                LOGGER.log(Level.SEVERE, "Splash screen interrupted", e);
            }
    
            splashScreen.dispose();
        });
    }

    private static void showErrorDialog(Exception e) {
        SwingUtilities.invokeLater(() -> {
            javax.swing.JOptionPane.showMessageDialog(
                null,
                "An error occurred: " + e.getMessage(),
                "Error",
                javax.swing.JOptionPane.ERROR_MESSAGE
            );
        });
    }
}
