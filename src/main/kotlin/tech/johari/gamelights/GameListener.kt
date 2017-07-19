package tech.johari.gamelights

import me.skreem.event.EventHandler
import me.skreem.event.Listener
import me.skreem.game.events.bomb.BombDefuseEvent
import me.skreem.game.events.bomb.BombExplodeEvent
import me.skreem.game.events.bomb.BombPlantEvent
import me.skreem.game.events.round.RoundFreezetimeEvent
import tech.johari.gamelights.lights.LightManager
import tech.johari.gamelights.lights.LightSignal

// Round playing -> nothing on
// Planted -> Flash yellow [faster]
// Defuse -> Green [slower]
// Explodes -> All lights flashing [same pace as defuse]
class GameListener(val lights: LightManager) : Listener {

    var currentAnimation : FlashThread? = null

    private fun killAnimation() {
        if(currentAnimation != null) {
            currentAnimation!!.stop()
        }
        lights.update(LightSignal.ALL, false)
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

        currentAnimation = FlashThread(lights, LightSignal.YELLOW, 500)
        currentAnimation!!.start()
    }

    @EventHandler
    fun onBombExplodeEvent(event: BombExplodeEvent) {
        killAnimation()

        currentAnimation = FlashThread(lights, LightSignal.ALL, 1000)
        currentAnimation!!.start()
    }

    @EventHandler
    fun onBombDefuseEvent(event: BombDefuseEvent) {
        killAnimation()

        currentAnimation = FlashThread(lights, LightSignal.GREEN, 1000)
        currentAnimation!!.start()
    }

}