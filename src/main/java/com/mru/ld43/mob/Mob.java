/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mru.ld43.mob;

import com.simsilica.es.EntityComponent;

/**
 * Mobs move
 * @author matt
 */
public class Mob implements EntityComponent{
    protected final float speed;

    public Mob(float speed) {
        this.speed = speed;
    }

    public float getSpeed() {
        return speed;
    }
    
}
