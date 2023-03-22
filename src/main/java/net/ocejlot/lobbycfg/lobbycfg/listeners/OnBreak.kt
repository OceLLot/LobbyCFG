package net.ocejlot.lobbycfg.lobbycfg.listeners

import org.bukkit.Material
import org.bukkit.configuration.Configuration
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent


class OnBreak(
    private val config: Configuration
): Listener{

    private val allowBlockBreak = config.getConfigurationSection("allowBlockBreak")
    private val blockBreakingIsEnabled = allowBlockBreak!!.getBoolean("toggle")
    private val blockBreakPerm = allowBlockBreak!!.getString("bypassPermission")
    private val exceptions = allowBlockBreak!!.getStringList("exceptions")
    private val allowedWorlds = config.getStringList("allowedWorlds")

    @EventHandler
    fun onBreak(event: BlockBreakEvent){
        val player = event.player
        if(!allowedWorlds.contains(player.world.name))return

        val block = event.block
        if(!blockBreakingIsEnabled)return
        if(player.hasPermission(blockBreakPerm!!))return

        val materialExceptions = arrayListOf<Material>()
        exceptions.forEach{block->
            val m = Material.getMaterial(block)
            materialExceptions.add(m!!)
        }
        if(materialExceptions.contains(block.type))return

        event.isCancelled = true
    }
}
