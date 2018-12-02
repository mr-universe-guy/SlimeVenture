import com.mru.ld43.scene.SceneState
import com.mru.ld43.scene.SlimeState
import com.mru.ld43.scene.SensorState
import com.mru.ld43.scene.EntityListener
import com.mru.ld43.scene.Sensor
import com.mru.ld43.ui.PlayerControlState
import com.mru.ld43.GameState

scene.spawnPlayer(6,4)
scene.addWall(0,2,1,5)
scene.addWall(2,0,3,1)
scene.addWall(7,1,9,1)
scene.addWall(3,4,1,3.5)
scene.addWall(13,1.5,3,2)
scene.addWall(15,3,1,5)

slime.spawnSlime(10,3,"Blue", 1)
slime.spawnSlime(13.5,4,"Blue",1)

finishedSensor = new Sensor(1,1,2,2)
finishedListener = new EntityListener(player.getPlayerId())
Runnable finish = {
    sensor.removeSensor(finishedSensor)
    game.finishLevel("Scenes/Level3.groovy")
}
finishedListener.addEvent(finish)
finishedSensor.addListener(finishedListener)
sensor.addSensor(finishedSensor)
