package me.aggellos2001.ksurvivaleuplugin.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.ConditionFailedException
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.Conditions
import co.aikar.commands.annotation.Default
import co.aikar.commands.annotation.Description
import me.aggellos2001.ksurvivaleuplugin.hooks.EssentialsHook.getEssentialsUser
import me.aggellos2001.ksurvivaleuplugin.persistentdata.pluginConfig
import me.aggellos2001.ksurvivaleuplugin.utils.isSafe
import me.aggellos2001.ksurvivaleuplugin.utils.sendColorizedMessage
import me.aggellos2001.ksurvivaleuplugin.utils.setCoolDown
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.entity.Player
import kotlin.random.Random
import kotlin.time.seconds

@CommandAlias("wild")
@Conditions("cooldown:time=120,name=Wild")
@Description("Teleport somewhere randomly into the world")
object Wild : BaseCommand() {


    private fun getRandomXZ(): Pair<Int, Int> {
        val maxDistance = pluginConfig.wildBlockRange
        val randomX = Random.nextInt(-maxDistance, maxDistance)
        val randomZ = Random.nextInt(-maxDistance, maxDistance)
        return randomX to randomZ

    }

    private fun teleportToRandomLocation(player: Player, maxRetries: Int) {
        if (maxRetries == 0) {
            player.setCoolDown("Wild", 15.seconds)
            throw ConditionFailedException("Teleportation failed. Do /wild again!")
        }
        val (x, z) = getRandomXZ()
        player.world.getChunkAtAsync(Location(player.world, x.toDouble(), 0.0, z.toDouble()), true).thenAccept {
            var teleported = false
            for (i in 1 until 15) {
                val randomXInChunk = Random.nextInt(i)
                val randomZInChunk = Random.nextInt(i)
                val highestBlockY = it.chunkSnapshot.getHighestBlockYAt(randomXInChunk, randomZInChunk)
                val possibleBlockForTeleport = it.getBlock(randomXInChunk, highestBlockY, randomZInChunk)
                if (!possibleBlockForTeleport.location.isSafe()) continue
                player.teleportAsync(possibleBlockForTeleport.location)
                player.getEssentialsUser().takeMoney(pluginConfig.wildCost.toBigDecimal())
                player.sendColorizedMessage("&aYou got teleported into the wild successfully!")
                teleported = true
                break
            }
            if (!teleported) {
                teleportToRandomLocation(player, maxRetries - 1)
            }
        }
    }

    @Default
    fun onWildCommand(player: Player) {

        if (player.world.environment != World.Environment.NORMAL) {
            player.setCoolDown("Wild", 5.seconds)
            throw ConditionFailedException("Wild is not supported in this world!")
        }

        val cost = pluginConfig.wildCost

        if (!player.getEssentialsUser().canAfford(cost.toBigDecimal())) {
            player.setCoolDown("Wild", 5.seconds)
            throw ConditionFailedException("You need at least $cost$ to do /wild!")
        }

        player.sendColorizedMessage("&eFinding a suitable location for teleportation!")
        teleportToRandomLocation(player, 10)
    }
}