/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mru.ld43.ui;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioData;
import com.jme3.audio.AudioNode;
import com.jme3.scene.Node;
import com.mru.ld43.SlimeApp;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Plays sounds
 * @author matt
 */
public class SoundState extends BaseAppState{
    private static final Random RANDOM = new Random();
    public static final String SLAP1 = "Slap1";
    public static final String SLAP2 = "Slap2";
    public static final String SLAP3 = "Slap3";
    private final Map<String, AudioNode> audioMap = new HashMap<>();

    @Override
    protected void initialize(Application app) {
        Node root = ((SlimeApp)app).getRootNode();
        AssetManager am = app.getAssetManager();
        audioMap.put(SLAP1, new AudioNode(am, "Audio/Slap1.wav", AudioData.DataType.Buffer));
        audioMap.put(SLAP2, new AudioNode(am, "Audio/Slap2.wav", AudioData.DataType.Buffer));
        audioMap.put(SLAP3, new AudioNode(am, "Audio/Slap3.wav", AudioData.DataType.Buffer));
        
        for(AudioNode node : audioMap.values()){
            root.attachChild(node);
        }
    }
    
    public static String getRandomSlap(){
        int v = RANDOM.nextInt(3);
        switch(v){
            case 0: return SLAP1;
            case 1: return SLAP2;
            default: return SLAP3;
        }
    }
    
    public void playClip(String key){
        AudioNode node = audioMap.get(key);
        node.playInstance();
    }

    @Override
    protected void cleanup(Application app) {
        for(AudioNode node : audioMap.values()){
            node.removeFromParent();
        }
    }

    @Override
    protected void onEnable() {
        
    }

    @Override
    protected void onDisable() {
        
    }
    
}
