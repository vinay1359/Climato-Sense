package com.weatherapp.ui;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

public class ThemeManager {
    private final Map<String, Color> lightTheme;
    private final Map<String, Color> darkTheme;
    private Map<String, Color> currentTheme;

    public ThemeManager() {
        lightTheme = new HashMap<>();
        lightTheme.put("background", new Color(240, 240, 240));
        lightTheme.put("text", new Color(33, 33, 33));
        lightTheme.put("border", new Color(200, 200, 200));

        darkTheme = new HashMap<>();
        darkTheme.put("background", new Color(50, 50, 50));
        darkTheme.put("text", new Color(220, 220, 220));
        darkTheme.put("border", new Color(100, 100, 100));

        currentTheme = lightTheme;
    }

    public void setTheme(String theme) {
        if (theme.equalsIgnoreCase("light")) {
            currentTheme = lightTheme;
        } else if (theme.equalsIgnoreCase("dark")) {
            currentTheme = darkTheme;
        }
    }

    public Color getColor(String key) {
        return currentTheme.get(key);
    }
}