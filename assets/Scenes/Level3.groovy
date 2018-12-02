import com.mru.ld43.scene.SceneState
import com.mru.ld43.scene.SlimeState
import com.mru.ld43.scene.SensorState
import com.mru.ld43.scene.EntityListener
import com.mru.ld43.scene.Sensor
import com.mru.ld43.ui.PlayerControlState
import com.mru.ld43.GameState

scene.spawnPlayer(2,3)
scene.addWall(0,2,1,5)
scene.addWall(8,0,17,1)
scene.addWall(7,1,1,1)
scene.addWall(10,1,1,1)
scene.addWall(14,2.7,1,3)
scene.addWall(17,2,1,5)

slime.spawnSlime(8.5,2,"Green",1)
slime.spawnSlime(5,2,"Blue",1)
slime.spawnSlime(12,2,"Blue",1)

finishedSensor = new Sensor(17,1,2,2)
finishedListener = new EntityListener(player.getPlayerId())
Runnable finish = {
    sensor.removeSensor(finishedSensor)
    game.finishLevel("Scenes/Level4.groovy")
}
finishedListener.addEvent(finish)
finishedSensor.addListener(finishedListener)
sensor.addSensor(finishedSensor)
