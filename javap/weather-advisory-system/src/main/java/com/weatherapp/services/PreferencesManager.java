package com.weatherapp.services;

import java.util.prefs.Preferences;

public class PreferencesManager {
    private static final String DEFAULT_LOCATION = "defaultLocation";
    private static final String PREFERRED_UNITS = "preferredUnits";

    private final Preferences preferences;

    public PreferencesManager() {
        preferences = Preferences.userNodeForPackage(PreferencesManager.class);
    }

    public void setDefaultLocation(String location) {
        preferences.put(DEFAULT_LOCATION, location);
    }

    public String getDefaultLocation() {
        return preferences.get(DEFAULT_LOCATION, "");
    }

    public void setPreferredUnits(String units) {
        preferences.put(PREFERRED_UNITS, units);
    }

    public String getPreferredUnits() {
        return preferences.get(PREFERRED_UNITS, "Celsius");
    }
}
