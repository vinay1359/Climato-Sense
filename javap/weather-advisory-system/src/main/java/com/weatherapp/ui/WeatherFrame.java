package com.weatherapp.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import com.weatherapp.model.WeatherData;
import com.weatherapp.services.GeminiService;
import com.weatherapp.services.OpenWeatherMapService;
import com.weatherapp.services.PreferencesManager;
import com.weatherapp.services.WeatherService;

public class WeatherFrame extends JFrame {
    private final WeatherBackgroundPanel backgroundPanel;
    private final WeatherService weatherService;
    private final GeminiService geminiService;
    private final ExecutorService executorService;
    private final SimpleDateFormat timeFormat;
    private final ThemeManager themeManager;
    private final PreferencesManager preferencesManager;

    private final SearchPanel searchPanel;
    private final CurrentWeatherPanel currentWeatherPanel;
    private final JTextArea textOutputTextArea;
    private final LoadingSpinner loadingSpinner;

    // Weather data labels
    private final JLabel cityNameLabel;
    private final JLabel temperatureLabel;
    private final JLabel humidityLabel;
    private final JLabel windSpeedLabel;
    private final JLabel pressureLabel;
    private final JLabel visibilityLabel;
    private final JLabel sunriseLabel;
    private final JLabel sunsetLabel;
    private final JLabel conditionLabel;
    private final JPanel weatherDetailsPanel;

    public WeatherFrame() {
        this.backgroundPanel = new WeatherBackgroundPanel();
        this.weatherService = new OpenWeatherMapService();
        this.geminiService = new GeminiService();
        this.executorService = Executors.newSingleThreadExecutor();
        this.timeFormat = new SimpleDateFormat("hh:mm a");
        this.loadingSpinner = new LoadingSpinner();
        this.themeManager = new ThemeManager();
        this.preferencesManager = new PreferencesManager();

        // Load custom font
        FontLoader.loadCustomFont("path/to/CustomFont.ttf");

        // Initialize UI
        setTitle("Weather Advisory System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setMinimumSize(new Dimension(800, 600));

        // Create components
        this.searchPanel = new SearchPanel(this::searchLocation);
        this.currentWeatherPanel = new CurrentWeatherPanel();
        this.textOutputTextArea = new JTextArea("Processed Data Output");
        textOutputTextArea.setLineWrap(true);
        textOutputTextArea.setWrapStyleWord(true);
        textOutputTextArea.setFont(new Font("CustomFont", Font.PLAIN, 16));
        textOutputTextArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        textOutputTextArea.setEditable(false);

        // Initialize weather data labels with styling
        this.cityNameLabel = createStyledLabel("City: ");
        this.temperatureLabel = createStyledLabel("Temperature: ");
        this.humidityLabel = createStyledLabel("Humidity: ");
        this.windSpeedLabel = createStyledLabel("Wind Speed: ");
        this.pressureLabel = createStyledLabel("Pressure: ");
        this.visibilityLabel = createStyledLabel("Visibility: ");
        this.sunriseLabel = createStyledLabel("Sunrise: ");
        this.sunsetLabel = createStyledLabel("Sunset: ");
        this.conditionLabel = createStyledLabel("Condition: ");

        // Initialize the weather details panel
        this.weatherDetailsPanel = createWeatherDetailsPanel();
        getContentPane().add(backgroundPanel, BorderLayout.CENTER);

        // Call setupLayout method
        setupLayout(backgroundPanel);
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        // Set font to be larger and bold
        label.setFont(new Font("CustomFont", Font.BOLD, 16));
        // Add padding around the text
        label.setBorder(new EmptyBorder(8, 12, 8, 12));
        // Set colors
        label.setForeground(themeManager.getColor("text"));
        return label;
    }

    private JPanel createWeatherDetailsPanel() {
        JPanel panel = new JPanel();
        // Use BoxLayout for vertical expansion
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        // Add padding and border
        panel.setBorder(new CompoundBorder(
            new MatteBorder(1, 1, 1, 1, themeManager.getColor("border")),
            new EmptyBorder(15, 20, 15, 20)
        ));
        // Add the labels
        panel.add(cityNameLabel);
        panel.add(temperatureLabel);
        panel.add(humidityLabel);
        panel.add(windSpeedLabel);
        panel.add(pressureLabel);
        panel.add(visibilityLabel);
        panel.add(sunriseLabel);
        panel.add(sunsetLabel);
        panel.add(conditionLabel);
        return panel;
    }

    private void setupLayout(WeatherBackgroundPanel backgroundPanel) {
        backgroundPanel.setLayout(new BorderLayout(10, 10));
        backgroundPanel.add(searchPanel, BorderLayout.NORTH);

        // Create main content panel with padding
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);
        centerPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Wrap the CurrentWeatherPanel in a JScrollPane
        JScrollPane scrollPane = new JScrollPane(currentWeatherPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        // Create panel for details and explanation with proper spacing
        JPanel detailsAndExplanationPanel = new JPanel(new GridLayout(1, 2));
        detailsAndExplanationPanel.setOpaque(false);
        JScrollPane explanationScrollPane = new JScrollPane(textOutputTextArea);
        explanationScrollPane.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 1, 1, 1, themeManager.getColor("border")),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        JScrollPane weatherDetailsScrollPane = new JScrollPane(weatherDetailsPanel);
        weatherDetailsScrollPane.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 1, 1, 1, themeManager.getColor("border")),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        detailsAndExplanationPanel.add(explanationScrollPane);
        detailsAndExplanationPanel.add(weatherDetailsScrollPane);

