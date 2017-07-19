package tech.johari.gamelights

import com.github.kittinunf.fuel.Fuel
import tech.johari.gamelights.Main

class FlashThread(val color: LightSignal, val speed: Long) : Thread() {
    var state = true
    val startTime = System.currentTimeMillis()

    override fun run() {
        if(!state) {
            // Turn it on
            Fuel.get("http://${Main.PI_LOCAL_IP}:${Main.PI_API_PORT}/ha-api?cmd=1&scope=${color.name.toLowerCase()}").response { request, response, result -> }
        } else {
            // Turn it off
            Fuel.get("http://${Main.PI_LOCAL_IP}:${Main.PI_API_PORT}/ha-api?cmd=0&scope=${color.name.toLowerCase()}").response { request, response, result -> }
        }
        state = !state
        Thread.sleep(speed)
        run()
    }
}