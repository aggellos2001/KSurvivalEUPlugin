package me.aggellos2001.ksurvivaleuplugin.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Default
import me.aggellos2001.ksurvivaleuplugin.utils.colorize
import me.aggellos2001.ksurvivaleuplugin.utils.inTicks
import org.bukkit.entity.Player
import kotlin.time.seconds

@CommandAlias("alert")
@CommandPermission("seu.alert")
object Alert : BaseCommand() {

    @Default
    private fun onCommand(player: Player, seconds: Int, message: String) {
        player.sendTitle(
            "&a&lAnnouncement".colorize(),
            message.colorize(),
            20,
            seconds.seconds.inTicks().toInt(),
            40
        )
    }
}