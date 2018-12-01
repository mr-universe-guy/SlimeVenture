/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mru.ld43;

import com.jme3.app.SimpleApplication;
import com.jme3.math.ColorRGBA;
import com.simsilica.lemur.input.InputMapper;

/**
 *
 * @author matt
 */
public class SlimeApp extends SimpleApplication{
    protected InputMapper mapper;

    @Override
    public void simpleInitApp() {
        //disable starting states
        this.flyCam.setEnabled(false);
        //initialize input mapper
        mapper = new InputMapper(inputManager);
        //pick a bg color
        viewPort.setBackgroundColor(ColorRGBA.DarkGray);
        //let's play
        System.out.println("Hello Ludum Dare 43!");
        stateManager.attach(new GameTestState());
    }

    public InputMapper getMapper() {
        return mapper;
    }
    
}
