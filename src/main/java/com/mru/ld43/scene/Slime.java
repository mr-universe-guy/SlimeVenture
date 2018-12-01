/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mru.ld43.scene;

import com.jme3.math.ColorRGBA;
import com.simsilica.es.EntityComponent;

/**
 *
 * @author matt
 */
public class Slime implements EntityComponent{
    private final ColorRGBA color;

    public Slime(ColorRGBA color) {
        this.color = color;
    }

    public ColorRGBA getColor() {
        return color;
    }
}
