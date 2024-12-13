package com.weatherapp.model;

import java.util.Date;

public class ForecastData {
     Date date;
     double maxTemp;
     double minTemp;
     String condition;
     String iconCode;

    public ForecastData(Date date, double maxTemp, double minTemp, 
                       String condition, String iconCode) {
        this.date = date;
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
        this.condition = condition;
        this.iconCode = iconCode;
    }

    // Getters
    public Date getDate() { return date; }
    public double getMaxTemp() { return maxTemp; }
    public double getMinTemp() { return minTemp; }
    public String getCondition() { return condition; }
    public String getIconCode() { return iconCode; }
}