package net.ocejlot.lobbycfg.lobbycfg.listeners

import org.bukkit.Material
import org.bukkit.configuration.Configuration
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent


class BlockEvents(
    private val config: Configuration
): Listener{

    private val allowBlockBreak = config.getConfigurationSection("allowBlockBreak")
    private val blockBreakingIsEnabled = allowBlockBreak!!.getBoolean("toggle")
    private val blockBreakPerm = allowBlockBreak!!.getString("bypassPermission")
    private val breakExceptions = allowBlockBreak!!.getStringList("exceptions")
    private val breakAllowedWorlds = config.getStringList("allowedWorlds")

    private val allowBlockPlace = config.getConfigurationSection("allowBlockPlace")
    private val blockPlaceIsEnabled = allowBlockPlace!!.getBoolean("toggle")
    private val blockPlacePerm = allowBlockPlace!!.getString("bypassPermission")
    private val placeExceptions = allowBlockPlace!!.getStringList("exceptions")
    private val placeAllowedWorlds = config.getStringList("allowedWorlds")

    @EventHandler
    fun onBreak(event: BlockBreakEvent){
        val player = event.player
        if(!breakAllowedWorlds.contains(player.world.name))return

        val block = event.block
        if(!blockBreakingIsEnabled)return
        if(player.hasPermission(blockBreakPerm!!))return

        val materialExceptions = arrayListOf<Material>()
        breakExceptions.forEach{block->
            val m = Material.getMaterial(block)
            materialExceptions.add(m!!)
        }
        if(materialExceptions.contains(block.type))return

        event.isCancelled = true
    }

    @EventHandler
    fun onPlace(event: BlockPlaceEvent){
        val player = event.player
        if(!placeAllowedWorlds.contains(player.world.name))return

        val block = event.block
        if(!blockPlaceIsEnabled)return
        if(player.hasPermission(blockPlacePerm!!))return

        val materialExceptions = arrayListOf<Material>()
        placeExceptions.forEach{block->
            val m = Material.getMaterial(block)
            materialExceptions.add(m!!)
        }
        if(materialExceptions.contains(block.type))return

        event.isCancelled = true
    }
}
