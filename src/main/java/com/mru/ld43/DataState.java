/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mru.ld43;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.simsilica.es.EntityData;
import com.simsilica.es.base.DefaultEntityData;

/**
 * Simple wrapper for entity data to treat it like an appstate
 * @author matt
 */
public class DataState extends BaseAppState{
    private final EntityData data;
    
    public DataState(){
        data = new DefaultEntityData();
    }
    
    public DataState(EntityData data){
        this.data = data;
    }

    @Override
    protected void initialize(Application app) {
        
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

    public EntityData getData() {
        return data;
    }
    
}
