package net.ocejlot.lobbycfg.lobbycfg.listeners

import io.papermc.paper.event.block.BlockBreakBlockEvent
import org.bukkit.configuration.Configuration
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent


class BlockBreakListener(
    private val config: Configuration
): Listener{

    @EventHandler
    fun breakBlockLogic(event: BlockBreakEvent){
        val blockBreakingIsEnabled = config.getBoolean("disallowBlockBreak.toggle")
        if(!blockBreakingIsEnabled)return

        val player = event.player

        val blockBreakPerm = config.getString("disallowBlockBreak.bypassPermission").toString()
        if(player.hasPermission(blockBreakPerm))return
        event.isCancelled = true

    }
}
