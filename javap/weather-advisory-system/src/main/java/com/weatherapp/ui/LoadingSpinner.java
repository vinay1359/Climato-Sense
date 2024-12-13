package com.weatherapp.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JPanel;

public class LoadingSpinner extends JPanel implements Runnable {
    private boolean isRunning;
    private ScheduledExecutorService executor;
    private int angle = 0;

    public LoadingSpinner() {
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int x = getWidth() / 2;
        int y = getHeight() / 2;
        int radius = Math.min(getWidth(), getHeight()) / 5;

        g2d.setColor(Color.GRAY);
        g2d.setStroke(new BasicStroke(5));
        g2d.drawArc(x - radius, y - radius, radius * 2, radius * 2, 90, angle);
    }

    @Override
    public void run() {
        executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(this::updateAngle, 0, 50, TimeUnit.MILLISECONDS);
    }

    private void updateAngle() {
        if (!isRunning) {
            executor.shutdown();
            return;
        }
        angle += 3;
        if (angle >= 360) {
            angle = 0;
        }
        repaint();
    }

    public void start() {
        isRunning = true;
        Thread thread = new Thread(this);
        thread.start();
    }

    public void stop() {
        isRunning = false;
        if (executor != null) {
            executor.shutdown();
        }
    }

    @Override
    public void addNotify() {
        super.addNotify();
        start();
    }

    @Override
    public void removeNotify() {
        super.removeNotify();
        stop();
    }
}