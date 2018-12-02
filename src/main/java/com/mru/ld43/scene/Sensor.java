/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mru.ld43.scene;

import java.util.ArrayList;
import java.util.List;
import org.dyn4j.dynamics.DetectResult;

/**
 *
 * @author matt
 */
public class Sensor{
    private float posX, posY, width, height;
    private List<SensorListener> listeners = new ArrayList<>();

    public Sensor(float posX, float posY, float width, float height) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
    }

    public float getPosX() {
        return posX;
    }

    public float getPosY() {
        return posY;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
    
    public void addListener(SensorListener listener){
        listeners.add(listener);
    }
    
    public void alertListeners(List<DetectResult> results){
        for(SensorListener listener : listeners){
            listener.sensed(results);
        }
    }
}
