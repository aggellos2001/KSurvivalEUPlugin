package me.aggellos2001.ksurvivaleuplugin.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.Conditions
import co.aikar.commands.annotation.Default
import me.aggellos2001.ksurvivaleuplugin.hooks.LuckPermsHookUtil.getRankPrefix
import me.aggellos2001.ksurvivaleuplugin.utils.sendColorizedMessage
import org.bukkit.Bukkit
import org.bukkit.entity.Player

@CommandAlias("uwu")
@Conditions("cooldown:time=30,name=UwU")
object UwU : BaseCommand() {

    @Default
    fun onUwUCommand(player: Player) {
        player.health = 0.0
        Bukkit.getServer().sendColorizedMessage("&b${player.getRankPrefix()} &e${player.name}&b got uwued!", true)
    }
}