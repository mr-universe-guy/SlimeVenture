/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mru.ld43.ui;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.mru.ld43.GameState;
import com.mru.ld43.SlimeApp;
import com.simsilica.lemur.Axis;
import com.simsilica.lemur.Button;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.FillMode;
import com.simsilica.lemur.Label;
import com.simsilica.lemur.component.BoxLayout;

/**
 *
 * @author matt
 */
public class MenuState extends BaseAppState{
    private final Node menuNode = new Node("Menu");

    @Override
    protected void initialize(Application app) {
        int width = app.getCamera().getWidth();
        int height = app.getCamera().getHeight();
        Label intro = new Label("Welcome to LD43 'Sacrifices Must be Made'");
        Vector3f labelSize = intro.getPreferredSize();
        intro.setLocalTranslation(((width/2)-labelSize.x/2), (height/2)+(labelSize.y/2)+100,0);
        menuNode.attachChild(intro);
        Container buttonContainer = new Container(new BoxLayout(Axis.Y, FillMode.None));
        //start game
        Button start = new Button("Start");
        start.addClickCommands((Command) -> {
            getStateManager().detach(this);
            getStateManager().attach(new GameState("Scenes/Level1.groovy"));
        });
        buttonContainer.addChild(start);
        //level selector
        Button select = new Button("Select Level");
        select.addClickCommands((Command) -> {
            getStateManager().detach(this);
            getStateManager().attach(new LevelSelectState());
        });
        buttonContainer.addChild(select);
        //help menu
        Button help = new Button("Help");
        help.addClickCommands((Command)->{
            getStateManager().detach(this);
            getStateManager().attach(new HelpState());
        });
        buttonContainer.addChild(help);
        //add container to menu
        Vector3f buttonScale = buttonContainer.getPreferredSize();
        buttonContainer.setLocalTranslation((width/2)-(buttonScale.x/2),
                (height/2)+(buttonScale.y/2), 0);
        menuNode.attachChild(buttonContainer);
        //attach menu to gui
        ((SlimeApp)app).getGuiNode().attachChild(menuNode);
    }

    @Override
    protected void cleanup(Application app) {
        menuNode.removeFromParent();
    }

    @Override
    protected void onEnable() {
        
    }

    @Override
    protected void onDisable() {
        
    }
    
}
