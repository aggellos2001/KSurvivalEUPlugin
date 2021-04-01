package me.aggellos2001.ksurvivaleuplugin.listeners

import me.aggellos2001.ksurvivaleuplugin.utils.sendColorizedMessage
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerCommandPreprocessEvent

object PaySelfPrevention : Listener {

    @EventHandler(ignoreCancelled = true)
    fun onPayingAPlayer(e: PlayerCommandPreprocessEvent) {
        if (!e.message.startsWith("/pay", true) && !e.message.startsWith("/epay", true)) return
        val args = e.message.split(" ")
        if (args.size == 1) return
        val sender = e.player
        val receiver = Bukkit.getPlayer(args[1]) ?: return
        if (sender.address == receiver.address) {
            sender.sendColorizedMessage("&cYou cannot send money to accounts with the same IP!")
            e.isCancelled = true
        }
    }
}