        centerPanel.add(detailsAndExplanationPanel, BorderLayout.SOUTH);
        backgroundPanel.add(centerPanel, BorderLayout.CENTER);
    }

    private void searchLocation(String location) {
        if (location == null || location.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please enter a location",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Show loading indicator
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        textOutputTextArea.setText("Fetching weather information...");
        loadingSpinner.start();
        backgroundPanel.add(loadingSpinner, BorderLayout.CENTER);
        backgroundPanel.revalidate();
        backgroundPanel.repaint();

        // Get current weather
        weatherService.getCurrentWeather(location)
            .thenAccept(weather -> {
                SwingUtilities.invokeLater(() -> {
                    backgroundPanel.remove(loadingSpinner);
                    loadingSpinner.stop();
                    backgroundPanel.revalidate();
                    backgroundPanel.repaint();
                    setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    if (weather != null) {
                        currentWeatherPanel.updateWeather(weather);
                        updateWeatherDisplay(weather);
                        fetchWeatherExplanation(weather);
                        checkWeatherAlerts(weather); // Check for weather alerts
                    } else {
                        JOptionPane.showMessageDialog(this,
                            "Failed to fetch weather data. Please check the city name and try again.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    }
                });
            })
            .exceptionally(e -> {
                SwingUtilities.invokeLater(() -> {
                    backgroundPanel.remove(loadingSpinner);
                    loadingSpinner.stop();
                    backgroundPanel.revalidate();
                    backgroundPanel.repaint();
                    setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    handleError("Error fetching weather", e);
                });
                return null;
            });
    }

    private void updateWeatherDisplay(WeatherData weather) {
        // Update the weather data labels
        cityNameLabel.setText("City: " + weather.getCityName());
        temperatureLabel.setText("Temperature: " + weather.getTemperature() + "°C");
        humidityLabel.setText("Humidity: " + weather.getHumidity() + "%");
        windSpeedLabel.setText("Wind Speed: " + weather.getWindSpeed() + " km/h");
        pressureLabel.setText("Pressure: " + weather.getPressure() + " hPa");
        visibilityLabel.setText("Visibility: " + weather.getVisibility() + " km");
        sunriseLabel.setText("Sunrise: " + timeFormat.format(new Date(weather.getSunrise() * 1000)));
        sunsetLabel.setText("Sunset: " + timeFormat.format(new Date(weather.getSunset() * 1000)));
        conditionLabel.setText("Condition: " + weather.getCondition());

        // Add hover effect to labels
        for (JLabel label : new JLabel[]{
            cityNameLabel, temperatureLabel, humidityLabel, windSpeedLabel,
            pressureLabel, visibilityLabel, sunriseLabel, sunsetLabel, conditionLabel
        }) {
            addHoverEffect(label);
        }
    }

    private void addHoverEffect(JLabel label) {
        label.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                label.setForeground(new Color(0, 102, 204));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                label.setForeground(themeManager.getColor("text"));
            }
        });
    }

    private void fetchWeatherExplanation(WeatherData weather) {
        textOutputTextArea.setText("Generating weather explanation...");

        String weatherDescription = formatWeatherDescription(weather);

        executorService.submit(() -> {
            try {
                String explanation = geminiService.getExplanation(weatherDescription);
                SwingUtilities.invokeLater(() -> {
                    textOutputTextArea.setText("<html><body style='width: 300px'>" +
                        "Weather Explanation:<br>" + explanation.replace("\n", "<br>") +
                        "</body></html>");
                });
            } catch (Exception e) {
                SwingUtilities.invokeLater(() -> {
                    handleError("Error getting weather explanation", e);
                });
            }
        });
    }

    private String formatWeatherDescription(WeatherData weather) {
        return String.format(
            "City: %s\nTemperature: %s°C\nCondition: %s\nHumidity: %s%%\nWind Speed: %s km/h\nPressure: %s hPa\nVisibility: %s km\nSunrise: %s\nSunset: %s",
            weather.getCityName(),
            weather.getTemperature(),
            weather.getCondition(),
            weather.getHumidity(),
            weather.getWindSpeed(),
            weather.getPressure(),
            weather.getVisibility(),
            timeFormat.format(new Date(weather.getSunrise() * 1000)),
            timeFormat.format(new Date(weather.getSunset() * 1000))
        );
    }

    private void handleError(String message, Throwable e) {
        String errorMessage = e.getMessage();
        if (errorMessage == null || errorMessage.trim().isEmpty()) {
            errorMessage = e.getClass().getSimpleName();
        }
        JOptionPane.showMessageDialog(this,
            message + ": " + errorMessage,
            "Error",
            JOptionPane.ERROR_MESSAGE);
    }

    private void checkWeatherAlerts(WeatherData weather) {
        // Check for specific weather conditions and display notifications
        if (weather.getTemperature() > 35) {
            displayNotification("High temperature alert! Temperature is above 35°C. Stay hydrated and avoid outdoor activities.");
        }
        if (weather.getHumidity() > 80) {
            displayNotification("High humidity alert! Humidity is above 80%. It might feel muggy. Take precautions to stay comfortable.");
        }
        if (weather.getWindSpeed() > 20) {
            displayNotification("High wind speed alert! Wind speed is above 20 km/h. Secure loose objects and exercise caution outdoors.");
        }
        if (weather.getTemperature() < 15) {
            displayNotification("Low temperature alert! Temperature is below 15°C. Dress warmly to stay comfortable.");
        }
        if (weather.getHumidity() < 30) {
            displayNotification("Low humidity alert! Humidity is below 30%. Use moisturizers and stay hydrated.");
        }
        if (weather.getTemperature() > 15 && weather.getTemperature() <= 35 
    && weather.getHumidity() > 30 && weather.getHumidity() <= 80 
    && weather.getWindSpeed() <= 20) {
    displayNotification("Weather is in a comfortable range. It's a great time to go outdoors or enjoy your day with your loved ones!");
}
    }

    private void displayNotification(String message) {
        JOptionPane.showMessageDialog(this,
            message,
            "Weather Alert",
            JOptionPane.INFORMATION_MESSAGE);
    }

    // Call this method when closing the application
    public void cleanup() {
        executorService.shutdown();
    }
}
