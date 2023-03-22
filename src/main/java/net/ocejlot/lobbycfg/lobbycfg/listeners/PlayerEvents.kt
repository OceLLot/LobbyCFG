package net.ocejlot.lobbycfg.lobbycfg.listeners

import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.Location
import org.bukkit.configuration.Configuration
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.FoodLevelChangeEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.Plugin

class PlayerEvents(
    config: Configuration,
    private val lobbyLocation: Location
): Listener {
    private val mechanics = config.getConfigurationSection("playerMechanics")
    private val defaultGameMode = GameMode.valueOf(mechanics!!.getString("defaultGameMode")!!.uppercase())
    private val deathHeight = mechanics!!.getInt("deathHeight")

    private val allowPvp = config.getConfigurationSection("allowPvp")
    private val pvpIsEnabled = allowPvp!!.getBoolean("toggle")
    private val pvpPerm = allowPvp!!.getString("bypassPermission")
    private val allowedWorlds = config.getStringList("allowedWorlds")

    private val allowDamage = config.getBoolean("allowDamage.toggle")

    private val allowHunger = config.getBoolean("allowHunger.toggle")

    @EventHandler
    fun onJoin(event: PlayerJoinEvent){
        val player = event.player

        player.setBedSpawnLocation(lobbyLocation, true)
        player.teleport(lobbyLocation)
        player.gameMode = defaultGameMode
    }

    @EventHandler
    fun onDamage(event: EntityDamageEvent){
        val victim = event.entity

        if(victim !is Player)return
        if(allowDamage)return
        if(!allowedWorlds.contains(victim.world.name))return

        event.isCancelled = true
    }

    @EventHandler
    fun onPunch(event: EntityDamageByEntityEvent){
        val attacker = event.damager
        val victim = event.entity

        if(attacker !is Player)return
        if(victim !is Player)return

        if(pvpIsEnabled)return
        if(!allowedWorlds.contains(attacker.world.name))return

        if(attacker.hasPermission(pvpPerm!!))return

        event.isCancelled = true
    }

    @EventHandler
    fun onHungerChange(event: FoodLevelChangeEvent){
        val player = event.entity

        if(allowHunger)return
        if(player !is Player)return

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
