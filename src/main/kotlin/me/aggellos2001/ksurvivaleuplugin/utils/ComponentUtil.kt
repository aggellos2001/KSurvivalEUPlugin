package me.aggellos2001.ksurvivaleuplugin.utils

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.event.HoverEvent
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.Style
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer

private val legacyComponentSerializer =
    LegacyComponentSerializer.legacySection().toBuilder().extractUrls().build()

fun Component.serializeToString(): String = legacyComponentSerializer.serialize(this)

fun String.deserializeToComponent(): Component = legacyComponentSerializer.deserialize(this)

fun textOf(text: String, color: TextColor? = null, vararg style: TextDecoration = emptyArray()): TextComponent =
    Component.text(text).style(Style.style(*style)).color(color)

fun interactiveTextOf(
    text: String,
    clickEvent: ClickEvent? = null,
    hoverEvent: HoverEvent<*>? = null,
    color: NamedTextColor? = null,
    vararg style: TextDecoration = emptyArray()
): TextComponent =
    Component.text(text).style(Style.style(*style)).color(color).hoverEvent(hoverEvent).clickEvent(clickEvent)


fun Component.colorize(): Component = this.serializeToString().colorizeToComponent()
fun TextComponent.colorize(): TextComponent = this.serializeToString().colorizeToTextComponent()

fun Component.stripColors(): Component = this.serializeToString().stripColors().deserializeToComponent()

operator fun TextComponent.Builder.plus(a: TextComponent): TextComponent.Builder {
    return this.append(a)
}

operator fun TextComponent.plus(a: TextComponent): TextComponent {
    return this.append(a)
}