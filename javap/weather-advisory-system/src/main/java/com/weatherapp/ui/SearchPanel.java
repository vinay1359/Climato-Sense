package com.weatherapp.ui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.function.Consumer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SearchPanel extends JPanel {
    private final JTextField searchField;
    private final JButton searchButton;

    public SearchPanel(Consumer<String> onSearch) {
        setLayout(new FlowLayout(FlowLayout.CENTER));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setOpaque(false); // Make the panel transparent

        searchField = new JTextField(20);
        searchButton = new JButton("Search");

        searchField.setPreferredSize(new Dimension(200, 30));
        searchButton.setPreferredSize(new Dimension(100, 30));

        searchButton.addActionListener(e -> onSearch.accept(searchField.getText()));
        searchField.addActionListener(e -> onSearch.accept(searchField.getText()));

        add(searchField);
        add(searchButton);
    }
}
