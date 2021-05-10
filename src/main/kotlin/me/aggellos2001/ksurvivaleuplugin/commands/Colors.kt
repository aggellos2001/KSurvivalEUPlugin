package me.aggellos2001.ksurvivaleuplugin.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.Default
import co.aikar.commands.annotation.Description
import me.aggellos2001.ksurvivaleuplugin.utils.plus
import me.aggellos2001.ksurvivaleuplugin.utils.textOf
import net.kyori.adventure.inventory.Book
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.entity.Player

@CommandAlias("color|colors")
@Description("Opens a book that helps you find how to use colors")
object Colors : BaseCommand() {

    @Default
    fun onColor(player: Player) {
        val bookTitle = textOf("Colors")
        val author = textOf("Google_Admin")
        val page1 = Component.text() +
                textOf("     Color Help\n", NamedTextColor.GOLD, TextDecoration.BOLD) +
                textOf("Black = &0\n", NamedTextColor.BLACK) +
                textOf("Dark Blue = &1\n", NamedTextColor.DARK_BLUE) +
                textOf("Dark Green = &2\n", NamedTextColor.DARK_GREEN) +
                textOf("Dark Aqua = &3\n", NamedTextColor.DARK_AQUA) +
                textOf("Dark Red = &4\n", NamedTextColor.DARK_RED) +
                textOf("Dark Purple = &5\n", NamedTextColor.DARK_PURPLE) +
                textOf("Gold = &6\n", NamedTextColor.GOLD) +
                textOf("Gray = &7\n", NamedTextColor.GRAY) +
                textOf("Dark Gray = &8\n", NamedTextColor.DARK_GRAY) +
                textOf("Blue = &9\n", NamedTextColor.BLUE) +
                textOf("Green = &a\n", NamedTextColor.GREEN) +
                textOf("Aqua = &b\n", NamedTextColor.AQUA) +
                textOf("Red = &c\n", NamedTextColor.RED)

        val page2 = Component.text() +
                textOf("Light purple = &d\n", NamedTextColor.LIGHT_PURPLE) +
                textOf("Yellow = &e\n", NamedTextColor.YELLOW) +
                textOf("White = &f\n", TextColor.color(184, 255, 249))


        val page3 = Component.text() +
                textOf("Bold", style = arrayOf(TextDecoration.BOLD)) +
                textOf("= &l | **text**\n") +
                textOf("Italic", style = arrayOf(TextDecoration.ITALIC)) +
                textOf("= &o | *text*\n") +
                textOf("Strikethrough", style = arrayOf(TextDecoration.STRIKETHROUGH)) +
                textOf("= &m | ~~text~~\n") +
                textOf("Underlined", style = arrayOf(TextDecoration.UNDERLINED)) +
                textOf("= &n | __text__\n")


        val page4 = Component.text() +
                textOf("Hex = <#1561ad>text | &#1561adtext\n") +
                textOf("Gradient = <g:#1561ad:#61147a>text\n") +
                textOf("Rainbow = <r>text | <r:0.5:1.0>text\n")

        val book = Book.book(bookTitle, author, page1.build(), page2.build(), page3.build(), page4.build())
        player.openBook(book)

    }
}