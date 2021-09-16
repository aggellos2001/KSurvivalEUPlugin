package me.aggellos2001.ksurvivaleuplugin.listeners

import io.papermc.paper.chat.ChatRenderer
import io.papermc.paper.event.player.AsyncChatEvent
import me.aggellos2001.ksurvivaleuplugin.hooks.LuckPermsHookUtil.getRankPrefix
import me.aggellos2001.ksurvivaleuplugin.hooks.LuckPermsHookUtil.getRankSuffix
import me.aggellos2001.ksurvivaleuplugin.persistentdata.getPluginPlayerData
import me.aggellos2001.ksurvivaleuplugin.utils.*
import net.kyori.adventure.audience.Audience
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import kotlin.time.Duration


object FormatChat : Listener, ChatRenderer {

    override fun render(source: Player, sourceDisplayName: Component, message: Component, viewer: Audience): Component {
        val display = Component.text()
        val rankPrefix = source.getRankPrefix() + " "
        val rankSuffix =
            if (source.getRankSuffix() != "") source.getRankSuffix() + " "
            else source.getRankSuffix()
        val data = source.getPluginPlayerData()
        return display.append(rankSuffix.colorizeToComponent())
            .append(rankPrefix.colorizeToComponent()).append(sourceDisplayName)
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
        player.setCoolDown("chat", Duration.seconds(0.5))
        e.renderer(this)
    }


}