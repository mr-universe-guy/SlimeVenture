import com.mru.ld43.scene.SceneState
import com.mru.ld43.scene.SlimeState
import com.mru.ld43.scene.SensorState
import com.mru.ld43.scene.EntityListener
import com.mru.ld43.scene.Sensor
import com.mru.ld43.ui.PlayerControlState

scene.spawnPlayer(0,2)
scene.addWall(0,-1,20,1)
slime.spawnSlime(5,5,"Blue", 1)
slime.spawnSlime(-5,5,"Green",1)

playerSensor = new EntityListener(player.getPlayerId())
Runnable test = {
    println "Test"
    scene.restartLevel()
}
playerSensor.addEvent(test)
exitSensor = new Sensor(10,0,2,2)
exitSensor.addListener(playerSensor)
sensor.addSensor(exitSensor)
