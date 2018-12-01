/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mru.ld43.ui;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.mru.ld43.SlimeApp;
import com.simsilica.es.WatchedEntity;
import com.simsilica.lemur.input.AnalogFunctionListener;
import com.simsilica.lemur.input.FunctionId;
import com.simsilica.lemur.input.InputMapper;

/**
 *
 * @author matt
 */
public class PlayerControlState extends BaseAppState implements AnalogFunctionListener{
    public static final String PLAYERGROUP = "PlayerGroup";
    public static final FunctionId MOVE_X = new FunctionId(PLAYERGROUP, "X_MOVEMENT");
    public static final FunctionId MOVE_Y = new FunctionId(PLAYERGROUP, "Y_MOVEMENT");
    protected WatchedEntity player;

    @Override
    protected void initialize(Application app) {
        InputMapper mapper = ((SlimeApp)app).getMapper();
        mapper.activateGroup(PLAYERGROUP);
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

    @Override
    public void valueActive(FunctionId fi, double d, double d1) {
        
    }
    
}
