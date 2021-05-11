package me.aggellos2001.ksurvivaleuplugin.listeners

import me.aggellos2001.ksurvivaleuplugin.hooks.LuckPermsHookUtil.getRankPrefix
import me.aggellos2001.ksurvivaleuplugin.hooks.LuckPermsHookUtil.getRankSuffix
import me.aggellos2001.ksurvivaleuplugin.utils.colorize
import me.aggellos2001.ksurvivaleuplugin.utils.colorizeToComponent
import me.aggellos2001.ksurvivaleuplugin.utils.inTicks
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import kotlin.time.Duration

object JoinLeaveMessage : Listener {

    @EventHandler(ignoreCancelled = true)
    fun onPlayerJoin(e: PlayerJoinEvent) {
        e.joinMessage(
            "${e.player.getRankSuffix()} ${e.player.getRankPrefix()}&b ${e.player.name}&a&l joined the server!".colorizeToComponent(
                true
            )
        )
        e.player.sendTitle(
            "&6&lWelcome to &2Survival&#000099EU &6S3".colorize(),
            "&aHope you like it here :D".colorize(),
            Duration.seconds(3).inTicks().toInt(),
            Duration.seconds(5).inTicks().toInt(),
            Duration.seconds(3).inTicks().toInt()
        )
    }

    @EventHandler(ignoreCancelled = true)
    fun onPlayerLeave(e: PlayerQuitEvent) {
        e.quitMessage(
            "${e.player.getRankSuffix()} ${e.player.getRankPrefix(false)}&b ${e.player.name}&c&l left the server!".colorizeToComponent(
                true
            )
        )
    }
}