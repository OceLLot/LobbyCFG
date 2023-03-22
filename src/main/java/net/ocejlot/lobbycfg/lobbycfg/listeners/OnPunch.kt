package net.ocejlot.lobbycfg.lobbycfg.listeners

import org.bukkit.Material
import org.bukkit.configuration.Configuration
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.player.PlayerHideEntityEvent

class OnPunch(
    private val config: Configuration
): Listener {

    private val allowPvp = config.getConfigurationSection("lobbycfg")
    private val pvpIsEnabled = allowPvp!!.getBoolean("toggle")
    private val pvpPerm = allowPvp!!.getString("bypassPermission")
    private val allowedWorlds = config.getStringList("allowedWorlds")

    @EventHandler
    fun onPunch(event: EntityDamageByEntityEvent){
        if(event.entity !is Player)return

        val player = event.entity
        if(!allowedWorlds.contains(player.world.name))return

        if(!pvpIsEnabled)return
        if(player.hasPermission(pvpPerm!!))return
        event.isCancelled = true
    }
}