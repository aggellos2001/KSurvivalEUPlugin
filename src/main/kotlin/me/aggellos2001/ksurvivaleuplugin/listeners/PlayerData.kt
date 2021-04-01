package me.aggellos2001.ksurvivaleuplugin.listeners

import com.google.gson.Gson
import me.aggellos2001.ksurvivaleuplugin.persistentdata.PluginPlayerDataHandler
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import java.util.*

object PlayerData : Listener {


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
    @EventHandler(ignoreCancelled = true)
    private fun onPlayerJoinLoadDataFromFile(e: PlayerJoinEvent) {
        PluginPlayerDataHandler.loadDataFromFile(e.player)
    }

    /**
     * Method that saves player data from the cache [pluginPlayerDataCache] to a json file in the disk when the
     * player leaves the server. The file has the following name format: **uuid.json**
     *
     * If the file does not exist, it will be created automatically by [Gson.toJson]
     */
    @EventHandler(ignoreCancelled = true)
    private fun onPlayerLeaveSaveToFile(e: PlayerQuitEvent) {
        PluginPlayerDataHandler.saveDataToFile(e.player)
    }
}