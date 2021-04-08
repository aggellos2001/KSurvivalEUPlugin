package me.aggellos2001.ksurvivaleuplugin.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.*
import co.aikar.commands.bukkit.contexts.OnlinePlayer
import me.aggellos2001.ksurvivaleuplugin.listeners.BlockVPN
import me.aggellos2001.ksurvivaleuplugin.utils.sendColorizedMessage
import org.bukkit.entity.Player


@CommandAlias("proxychecker")
@Conditions("op")
object ProxyChecker : BaseCommand() {

    @Default
    @CommandCompletion("@players @nothing")
    fun onPlayerProxyCheck(player: Player, playerToCheck: OnlinePlayer) {

        val detection = BlockVPN.getFromCache(playerToCheck.player.address.address.hostAddress)
        if (detection != null)
            player.sendColorizedMessage("&aProxy info for player &e${playerToCheck.player.name}: &e$detection")
        else
            player.sendColorizedMessage("&cError! Something went wrong! (Cache expired?)")
    }

    @Subcommand("clearIP")
    fun onCleanIPCommand(player: Player, @Single hostName: String) {
        val result = BlockVPN.clearHostName(hostName)
        if (result != null)
            player.sendColorizedMessage("&aSuccessfully cleared hostname &e$hostName&a from the cache!")
        else
            player.sendColorizedMessage("&cHostname &e$hostName&c not found in the cache!")
    }
}