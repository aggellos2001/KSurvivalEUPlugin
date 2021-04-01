package me.aggellos2001.ksurvivaleuplugin.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.Default
import me.aggellos2001.ksurvivaleuplugin.utils.sendColorizedMessage
import org.bukkit.entity.Player

@CommandAlias("social|discord|twitter")
object SocialMedia : BaseCommand() {

    @Default
    fun onCommand(player: Player) {
        player.sendColorizedMessage(
            """
            &6Social Media:
            &eDiscord:&a&l https://discord.survivaleu.com/&r
            &eTwitter:&b&l https://twitter.com/SurvivalEUMC
        """.trimIndent()
        )
    }
}