package me.aggellos2001.ksurvivaleuplugin.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Default
import me.aggellos2001.ksurvivaleuplugin.utils.sendColorizedMessage
import me.aggellos2001.ksurvivaleuplugin.utils.toNiceString
import me.aggellos2001.ksurvivaleuplugin.utils.wrapToWeakReference
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import java.lang.ref.WeakReference

@CommandAlias("orebroadcast|ob")
@CommandPermission("seu.ore")
object OreBroadcast : BaseCommand(), Listener {

    private val receivingOreAlertPlayerList = mutableListOf<WeakReference<Player>>()

    @Default
    fun toggleAlerts(player: Player) {
        if (receivingOreAlertPlayerList.any { it.get() == player }) {
            receivingOreAlertPlayerList.removeIf { it.get() == player }
            player.sendColorizedMessage("&eNo longer receiving ore alerts!")
        } else {
            receivingOreAlertPlayerList.add(player.wrapToWeakReference())
            player.sendColorizedMessage("&aNow receiving ore alerts!")
        }
    }

    private val detectableBlocks =
        arrayOf(
            Material.DIAMOND_ORE, Material.EMERALD_ORE, Material.ANCIENT_DEBRIS, Material.SPAWNER,
            Material.NETHER_QUARTZ_ORE, Material.LAPIS_ORE, Material.IRON_ORE, Material.REDSTONE_ORE,
            Material.GOLD_ORE
        )

    @EventHandler(ignoreCancelled = true)
    fun onOreFound(e: BlockBreakEvent) {

        val blockType = e.block.type
        val player = e.player
        val blockLocationNiceString = e.block.location.toNiceString()

        if (player.gameMode != GameMode.SURVIVAL) return //not interested in other gameModes obviously

        if (blockType !in detectableBlocks) return //return if ore mined is not in list above

        Bukkit.getOnlinePlayers().sendColorizedMessage("&aPlayer &e${player.name}&a mined &b${blockType.toNiceString()}&a at location &e$blockLocationNiceString!",true) {
            it.hasPermission("seu.ore") && receivingOreAlertPlayerList.any { ref -> ref.get() == it } // same as receivingOreAlertPlayers.contains(it) but without using weak reference
        }
    }
}