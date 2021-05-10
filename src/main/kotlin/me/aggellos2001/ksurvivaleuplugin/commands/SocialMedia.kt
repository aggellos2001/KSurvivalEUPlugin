package me.aggellos2001.ksurvivaleuplugin.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.Default
import co.aikar.commands.annotation.Description
import me.aggellos2001.ksurvivaleuplugin.utils.sendColorizedMessage
import org.bukkit.entity.Player

@CommandAlias("social|discord|twitter")
@Description("Find out our social media and some useful links")
object SocialMedia : BaseCommand() {

    @Default
    fun onCommand(player: Player) {
        player.sendColorizedMessage(
            """
            &6Social Media:
            &eWebsite:&2&l https://survivaleu.com/
            &eDiscord:&a&l https://discord.survivaleu.com/&r
            &eTwitter:&b&l https://twitter.com/SurvivalEUMC
            &eTrello Roadmap:&c&l https://trello.com/b/uKxUd8nY/survivaleu-roadmap
        """.trimIndent()
        )
    }
}