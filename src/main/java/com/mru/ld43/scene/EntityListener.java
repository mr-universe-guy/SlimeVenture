/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mru.ld43.scene;

import com.simsilica.es.EntityId;
import java.util.ArrayList;
import java.util.List;
import org.dyn4j.dynamics.DetectResult;

/**
 *
 * @author matt
 */
public class EntityListener implements SensorListener{
    private final EntityId id;
    private List<Runnable> events = new ArrayList<>();
    
    public EntityListener(EntityId id){
        this.id = id;
    }
    
    public void addEvent(Runnable event){
        events.add(event);
    }

    @Override
    public void sensed(List<DetectResult> results) {
        for(DetectResult result : results){
            EntityId bodyId = (EntityId)result.getBody().getUserData();
            if(id.equals(bodyId)){
                for(Runnable event : events){
                    event.run();
                }
            }
        }
    }
    
}
