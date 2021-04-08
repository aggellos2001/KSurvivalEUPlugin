package me.aggellos2001.ksurvivaleuplugin.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.Default
import com.earth2me.essentials.utils.DateUtil
import me.aggellos2001.ksurvivaleuplugin.utils.sendColorizedMessage
import org.bukkit.Statistic
import org.bukkit.entity.Player

@CommandAlias("playtime")
object PlayTime : BaseCommand() {

    @Default
    fun onPlayTimeCommand(player: Player) {
        val playtimeMs = System.currentTimeMillis() - player.getStatistic(Statistic.PLAY_ONE_MINUTE) * 50
        val playTime = DateUtil.formatDateDiff(playtimeMs)
        player.sendColorizedMessage("&6Total Playtime = &a$playTime")
    }
}