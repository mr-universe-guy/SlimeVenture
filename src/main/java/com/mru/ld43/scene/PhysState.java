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
                Body b = new Body();
                Rectangle rec = new Rectangle(1,1);
                //check if slime
                Slime slime = data.getComponent(e.getId(), Slime.class);
                if(slime != null){
                    rec = new Rectangle(slime.getSize()*SlimeState.SLIMESCALE,
                        slime.getSize()*SlimeState.SLIMESCALE);
                }
                //check if wall
                Wall wall = data.getComponent(e.getId(), Wall.class);
                if(wall != null){
                    rec = new Rectangle(wall.getWidth(), wall.getHeight());
                }
                b.addFixture(rec);
                //kinematic and mask
                Collider col = e.get(Collider.class);
                if(col.isKinematic()){
                    b.setMassType(MassType.INFINITE);
                } else{
                    b.setMassType(MassType.NORMAL);
                }
                b.updateMass();
                //set initial world pos
                Position pos = e.get(Position.class);
                b.translate(pos.getX(), pos.getY());
                world.addBody(b);
                bodyMap.put(e.getId(), b);
            }
        }
        for(Entity e : colliders){
            //apply physics
            Body b = bodyMap.get(e.getId());
            Vector2 physPos = b.getWorldCenter();
            e.set(new Position((float)physPos.x, (float)physPos.y));
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
