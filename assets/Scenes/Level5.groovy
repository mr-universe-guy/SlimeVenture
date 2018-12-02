import com.mru.ld43.scene.SceneState
import com.mru.ld43.scene.SlimeState
import com.mru.ld43.scene.SensorState
import com.mru.ld43.scene.EntityListener
import com.mru.ld43.scene.Sensor
import com.mru.ld43.ui.PlayerControlState
import com.mru.ld43.GameState

scene.spawnPlayer(2,2,1)
scene.addWall(0,2,1,5)
scene.addWall(8,0,15,1)
scene.addWall(16,2,1,5)
scene.addWall(14.5,1.25,2,1.5)

slime.spawnSlime(4,2,"Blue",2)
slime.spawnSlime(6,2,"Green",3)
slime.spawnSlime(8,2,"Blue",4)
slime.spawnSlime(10,2,"Green",3)
slime.spawnSlime(12,2,"Blue",2)

finishedSensor = new Sensor(16,2,2,2)
finishedListener = new EntityListener(player.getPlayerId())
Runnable finish = {
    sensor.removeSensor(finishedSensor)
    game.finishLevel()
}
finishedListener.addEvent(finish)
finishedSensor.addListener(finishedListener)
sensor.addSensor(finishedSensor)
