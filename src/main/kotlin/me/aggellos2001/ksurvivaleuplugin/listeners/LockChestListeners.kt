package me.aggellos2001.ksurvivaleuplugin.listeners

import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.Caffeine
import com.github.benmanes.caffeine.cache.Scheduler
import me.aggellos2001.ksurvivaleuplugin.utils.ChestLockUtil
import me.aggellos2001.ksurvivaleuplugin.utils.sendColorizedMessage
import me.aggellos2001.ksurvivaleuplugin.utils.serializeToString
import me.aggellos2001.ksurvivaleuplugin.utils.stripColors
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.block.Chest
import org.bukkit.block.Sign
import org.bukkit.block.data.Directional
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.SignChangeEvent
import org.bukkit.event.inventory.InventoryMoveItemEvent
import org.bukkit.event.player.PlayerInteractEvent
import kotlin.time.seconds
import kotlin.time.toJavaDuration

object LockChestListeners : Listener {


    /**
     * A cache to store blocks to speed up [preventHopperSteal] method
     *
     * Basically what it does is it stores the [ChestLockUtil] for this block location in the cache for 10 seconds
     * so it doesn't create a [ChestLockUtil] object every time AND returns [InventoryMoveItemEvent] faster
     *
     */
    private val lockedChestCache: Cache<Block, ChestLockUtil> =
        Caffeine.newBuilder().scheduler(Scheduler.systemScheduler())
            .expireAfterWrite(30.seconds.toJavaDuration()).build()

    @EventHandler(ignoreCancelled = true)
    fun preventLockingWithoutCommand(e: SignChangeEvent) {
        val sign = e.block.state as Sign
        val signOppositeFace = (sign.blockData as? Directional)?.facing?.oppositeFace ?: return
        val chest = ChestLockUtil(e.block.getRelative(signOppositeFace).state as? Chest ?: return)
        if (!e.line(0)?.serializeToString()?.stripColors().equals(chest.lockLabel, true)) return
        e.player.sendColorizedMessage("&cYou must use /lockchest to lock this chest!")
        e.block.breakNaturally()
        e.isCancelled = true
    }

    @EventHandler(ignoreCancelled = true)
    fun onLockSignBreak(e: BlockBreakEvent) {
        val sign = e.block.state as? Sign ?: return
        val player = e.player
        val signOppositeFace = (sign.blockData as? Directional)?.facing?.oppositeFace ?: return
        val chest = ChestLockUtil(e.block.getRelative(signOppositeFace).state as? Chest ?: return)
        if (!sign.line(0).serializeToString().stripColors().equals(chest.lockLabel, true)) return
        val signPlayers = chest.chestOwners()
        e.isCancelled = true
        if (!player.isSneaking) {
            player.sendColorizedMessage("&cYou must sneak to break a lock sign!")
            return
        }
        if (player.isOp && !signPlayers.contains(player.name)) {
            e.block.type = Material.AIR
            player.sendColorizedMessage("&aLock removed! &e(Bypassed because you are a server operator)")
        } else if (signPlayers.contains(player.name)) {
            e.block.type = Material.AIR
            player.sendColorizedMessage("&aLock removed!")
        } else
            player.sendColorizedMessage("&cYou don't own this lock!")
    }

    @EventHandler(ignoreCancelled = true)
    fun preventAccess(e: PlayerInteractEvent) {
        val player = e.player
        if (player.isOp) return

        val chest = ChestLockUtil(e.clickedBlock?.state as? Chest ?: return)
        val owners = chest.chestOwners()
        if (owners.isEmpty()) return
        val isOwner = player.name in chest.chestOwners()
        if (!isOwner) {
            player.sendColorizedMessage("&cThis chest is locked by ${owners.joinToString(", ")}!")
            e.isCancelled = true
        }
    }

    @EventHandler(ignoreCancelled = true)
    fun preventBreakFromTrusted(e: BlockBreakEvent) {
        val player = e.player
        if (player.isOp) return
        val chest = ChestLockUtil(e.block.state as? Chest ?: return)
        val owners = chest.chestOwners()
        if (owners.isEmpty()) return
        val lockOwner = owners.first() == player.name
        val isOwner = owners.contains(player.name)
        if (lockOwner) {
            chest.removeAnyLockSign()
            player.sendColorizedMessage("&aYou broke a protected chest!")
        } else if (!lockOwner && isOwner) {
            player.sendColorizedMessage("&cChest is locked by &e${owners.first()}&c! Ask him to break the chest!")
            e.isCancelled = true
        } else {
            player.sendColorizedMessage("&cThis chest is locked by &e${owners.joinToString(", ")}&c!")
            e.isCancelled = true
        }
    }

    @EventHandler(ignoreCancelled = true)
    fun preventHopperSteal(e: InventoryMoveItemEvent) {
        val block = e.source.location?.block ?: return
        var chest = lockedChestCache.getIfPresent(block)
        if (chest != null) {
            e.isCancelled = true
            return
        }
        chest = ChestLockUtil(block.state as? Chest ?: return)
        if (chest.chestOwners().isNotEmpty()) {
            //this will run only when block is not in cache and chest is locked
            e.isCancelled = true
            lockedChestCache.put(block, chest)
        }
    }
}