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
import org.dyn4j.dynamics.Step;
import org.dyn4j.dynamics.StepAdapter;
import org.dyn4j.dynamics.World;

/**
 * Makes slimes the correct color/size
 * @author matt
 */
public class SlimeState extends BaseAppState{
    public static final float SLIMESCALE = 0.1f;
    private final EntityData data;
    private final SlimeListener slimeListener = new SlimeListener();
    private EntitySet slimes, collidedSlimes;

    public SlimeState(EntityData data) {
        this.data = data;
    }

    @Override
    protected void initialize(Application app) {
        slimes = data.getEntities(Slime.class);
        collidedSlimes = data.getEntities(Slime.class, Contact.class);
        slimes.applyChanges();
        getState(PhysState.class).getWorld().addListener(slimeListener);
    }

    @Override
    public void update(float tpf) {
        if(slimes.applyChanges()){
            for(Entity e : slimes.getRemovedEntities()){
                data.removeComponent(e.getId(), Model.class);
            }
            for(Entity e : slimes.getAddedEntities()){
                Position pos = e.get(Position.class);
                Slime slime = e.get(Slime.class);
                float colSize = slime.getSize()*(SLIMESCALE*2);
                e.set(new Collider(false, colSize, colSize));
                e.set(new Model(Model.SLIME, slime.getColor(), slime.getSize()));
            }
            for(Entity e : slimes.getChangedEntities()){
                Slime slime = e.get(Slime.class);
                //todo: change color and size
                if(slime.getSize() <= 0){
                    //kill slime
                }
                float colSize = slime.getSize()*(SLIMESCALE*2);
                e.set(new Collider(false, colSize, colSize));
                e.set(new Model(Model.SLIME, slime.getColor(), slime.getSize()));
            }
        }
    }
    
    public EntityId spawnSlime(float posX, float posY, String color, int size){
        EntityId id = data.createEntity();
        Position pos = new Position(posX, posY);
        data.setComponents(id,
                pos,
                new Slime(color, size)
        );
        
        return id;
    }

    @Override
    protected void cleanup(Application app) {
        slimes.release();
        getState(PhysState.class).getWorld().removeListener(slimeListener);
    }

    @Override
    protected void onEnable() {
        
    }

    @Override
    protected void onDisable() {
        
    }
    
    /**
     * Slime Listener looks for collisions between slimes and determines if they
     * should grow or shrink/die
     */
    private class SlimeListener extends StepAdapter{
        //lets listen to the beginning of each new step
        @Override
        public void begin(Step step, World world) {
            if(collidedSlimes.applyChanges()){
                for(Entity e : collidedSlimes){
                    Slime slime = e.get(Slime.class);
                    for(EntityId id : e.get(Contact.class).getContactIds()){
                        //we only care about slime x slime
                        Entity e2 = collidedSlimes.getEntity(id);
                        if(e2 == null) continue;
                        Slime slime2 = e2.get(Slime.class);
                        if(slime.getColor().equals(slime2.getColor())){
                            //we combine
                            if(slime.getSize() > slime2.getSize()){
                                //kill slime2 and grow slime 1
                                combine(e, slime, e2);
                            } else{
                                combine(e2, slime2, e);
                            }
                        } else{
                            //we attak
                            if(slime.getSize() > slime2.getSize()){
                                attack(e, slime, e2);
                            } else{
                                attack(e2, slime2, e);
                            }
                        }
                    }
                }
            }
        }
        
        private void combine(Entity e1, Slime slime, Entity e2){
            data.removeEntity(e2.getId());
            e1.set(new Slime(slime.getColor(), slime.getSize()+1));
        }
        
        private void attack(Entity e1, Slime slime, Entity e2){
            data.removeEntity(e2.getId());
            e1.set(new Slime(slime.getColor(), slime.getSize()-1));
        }
    }
}
