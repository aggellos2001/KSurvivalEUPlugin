package me.aggellos2001.ksurvivaleuplugin.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.ConditionFailedException
import co.aikar.commands.annotation.*
import co.aikar.commands.bukkit.contexts.OnlinePlayer
import me.aggellos2001.javafiles.DiscordWebHook
import me.aggellos2001.ksurvivaleuplugin.persistentdata.pluginConfig
import me.aggellos2001.ksurvivaleuplugin.plugin.CHAIN_FACTORY
import me.aggellos2001.ksurvivaleuplugin.utils.sendColorizedMessage
import me.aggellos2001.ksurvivaleuplugin.utils.setCoolDown
import me.aggellos2001.ksurvivaleuplugin.utils.toNiceString
import org.bukkit.entity.Player
import java.awt.Color
import java.net.http.HttpResponse
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.time.seconds

@CommandAlias("report")
@Conditions("cooldown:time=5,name=Report")
object Report : BaseCommand() {

    @Default
    @Syntax("<player> <report>")
    @CommandCompletion("@players @nothing")
    fun onPlayerReport(playerReporting: Player, playerGettingReported: OnlinePlayer, reportMessage: String) {
        if (playerReporting == playerGettingReported.player) {
            throw ConditionFailedException("You cannot report yourself!")
        }
        val webHookUrl = pluginConfig.reportWebHookURL
        val report = DiscordWebHook(webHookUrl, CHAIN_FACTORY)
        val timeFormat = DateTimeFormatter.ofPattern(("dd/MM/yyyy HH:mm:ss"))
        val time = timeFormat.format(LocalDateTime.now())
        report.addEmbed(
            DiscordWebHook.EmbedObject()
                .setAuthor(
                    "Minecraft Report",
                    null,
                    "https://cdn4.iconfinder.com/data/icons/the-weather-is-nice-today/64/weather_48-256.png"
                )
                .setColor(Color.GREEN)
                .addField("Player created report: ", playerReporting.name, true)
                .addField("Player being reported: ", playerGettingReported.player.name, true)
                .addField("Report:", reportMessage, false)
                .addField("Player reporting cords:", playerReporting.location.toNiceString(), false)
                .addField("Player being reported cords:", playerGettingReported.player.location.toNiceString(), false)
                .setFooter("Minecraft server time: $time", "https://www.iconsdb.com/icons/preview/white/clock-xxl.png")
        )
        try {
            val chain = report.createHTTPChain()
            chain.syncLast { response ->
                val httpResponse = response as? HttpResponse<*>
                if (httpResponse != null && httpResponse.statusCode() == 204)
                    playerReporting.sendColorizedMessage("&aReported player &e${playerGettingReported.player.name}&a successfully!")
                else
                    playerReporting.sendColorizedMessage("&cReport failed! Try again in a few minutes!!!")
                playerReporting.setCoolDown("Report",60.seconds)
            }.execute()
        } catch (t: Throwable) {
            t.printStackTrace()
            playerReporting.sendColorizedMessage("&cSomething went wrong! Try again later!")
        }
    }
}