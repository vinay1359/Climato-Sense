package com.weatherapp.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.weatherapp.model.WeatherData;
public class CurrentWeatherPanel extends JPanel {
    private final JLabel temperatureLabel;
    private final JLabel humidityLabel;
    private final JLabel windSpeedLabel;
    private final JLabel conditionLabel;
    private final JLabel cityLabel;
    private final JLabel pressureLabel;
    private final JLabel visibilityLabel;
    private final JLabel sunriseLabel;
    private final JLabel sunsetLabel;
    private final JLabel weatherIconLabel;

    public CurrentWeatherPanel() {
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(new Color(240, 240, 240)); // Light gray background

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridwidth = GridBagConstraints.REMAINDER; // Make labels span the entire width

        cityLabel = createStyledLabel("", 24);
        temperatureLabel = createStyledLabel("--°C", 48);
        conditionLabel = createStyledLabel("--", 18);
        humidityLabel = createStyledLabel("Humidity: --%", 16);
        windSpeedLabel = createStyledLabel("Wind: -- m/s", 16);
        pressureLabel = createStyledLabel("Pressure: -- hPa", 16);
        visibilityLabel = createStyledLabel("Visibility: -- m", 16);
        sunriseLabel = createStyledLabel("Sunrise: --", 16);
        sunsetLabel = createStyledLabel("Sunset: --", 16);
        weatherIconLabel = new JLabel();

        add(cityLabel, gbc);
        add(weatherIconLabel, gbc);
        add(temperatureLabel, gbc);
        add(conditionLabel, gbc);
        add(humidityLabel, gbc);
        add(windSpeedLabel, gbc);
        add(pressureLabel, gbc);
        add(visibilityLabel, gbc);
        add(sunriseLabel, gbc);
        add(sunsetLabel, gbc);
    }

    private JLabel createStyledLabel(String text, int fontSize) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, fontSize));
        label.setForeground(Color.BLACK);
        return label;
    }

    public void updateWeather(WeatherData weather) {
        cityLabel.setText(weather.getCityName());
        temperatureLabel.setText(String.format("%.1f°C", weather.getTemperature()));
        humidityLabel.setText(String.format("Humidity: %.1f%%", weather.getHumidity()));
        windSpeedLabel.setText(String.format("Wind: %.1f m/s", weather.getWindSpeed()));
        pressureLabel.setText(String.format("Pressure: %.1f hPa", weather.getPressure()));
        visibilityLabel.setText(String.format("Visibility: %d m", weather.getVisibility()));

        // Format sunrise and sunset times to HH:mm format
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        sunriseLabel.setText("Sunrise: " + timeFormat.format(new Date(weather.getSunrise() * 1000)));
        sunsetLabel.setText("Sunset: " + timeFormat.format(new Date(weather.getSunset() * 1000)));

        conditionLabel.setText(weather.getCondition());
        
    }
}
