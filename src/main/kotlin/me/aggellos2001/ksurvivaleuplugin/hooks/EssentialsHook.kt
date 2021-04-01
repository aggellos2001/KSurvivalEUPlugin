package me.aggellos2001.ksurvivaleuplugin.hooks

import com.earth2me.essentials.User
import me.aggellos2001.ksurvivaleuplugin.plugin.ESSENTIALS_API
import org.bukkit.entity.Player

object EssentialsHook {

    fun Player.getEssentialsUser(): User = ESSENTIALS_API.getUser(this)
}