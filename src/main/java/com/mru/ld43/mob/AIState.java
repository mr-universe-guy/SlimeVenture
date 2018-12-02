/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mru.ld43.mob;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;
import com.simsilica.es.EntitySet;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Handles supes dumb slimes
 * @author matt
 */
public class AIState extends BaseAppState{
    private final Map<EntityId, AIHandler> aiMap = new HashMap<>();
    private final Random random = new Random();
    private final EntityData data;
    private EntitySet ais;

    public AIState(EntityData data) {
        this.data = data;
    }

    @Override
    protected void initialize(Application app) {
        ais = data.getEntities(AI.class);
    }

    @Override
    public void update(float tpf) {
        if(ais.applyChanges()){
            for(Entity e : ais.getRemovedEntities()){
                aiMap.remove(e.getId());
            }
            for(Entity e : ais.getAddedEntities()){
                aiMap.put(e.getId(), new AIHandler(timeToNextAction()));
            }
        }
        for(Entity e : ais){
            AIHandler handler = aiMap.get(e.getId());
            if((handler.actionHold -= tpf) > 0f){
                //parse behaviour, for now just assume everything will hop
                e.set(new Driver(0,1f));
            } else if((handler.timer -= tpf) <= 0){
                handler.timer = timeToNextAction();
                handler.actionHold = 0.25f;
            } else{
                if(e.get(Driver.class) != new Driver(0,0)){
                    e.set(new Driver(0,0));
                }
            }
        }
    }
    
    private float timeToNextAction(){
        return 0.5f+(random.nextFloat()*2);
    }

    @Override
    protected void cleanup(Application app) {
        ais.release();
    }

    @Override
    protected void onEnable() {
        
    }

    @Override
    protected void onDisable() {
        
    }
    
    private class AIHandler{
        float timer = 0;
        float actionHold = 0.25f;
        
        private AIHandler(float timer){
            this.timer = timer;
        }
    }
}
