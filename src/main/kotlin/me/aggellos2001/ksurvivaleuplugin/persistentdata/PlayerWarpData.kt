package me.aggellos2001.ksurvivaleuplugin.persistentdata

import co.aikar.commands.annotation.CommandAlias
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import me.aggellos2001.ksurvivaleuplugin.plugin.pluginInstance
import org.bukkit.entity.Player
import java.io.File
import java.nio.file.Files
import java.nio.file.Path

data class PlayerWarpDataClass(
    val warpName: String,
    val playerUUID: String,
    val world: String,
    val x: Int,
    val y: Int,
    val z: Int,
    val yaw: Float,
    val pitch: Float
)

private val gson: Gson = GsonBuilder().setPrettyPrinting().create()


@CommandAlias("playerwarp|playerwarps|pwarp|pwarps")
object PlayerWarpData {

    val warpDirectory = File(Path.of(pluginInstance.dataFolder.toString() + File.separator + "playerWarps").toUri())


    /**
     * Initializes the list lazily aka the files are read on first access of the [playerWarpList]
     *
     * Populates a [MutableList] with all the player warp files found in the warp directory [PlayerWarpData.warpDirectory]
     */
    val playerWarpList by lazy {
        mutableListOf<PlayerWarpDataClass>().apply {
            for (warp in File(warpDirectory.toURI()).listFiles()!!) {
                val reader = Files.newBufferedReader(warp.toPath())
                reader.use {
                    this.add(gson.fromJson(it, PlayerWarpDataClass::class.java))
                }
            }
        }
    }

    /**
     *  Adds a new .json file with a player warp parsed by Gson
     *
     *  @throws [IllegalStateException] if the [warpName] already exists as a file in the disk
     */
    fun addWarp(player: Player, warpName: String) {

        val file = File(warpDirectory, "${warpName}.json")
        if (file.exists())
            throw IllegalStateException("Trying to add a already existing warp file!")

        Files.newBufferedWriter(file.toPath()).use {
            val newWarp = PlayerWarpDataClass(
                warpName,
                player.uniqueId.toString(),
                player.location.world.name,
                player.location.blockX,
                player.location.blockY,
                player.location.blockZ,
                player.location.yaw,
                player.location.pitch
            )
            gson.toJson(newWarp, it)
            playerWarpList.add(newWarp)
        }
    }

    /**
     * Deletes a player warp json file from the disk.
     *
     * @throws [IllegalStateException] if the [playerWarp] does not exist in the disk
     */
    fun deleteWarp(player: Player, playerWarp: PlayerWarpDataClass) {
        val file = File(warpDirectory, "${playerWarp.warpName}.json")
        if (!file.exists())
            throw IllegalStateException("Trying to delete a warp that doesn't exist!")
        playerWarpList.remove(playerWarp)
        file.delete()
    }

}