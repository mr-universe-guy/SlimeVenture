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
import org.dyn4j.dynamics.Step;
import org.dyn4j.dynamics.StepAdapter;
import org.dyn4j.dynamics.World;

/**
 * Makes slimes the correct color/size
 * @author matt
 */
public class SlimeState extends BaseAppState{
    public static final float SLIMESCALE = 0.1f;
    private final Map<EntityId, Spatial> spatMap = new HashMap<>();
    private final Node slimeNode = new Node("Slimes");
    private final EntityData data;
    private final SlimeListener slimeListener = new SlimeListener();
    private EntitySet slimes, collidedSlimes;
    private Material slimeMat;

    public SlimeState(EntityData data) {
        this.data = data;
    }

    @Override
    protected void initialize(Application app) {
        slimes = data.getEntities(Slime.class, Position.class);
        collidedSlimes = data.getEntities(Slime.class, Contact.class);
        slimeMat = new Material(app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        ((SlimeApp)app).getRootNode().attachChild(slimeNode);
        slimes.applyChanges();
        for(Entity e : slimes){
            addSlime(e);
        }
        getState(PhysState.class).getWorld().addListener(slimeListener);
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
                Slime slime = e.get(Slime.class);
                //todo: change color and size
                float colSize = slime.getSize()*(SLIMESCALE*2);
                e.set(new Collider(false, colSize, colSize));
                Position pos = e.get(Position.class);
                spat.setLocalTranslation(pos.getX(), pos.getY(), 0);
            }
        }
    }
    
    private void addSlime(Entity e){
        Position pos = e.get(Position.class);
        Slime slime = e.get(Slime.class);
        Spatial spat = createSlimeModel(slime.getColor(), slime.getSize());
        float colSize = slime.getSize()*(SLIMESCALE*2);
        e.set(new Collider(false, colSize, colSize));
        slimeNode.attachChild(spat);
        spatMap.put(e.getId(), spat);
        spat.setLocalTranslation(pos.getX(), pos.getY(), 0);
        System.out.println("Slime created at "+pos);
    }
    
    private Spatial createSlimeModel(String color, int size){
        float slimeScale = size*SLIMESCALE;
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
    
    public EntityId spawnSlime(float posX, float posY, String color, int size){
        EntityId id = data.createEntity();
        Position pos = new Position(posX, posY);
        data.setComponents(id,
                pos,
                new Slime(color, size)
        );
        
        return id;
    }

    @Override
    protected void cleanup(Application app) {
        slimeNode.removeFromParent();
        spatMap.clear();
        slimes.release();
        getState(PhysState.class).getWorld().removeListener(slimeListener);
    }

    @Override
    protected void onEnable() {
        
    }

    @Override
    protected void onDisable() {
        
    }
    
    /**
     * Slime Listener looks for collisions between slimes and determines if they
     * should grow or shrink/die
     */
    private class SlimeListener extends StepAdapter{
        //lets listen to the beginning of each new step
        @Override
        public void begin(Step step, World world) {
            if(collidedSlimes.applyChanges()){
                for(Entity e : collidedSlimes){
                    Slime slime = e.get(Slime.class);
                    for(EntityId id : e.get(Contact.class).getContactIds()){
                        //we only care about slime x slime
                        Entity e2 = collidedSlimes.getEntity(id);
                        if(e2 == null) continue;
                        Slime slime2 = e2.get(Slime.class);
                        if(slime.getColor().equals(slime2.getColor())){
                            //we combine
                            if(slime.getSize() > slime2.getSize()){
                                //kill slime2 and grow slime 1
                                combine(e, slime, e2);
                            } else{
                                combine(e2, slime2, e);
                            }
                        } else{
                            //we attak
                            if(slime.getSize() > slime2.getSize()){
                                attack(e, slime, e2);
                            } else{
                                attack(e2, slime2, e);
                            }
                        }
                    }
                }
            }
        }
        
        private void combine(Entity e1, Slime slime, Entity e2){
            data.removeEntity(e2.getId());
            e1.set(new Slime(slime.getColor(), slime.getSize()+1));
        }
        
        private void attack(Entity e1, Slime slime, Entity e2){
            data.removeEntity(e2.getId());
            e1.set(new Slime(slime.getColor(), slime.getSize()-1));
        }
    }
}
