/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mru.ld43.ui;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.mru.ld43.GameState;
import com.mru.ld43.SlimeApp;
import com.simsilica.lemur.Axis;
import com.simsilica.lemur.Button;
import com.simsilica.lemur.Command;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.FillMode;
import com.simsilica.lemur.component.BorderLayout;
import com.simsilica.lemur.component.BoxLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;

/**
 * Lets you go straight to a level, mostly for testing
 * @author matt
 */
public class LevelSelectState extends BaseAppState{
    private final String[] levels;
    private final Node uiNode = new Node("UI");
    private final Container menuCont = new Container(new BorderLayout());
    private final Container levelCont = new Container(new BoxLayout(Axis.Y, FillMode.Even));
    private JFileChooser chooser = new JFileChooser();
    private Camera cam;
    
    public LevelSelectState(){
        //read level list file and populate the levels array;
        InputStream stream = LevelSelectState.class.getClassLoader().getResourceAsStream("Scenes/Scenes");
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        List<String> levelList = new ArrayList<>();
        try {
            String line;
            while((line = reader.readLine()) != null){
                levelList.add(line);
            }
        } catch (IOException ex) {
            Logger.getLogger(LevelSelectState.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            try {
                reader.close();
            } catch (IOException ex) {
                Logger.getLogger(LevelSelectState.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        levels = levelList.toArray(new String[levelList.size()]);
    }

    @Override
    protected void initialize(Application app) {
        chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        cam = app.getCamera();
        Container buttonCont = new Container(new BorderLayout());
        Button prev = new Button("<");
        buttonCont.addChild(prev, BorderLayout.Position.West);
        Button next = new Button(">");
        buttonCont.addChild(next, BorderLayout.Position.East);
        menuCont.addChild(buttonCont, BorderLayout.Position.North);
        //levels
        fillLevels(0);
        menuCont.addChild(levelCont, BorderLayout.Position.Center);
        //from file
        Button fromFile = new Button("File");
        fromFile.addClickCommands(new Command(){
            @Override
            public void execute(Object s) {
                if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
                    loadLevel(chooser.getSelectedFile());
                }
            }
        });
        menuCont.addChild(fromFile, BorderLayout.Position.South);
        uiNode.attachChild(menuCont);
        centerMenu();
        ((SlimeApp)app).getGuiNode().attachChild(uiNode);
    }
    
    private void fillLevels(int startIndex){
        levelCont.clearChildren();
        for(int i=startIndex; i<levels.length; i++){
            String level = levels[i];
            Button selectLevel = new Button(level);
            selectLevel.addClickCommands(new Command(){
                @Override
                public void execute(Object s) {
                    loadLevel(level);
                }
            });
            levelCont.addChild(selectLevel);
        }
    }
    
    private void centerMenu(){
        int width = cam.getWidth();
        int height = cam.getHeight();
        Vector3f menuSize = menuCont.getPreferredSize();
        menuCont.setLocalTranslation((width/2)-(menuSize.x/2), (height/2)+(menuSize.y/2), 0);
    }
    
    private void loadLevel(Object level){
        if(level instanceof String){
            String levelPath = "Scenes/"+level+".groovy";
            getStateManager().attach(new GameState(levelPath));
        } else if(level instanceof File){
            getStateManager().attach(new GameState((File)level));
        }
        getStateManager().detach(this);
    }

    @Override
    protected void cleanup(Application app) {
        uiNode.removeFromParent();
    }

    @Override
    protected void onEnable() {
        
    }

    @Override
    protected void onDisable() {
        
    }
    
}
