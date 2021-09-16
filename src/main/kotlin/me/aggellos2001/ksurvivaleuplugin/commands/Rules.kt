package me.aggellos2001.ksurvivaleuplugin.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.Default
import me.aggellos2001.ksurvivaleuplugin.utils.div
import me.aggellos2001.ksurvivaleuplugin.utils.interactiveTextOf
import me.aggellos2001.ksurvivaleuplugin.utils.textOf
import net.kyori.adventure.text.event.HoverEvent
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.entity.Player


@CommandAlias("server-rules|srules")
object Rules : BaseCommand() {

    @Default
    fun onHelp(player: Player) {
        player.sendMessage(
            textOf("Rules", NamedTextColor.GREEN) /
                    textOf("You are NOT allowed to:", NamedTextColor.YELLOW)
                    /
                    interactiveTextOf(
                        "1. Advertisement", color = NamedTextColor.GOLD,
                        hoverEvent = HoverEvent.showText(textOf("Advertising other servers, locations, companies without permission"))
                    )
                    /
                    interactiveTextOf(
                        "2. Spam", color = NamedTextColor.GOLD,
                        hoverEvent = HoverEvent.showText(textOf("Any type of spamming in the chat including spam bots."))
                    )
                    /
                    interactiveTextOf(
                        "3. Bad Links", color = NamedTextColor.GOLD,
                        hoverEvent = HoverEvent.showText(textOf("Posting malicious or bad links."))
                    )
                    /
                    interactiveTextOf(
                        "4. Bad Behavior", color = NamedTextColor.GOLD,
                        hoverEvent = HoverEvent.showText(textOf("Being toxic or insulting others."))
                    )
                    /
                    interactiveTextOf(
                        "5. Asking to be staff (repeatedly)", color = NamedTextColor.GOLD,
                        hoverEvent = HoverEvent.showText(textOf("You won't get staff if you do that."))
                    )
                    /
                    interactiveTextOf(
                        "6. Asking (stuff) repeatedly", color = NamedTextColor.GOLD,
                        hoverEvent = HoverEvent.showText(textOf("Asking to get stuff from players or staff repeatedly"))
                    )
                    /
                    interactiveTextOf(
                        "7. Griefing", color = NamedTextColor.GOLD,
                        hoverEvent = HoverEvent.showText(
                            textOf(
                                "Destroying other's houses or getting their items. Make sure to claim " +
                                        "your house / items. Also use /lockchest to be protected against theft!"
                            )
                        )
                    )
                    /
                    interactiveTextOf(
                        "8. TPA Trap", color = NamedTextColor.GOLD,
                        hoverEvent = HoverEvent.showText(
                            textOf(
                                "Trapping someone when using /tpa. Beware when accepting /tpa requests from" +
                                        "other players. You are responsible!"
                            )
                        )
                    )
                    /
                    interactiveTextOf(
                        "9. Offensive Builds/Skins/Usernames", color = NamedTextColor.GOLD,
                        hoverEvent = HoverEvent.showText(textOf("Racism, Sexism and other bad stuff are NOT welcome here."))
                    )
                    /
                    interactiveTextOf(
                        "10. Harassing", color = NamedTextColor.GOLD,
                        hoverEvent = HoverEvent.showText(
                            textOf(
                                "Entering on other's claim without their permission. Following someone" +
                                        "around the server on constantly trying to bother him."
                            )
                        )
                    )
                    /
                    interactiveTextOf(
                        "11. Lag machines", color = NamedTextColor.GOLD,
                        hoverEvent = HoverEvent.showText(textOf("Building redstone machines to lag the server."))
                    )
                    /
                    interactiveTextOf(
                        "12. Cheats / Exploits", color = NamedTextColor.GOLD,
                        hoverEvent = HoverEvent.showText(textOf("Using illegal modifications."))
                    )
                    /
                    interactiveTextOf(
                        "13. Trolling", color = NamedTextColor.GOLD,
                        hoverEvent = HoverEvent.showText(textOf("You should stop bothering other's when they are bothered."))
                    )
                    /
                    interactiveTextOf(
                        "14. Staff disobedience", color = NamedTextColor.GOLD,
                        hoverEvent = HoverEvent.showText(textOf("Ignoring staff member's orders."))
                    )
                    /
                    interactiveTextOf(
                        "15. PvP", color = NamedTextColor.GOLD,
                        hoverEvent = HoverEvent.showText(
                            textOf(
                                "You must enable pvp in /settings to kill others. " +
                                        "Make sure they want to fight with you too before killing them."
                            )
                        )
                    )
        )
    }
}