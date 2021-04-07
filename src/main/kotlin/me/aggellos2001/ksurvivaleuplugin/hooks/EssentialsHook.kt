package me.aggellos2001.ksurvivaleuplugin.hooks

import com.earth2me.essentials.User
import me.aggellos2001.ksurvivaleuplugin.plugin.ESSENTIALS_API
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import java.util.*

object EssentialsHook {

    fun Player.getEssentialsUser(): User = ESSENTIALS_API.getUser(this)
    fun OfflinePlayer.getEssentialsUser(): User? = ESSENTIALS_API.getUser(this.uniqueId)
    fun UUID.getEssentialsUser(): User? = ESSENTIALS_API.getUser(this)

    /**
     * Returns a player object if exists from the given string or null otherwise
     */
    fun String.getPlayerIfExists(): Player? {
        val offlinePlayers = Bukkit.getOfflinePlayers()
        for (offlinePlayer in offlinePlayers) {
            val essentialsPlayer = offlinePlayer.getEssentialsUser() ?: continue
            if (essentialsPlayer.name.equals(this, true)) {
                return essentialsPlayer.base
            }
        }
        return null
    }
}