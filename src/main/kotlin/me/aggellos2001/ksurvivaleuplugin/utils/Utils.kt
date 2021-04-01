package me.aggellos2001.ksurvivaleuplugin.utils

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import kotlin.time.Duration

fun Enum<*>.getNameBeatify(): String {
    val words = this.name.replace("_", " ").split(" ")
    val sb = StringBuilder()
    for (word in words) {
        sb.append(word.toLowerCase().capitalize())
        if (words[words.size - 1] != word) {
            sb.append(' ')
        }
    }
    return sb.toString()
}

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