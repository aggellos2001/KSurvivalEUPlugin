package me.aggellos2001.ksurvivaleuplugin.plugin

import co.aikar.commands.PaperCommandManager
import co.aikar.taskchain.BukkitTaskChainFactory
import co.aikar.taskchain.TaskChainFactory
import me.aggellos2001.ksurvivaleuplugin.persistentdata.PlayerWarpData
import me.aggellos2001.ksurvivaleuplugin.persistentdata.PluginConfigHandler
import me.aggellos2001.ksurvivaleuplugin.persistentdata.PluginPlayerDataHandler
import me.aggellos2001.ksurvivaleuplugin.utils.colorize
import me.bristermitten.pdm.SpigotDependencyManager
import net.coreprotect.CoreProtect
import net.coreprotect.CoreProtectAPI
import net.ess3.api.IEssentials
import net.luckperms.api.LuckPerms
import net.luckperms.api.LuckPermsProvider
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import java.nio.file.Files
import java.nio.file.Path
import java.util.logging.Logger


lateinit var pluginInstance: KSurvivalEUPlugin
lateinit var COMMAND_MANAGER: PaperCommandManager
lateinit var CHAIN_FACTORY: TaskChainFactory
lateinit var ESSENTIALS_API: IEssentials
lateinit var COREPROTECT_API: CoreProtectAPI
lateinit var LUCKPERMS_API: LuckPerms
lateinit var pluginLogger: Logger

/**
 * We extend the JavaPlugin constructor
 * @constructor [org.bukkit.plugin.java.JavaPlugin]
 */
class KSurvivalEUPlugin : JavaPlugin() {

    /**
     * [SpigotDependencyManager] loads/downloads all dependencies at runtime
     * Blocks the server thread until its complete (minimal lag first time when it needs to download them.
     *
     *  Here we want to make sure that the directories below exist before the plugin is enabled.
     */
    override fun onLoad() {

        SpigotDependencyManager.of(this).loadAllDependencies().join() //block until everything is loaded

        pluginInstance = this
        try {
            Files.createDirectories(Path.of(this.dataFolder.toURI()))
            Files.createDirectories(Path.of(PluginPlayerDataHandler.pluginDataDir.toURI()))
            Files.createDirectories(Path.of(PlayerWarpData.warpDirectory.toURI()))
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    override fun onEnable() {
        CHAIN_FACTORY = BukkitTaskChainFactory.create(this)
        COMMAND_MANAGER = PaperCommandManager(this)
        ESSENTIALS_API = getEssentialsAPI()
        COREPROTECT_API = getCoreProtectAPI()
        LUCKPERMS_API = getLuckPermsAPI()
        pluginLogger = this.logger
        registerCommandsAndEvents()
        PluginConfigHandler.loadConfig()
        pluginLogger.info("&aPlugin loaded successfully".colorize())

    }

    override fun onDisable() {
        PluginConfigHandler.saveConfig()
    }
}

private fun getEssentialsAPI(): IEssentials {
    val ess = Bukkit.getPluginManager().getPlugin("Essentials")
    return ess as? IEssentials ?: throw IllegalStateException("Getting EssentialsX API failed!")
}

private fun getCoreProtectAPI(): CoreProtectAPI {
    val core = Bukkit.getPluginManager().getPlugin("CoreProtect") as? CoreProtect
        ?: throw IllegalStateException("Getting Core Protect API failed!")
    return if (core.api.APIVersion() < 6) throw IllegalArgumentException("Getting CoreProtect API failed!") else core.api
}

private fun getLuckPermsAPI(): LuckPerms {
    Bukkit.getPluginManager().getPlugin("LuckPerms") ?: throw IllegalArgumentException("Getting LuckPerms API failed!")
    return LuckPermsProvider.get();
}
