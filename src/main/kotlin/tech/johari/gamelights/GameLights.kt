package tech.johari.gamelights

import me.skreem.event.Event
import me.skreem.game.net.Server
import tech.johari.gamelights.lights.LightManager

import javax.imageio.ImageIO
import javax.swing.*
import java.awt.*
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.io.IOException

class GameLights(val lights: LightManager) {

    var trayIcon: TrayIcon? = null

    init {
        setupTray()
        setupIntegration()
    }

    private fun setupIntegration() {
        Event.addListener(GameListener(lights))
        val server = Server()
        try {
            server.start(3000)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun setupTray() {
        if (SystemTray.isSupported()) {
            val tray = SystemTray.getSystemTray()
            val exitListener = object : ActionListener {
                override fun actionPerformed(e: ActionEvent) {
                    val r = Runtime.getRuntime()
                    println("Exiting...")
                    System.exit(0)
                }
            }

            val popup = PopupMenu()
            val menuExit = MenuItem("Quit")
            menuExit.addActionListener(exitListener);
            popup.add(menuExit);

            var image: Image? = null
            try {
                image = ImageIcon(ImageIO.read(this.javaClass.getResourceAsStream("tray.png"))).image
            } catch (e: IOException) {
                e.printStackTrace()
            }

            trayIcon = TrayIcon(image!!, "CSGO Traffic Light Integration", popup)
            trayIcon!!.isImageAutoSize = true


            try {
                tray.add(trayIcon!!)
            } catch (e: AWTException) {
                System.err.println("TrayIcon could not be added.")
            }
        }
        trayIcon!!.displayMessage("CSGO Traffic Light Integration", "Traffic light will now be synced with game events.", TrayIcon.MessageType.INFO)

    }
}

fun main(vararg args: String) {
    GameLights(LightManager("192.168.0.101", 5000))
}