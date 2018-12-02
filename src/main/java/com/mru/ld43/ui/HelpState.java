/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mru.ld43.ui;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.input.KeyInput;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.jme3.texture.Texture;
import com.mru.ld43.SlimeApp;
import com.simsilica.lemur.Panel;
import com.simsilica.lemur.component.QuadBackgroundComponent;
import com.simsilica.lemur.input.FunctionId;
import com.simsilica.lemur.input.InputMapper;
import com.simsilica.lemur.input.InputState;
import com.simsilica.lemur.input.StateFunctionListener;

/**
 * Simple help menu with the info splash
 * @author matt
 */
public class HelpState extends BaseAppState implements StateFunctionListener{
    private static final String HELPERGROUP = "Helper";
    private static final FunctionId RETURN = new FunctionId(HELPERGROUP, "RETURN");
    private final Node helpNode = new Node("Help");

    @Override
    protected void initialize(Application app) {
        //inputs
        InputMapper mapper = ((SlimeApp)app).getMapper();
        mapper.activateGroup(HELPERGROUP);
        mapper.map(RETURN, KeyInput.KEY_BACK);
        mapper.map(RETURN, KeyInput.KEY_LEFT);
        mapper.map(RETURN, KeyInput.KEY_RIGHT);
        mapper.map(RETURN, KeyInput.KEY_UP);
        
        mapper.addStateListener(this, RETURN);
        //ui
        Camera cam = app.getCamera();
        int width = cam.getWidth();
        int height = cam.getHeight();
        Panel instructions = new Panel(width, height);
        instructions.setLocalTranslation(0, height, 0);
        Texture image = app.getAssetManager().loadTexture("Textures/HelpMenu.png");
        instructions.setBackground(new QuadBackgroundComponent(image));
        helpNode.attachChild(instructions);
        ((SlimeApp)app).getGuiNode().attachChild(helpNode);
    }

    @Override
    protected void cleanup(Application app) {
        helpNode.removeFromParent();
        
        InputMapper mapper = ((SlimeApp)app).getMapper();
        mapper.deactivateGroup(HELPERGROUP);
        mapper.removeMapping(RETURN, KeyInput.KEY_BACK);
        mapper.removeMapping(RETURN, KeyInput.KEY_LEFT);
        mapper.removeMapping(RETURN, KeyInput.KEY_RIGHT);
        mapper.removeMapping(RETURN, KeyInput.KEY_UP);
        
        mapper.removeStateListener(this, RETURN);
    }
    
    public void returnToMenu(){
        getStateManager().detach(this);
        getStateManager().attach(new MenuState());
    }

    @Override
    protected void onEnable() {
        
    }

    @Override
    protected void onDisable() {
        
    }

    @Override
    public void valueChanged(FunctionId fi, InputState is, double d) {
        if(fi.equals(RETURN) && is.equals(InputState.Positive)){
            returnToMenu();
        }
    }
    
}
