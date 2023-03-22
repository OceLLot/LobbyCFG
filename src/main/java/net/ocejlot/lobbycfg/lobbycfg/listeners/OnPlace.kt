package net.ocejlot.lobbycfg.lobbycfg.listeners

import org.bukkit.Material
import org.bukkit.configuration.Configuration
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPlaceEvent


class OnPlace(
    private val config: Configuration
): Listener{

    private val allowBlockPlace = config.getConfigurationSection("allowBlockPlace")
    private val blockPlaceIsEnabled = allowBlockPlace!!.getBoolean("toggle")
    private val blockPlacePerm = allowBlockPlace!!.getString("bypassPermission")
    private val exceptions = allowBlockPlace!!.getStringList("exceptions")
    private val allowedWorlds = config.getStringList("allowedWorlds")

    @EventHandler
    fun onPlace(event: BlockPlaceEvent){
        val player = event.player
        if(!allowedWorlds.contains(player.world.name))return

        val block = event.block
        if(!blockPlaceIsEnabled)return
        if(player.hasPermission(blockPlacePerm!!))return

        val materialExceptions = arrayListOf<Material>()
        exceptions.forEach{block->
            val m = Material.getMaterial(block)
            materialExceptions.add(m!!)
        }
        if(materialExceptions.contains(block.type))return

        event.isCancelled = true
    }
}
