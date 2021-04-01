package me.aggellos2001.ksurvivaleuplugin.listeners

import com.linkedin.urls.detection.UrlDetector
import com.linkedin.urls.detection.UrlDetectorOptions
import io.papermc.paper.event.player.AsyncChatEvent
import me.aggellos2001.ksurvivaleuplugin.hooks.LuckPermsHookUtil
import me.aggellos2001.ksurvivaleuplugin.utils.sendColorizedMessage
import me.aggellos2001.ksurvivaleuplugin.utils.serializeToString
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener

object AdFilter : Listener {

    private val ads = arrayOf(
        "Connected with MineChat",
        "I joined using ChatCraft from my Android device! Download it for free!"
    )

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
    fun checkChatForAd(e: AsyncChatEvent) {
        for (ad in ads) {
            if (e.message().serializeToString().equals(ad, true)) {
                LuckPermsHookUtil.addMobileUser(e.player)
                e.isCancelled = true
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    fun onRemoveLinksFromChat(e: AsyncChatEvent) {
        if (e.player.hasPermission("seu.links")) return
        val foundUrl = UrlDetector(e.message().serializeToString(), UrlDetectorOptions.Default).detect()
        if (foundUrl.isNotEmpty()) {
            e.player.sendColorizedMessage("&cIPs/Urls are not allowed without permission!")
            e.isCancelled = true
        }
    }
}