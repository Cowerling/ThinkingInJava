package io;

import java.util.prefs.*;

/**
 * Created by cowerling on 16-2-1.
 */
public class PreferencesDemo {
    public static void main(String[] args) throws Exception {
        Preferences preferences = Preferences.userNodeForPackage(PreferencesDemo.class);
        preferences.put("Location", "Oz");
        preferences.put("Footwear", "Ruby Slippers");
        preferences.putInt("Companions", 4);
        preferences.putBoolean("Are there witches?", true);

        int usageCount = preferences.getInt("UsageCount", 0);
        usageCount++;
        preferences.putInt("usageCount", usageCount);

        for(String key : preferences.keys())
            System.out.println(key + ": " + preferences.get(key, null));

        System.out.println("How many companions does Dorothy have? " + preferences.getInt("Companions", 0));
    }
}
