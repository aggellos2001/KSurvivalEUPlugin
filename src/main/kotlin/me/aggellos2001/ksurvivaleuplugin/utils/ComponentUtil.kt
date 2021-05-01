package me.aggellos2001.ksurvivaleuplugin.utils

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer

private val legacyComponentSerializer =
    LegacyComponentSerializer.legacySection().toBuilder().extractUrls().build()

fun Component.serializeToString(): String = legacyComponentSerializer.serialize(this)

fun String.deserializeToComponent(): Component = legacyComponentSerializer.deserialize(this)

fun textOf(text: String): TextComponent = Component.text(text)