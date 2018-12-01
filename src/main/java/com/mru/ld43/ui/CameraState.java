/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mru.ld43.ui;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.renderer.Camera;
import com.mru.ld43.scene.Position;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;
import com.simsilica.es.WatchedEntity;

/**
 * Simple camera state that follows an entity along a 2d plane
 * @author matt
 */
public class CameraState extends BaseAppState{
    private final EntityData data;
    private Camera cam;
    protected WatchedEntity target;

    public CameraState(EntityData data) {
        this.data = data;
    }

    @Override
    protected void initialize(Application app) {
        cam = app.getCamera();
    }

    public WatchedEntity getTarget() {
        return target;
    }

    public void setTarget(EntityId target) {
        this.target = data.watchEntity(target, Position.class);
    }

    @Override
    public void update(float tpf) {
        if(target != null && target.applyChanges()){
            
        }
    }

    @Override
    protected void cleanup(Application app) {
        target.release();
    }

    @Override
    protected void onEnable() {
        
    }

    @Override
    protected void onDisable() {
        
    }
    
}
