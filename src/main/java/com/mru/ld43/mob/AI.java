/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mru.ld43.mob;

import com.simsilica.es.EntityComponent;

/**
 * Supes dumb AI for slimes
 * @author matt
 */
public class AI implements EntityComponent{
    protected final Behaviour behaviour;

    public AI(Behaviour behaviour) {
        this.behaviour = behaviour;
    }

    public Behaviour getBehaviour() {
        return behaviour;
    }
    
    public enum Behaviour{
        IDLE,
        LEFT,
        RIGHT,
        JUMP
    }
}
