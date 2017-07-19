package tech.johari.gamelights

import com.github.kittinunf.fuel.Fuel
import me.skreem.event.EventHandler
import me.skreem.event.Listener
import me.skreem.game.events.bomb.BombDefuseEvent
import me.skreem.game.events.bomb.BombExplodeEvent
import me.skreem.game.events.bomb.BombPlantEvent
import me.skreem.game.events.round.RoundFreezetimeEvent

// Round playing -> nothing on
// Planted -> Flash yellow [faster]
// Defuse -> Green [slower]
// Explodes -> All lights flashing [same pace as defuse]
class Listener : Listener {
    var currentAnimation : FlashThread? = null


    private fun killAnimation() {
        if(currentAnimation != null) {
            currentAnimation!!.stop()
        }
        Fuel.get("http://${Main.PI_LOCAL_IP}:${Main.PI_API_PORT}/ha-api?cmd=0&scope=all").response { request, response, result -> }
    }

    init {
        killAnimation()
    }

    @EventHandler
    fun onRoundFreezetimeEvent(event: RoundFreezetimeEvent) {
        killAnimation()
    }

    @EventHandler
    fun onBombPlantEvent(event: BombPlantEvent) {
        killAnimation()

        currentAnimation = FlashThread(LightSignal.YELLOW, 500)
        currentAnimation!!.start()
    }

    @EventHandler
    fun onBombExplodeEvent(event: BombExplodeEvent) {
        killAnimation()

        currentAnimation = FlashThread(LightSignal.ALL, 1000)
        currentAnimation!!.start()
    }

    @EventHandler
    fun onBombDefuseEvent(event: BombDefuseEvent) {
        killAnimation()

        currentAnimation = FlashThread(LightSignal.GREEN, 1000)
        currentAnimation!!.start()
    }

}