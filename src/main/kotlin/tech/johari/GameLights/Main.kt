package tech.johari.GameLights

import com.github.kittinunf.fuel.Fuel
import me.skreem.event.Event
import me.skreem.game.net.Server

import javax.imageio.ImageIO
import javax.swing.*
import java.awt.*
import java.io.IOException

class Main {
    var trayIcon: TrayIcon? = null
        private set

    init {
        setupTray()
        setupIntegration()
    }

    private fun setupIntegration() {
        Event.addListener(Listener())
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

            var image: Image? = null
            try {
                image = ImageIcon(ImageIO.read(this.javaClass.getResourceAsStream("tray.png"))).image
            } catch (e: IOException) {
                e.printStackTrace()
            }

            trayIcon = TrayIcon(image!!, "CSGO Alert")
            trayIcon!!.isImageAutoSize = true


            try {
                tray.add(trayIcon!!)
            } catch (e: AWTException) {
                System.err.println("TrayIcon could not be added.")
            }

        }
    }
}

fun main(vararg args: String) {
    Fuel.get("http://192.168.0.101:5000/ha-api?cmd=0&scope=all").response { request, response, result -> }
    Main()
}