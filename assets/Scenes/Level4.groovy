import com.mru.ld43.scene.SceneState
import com.mru.ld43.scene.SlimeState
import com.mru.ld43.scene.SensorState
import com.mru.ld43.scene.EntityListener
import com.mru.ld43.scene.Sensor
import com.mru.ld43.ui.PlayerControlState
import com.mru.ld43.GameState

scene.spawnPlayer(2,7)
scene.addWall(0,4,1,9)
scene.addWall(5.75,4,10.5,1)
scene.addWall(6,0,11,1)
scene.addWall(12,4,1,9)
scene.addWall(5,1,1,1)
scene.addWall(9,1,1,1)

slime.spawnSlime(6,7,"Blue",1)
slime.spawnSlime(8,7,"Blue",1)
slime.spawnSlime(10,7,"Blue",1)
slime.spawnSlime(5,2.5,"Green",1)
slime.spawnSlime(7,2,"Green",1)
slime.spawnSlime(9,2.5,"Green",1)
slime.spawnSlime(3,2,"Blue", 4)

finishedSensor = new Sensor(0.5,1,1,1)
finishedListener = new EntityListener(player.getPlayerId())
Runnable finish = {
    sensor.removeSensor(finishedSensor)
    game.finishLevel("Scenes/Level5.groovy")
}
finishedListener.addEvent(finish)
finishedSensor.addListener(finishedListener)
sensor.addSensor(finishedSensor)
