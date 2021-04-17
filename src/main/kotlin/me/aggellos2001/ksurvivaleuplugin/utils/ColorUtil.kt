package me.aggellos2001.ksurvivaleuplugin.utils

import me.mattstudios.mfmsg.base.MessageOptions
import me.mattstudios.mfmsg.base.internal.Format
import me.mattstudios.mfmsg.bukkit.BukkitMessage
import net.kyori.adventure.text.Component
import org.bukkit.ChatColor
import org.bukkit.Server
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin
import java.util.function.Predicate

private val messageParser = BukkitMessage.create(MessageOptions.builder().removeFormat(Format.ITALIC).build())

fun String.colorizeToComponent(addPrefix: Boolean = false): Component {
    return if (addPrefix)
        messageParser.parse("&6[&bSurvivalEU&6]&r $this").toString().deserializeToComponent()
    else messageParser.parse(this).toString().deserializeToComponent()
}

fun String.colorize(addPrefix: Boolean = false): String {
    return if (addPrefix) messageParser.parse("&6[&bSurvivalEU&6]&r $this").toString()
    else messageParser.parse(this).toString()
}

fun String.stripColors() = ChatColor.stripColor(this)!!

fun Component.colorize(): Component = this.serializeToString().colorizeToComponent()

fun Component.stripColors(): Component = this.serializeToString().stripColors().deserializeToComponent()

fun CommandSender.sendColorizedMessage(message: String, addPrefix: Boolean = true) {
    this.sendMessage(message.colorizeToComponent(addPrefix))
}

fun Server.sendColorizedMessage(message: String, addPrefix: Boolean = true) {
    this.sendMessage(message.colorizeToComponent(addPrefix))
}

fun JavaPlugin.logInGame(message: String, addPrefix: Boolean = true) {
    this.server.sendColorizedMessage(message, addPrefix)
}

fun MutableCollection<out CommandSender>.sendColorizedMessage(
    message: String,
    addPrefix: Boolean = false,
    predicate: Predicate<CommandSender>? = null
) {
    this.forEach {
        if (predicate == null)
            it.sendColorizedMessage(message, addPrefix)
        else if (predicate.test(it))
            it.sendColorizedMessage(message, addPrefix)
    }
}



