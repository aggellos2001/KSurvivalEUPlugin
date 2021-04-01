package me.aggellos2001.ksurvivaleuplugin.persistentdata

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import me.aggellos2001.ksurvivaleuplugin.plugin.pluginInstance
import me.aggellos2001.ksurvivaleuplugin.plugin.pluginLogger
import me.aggellos2001.ksurvivaleuplugin.utils.colorize
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.util.*

private val pluginPlayerDataCache = WeakHashMap<Player, PluginPlayerData>()

data class PluginPlayerData(
    var keepInventory: Boolean = true,
    var sittingOnStairs: Boolean = false,
    var pvp: Boolean = false,
    var chatColor: Char = 'f'
)

object PluginPlayerDataHandler {

    val pluginDataDir = File(Path.of(pluginInstance.dataFolder.toString() + File.separator + "playerData").toUri())

    private val gson: Gson = GsonBuilder().setPrettyPrinting().create()
    private fun getFile(player: Player): File = File(pluginDataDir, "${player.uniqueId}.json")

    /**
     * This method will load the players data from the json file to the cache [pluginPlayerDataCache]
     *
     * We use players UUID to find the file in the data directory @see [pluginDataDir].
     *
     * File names are following the format: **uuid.json**
     *
     * If the file doesn't exist it add the default values to the cache.
     * We will create the file when the we have save the data when the player leaves the server [onPlayerLeaveSaveToFile(PlayerQuitEvent]
     *
     */
    fun loadDataFromFile(player: Player): PluginPlayerData {
        val file = getFile(player)

        if (!file.exists()) {
            player.setPluginPlayerData(PluginPlayerData())
            pluginLogger.info("&7Loaded default settings for player ${player.name}".colorize())
            return PluginPlayerData()
        }
        Files.newBufferedReader(file.toPath()).use {
            val data = gson.fromJson(it, PluginPlayerData::class.java) ?: PluginPlayerData()
            player.setPluginPlayerData(data)
            return data
        }
    }

    /**
     * Method that saves player data from the cache [pluginPlayerDataCache] to a json file in the disk when the
     * player leaves the server. The file has the following name format: **uuid.json**
     *
     * If the file does not exist, it will be created automatically by [Gson.toJson]
     */
    @EventHandler(ignoreCancelled = true)
    fun saveDataToFile(player: Player) {
        val file = getFile(player)
        Files.newBufferedWriter(file.toPath()).use {
            gson.toJson(player.getPluginPlayerData(), it)
            player.removePluginPlayerData()
        }
    }

}

/**
 * Method that returns a players data from the [pluginPlayerDataCache] map
 */
fun Player.getPluginPlayerData(default: Boolean = false): PluginPlayerData =
    if (default)
        PluginPlayerData()
    else
        pluginPlayerDataCache.getOrElse(this, { PluginPlayerDataHandler.loadDataFromFile(this) })


/**
 * Method that is used to "update" the players data
 */
fun Player.setPluginPlayerData(data: PluginPlayerData) = pluginPlayerDataCache.put(this, data)

/**
 * This method will remove the data from the [pluginPlayerDataCache] cache map.
 * Must be called when a player leaves the game for example to clear the memory
 * or when this data is no longer usefull
 */
fun Player.removePluginPlayerData() = pluginPlayerDataCache.remove(this)




