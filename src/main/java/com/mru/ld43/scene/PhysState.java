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
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.Step;
import org.dyn4j.dynamics.StepAdapter;
import org.dyn4j.dynamics.World;
import org.dyn4j.dynamics.contact.ContactAdapter;
import org.dyn4j.dynamics.contact.ContactPoint;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Rectangle;
import org.dyn4j.geometry.Vector2;

/**
 *
 * @author matt
 */
public class PhysState extends BaseAppState{
    protected final Map<EntityId, Body> bodyMap = new HashMap<>();
    protected final Map<EntityId, Set<EntityId>> contactMap = new HashMap<>();
    protected final World world = new World();
    private final Contacts contactListener = new Contacts();
    private final Stepper stepListener = new Stepper();
    private final EntityData data;
    private EntitySet colliders;
    

    public PhysState(EntityData data) {
        this.data = data;
    }

    @Override
    protected void initialize(Application app) {
        colliders = data.getEntities(Collider.class);
        world.setGravity(new Vector2(0,-10));
        world.addListener(stepListener);
        world.addListener(contactListener);
    }

    @Override
    public void update(float tpf) {
        world.update(tpf);
        if(colliders.applyChanges()){
            //add and remove
            for(Entity e : colliders.getRemovedEntities()){
                Body b = bodyMap.remove(e.getId());
                world.removeBody(b);
            }
            for(Entity e : colliders.getAddedEntities()){
                Body b = new Body();
                Collider col = e.get(Collider.class);
                updateBody(b, col);
                //userdata
                b.setUserData(e.getId());
                //set initial world pos
                Position pos = data.getComponent(e.getId(), Position.class);
                if(pos != null){
                    b.translate(pos.getX(), pos.getY());
                }
                world.addBody(b);
                bodyMap.put(e.getId(), b);
            }
            for(Entity e : colliders.getChangedEntities()){
                Body b = bodyMap.get(e.getId());
                b.removeAllFixtures();
                Collider col = e.get(Collider.class);
                updateBody(b, col);
            }
        }
        for(Entity e : colliders){
            //apply physics
            Body b = bodyMap.get(e.getId());
            Vector2 physPos = b.getWorldCenter();
            e.set(new Position((float)physPos.x, (float)physPos.y));
        }
    }
    
    private void updateBody(Body b, Collider col){
        Rectangle rec = new Rectangle(col.getWidth(), col.getHeight());
        b.addFixture(rec);
        if(col.isKinematic()){
            b.setMassType(MassType.INFINITE);
        } else{
            b.setMassType(MassType.FIXED_ANGULAR_VELOCITY);
        }
        b.updateMass();
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

    public World getWorld() {
        return world;
    }
    
    public Body getBodyFromId(EntityId id){
        return bodyMap.get(id);
    }
    
    private class Contacts extends ContactAdapter{
        @Override
        public boolean begin(ContactPoint point) {
            EntityId id1 = (EntityId)point.getBody1().getUserData();
            EntityId id2 = (EntityId)point.getBody2().getUserData();
            Set<EntityId> id1Current = contactMap.get(id1);
            Set<EntityId> id2Current = contactMap.get(id2);
            if(id1Current == null){
                id1Current = new HashSet<>();
                contactMap.put(id1, id1Current);
            }
            if(id2Current == null){
                id2Current = new HashSet<>();
                contactMap.put(id2, id2Current);
            }
            id1Current.add(id2);
            id2Current.add(id1);
            return true;
        }
    }
    
    private class Stepper extends StepAdapter{
        @Override
        public void end(Step step, World world) {
            //publish the previous steps results to ecs
            for(EntityId id : contactMap.keySet()){
                Set<EntityId> contacts = contactMap.get(id);
                Contact contact = new Contact(contacts.toArray(new EntityId[contacts.size()]));
                data.setComponent(id, contact);
            }
            contactMap.clear();
        }
    }
}
