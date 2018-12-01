/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mru.ld43.mob;

import com.simsilica.es.EntityComponent;

/**
 * Drives mobs in 2 axis
 * @author matt
 */
public class Driver implements EntityComponent{
    protected final float x,y;

    public Driver(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
    
}
