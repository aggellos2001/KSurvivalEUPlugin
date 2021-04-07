package me.aggellos2001.ksurvivaleuplugin.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Default
import me.aggellos2001.ksurvivaleuplugin.utils.sendColorizedMessage
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender

@CommandAlias("staffchat|schat")
@CommandPermission("seu.staffchat")
object StaffChat : BaseCommand() {

    @Default
    fun onStaffChatCommand(sender: CommandSender, message: String) {
        Bukkit.getOnlinePlayers().sendColorizedMessage("&2[&6&lSC&r&2]&e ${sender.name}:&r&b $message") {
            it.hasPermission("seu.staffchat")
        }
    }
}