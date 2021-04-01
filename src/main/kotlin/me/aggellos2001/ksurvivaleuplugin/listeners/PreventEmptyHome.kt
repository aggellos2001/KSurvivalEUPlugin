package me.aggellos2001.ksurvivaleuplugin.listeners

import me.aggellos2001.ksurvivaleuplugin.utils.sendColorizedMessage
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerCommandPreprocessEvent

object PreventEmptyHome : Listener {

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
    fun onSetHomeCommand(e: PlayerCommandPreprocessEvent) {
        if (!e.player.hasPermission("essentials.sethome")) return
        if (e.message == "/sethome" || e.message == "/esethome" || e.message == "/createhome" || e.message == "/ecreatehome") {
            e.player.sendColorizedMessage("&cYou have to give a name to your home first! For example ${e.message} myhome")
            e.isCancelled = true
        }
    }
}