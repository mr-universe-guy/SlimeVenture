/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mru.ld43.scene;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;
import com.simsilica.es.EntitySet;
import java.util.HashMap;
import java.util.Map;
import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.World;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Rectangle;
import org.dyn4j.geometry.Vector2;

/**
 *
 * @author matt
 */
public class PhysState extends BaseAppState{
    protected final Map<EntityId, Body> bodyMap = new HashMap<>();
    protected final World world = new World();
    private final EntityData data;
    private EntitySet colliders;

    public PhysState(EntityData data) {
        this.data = data;
    }

    @Override
    protected void initialize(Application app) {
        colliders = data.getEntities(Collider.class, Position.class);
        world.setGravity(new Vector2(0,-10));
    }

    @Override
    public void update(float tpf) {
        world.update(tpf);
        if(colliders.applyChanges()){
            //add and remove
            for(Entity e : colliders.getRemovedEntities()){
                
            }
            for(Entity e : colliders.getAddedEntities()){
                //for now just adding a basic unit box to all colliders, just to
                //see the phys
                Body b = new Body();
                Rectangle rec = new Rectangle(1,1);
                b.addFixture(rec);
                b.setMassType(MassType.NORMAL);
                b.updateMass();
                world.addBody(b);
                bodyMap.put(e.getId(), b);
            }
        }
        for(Entity e : colliders){
            //apply physics
            Body b = bodyMap.get(e.getId());
            Vector2 physPos = b.getWorldCenter();
            e.set(new Position((float)physPos.x, (float)physPos.y));
//            System.out.println(physPos);
        }
    }

    @Override
    protected void cleanup(Application app) {
        colliders.release();
    }

    @Override
    protected void onEnable() {
        
    }

    @Override
    protected void onDisable() {
        
    }
    
}
