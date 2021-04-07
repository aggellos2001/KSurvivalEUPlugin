package me.aggellos2001.ksurvivaleuplugin.utils

import co.aikar.commands.BaseCommand
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.plugin.java.JavaPlugin
import java.math.RoundingMode
import java.util.concurrent.TimeUnit
import kotlin.time.Duration
import kotlin.time.toDuration

fun Material.isLiquid(): Boolean {
    return when (this) {
        Material.LAVA, Material.WATER -> true
        else -> false
    }
}

fun scheduleRunnable(runnable: Runnable, plugin: JavaPlugin, delay: Duration) =
    plugin.server.scheduler.runTaskLater(plugin, runnable, delay.inTicks())

fun Player.getEmptySlots(): Int {
    val contents = this.inventory.contents
    var count = 0
    for (content in contents) {
        if (content == null) count++
    }
    return count
}

fun Enum<*>.toNiceString(): String {
    val processed = this.name.replace('_', ' ').split("\\s+").toTypedArray()
    val result = StringBuilder()
    for (word in processed) {
        result.append(word.toLowerCase().capitalize())
        if (processed[processed.size - 1] != word) {
            result.append(' ')
        }
    }
    return result.toString()
}

fun Double.stripDecimals(decimalsToKeep: Int) =
    this.toBigDecimal().setScale(decimalsToKeep, RoundingMode.HALF_EVEN).toDouble()

fun Player.canBuild(): Boolean {
    var canBuild = true
    val placedBlock = this.location.block
    val replacedBlockState = this.location.block.state
    val playerEquipment = this.equipment ?: return false
    val itemInHand = if (this.activeItem != null) playerEquipment.itemInMainHand else playerEquipment.itemInOffHand
    val event =
        BlockPlaceEvent(placedBlock, replacedBlockState, placedBlock, itemInHand, this, true, EquipmentSlot.HAND)
    Bukkit.getPluginManager().callEvent(event)
    if (event.isCancelled) {
        canBuild = false
    } else {
        replacedBlockState.update(true)
    }
    return canBuild
}

fun CommandSender.sendHelpMessage(command: BaseCommand, message: String) {
    this.sendMessage(
        ("&6[&eHelp&6]&r &e&m-----&r &e/${command.name} &m-----&r\n" + message.trimIndent()).colorizeToComponent()
    )
}

inline fun JavaPlugin.measureBlockTime(block: () -> Unit) {
    val start = System.nanoTime()
    block()
    val totalTime = System.nanoTime() - start
    val duration = totalTime.toDuration(TimeUnit.NANOSECONDS)
    this.logInGame("&eBlock took: &b${duration.inMilliseconds}&ems")
}