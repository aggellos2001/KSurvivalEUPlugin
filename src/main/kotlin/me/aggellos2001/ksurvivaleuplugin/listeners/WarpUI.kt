package me.aggellos2001.ksurvivaleuplugin.listeners

import dev.triumphteam.gui.builder.item.ItemBuilder
import dev.triumphteam.gui.components.InteractionModifier
import dev.triumphteam.gui.guis.PaginatedGui
import me.aggellos2001.ksurvivaleuplugin.plugin.ESSENTIALS_API
import me.aggellos2001.ksurvivaleuplugin.utils.textOf
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerCommandPreprocessEvent

object WarpUI : Listener {

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
    fun onWarpCommand(e: PlayerCommandPreprocessEvent) {
        val command = e.message.lowercase()
        val player = e.player
        if (command != "/warp" && command != "/warps") return

        e.isCancelled = true

        val warps = ESSENTIALS_API.warps
        val rows = when (warps.count) {
            in 0..9 -> 1
            in 10..18 -> 2
            in 19..27 -> 3
            in 27..36 -> 4
            in 36..45 -> 5
            else -> 6
        }

        PaginatedGui(rows, 0, textOf("Warps", NamedTextColor.YELLOW).content(), InteractionModifier.VALUES).run {
            setDefaultClickAction {
                it.isCancelled = true
            }
            for (warpName in warps.list) {
                val material = when {
                    warpName.contains("pvp", true) -> Material.NETHERITE_SWORD
                    warpName.contains("jail", true) -> Material.IRON_BARS
                    warpName.contains("game", true) -> Material.EMERALD
                    else -> Material.MAP
                }
                addItem(
                    ItemBuilder.from(material)
                        .name(textOf(warpName, NamedTextColor.YELLOW, TextDecoration.BOLD))
                        .glow(true)
                        .asGuiItem {
                            Bukkit.dispatchCommand(player, "warp $warpName")
                        }
                )
            }
            open(player)
        }
    }
}