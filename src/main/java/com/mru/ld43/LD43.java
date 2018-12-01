package com.mru.ld43;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFrame;

public class LD43 {
    private static final JFrame frame = new JFrame();
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //custom starter
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Container cont = frame.getContentPane();
        JButton start = new JButton("Start");
        start.addActionListener((ActionEvent e) -> {
            System.out.println("Starting LD43");
            SlimeApp app = new SlimeApp();
            app.setShowSettings(false);
            app.start();
            frame.dispose();
        });
        cont.add(start, BorderLayout.CENTER);
        
        //display
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
