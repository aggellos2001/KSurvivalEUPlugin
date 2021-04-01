package me.aggellos2001.ksurvivaleuplugin.listeners

import io.papermc.paper.event.player.AsyncChatEvent
import me.aggellos2001.ksurvivaleuplugin.utils.deserializeToComponent
import me.aggellos2001.ksurvivaleuplugin.utils.serializeToString
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener

object AntiCaps : Listener {

    private fun String.capsInMessage(): Int {
        var caps = 0
        for (character in this) {
            if (character.isUpperCase())
                caps++
        }
        return caps
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    fun onChatCheckCaps(e: AsyncChatEvent) {
        val player = e.player
        if (player.hasPermission("seu.caps")) return
        val message = e.message().serializeToString()
        if (message.capsInMessage() > 5) {
            e.message(message.toLowerCase().deserializeToComponent())
        }
    }
}