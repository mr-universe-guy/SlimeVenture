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
    public static final int PLAYER_GROUP = 1;
    public static final int SLIME_GROUP = 1 << 1;
    public static final int GROUND_GROUP = 1 << 2;
    public static final int POWERUP_GROUP = 1 << 3;
    private final boolean kinematic;
    private final int collisionMask;
    private final int collisionGroup;

    public Collider(boolean kinematic, int collisionMask, int collisionGroup) {
        this.kinematic = kinematic;
        this.collisionMask = collisionMask;
        this.collisionGroup = collisionGroup;
    }

    public boolean isKinematic() {
        return kinematic;
    }

    public int getCollisionMask() {
        return collisionMask;
    }

    public int getCollisionGroup() {
        return collisionGroup;
    }
    
}
