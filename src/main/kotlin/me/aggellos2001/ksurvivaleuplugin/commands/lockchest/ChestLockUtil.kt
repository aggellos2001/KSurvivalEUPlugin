package me.aggellos2001.ksurvivaleuplugin.commands.lockchest

import me.aggellos2001.ksurvivaleuplugin.hooks.EssentialsHook.getPlayerIfExists
import me.aggellos2001.ksurvivaleuplugin.utils.*
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.bukkit.block.Chest
import org.bukkit.block.Sign
import org.bukkit.block.data.Directional
import org.bukkit.entity.Player
import org.bukkit.inventory.DoubleChestInventory

class ChestLockUtil(private val chestState: Chest) {

    val lockLabel: String = "[Locked]"
    private val lockLabelColorized: String = "&b[&aLocked&b]"

    fun addLockSign(player: Player, vararg players: String): Int {
        val blockFaceToPlaceSign = player.facing.oppositeFace
        val signBlock = chestState.block.getRelative(blockFaceToPlaceSign)
        if (signBlock.type != Material.AIR) return 0
        signBlock.type = Material.OAK_WALL_SIGN
        val sign = signBlock.state as? Sign ?: return 0
        sign.line(0, lockLabelColorized.colorizeToComponent())
        sign.line(1, player.name.deserializeToComponent())
        var index = 2
        for (extraPlayer in players.toSet()) {
            val playerToAdd = extraPlayer.getPlayerIfExists()
            if (playerToAdd == null) {
                player.sendColorizedMessage("&cPlayer &e${extraPlayer}&c does not exist!")
                signBlock.type = Material.AIR
                return -1
            }
            if (playerToAdd.name == player.name) {
                player.sendColorizedMessage("&cYou cannot add yourself as an extra player! Do /lockchest help for more info!")
                signBlock.type = Material.AIR
                return -1
            }
            sign.line(index++, playerToAdd.name.deserializeToComponent())
        }
        val direction = signBlock.blockData as Directional
        direction.facing = blockFaceToPlaceSign
        sign.blockData = direction
        sign.update()
        return 1
    }

    fun removeAnyLockSign() {
        if (chestState.inventory !is DoubleChestInventory) {
            for (block in this.getBlocksAround()) {
                val sign = block.state as? Sign ?: continue
                if (sign.isLockSign().first)
                    block.type = Material.AIR
            }
        } else {
            val blocksAroundSelectedSide = this.getBlocksAround()
            for (block in blocksAroundSelectedSide) {
                val sign = block.state as? Sign ?: continue
                if (sign.isLockSign().first)
                    block.type = Material.AIR
            }
            val blocksAroundOtherSide = this.getOtherHalf().getBlocksAround()
            for (block in blocksAroundOtherSide) {
                val sign = block.state as? Sign ?: continue
                if (sign.isLockSign().first)
                    block.type = Material.AIR
            }
        }
    }

    fun chestOwners(): Set<String> {
        val result = mutableSetOf<String>()
        if (chestState.inventory !is DoubleChestInventory) {
            for (block in this.getBlocksAround()) {
                val sign = block.state as? Sign ?: continue
                if (sign.isLockSign().first)
                    result.addAll(sign.isLockSign().second)
            }
        } else {
            //double chest
            val blocksAroundSelectedSide = this.getBlocksAround()
            for (block in blocksAroundSelectedSide) {
                val sign = block.state as? Sign ?: continue
                if (sign.isLockSign().first)
                    result.addAll(sign.isLockSign().second)
            }
            val blocksAroundOtherSide = this.getOtherHalf().getBlocksAround()
            for (block in blocksAroundOtherSide) {
                val sign = block.state as? Sign ?: continue
                if (sign.isLockSign().first)
                    result.addAll(sign.isLockSign().second)
            }
        }
        return result
    }

    private fun getBlocksAround(): List<Block> {
        return mutableListOf<Block>().apply {
            add(chestState.block.getRelative(BlockFace.EAST))
            add(chestState.block.getRelative(BlockFace.WEST))
            add(chestState.block.getRelative(BlockFace.NORTH))
            add(chestState.block.getRelative(BlockFace.SOUTH))
        }
    }

    /**
     * Will return an instance of [ChestLockUtil] with the other half of a doublechest
     * or itself if it is a normal chest
     */
    private fun getOtherHalf(): ChestLockUtil {
        val inventory = chestState.inventory
        if (inventory !is DoubleChestInventory) return this
        val doubleChestData = chestState.blockData as org.bukkit.block.data.type.Chest
        return if (doubleChestData.type == org.bukkit.block.data.type.Chest.Type.LEFT)
            ChestLockUtil(inventory.leftSide.location?.block?.state as Chest)
        else ChestLockUtil((inventory.rightSide.location?.block?.state as Chest))
    }

    private fun Sign.isLockSign(): Pair<Boolean, Set<String>> {
        val result = mutableSetOf<String>()
        val strippedFirstLine = this.line(0).serializeToString().stripColors()
        if (strippedFirstLine != lockLabel) {
            return false to emptySet()
        }
        for (line in this.lines()) {
            val lineSerialized = line.serializeToString().stripColors()
            val player = lineSerialized.getPlayerIfExists()
            if (player != null)
                result.add(lineSerialized)
        }
        return true to result
    }
}