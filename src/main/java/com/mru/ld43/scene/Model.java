/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mru.ld43.scene;

import com.simsilica.es.EntityComponent;
import java.util.Arrays;
import java.util.Objects;

/**
 * Marks an entity to have a model built
 * @author matt
 */
public class Model implements EntityComponent{
    public static final String SLIME = "Slime";
    public static final String WALL = "Wall";
    private final String model;
    private final Object[] args;

    public Model(String model, Object... args) {
        this.model = model;
        this.args = args;
    }

    public String getModel() {
        return model;
    }

    public Object[] getArgs() {
        return args;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.model);
        hash = 37 * hash + Arrays.deepHashCode(this.args);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Model other = (Model) obj;
        if (!Objects.equals(this.model, other.model)) {
            return false;
        }
        if (!Arrays.deepEquals(this.args, other.args)) {
            return false;
        }
        return true;
    }
    
}
