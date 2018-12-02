/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mru.ld43.scene;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.simsilica.es.EntityData;
import java.util.ArrayList;
import java.util.List;
import org.dyn4j.dynamics.DetectResult;
import org.dyn4j.dynamics.Step;
import org.dyn4j.dynamics.StepListener;
import org.dyn4j.dynamics.World;
import org.dyn4j.geometry.Rectangle;

/**
 *
 * @author matt
 */
public class SensorState extends BaseAppState implements StepListener{
    private final List<Sensor> sensors = new ArrayList<>();
    private final EntityData ed;
    private World world;

    public SensorState(EntityData ed) {
        this.ed = ed;
    }

    @Override
    protected void initialize(Application app) {
        world = getState(PhysState.class).getWorld();
        world.addListener(this);
    }

    @Override
    public void update(float tpf) {
    }

    @Override
    protected void cleanup(Application app) {
    }

    @Override
    protected void onEnable() {
        
    }

    @Override
    protected void onDisable() {
        
    }
    
    public void addSensor(Sensor sensor){
        sensors.add(sensor);
    }
    
    public void removeSensor(Sensor sensor){
        getApplication().enqueue(() -> {
            sensors.remove(sensor);
        });
    }

    @Override
    public void begin(Step step, World world) {
        
    }

    @Override
    public void updatePerformed(Step step, World world) {
        
    }

    @Override
    public void postSolve(Step step, World world) {
        
    }

    @Override
    public void end(Step step, World world) {
        for(Sensor sensor : sensors){
            Rectangle rec = new Rectangle(sensor.getWidth()*2, sensor.getHeight()*2);
            rec.translate(sensor.getPosX(), sensor.getPosY());
            List<DetectResult> results = new ArrayList<>();
            if(world.detect(rec, results)){
                sensor.alertListeners(results);
            }
        }
    }
    
}
