package me.aggellos2001.ksurvivaleuplugin.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.Conditions
import co.aikar.commands.annotation.Default
import me.aggellos2001.ksurvivaleuplugin.persistentdata.PluginConfigHandler
import me.aggellos2001.ksurvivaleuplugin.utils.sendColorizedMessage
import org.bukkit.command.CommandSender

@CommandAlias("seureload|seurl")
@Conditions("op")
object ReloadConfig : BaseCommand() {

    @Default
    fun onReloadConfig(sender: CommandSender) {
        PluginConfigHandler.loadConfig()
        sender.sendColorizedMessage("&aReload completed!")
    }
}