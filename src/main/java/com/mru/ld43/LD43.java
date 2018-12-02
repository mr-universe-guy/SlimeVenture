package com.mru.ld43;

import com.jme3.system.AppSettings;
import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LD43 {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            //setup logging
            String path = System.getProperty("user.dir")+File.separator+"log.txt";
            System.out.println(path);
            FileHandler fh = new FileHandler(path);
            Logger logger = Logger.getLogger(LD43.class.getName()).getParent();
            logger.addHandler(fh);
            fh.setFormatter(new SimpleFormatter());
        } catch (IOException ex) {
            Logger.getLogger(LD43.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(LD43.class.getName()).log(Level.SEVERE, null, ex);
        }
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
