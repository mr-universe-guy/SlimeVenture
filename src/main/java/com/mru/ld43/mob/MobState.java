/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mru.ld43.mob;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.mru.ld43.scene.Collider;
import com.mru.ld43.scene.PhysState;
import com.mru.ld43.ui.SoundState;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntitySet;
import java.util.ArrayList;
import java.util.List;
import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.RaycastResult;
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
        mobs = data.getEntities(Mob.class, Driver.class, Collider.class);
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
            if(b != null){
                //simple horizontal movement
                Mob mob = e.get(Mob.class);
                Driver drive = e.get(Driver.class);
                b.applyForce(new Vector2(drive.x*mob.speed, 0));
                //basic jump, must be falling or grounded
                if(drive.y > 0.1f && b.getLinearVelocity().y <= 0){
                    //test grounded
                    Collider col = e.get(Collider.class);
                    Vector2 pos = b.getWorldCenter();
                    Vector2 end = new Vector2(pos.x, pos.y-col.getHeight()*0.55);
                    List<RaycastResult> results = new ArrayList<>();
                    if(world.raycast(pos, end, true, true, results)){
                        for(RaycastResult r : results){
                            if(!r.getBody().equals(b)){
                                //grounded
                                b.applyImpulse(new Vector2(0,mob.speed));
                                getState(SoundState.class).playClip(SoundState.getRandomSlap());
                            }
                        }
                    }
                }
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
