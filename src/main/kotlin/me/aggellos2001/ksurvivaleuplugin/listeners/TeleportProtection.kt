package me.aggellos2001.ksurvivaleuplugin.listeners

import me.aggellos2001.ksurvivaleuplugin.utils.inTicks
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerTeleportEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import kotlin.time.seconds

object TeleportProtection : Listener {

    @EventHandler(ignoreCancelled = true)
    fun onPlayerTeleport(e: PlayerTeleportEvent) {
        if (e.cause != PlayerTeleportEvent.TeleportCause.PLUGIN && e.cause != PlayerTeleportEvent.TeleportCause.COMMAND) return
        e.player.addPotionEffect(PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 5.seconds.inTicks().toInt(), 5))
    }
}