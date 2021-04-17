package me.aggellos2001.ksurvivaleuplugin.persistentdata

import com.google.gson.GsonBuilder
import me.aggellos2001.ksurvivaleuplugin.plugin.pluginInstance
import java.io.File
import java.nio.file.Files

lateinit var pluginConfig: PluginConfig

data class PluginConfig(
    var wildBlockRange: Int = 10_000,
    var wildCost: Int = 10,
    var voteApiToken: String? = "null",
    var vpnProxyCheckerToken: String? = "null",
    var reportWebHookURL: String? = "null",
    var bugReportWebHookURL: String? = "null"
)

object PluginConfigHandler {
    private val gson = GsonBuilder().setPrettyPrinting().create()
    private val configFile = File(pluginInstance.dataFolder, "config.json")

    fun loadConfig() {
        return if (!configFile.exists()) {
            pluginConfig = PluginConfig()
        } else {
            Files.newBufferedReader(configFile.toPath()).use {
                pluginConfig = gson.fromJson(it, PluginConfig::class.java) ?: PluginConfig()
            }
        }
    }

    fun saveConfig() {
        Files.newBufferedWriter(configFile.toPath()).use {
            gson.toJson(pluginConfig, it)
        }
    }
}