package me.aggellos2001.ksurvivaleuplugin.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.Default
import co.aikar.commands.annotation.Description
import me.aggellos2001.ksurvivaleuplugin.utils.sendColorizedMessage
import org.bukkit.block.Biome
import org.bukkit.entity.Player

@CommandAlias("slimechunk|chunkslime")
@Description("Check if you are on a slime chunk")
object SlimeChunk : BaseCommand() {

    @Default
    fun onSlimeChunkCommand(player: Player) {
        val chunk = player.chunk
        when {
            chunk.isSlimeChunk ->
                player.sendColorizedMessage("&aYou're in a Slime Chunk. Slimes spawn at Y less or equal of 40!")
            player.location.block.biome == Biome.SWAMP ->
                player.sendColorizedMessage("aYou are in a SWAMP Biome. Slimes here can spawn on a light level below 8 (Which means no bright light sources nearby)!")
            else ->
                player.sendColorizedMessage("&cSlimes cannot spawn in the biome you're in right now! Find a slime chunk or a swamp biome!")
        }
    }
}