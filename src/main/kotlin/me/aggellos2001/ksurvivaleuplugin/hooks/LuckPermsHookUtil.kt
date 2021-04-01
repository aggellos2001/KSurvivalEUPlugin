package me.aggellos2001.ksurvivaleuplugin.hooks

import me.aggellos2001.ksurvivaleuplugin.plugin.LUCKPERMS_API
import me.aggellos2001.ksurvivaleuplugin.utils.wrapToWeakReference
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.lang.ref.WeakReference


object LuckPermsHookUtil {

    private val mobileUsers = mutableListOf<WeakReference<Player>>()
    fun addMobileUser(player: Player) = mobileUsers.add(player.wrapToWeakReference())

    fun Player.getLuckPermsGroup(): String? {
        return LUCKPERMS_API.userManager.getUser(this.uniqueId)?.cachedData?.metaData?.primaryGroup
    }

    fun Player.isStaff(): Boolean {
        return LUCKPERMS_API.userManager.getUser(player!!.uniqueId)?.cachedData?.metaData?.getMetaValue("staff")
            ?.toBoolean() ?: false
    }

    fun Player.hasDonated(): Boolean {
        return LUCKPERMS_API.userManager.getUser(player!!.uniqueId)?.cachedData?.metaData?.getMetaValue("donated")
            ?.toBoolean() ?: false
    }

    fun Player.getRankPrefix(): String {
        val prefix = LUCKPERMS_API.userManager.getUser(this.uniqueId)?.cachedData?.metaData?.prefix ?: "&aMember"
        return if (mobileUsers.any { it.get() == player })
            "&b✆&r $prefix"
        else
            prefix
    }

    fun getStaffOnline(): Set<Player> {
        return mutableSetOf<Player>().apply {
            for (onlinePlayer in Bukkit.getOnlinePlayers()) {
                if (onlinePlayer.isStaff())
                    this.add(onlinePlayer)
            }
        }
    }

    fun getDonatorsOnline(): Set<Player> {
        return mutableSetOf<Player>().apply {
            for (onlinePlayer in Bukkit.getOnlinePlayers()) {
                if (onlinePlayer.hasDonated())
                    this.add(onlinePlayer)
            }
        }
    }
}
