package tech.johari.GameLights

import com.github.kittinunf.fuel.Fuel

class FlashThread(val color: LightSignal, val speed: Long) : Thread() {
    var state = true
    val startTime = System.currentTimeMillis()

    override fun run() {
        if(!state) {
            // Turn it on
            Fuel.get("http://192.168.0.101:5000/ha-api?cmd=1&scope=${color.name.toLowerCase()}").response { request, response, result -> }
        } else {
            // Turn it off
            Fuel.get("http://192.168.0.101:5000/ha-api?cmd=0&scope=${color.name.toLowerCase()}").response { request, response, result -> }
        }
        state = !state
        Thread.sleep(speed)
        run()
    }
}