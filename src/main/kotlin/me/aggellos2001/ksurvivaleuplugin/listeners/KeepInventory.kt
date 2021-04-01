package me.aggellos2001.ksurvivaleuplugin.listeners

import me.aggellos2001.ksurvivaleuplugin.persistentdata.getPluginPlayerData
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent

object KeepInventory : Listener {

    @EventHandler(ignoreCancelled = true)
    fun onPlayerDeathEvent(e: PlayerDeathEvent) {
        val data = e.entity.getPluginPlayerData()
        if (data.keepInventory) {
            e.keepInventory = true
            e.keepLevel = true
            e.drops.clear()
            e.droppedExp = 0
        }
    }
}