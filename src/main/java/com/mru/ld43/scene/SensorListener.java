/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mru.ld43.scene;

import java.util.List;
import org.dyn4j.dynamics.DetectResult;

/**
 *
 * @author matt
 */
public interface SensorListener {
    public abstract void sensed(List<DetectResult> results);
}
