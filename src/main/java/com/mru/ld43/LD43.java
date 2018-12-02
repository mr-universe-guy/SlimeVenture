package com.mru.ld43;

import com.jme3.system.AppSettings;

public class LD43 {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SlimeApp app = new SlimeApp();
        app.setShowSettings(false);
        AppSettings settings = new AppSettings(true);
        settings.setFullscreen(false);
        settings.setResolution(600, 400);
        app.setDisplayStatView(false);
        app.setSettings(settings);
        app.start();
    }
}
