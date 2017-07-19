package tech.johari.gamelights

import tech.johari.gamelights.lights.LightManager
import tech.johari.gamelights.lights.LightSignal

class FlashThread(val lights: LightManager, val color: LightSignal, val speed: Long) : Thread() {

    var state = true

    override fun run() {
        if(!state) {
            // Turn it on
            lights.update(color, true)
        } else {
            // Turn it off
            lights.update(color, false)
        }
        state = !state
        Thread.sleep(speed)
        run()
    }

}