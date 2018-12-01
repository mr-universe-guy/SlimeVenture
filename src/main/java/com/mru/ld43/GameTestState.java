/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mru.ld43;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.math.ColorRGBA;
import com.mru.ld43.scene.PhysState;
import com.mru.ld43.scene.Position;
import com.mru.ld43.scene.Slime;
import com.mru.ld43.scene.SlimeState;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;

/**
 * Master test state, for testing
 * @author matt
 */
public class GameTestState extends BaseAppState{

    @Override
    protected void initialize(Application app) {
        DataState dataState = new DataState();
        EntityData data = dataState.getData();
        app.getStateManager().attachAll(
                dataState,
                new PhysState(data),
                new SlimeState(data)
        );
//        EntityId test = data.createEntity();
//        data.setComponents(test, new Position(0,0), new Slime(ColorRGBA.Green));
    }

    @Override
    protected void cleanup(Application app) {
        getStateManager().detach(getState(DataState.class));
    }

    @Override
    protected void onEnable() {
        
    }

    @Override
    protected void onDisable() {
        
    }
    
}
