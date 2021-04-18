package me.aggellos2001.ksurvivaleuplugin.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.Default
import me.aggellos2001.ksurvivaleuplugin.utils.textOf
import net.kyori.adventure.inventory.Book
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.Style
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.entity.Player

@CommandAlias("colors")
object Colors : BaseCommand() {

    @Default
    fun onColor(player: Player) {
        val bookTitle = textOf("Colors")
        val author = textOf("Google_Admin")
        val page1 = Component.text()
            .append(
                textOf("     Color Help\n")
                    .style(Style.style(TextDecoration.BOLD))
                    .color(NamedTextColor.GOLD)
            )
            .append(
                textOf("Black = &0\n")
                    .color(NamedTextColor.BLACK)
            )
            .append(
                textOf("Dark Blue = &1\n")
                    .color(NamedTextColor.DARK_BLUE)
            )
            .append(
                textOf("Dark Green = &2\n")
                    .color(NamedTextColor.DARK_GREEN)
            )
            .append(
                textOf("Dark Aqua = &3\n")
                    .color(NamedTextColor.DARK_AQUA)
            )
            .append(
                textOf("Dark Red = &4\n")
                    .color(NamedTextColor.DARK_RED)
            )
            .append(
                textOf("Dark Purple = &5\n")
                    .color(NamedTextColor.DARK_PURPLE)
            )
            .append(
                textOf("Gold = &6\n")
                    .color(NamedTextColor.GOLD)
            )
            .append(
                textOf("Gray = &7\n")
                    .color(NamedTextColor.GRAY)
            )
            .append(
                textOf("Dark Gray = &8\n")
                    .color(NamedTextColor.DARK_GRAY)
            )
            .append(
                textOf("Blue = &9\n")
                    .color(NamedTextColor.BLUE)
            )
            .append(
                textOf("Green = &a\n")
                    .color(NamedTextColor.GREEN)
            ).append(
                textOf("Aqua = &b\n")
                    .color(NamedTextColor.AQUA)
            ).append(
                textOf("Red = &c\n")
                    .color(NamedTextColor.RED)
            )
            .build()

        val page2 = Component.text()
            .append(
                textOf("Light purple = &d\n")
                    .color(NamedTextColor.LIGHT_PURPLE)
            ).append(
                textOf("Yellow = &e\n")
                    .color(NamedTextColor.YELLOW)
            ).append(
                textOf("White = &f\n")
                    .color(TextColor.color(184, 255, 249))
            )
            .build()

        val page3 = Component.text()
            .append(
                textOf("Bold")
                    .style(Style.style(TextDecoration.BOLD))
            )
            .append(
                textOf("= &l | **text**\n")
            )
            .append(
                textOf("Italic")
                    .style(Style.style(TextDecoration.ITALIC))
            )
            .append(
                textOf("= &o | *text*\n")
            )
            .append(
                textOf("Strikethrough")
                    .style(Style.style(TextDecoration.STRIKETHROUGH))
            )
            .append(
                textOf("= &m | ~~text~~\n")
            )
            .append(
                textOf("Underlined")
                    .style(Style.style(TextDecoration.UNDERLINED))
            )
            .append(
                textOf("= &n | __text__\n")
            )
            .build()


        val page4 = Component.text()
            .append(
                textOf("Hex")
            )
            .append(
                textOf("= <#1561ad>text | &#1561adtext\n")
            )
            .append(
                textOf("Gradient = <g:#1561ad:#61147a>text\n")
            )
            .append(
                textOf("Rainbow = <r>text | <r:0.5:1.0>text\n")
            )
            .build()

        val book = Book.book(bookTitle, author, page1, page2, page3, page4)
        player.openBook(book)

    }
}