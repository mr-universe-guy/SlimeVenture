/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mru.ld43.ui;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.input.KeyInput;
import com.mru.ld43.SlimeApp;
import com.mru.ld43.mob.Driver;
import com.mru.ld43.scene.Contact;
import com.mru.ld43.scene.Position;
import com.mru.ld43.scene.Slime;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;
import com.simsilica.es.WatchedEntity;
import com.simsilica.lemur.input.AnalogFunctionListener;
import com.simsilica.lemur.input.FunctionId;
import com.simsilica.lemur.input.InputMapper;
import com.simsilica.lemur.input.InputState;
import com.simsilica.lemur.input.StateFunctionListener;

/**
 *
 * @author matt
 */
public class PlayerControlState extends BaseAppState implements AnalogFunctionListener,
        StateFunctionListener{
    public static final String PLAYERGROUP = "PlayerGroup";
    public static final FunctionId MOVE_X = new FunctionId(PLAYERGROUP, "X_MOVEMENT");
    public static final FunctionId JUMP = new FunctionId(PLAYERGROUP, "Y_MOVEMENT");
    private final EntityData data;
    protected WatchedEntity player;
    private float xInput = 0;
    private float yInput = 0;

    public PlayerControlState(EntityData data) {
        this.data = data;
        player = data.watchEntity(data.createEntity(),
                Position.class,
                Slime.class,
                Contact.class);
    }

    @Override
    protected void initialize(Application app) {
        InputMapper mapper = ((SlimeApp)app).getMapper();
        mapper.activateGroup(PLAYERGROUP);
        mapper.map(MOVE_X, InputState.Negative, KeyInput.KEY_LEFT);
        mapper.map(MOVE_X, InputState.Positive, KeyInput.KEY_RIGHT);
        mapper.map(JUMP, InputState.Positive, KeyInput.KEY_UP);
        
        mapper.addAnalogListener(this, MOVE_X);
        mapper.addStateListener(this, JUMP);
        //add camera state
        CameraState cam = new CameraState(data);
        cam.setTarget(player.getId());
        getStateManager().attach(cam);
    }

    @Override
    public void update(float tpf) {
        if(player.applyChanges()){
            
        }
        player.set(new Driver(xInput, yInput));
    }
    
    public EntityId getPlayerId(){
        return player.getId();
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
        if(fi.equals(MOVE_X)){
            xInput = (float)d;
        }
    }

    @Override
    public void valueChanged(FunctionId fi, InputState is, double d) {
        if(fi.equals(JUMP)){
            if(is.equals(InputState.Positive)){
                yInput = 1f;
            } else{
                yInput = 0;
            }
        }
    }
    
}
