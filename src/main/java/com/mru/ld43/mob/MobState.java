/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mru.ld43.mob;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.mru.ld43.scene.PhysState;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntitySet;
import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.Step;
import org.dyn4j.dynamics.StepListener;
import org.dyn4j.dynamics.World;
import org.dyn4j.geometry.Vector2;

/**
 *
 * @author matt
 */
public class MobState extends BaseAppState implements StepListener{
    private final EntityData data;
    private EntitySet mobs;
    private PhysState phys;
    private World world;

    public MobState(EntityData data) {
        this.data = data;
    }

    @Override
    protected void initialize(Application app) {
        phys = getState(PhysState.class);
        world = phys.getWorld();
        world.addListener(this);
        mobs = data.getEntities(Mob.class, Driver.class);
    }

    @Override
    protected void cleanup(Application app) {
        world.removeListener(this);
    }

    @Override
    protected void onEnable() {
        
    }

    @Override
    protected void onDisable() {
        
    }

    @Override
    public void begin(Step step, World world) {
        mobs.applyChanges();
        for(Entity e : mobs){
            Body b = phys.getBodyFromId(e.getId());
//            System.out.println("Entity has body "+e.getId()+":"+b);
            if(b != null){
                //simple horizontal movement
                Mob mob = e.get(Mob.class);
                Driver drive = e.get(Driver.class);
                b.applyForce(new Vector2(drive.x * mob.speed, 0));
            }
        }
    }

    @Override
    public void updatePerformed(Step step, World world) {
        
    }

    @Override
    public void postSolve(Step step, World world) {
        
    }

    @Override
    public void end(Step step, World world) {
        
    }
    
}
