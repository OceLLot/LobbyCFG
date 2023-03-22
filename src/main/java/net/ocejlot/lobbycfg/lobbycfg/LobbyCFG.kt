package net.ocejlot.lobbycfg.lobbycfg

import net.ocejlot.lobbycfg.lobbycfg.listeners.OnBreak
import net.ocejlot.lobbycfg.lobbycfg.listeners.OnJoin
import net.ocejlot.lobbycfg.lobbycfg.listeners.OnPlace
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class LobbyCFG : JavaPlugin() {
    override fun onEnable() {
        registerEvents()
    }

    private fun registerEvents() {
        Bukkit.getPluginManager().registerEvents(OnBreak(this.config), this)
        Bukkit.getPluginManager().registerEvents(OnPlace(this.config), this)
        Bukkit.getPluginManager().registerEvents(OnJoin(this.config), this)
    }//ya negar

//negram bit` slojna

    //no ya negar

}