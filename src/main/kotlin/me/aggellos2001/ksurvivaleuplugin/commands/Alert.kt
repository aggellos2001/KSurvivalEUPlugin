package me.aggellos2001.ksurvivaleuplugin.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Default
import co.aikar.commands.annotation.Description
import me.aggellos2001.ksurvivaleuplugin.utils.colorize
import me.aggellos2001.ksurvivaleuplugin.utils.inTicks
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import kotlin.time.seconds

@CommandAlias("alert")
@CommandPermission("seu.alert")
@Description("Make an announcement to anyone. Only for staff!")
object Alert : BaseCommand() {

    @Default
    private fun onCommand(sender: CommandSender, seconds: Int, message: String) {
        for (onlinePlayer in Bukkit.getOnlinePlayers()) {
            onlinePlayer.sendTitle(
                "&a&lAnnouncement".colorize(),
                message.colorize(),
                20,
                seconds.seconds.inTicks().toInt(),
                40
            )
        }
    }
}