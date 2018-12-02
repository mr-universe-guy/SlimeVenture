import com.mru.ld43.scene.SceneState
import com.mru.ld43.scene.SlimeState
import com.mru.ld43.scene.SensorState
import com.mru.ld43.scene.EntityListener
import com.mru.ld43.scene.Sensor
import com.mru.ld43.ui.PlayerControlState
import com.mru.ld43.GameState

scene.spawnPlayer(1,3)
scene.addWall(-1,4,1,11)
scene.addWall(5,-1,11,1)
scene.addWall(11,-0.5,1,2)
scene.addWall(13,0,3,1)
scene.addWall(15,1,1,3)

spawnSlimeSensor = new Sensor(11,0,1,1)
spawnSlimeListener = new EntityListener(player.getPlayerId())
Runnable spawnSlime = {
    slime.spawnSlime(3,3,"Blue",1)
    sensor.removeSensor(spawnSlimeSensor)
}
spawnSlimeListener.addEvent(spawnSlime)
spawnSlimeSensor.addListener(spawnSlimeListener)
sensor.addSensor(spawnSlimeSensor)

finishedSensor = new Sensor(14,1,2,2)
finishedListener = new EntityListener(player.getPlayerId())
Runnable finish = {
    sensor.removeSensor(finishedSensor)
    game.finishLevel("Scenes/Level2.groovy")
}
finishedListener.addEvent(finish)
finishedSensor.addListener(finishedListener)
sensor.addSensor(finishedSensor)
