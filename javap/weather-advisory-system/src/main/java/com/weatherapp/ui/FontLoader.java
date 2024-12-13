package com.weatherapp.ui;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
public class FontLoader {
    public static void loadCustomFont(String fontPath) {
        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(fontPath)));
        } catch (IOException | FontFormatException e) {
            System.err.println("Error loading font: " + e.getMessage());
        }
    }
}