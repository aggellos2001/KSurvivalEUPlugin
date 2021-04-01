package me.aggellos2001.ksurvivaleuplugin.utils

import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.BlockFace

fun Location.isSafe(): Boolean {

    //block where is the players feet (not the block below the player)
    val feetBlock = this.block.type
    if (feetBlock.isSolid || feetBlock == Material.TRIPWIRE || feetBlock.isLiquid())
        return false //not safe if its solid or liquid (lava or water)

    //block half up of the player
    val headBlock = this.block.getRelative(BlockFace.UP).type
    if (headBlock.isSolid || headBlock.isLiquid())
        return false  // not safe if its solid or liquid for the player

    //block under legs
    val groundBlock = this.block.getRelative(BlockFace.DOWN).type
    if (!groundBlock.isSolid) return false // must be solid so the player can stand

    /* also the ground block must also not be a pressure plate or a tripwire for example
		   so we can avoid traps. also no leaves so we do not spawn on top of a tree if we use
		   this in a (wild) command for example.
		 */
    if (groundBlock.name.contains("PRESSURE_PLATE") || groundBlock.name.contains("LEAVES"))
        return false
    if (groundBlock == Material.LAVA || groundBlock == Material.WATER || groundBlock == Material.MAGMA_BLOCK)
        return false

    return true
}

fun Location.toNiceString(): String =
    "World = ${this.world.name}, X = ${this.blockX}, Y = ${this.blockY}, Z= ${this.blockZ}"

fun Location.toNiceStringColorized(): String =
    "&eWorld = &b${this.world.name}, &eX = &b${this.blockX}, &eY = &b${this.blockY}, &eZ= &b${this.blockZ}"