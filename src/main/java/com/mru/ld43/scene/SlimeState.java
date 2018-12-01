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
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.mru.ld43.SlimeApp;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;
import com.simsilica.es.EntitySet;
import java.util.HashMap;
import java.util.Map;

/**
 * Makes slimes the correct color/size
 * @author matt
 */
public class SlimeState extends BaseAppState{
    private final Map<EntityId, Spatial> spatMap = new HashMap<>();
    private final Node slimeNode = new Node("Slimes");
    private final EntityData data;
    private EntitySet slimes;
    private Material slimeMat;

    public SlimeState(EntityData data) {
        this.data = data;
    }

    @Override
    protected void initialize(Application app) {
        slimes = data.getEntities(Slime.class, Position.class);
        slimeMat = new Material(app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        ((SlimeApp)app).getRootNode().attachChild(slimeNode);
        slimes.applyChanges();
        for(Entity e : slimes){
            addSlime(e);
        }
        app.enqueue(() -> {
            spawnSlime(0,0,Slime.GREEN, 5);
        });
    }

    @Override
    public void update(float tpf) {
        if(slimes.applyChanges()){
            for(Entity e : slimes.getRemovedEntities()){
                Spatial spat = spatMap.remove(e.getId());
                spat.removeFromParent();
            }
            for(Entity e : slimes.getAddedEntities()){
                addSlime(e);
            }
            for(Entity e : slimes.getChangedEntities()){
                Spatial spat = spatMap.get(e.getId());
                Position pos = e.get(Position.class);
                spat.setLocalTranslation(pos.x, pos.y, 0);
            }
        }
    }
    
    private void addSlime(Entity e){
        Position pos = e.get(Position.class);
        Slime slime = e.get(Slime.class);
        Spatial spat = createSlimeModel(Slime.GREEN, 5);
        slimeNode.attachChild(spat);
        spatMap.put(e.getId(), spat);
        spat.setLocalTranslation(pos.x, pos.y, 0);
    }
    
    private Spatial createSlimeModel(String color, int size){
        float slimeScale = size*0.1f;
        Geometry geo = new Geometry("Slime", new Box(slimeScale, slimeScale, slimeScale));
        //TODO: Cache materials per color to save draw calls
        Material mat = new Material(slimeMat.getMaterialDef());
        ColorRGBA col;
        switch(color){
            case Slime.GREEN: col = ColorRGBA.Green;
            break;
            case Slime.BLUE: col = ColorRGBA.Blue;
            break;
            case Slime.RED: col = ColorRGBA.Red;
            break;
            default: col = ColorRGBA.White;
            break;
        }
        mat.setColor("Color", col);
        geo.setMaterial(mat);
        System.out.println("Made a "+color+" slime");
        return geo;
    }
    
    public EntityId spawnSlime(float posX, float posY, String color, int size){
        EntityId id = data.createEntity();
        
        data.setComponents(id,
                new Position(posX, posY),
                new Slime(color, size),
                new Collider(false, 
                        Collider.GROUND_GROUP|Collider.PLAYER_GROUP,
                        Collider.SLIME_GROUP)
        );
        
        return id;
    }

    @Override
    protected void cleanup(Application app) {
        slimeNode.removeFromParent();
        spatMap.clear();
        slimes.release();
    }

    @Override
    protected void onEnable() {
        
    }

    @Override
    protected void onDisable() {
        
    }
    
}
