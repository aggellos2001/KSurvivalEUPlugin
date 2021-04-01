package me.aggellos2001.ksurvivaleuplugin.listeners

import io.papermc.paper.chat.ChatComposer
import io.papermc.paper.event.player.AsyncChatEvent
import me.aggellos2001.ksurvivaleuplugin.hooks.LuckPermsHookUtil.getRankPrefix
import me.aggellos2001.ksurvivaleuplugin.persistentdata.getPluginPlayerData
import me.aggellos2001.ksurvivaleuplugin.utils.*
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import kotlin.time.seconds


object FormatChat : Listener, ChatComposer {

    override fun composeChat(source: Player, displayName: Component, message: Component): Component {
        val display = Component.text()
        val rankPrefix = source.getRankPrefix()
        val data = source.getPluginPlayerData()
        return display.append(rankPrefix.colorizeToComponent()).append(" ".deserializeToComponent()).append(displayName)
            .append(": ".deserializeToComponent())
            .append("&${data.chatColor}${message.serializeToString()}".colorizeToComponent())
            .build()
    }

    @EventHandler(ignoreCancelled = true)
    fun formatChat(e: AsyncChatEvent) {
        val player = e.player
        if (player.getCoolDown("chat").first) {
            e.isCancelled = true
            player.sendColorizedMessage(player.getCoolDownErrorMessage("chat"))
        }
        player.setCoolDown("chat", 0.5.seconds)
        e.composer(this)
    }
}