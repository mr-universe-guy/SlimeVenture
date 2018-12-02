/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mru.ld43;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.mru.ld43.mob.AIState;
import com.mru.ld43.mob.MobState;
import com.mru.ld43.scene.PhysState;
import com.mru.ld43.scene.SceneState;
import com.mru.ld43.scene.SlimeState;
import com.mru.ld43.scene.VisualState;
import com.mru.ld43.ui.PlayerControlState;
import com.mru.ld43.ui.SoundState;
import com.simsilica.es.EntityData;

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
                new VisualState(data),
                new PhysState(data),
                new MobState(data),
                new SlimeState(data),
                new PlayerControlState(data),
                new SceneState(data),
                new AIState(data),
                new SoundState()
        );
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
