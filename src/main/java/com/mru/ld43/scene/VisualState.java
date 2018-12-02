/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mru.ld43.scene;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;
import com.mru.ld43.SlimeApp;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;
import com.simsilica.es.EntitySet;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author matt
 */
public class VisualState extends BaseAppState{
    private final Map<EntityId, Actor> spatMap = new HashMap<>();
    private final Node visualNode = new Node("Visuals");
    private final EntityData data;
    private Material slimeMat, wallMat;
    private EntitySet models;

    public VisualState(EntityData data) {
        this.data = data;
    }
    
    @Override
    protected void initialize(Application app) {
        ((SlimeApp)app).getRootNode().attachChild(visualNode);
        AssetManager am = app.getAssetManager();
        models = data.getEntities(Model.class, Position.class);
        slimeMat = new Material(am, "Common/MatDefs/Misc/Unshaded.j3md");
        wallMat = new Material(am, "Common/MatDefs/Misc/Unshaded.j3md");
        Texture stone = am.loadTexture("Textures/Stone.png");
        stone.setWrap(Texture.WrapMode.Repeat);
        wallMat.setTexture("ColorMap", stone);
    }

    @Override
    public void update(float tpf) {
        if(models.applyChanges()){
            for(Entity e : models.getRemovedEntities()){
                Actor actor = spatMap.remove(e.getId());
                actor.spat.removeFromParent();
            }
            for(Entity e : models.getAddedEntities()){
                Model model = e.get(Model.class);
                Spatial spat = parseModel(model);
                spatMap.put(e.getId(), new Actor(spat, model));
                Position pos = e.get(Position.class);
                spat.setLocalTranslation(pos.getX(), pos.getY(), 0);
                visualNode.attachChild(spat);
            } for(Entity e : models.getChangedEntities()){
                //first determine if the visual model needs to change
                Model model = e.get(Model.class);
                Actor actor = spatMap.get(e.getId());
                Spatial spat = actor.spat;
                if(!actor.model.equals(model)){
                    //we need to rebuild the spatial
                    spat.removeFromParent();
                    spat = parseModel(model);
                    actor.spat = spat;
                    actor.model = model;
                    visualNode.attachChild(spat);
                }
                //then update the position
                Position pos = e.get(Position.class);
                spat.setLocalTranslation(pos.getX(), pos.getY(), 0);
            }
        }
    }
    
    private Spatial parseModel(Model model){
        Spatial spat = null;
        if(model.getModel().equals(Model.SLIME)){
            String color = (String)model.getArgs()[0];
            int size = (int)model.getArgs()[1];
            spat = createSlimeModel(color, size);
        } else if(model.getModel().equals(Model.WALL)){
            float width = (float)model.getArgs()[0]/2;
            float height = (float)model.getArgs()[1]/2;
            Box box = new Box(width, height, 1f);
            Geometry wallGeo = new Geometry("Wall", box);
            wallGeo.setMaterial(wallMat);
            spat = wallGeo;
        }
        
        return spat;
    }
    
    private Spatial createSlimeModel(String color, int size){
        float slimeScale = size*SlimeState.SLIMESCALE;
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
        return geo;
    }

    @Override
    protected void cleanup(Application app) {
        models.release();
        for(Actor actor : spatMap.values()){
            actor.spat.removeFromParent();
        }
        spatMap.clear();
        visualNode.removeFromParent();
    }

    @Override
    protected void onEnable() {
        
    }

    @Override
    protected void onDisable() {
        
    }
    
    private class Actor{
        private Spatial spat;
        private Model model;

        public Actor(Spatial spat, Model model) {
            this.spat = spat;
            this.model = model;
        }
        
    }
}
