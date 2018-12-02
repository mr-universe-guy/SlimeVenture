/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mru.ld43.scene;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.mru.ld43.GameState;
import com.mru.ld43.ui.PlayerControlState;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.groovy.control.CompilationFailedException;

/**
 * Reads groovy scripts that create a new scene
 * @author matt
 */
public class SceneState extends BaseAppState{
    private final List<EntityId> sceneObjects = new ArrayList<>();
    protected final Binding binding = new Binding();
    protected final GroovyShell shell = new GroovyShell(binding);
    protected Script currentLevel;
    private final EntityData data;

    public SceneState(EntityData data) {
        this.data = data;
    }

    @Override
    protected void initialize(Application app) {
        //groovy
        binding.setProperty("scene", this);
        binding.setProperty("slime", getState(SlimeState.class));
        binding.setProperty("sensor", getState(SensorState.class));
        binding.setProperty("player", getState(PlayerControlState.class));
        binding.setProperty("game", getState(GameState.class));
    }

    @Override
    public void update(float tpf) {
    }

    @Override
    protected void cleanup(Application app) {
        for(EntityId id : sceneObjects){
            data.removeEntity(id);
        }
    }
    
    protected void clearLevel(){
        Iterator<EntityId> it = sceneObjects.iterator();
        while(it.hasNext()){
            EntityId id = it.next();
            data.removeEntity(id);
            it.remove();
        }
        getState(SlimeState.class).clearSlimes();
    }
    
    public void restartLevel(){
        clearLevel();
        getApplication().enqueue(() -> {
            currentLevel.run();
        });
    }
    
    public void loadLevel(String resource){
        clearLevel();
        //we are not caching scripts atm
        try {
            binding.setProperty("scene", this);
            currentLevel = shell.parse(SceneState.class.getClassLoader().getResource(resource).toURI());
            currentLevel.run();
        } catch (URISyntaxException | CompilationFailedException | IOException ex) {
            Logger.getLogger(SceneState.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void onEnable() {
        
    }

    @Override
    protected void onDisable() {
        
    }
    
    public void addWall(float xPos, float yPos, float width, float height){
        EntityId id = data.createEntity();
        data.setComponents(id,
                new Position(xPos, yPos),
                new Wall(width, height),
                new Collider(true, width, height),
                new Model(Model.WALL, width, height)
        );
        sceneObjects.add(id);
    }
    
    public void spawnPlayer(float xPos, float yPos){
        getState(PlayerControlState.class).spawnPlayer(xPos, yPos);
        getState(PlayerControlState.class).setEnabled(true);
    }
}
