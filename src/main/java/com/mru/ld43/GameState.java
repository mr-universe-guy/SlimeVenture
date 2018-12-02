/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mru.ld43;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.mru.ld43.mob.AIState;
import com.mru.ld43.mob.MobState;
import com.mru.ld43.scene.PhysState;
import com.mru.ld43.scene.SceneState;
import com.mru.ld43.scene.SensorState;
import com.mru.ld43.scene.SlimeState;
import com.mru.ld43.scene.VisualState;
import com.mru.ld43.ui.MenuState;
import com.mru.ld43.ui.PlayerControlState;
import com.mru.ld43.ui.SoundState;
import com.simsilica.es.EntityData;
import com.simsilica.lemur.Axis;
import com.simsilica.lemur.Button;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.FillMode;
import com.simsilica.lemur.Label;
import com.simsilica.lemur.component.BoxLayout;
import java.io.File;

/**
 *
 * @author matt
 */
public class GameState extends BaseAppState{
    private boolean gameStarted = false;
    private NextLevelPopup popup;
    private Object startingLevel;

    public GameState(Object startingLevel) {
        this.startingLevel = startingLevel;
    }
    
    @Override
    protected void initialize(Application app) {
        DataState dataState = new DataState();
        EntityData data = dataState.getData();
        app.getStateManager().attachAll(
                dataState,
                new VisualState(data),
                new PhysState(data),
                new SensorState(data),
                new MobState(data),
                new SlimeState(data),
                new PlayerControlState(data),
                new SceneState(data),
                new AIState(data),
                new SoundState()
        );
        popup = new NextLevelPopup();
    }
    
    /**
     * Displays the "level complete" message and allows the player to choose when
     * to start the next level
     * @param nextLevel 
     */
    public void finishLevel(String nextLevel){
        popup.setNextLevel(nextLevel);
        popup.setVisible(true);
        getState(PlayerControlState.class).setEnabled(false);
    }
    
    @Override
    public void update(float tpf){
        //load the first level only if the game has not yet started
        if(!gameStarted){
            SceneState scene = getState(SceneState.class);
            if(scene != null && scene.isInitialized()){
                gameStarted = true;
                if(startingLevel instanceof String){
                    scene.loadLevel((String)startingLevel);
                } else if(startingLevel instanceof File){
                    scene.loadLevelFromFile((File)startingLevel);
                }
            }
        }
    }

    @Override
    protected void cleanup(Application app) {
        getStateManager().detach(getState(VisualState.class));
        getStateManager().detach(getState(PhysState.class));
        getStateManager().detach(getState(SensorState.class));
        getStateManager().detach(getState(MobState.class));
        getStateManager().detach(getState(SlimeState.class));
        getStateManager().detach(getState(PlayerControlState.class));
        getStateManager().detach(getState(SceneState.class));
        getStateManager().detach(getState(AIState.class));
        getStateManager().detach(getState(SoundState.class));
        app.enqueue(() -> {getStateManager().detach(getState(DataState.class));});
    }

    @Override
    protected void onEnable() {
        
    }

    @Override
    protected void onDisable() {
        
    }
    
    private class NextLevelPopup{
        private final Container popupContainer;
        private String nextLevel;
        
        public NextLevelPopup(){
            popupContainer = new Container(new BoxLayout(Axis.Y,FillMode.None));
            Label complete = new Label("You completed the level!");
            popupContainer.addChild(complete);
            Button next = new Button("Continue");
            next.addClickCommands((Command) -> {
                if(nextLevel != null){
                    getState(SceneState.class).loadLevel(nextLevel);
                    setVisible(false);
                } else{
                    getStateManager().detach(GameState.this);
                    setVisible(false);
                    getStateManager().attach(new MenuState());
                }
            });
            popupContainer.addChild(next);
        }
        
        public void setVisible(boolean visible){
            if(visible){
                Camera cam = getApplication().getCamera();
                int width = cam.getWidth();
                int height = cam.getHeight();
                Vector3f popupSize = popupContainer.getPreferredSize();
                popupContainer.setLocalTranslation((width/2)-(popupSize.x/2),
                        (height/2)+(popupSize.y/2), 0);
                ((SlimeApp)getApplication()).getGuiNode().attachChild(popupContainer);
            } else{
                popupContainer.removeFromParent();
            }
        }

        public String getNextLevel() {
            return nextLevel;
        }

        public void setNextLevel(String nextLevel) {
            this.nextLevel = nextLevel;
        }
        
    }
}
