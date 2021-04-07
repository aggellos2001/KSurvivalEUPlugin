package me.aggellos2001.ksurvivaleuplugin.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.Conditions
import co.aikar.commands.annotation.Default
import me.aggellos2001.javafiles.DiscordWebHook
import me.aggellos2001.ksurvivaleuplugin.persistentdata.pluginConfig
import me.aggellos2001.ksurvivaleuplugin.plugin.CHAIN_FACTORY
import me.aggellos2001.ksurvivaleuplugin.utils.sendColorizedMessage
import me.aggellos2001.ksurvivaleuplugin.utils.setCoolDown
import org.bukkit.entity.Player
import java.awt.Color
import java.net.http.HttpResponse
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.time.seconds

@CommandAlias("reportbug|bugreport|rbug")
@Conditions("cooldown:time=5,name=BugReport")
object ReportBug : BaseCommand() {

    @Default
    fun onReportBugCommand(player: Player, message: String) {

        val report = DiscordWebHook(pluginConfig.bugReportWebHookURL, CHAIN_FACTORY)
        val timeFormat = DateTimeFormatter.ofPattern(("dd/MM/yyyy HH:mm:ss"))
        val time = timeFormat.format(LocalDateTime.now())
        report.addEmbed(
            DiscordWebHook.EmbedObject()
                .setAuthor(
                    "Minecraft Bug Report",
                    null,
                    "https://cdn4.iconfinder.com/data/icons/the-weather-is-nice-today/64/weather_48-256.png"
                )
                .setColor(Color.RED)
                .addField("From player:", player.name, false)
                .addField("Report:", message, false)
                .setFooter("Minecraft server time: $time", "https://www.iconsdb.com/icons/preview/white/clock-xxl.png")
        )

        try {
            val chain = report.createHTTPChain()
            chain.syncLast { response ->
                val httpResponse = response as? HttpResponse<*>
                if (httpResponse != null && httpResponse.statusCode() == 204)
                    player.sendColorizedMessage("&aBug report sent successfully! Thank you :D! ")
                else
                    player.sendColorizedMessage("&cBug report failed! Try again in a few minutes!!!")
                player.setCoolDown("BugReport", 60.seconds)
            }.execute()
        } catch (t: Throwable) {
            t.printStackTrace()
            player.sendColorizedMessage("&cSomething went wrong! Try again later!")
        }

    }
}