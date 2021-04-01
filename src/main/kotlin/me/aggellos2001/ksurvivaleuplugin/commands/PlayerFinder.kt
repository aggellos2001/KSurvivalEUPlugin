package me.aggellos2001.ksurvivaleuplugin.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.Conditions
import co.aikar.commands.annotation.Single
import co.aikar.commands.annotation.Subcommand
import me.aggellos2001.ksurvivaleuplugin.plugin.CHAIN_FACTORY
import me.aggellos2001.ksurvivaleuplugin.plugin.ESSENTIALS_API
import me.aggellos2001.ksurvivaleuplugin.utils.sendColorizedMessage
import org.bukkit.Bukkit
import org.bukkit.entity.Player

@CommandAlias("pfinder|playername|playerfinder")
@Conditions("op")
object PlayerFinder : BaseCommand() {

    @Subcommand("startsWith")
    fun onPlayerFindStartsWith(player: Player, @Single name: String) {
        val chain = CHAIN_FACTORY.newChain<Any>()
        chain.asyncFirst {
            val players = Bukkit.getOfflinePlayers()
            val matches: MutableSet<String> = mutableSetOf()
            for (offlinePlayer in players) {
                val essentialUser = ESSENTIALS_API.getUser(offlinePlayer.uniqueId) ?: continue
                if (essentialUser.name.startsWith(name, true))
                    matches.add(essentialUser.name)
            }
            return@asyncFirst matches
        }.syncLast {
            if (it.isEmpty())
                player.sendColorizedMessage("&cNo players found!")
            else {
                player.sendColorizedMessage("&aFound ${it.size} players!")
                it.forEachIndexed { index, playerName ->
                    val i = index + 1
                    player.sendColorizedMessage("&e${i}. $playerName",false)
                }
            }
        }.execute()
    }

    @Subcommand("contains")
    fun onPlayerFindContains(player: Player, @Single name: String) {
        val chain = CHAIN_FACTORY.newChain<Any>()
        chain.asyncFirst {
            val players = Bukkit.getOfflinePlayers()
            val matches: MutableSet<String> = mutableSetOf()
            for (offlinePlayer in players) {
                val essentialUser = ESSENTIALS_API.getUser(offlinePlayer.uniqueId) ?: continue
                if (essentialUser.name.contains(name, true))
                    matches.add(essentialUser.name)
            }
            return@asyncFirst matches
        }.syncLast {
            if (it.isEmpty())
                player.sendColorizedMessage("&cNo players found!")
            else {
                player.sendColorizedMessage("&aFound ${it.size} players!")
                it.forEachIndexed { index, playerName ->
                    val i = index + 1
                    player.sendColorizedMessage("&e${i}. $playerName",false)
                }
            }
        }.execute()
    }
    @Subcommand("endsWith")
    fun onPlayerFindEndsWith(player: Player, @Single name: String) {
        val chain = CHAIN_FACTORY.newChain<Any>()
        chain.asyncFirst {
            val players = Bukkit.getOfflinePlayers()
            val matches: MutableSet<String> = mutableSetOf()
            for (offlinePlayer in players) {
                val essentialUser = ESSENTIALS_API.getUser(offlinePlayer.uniqueId) ?: continue
                if (essentialUser.name.endsWith(name, true))
                    matches.add(essentialUser.name)
            }
            return@asyncFirst matches
        }.syncLast {
            if (it.isEmpty())
                player.sendColorizedMessage("&cNo players found!")
            else {
                player.sendColorizedMessage("&aFound ${it.size} players!")
                it.forEachIndexed { index, playerName ->
                    val i = index + 1
                    player.sendColorizedMessage("&e${i}. $playerName",false)
                }
            }
        }.execute()
    }
}