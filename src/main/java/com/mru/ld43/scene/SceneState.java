/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mru.ld43.scene;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.mru.ld43.SlimeApp;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;
import com.simsilica.es.EntitySet;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.groovy.control.CompilationFailedException;

/**
 * Reads groovy scripts that create a new scene
 * @author matt
 */
public class SceneState extends BaseAppState{
    private static final float WALLWIDTH = 2f;
    private final Map<EntityId, Spatial> spatMap = new HashMap<>();
    private final List<EntityId> sceneObjects = new ArrayList<>();
    private final Node wallNode = new Node("Walls");
    protected final Binding binding = new Binding();
    protected final GroovyShell shell = new GroovyShell(binding);
    private final EntityData data;
    private EntitySet walls;
    private Material wallMat;

    public SceneState(EntityData data) {
        this.data = data;
    }

    @Override
    protected void initialize(Application app) {
        //walls
        walls = data.getEntities(Wall.class, Position.class, Collider.class);
        ((SlimeApp)app).getRootNode().attachChild(wallNode);
        wallMat = new Material(app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        wallMat.getAdditionalRenderState().setWireframe(true);
        wallMat.setColor("Color", ColorRGBA.Magenta);
        //groovy
        binding.setProperty("scene", this);
        binding.setProperty("slime", getState(SlimeState.class));
        app.enqueue(()->{
            loadLevel("Scenes/Test.groovy");
        });
    }

    @Override
    public void update(float tpf) {
        if(walls.applyChanges()){
            for(Entity e : walls.getAddedEntities()){
                //build wall geo
                Wall wall = e.get(Wall.class);
                Position pos = e.get(Position.class);
                Box box = new Box(wall.getWidth()/2, wall.getHeight()/2, WALLWIDTH/2);
                Geometry wallGeo = new Geometry("Wall", box);
                wallGeo.setLocalTranslation(pos.getX(), pos.getY(), 0);
                wallGeo.setMaterial(wallMat);
                wallNode.attachChild(wallGeo);
                spatMap.put(e.getId(), wallGeo);
                
            }
            for(Entity e : walls.getChangedEntities()){
                Position pos = e.get(Position.class);
                Spatial wallSpat = spatMap.get(e.getId());
                wallSpat.setLocalTranslation(pos.getX(), pos.getY(), 0);
            }
        }
    }

    @Override
    protected void cleanup(Application app) {
        for(EntityId id : sceneObjects){
            data.removeEntity(id);
        }
    }
    
    public void loadLevel(String resource){
        //clear previous level
        Iterator<EntityId> it = sceneObjects.iterator();
        while(it.hasNext()){
            EntityId id = it.next();
            data.removeEntity(id);
            it.remove();
        }
        //we are not caching scripts atm
        try {
            binding.setProperty("scene", this);
            Script s = shell.parse(SceneState.class.getClassLoader().getResource(resource).toURI());
            s.run();
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
                new Collider(true, Collider.GROUND_GROUP, Collider.PLAYER_GROUP|Collider.SLIME_GROUP)
        );
        sceneObjects.add(id);
    }
}
