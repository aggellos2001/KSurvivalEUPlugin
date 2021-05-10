package me.aggellos2001.ksurvivaleuplugin.commands

import me.aggellos2001.ksurvivaleuplugin.utils.*
import net.kyori.adventure.text.event.HoverEvent
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerCommandPreprocessEvent

object Help : Listener {

    @EventHandler(ignoreCancelled = true)
    fun onHelpCommand(e: PlayerCommandPreprocessEvent) {
        if (e.message == "/help") {
            e.player.sendColorizedMessage("&e&lhttps://survivaleu.com/docs.html")
            e.isCancelled = true
        }
    }
}