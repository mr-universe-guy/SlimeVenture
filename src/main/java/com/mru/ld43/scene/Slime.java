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
public class Slime implements EntityComponent{
    public static final String GREEN = "Green";
    public static final String BLUE = "Blue";
    public static final String RED = "Red";
    
    private final String color;
    private final int size;

    public Slime(String color, int size) {
        this.color = color;
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public int getSize() {
        return size;
    }
}
