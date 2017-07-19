package tech.johari.gamelights.lights

import com.github.kittinunf.fuel.Fuel

class LightManager(val host: String, val port: Int) {

    /**
     * Change the state of the lights.
     * @param signal the signal to update
     * @param state the updated state of the signal
     */
    fun update(signal: LightSignal, state: Boolean) {
        Fuel.get("http://$host:$port/ha-api?cmd=${if (state) 1 else 0}&scope=${signal.name.toLowerCase()}").response { _, _, _ -> }
    }

}