package me.aggellos2001.ksurvivaleuplugin.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.Default
import co.aikar.commands.annotation.Description
import me.aggellos2001.ksurvivaleuplugin.utils.sendColorizedMessage
import org.bukkit.entity.Player

@CommandAlias("vote")
@Description("Vote for our server to help up get more players")
object Vote : BaseCommand() {

    @Default
    fun showVoteLinks(player: Player) {
        player.sendColorizedMessage(
            """
            &aVote links:
            &e1. https://minecraft-mp.com/server/173291/vote/
            2. https://minecraftservers.org/vote/458700
            3. https://minecraft-server-list.com/server/476790/vote/
            4. https://minecraft-server.net/vote/SurvivalEU/
            5. https://topg.org/minecraft-servers/server-628600
            6. https://minecraftservers.biz/servers/73618/#vote_now
            7. https://minecraft.buzz/server/1483&tab=vote
        """.trimIndent()
        )

    }
}