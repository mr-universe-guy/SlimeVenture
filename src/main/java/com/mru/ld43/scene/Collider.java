/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mru.ld43.scene;

import com.simsilica.es.EntityComponent;

/**
 *
 * @author matt
 */
public class Collider implements EntityComponent{
    private final boolean kinematic;
    private final float width, height;

    public Collider(boolean kinematic, float width, float height) {
        this.kinematic = kinematic;
        this.width = width;
        this.height = height;
    }

    public boolean isKinematic() {
        return kinematic;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
    
}
