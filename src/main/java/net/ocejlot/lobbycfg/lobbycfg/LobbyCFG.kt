package net.ocejlot.lobbycfg.lobbycfg

import net.ocejlot.lobbycfg.lobbycfg.listeners.BlockEvents
import net.ocejlot.lobbycfg.lobbycfg.listeners.PlayerEvents
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.plugin.java.JavaPlugin

class LobbyCFG : JavaPlugin() {
    private val mechanics = config.getConfigurationSection("playerMechanics")
    private val spawnPoint = mechanics!!.getString("spawnLocation")
    private val configWorld = mechanics!!.getString("spawnWorld")

    override fun onEnable() {
        registerEvents()
        println(lobbyLocation())
        PlayerEvents(this.config, lobbyLocation()).playerFall(this)
    }

    private fun registerEvents() {
        Bukkit.getPluginManager().registerEvents(BlockEvents(this.config), this)
        Bukkit.getPluginManager().registerEvents(PlayerEvents(this.config, lobbyLocation()), this)
    }

    private fun lobbyLocation() : Location{
        val spawnLoc = spawnPoint!!.split(",")

        val world = Bukkit.getWorld(configWorld!!)
        val x = spawnLoc[0].toDouble()
        val y = spawnLoc[1].toDouble()
        val z = spawnLoc[2].toDouble()
        val yaw = spawnLoc[3].toFloat()
        val pitch = spawnLoc[4].toFloat()

        return Location(world, x, y, z, yaw, pitch)
    }
}
