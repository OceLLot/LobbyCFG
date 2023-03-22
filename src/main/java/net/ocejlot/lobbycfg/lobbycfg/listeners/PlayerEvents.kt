package net.ocejlot.lobbycfg.lobbycfg.listeners

import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.Location
import org.bukkit.configuration.Configuration
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.Plugin

class PlayerEvents(
    private val config: Configuration,
    private val lobbyLocation: Location
): Listener {
    private val mechanics = config.getConfigurationSection("playerMechanics")
    private val defaultGameMode = GameMode.valueOf(mechanics!!.getString("defaultGameMode")!!.uppercase())
    private val deathHeight = mechanics!!.getInt("deathHeight")

    private val allowPvp = config.getConfigurationSection("allowPvp")
    private val pvpIsEnabled = allowPvp!!.getBoolean("toggle")
    private val pvpPerm = allowPvp!!.getString("bypassPermission")
    private val allowedWorlds = config.getStringList("allowedWorlds")

    @EventHandler
    fun onJoin(event: PlayerJoinEvent){
        val player = event.player

        player.teleport(lobbyLocation)
        player.gameMode = defaultGameMode
    }

    @EventHandler
    fun onPunch(event: EntityDamageByEntityEvent){
        if(event.entity !is Player)return

        val player = event.entity
        if(!allowedWorlds.contains(player.world.name))return

        if(!pvpIsEnabled)return
        if(player.hasPermission(pvpPerm!!))return
        event.isCancelled = true
    }

    fun playerFall(plugin: Plugin){
        Bukkit.getScheduler().runTaskTimer(plugin, Runnable {
            Bukkit.getOnlinePlayers().forEach {player->
                if(player.location.y <= deathHeight){
                    player.teleport(lobbyLocation)
                }
            }
        }, 0, 20)
    }
}
