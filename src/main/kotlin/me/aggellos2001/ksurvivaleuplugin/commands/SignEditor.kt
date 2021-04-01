package me.aggellos2001.ksurvivaleuplugin.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.ConditionFailedException
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.Conditions
import co.aikar.commands.annotation.Default
import co.aikar.commands.annotation.Optional
import me.aggellos2001.ksurvivaleuplugin.plugin.pluginInstance
import me.aggellos2001.ksurvivaleuplugin.utils.colorize
import me.aggellos2001.ksurvivaleuplugin.utils.colorizeToComponent
import me.aggellos2001.ksurvivaleuplugin.utils.sendColorizedMessage
import me.mattstudios.mfmsg.bukkit.BukkitMessage
import org.bukkit.NamespacedKey
import org.bukkit.block.Sign
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.SignChangeEvent
import org.bukkit.persistence.PersistentDataType

@CommandAlias("signeditor|seditor")
@Conditions("cooldown:time=2,name=SignEditor")
object SignEditor : BaseCommand() {

    private val signOwnerKey = NamespacedKey(pluginInstance, "signOwner")

    @Default
    fun onSignEditCommand(player: Player, line: Int, @Optional newLine: String?) {
        if (line < 1 || line > 4) {
            throw ConditionFailedException("Valid numbers are 1,2,3,4!")
        }
        val sign = player.getTargetBlock(5)?.state ?: return

        if (sign !is Sign) {
            player.sendColorizedMessage("&cYou must look a sign (&e&oWithin 5 blocks range!&c)")
            return
        }
        val signOwner = sign.persistentDataContainer.getOrDefault(signOwnerKey, PersistentDataType.STRING, "null")

        if (signOwner == "null")
            throw ConditionFailedException("This sign has no owner!")
        if (signOwner != player.name)
            throw ConditionFailedException("This sign was placed by &e$signOwner &cso you cannot edit it!")

        val newLineString = newLine ?: " "
        sign.line(line - 1, newLineString.colorizeToComponent())
        val result = sign.update()
        if (result)
            if (newLineString != " ")
                player.sendColorizedMessage("&aSuccessfully changed line $line to ${newLineString.colorize()}")
            else
                player.sendColorizedMessage("&aSuccessfully cleared line $line!")
        else
            throw ConditionFailedException("Something went wrong (o).(o)! Try again!")
    }

    object SignEditorListener : Listener {
        @EventHandler(ignoreCancelled = true)
        fun onSignChangeEvent(e: SignChangeEvent) {
            val player = e.player
            val sign = e.block.state as Sign
            sign.persistentDataContainer.set(signOwnerKey, PersistentDataType.STRING, player.name)
            sign.update()
        }
    }
}