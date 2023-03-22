package net.ocejlot.lobbycfg.lobbycfg.listeners

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.configuration.Configuration
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent


class OnJoin(
    private val config: Configuration
): Listener {

    private val mechanics = config.getConfigurationSection("playerMechanics")
    private val spawnPoint = mechanics!!.getString("spawnLocation")

    @EventHandler
    fun onJoin(event: PlayerJoinEvent){
        val spawnLoc = spawnPoint!!.split(",")
        val player = event.player
        val configWorld = mechanics!!.getString("spawnWorld").toString()
        val world = Bukkit.getWorld(configWorld)

        val x = spawnLoc[0].toDouble()
        val y = spawnLoc[1].toDouble()
        val z = spawnLoc[2].toDouble()
        val yaw = spawnLoc[3].toFloat()
        val pitch = spawnLoc[4].toFloat()

        player.teleport(Location(world, x, y, z, yaw, pitch))
    }


}