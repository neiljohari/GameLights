package tech.johari.GameLights

import com.github.kittinunf.fuel.Fuel
import me.skreem.event.Event
import me.skreem.game.net.Server

import javax.imageio.ImageIO
import javax.swing.*
import java.awt.*
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.io.IOException

class Main {
    companion object {
        val PI_LOCAL_IP = "192.168.0.101"
        val PI_API_PORT = 5000
    }

    var trayIcon: TrayIcon? = null

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
    Main()
}