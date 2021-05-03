package me.aggellos2001.ksurvivaleuplugin.listeners

import com.earth2me.essentials.api.Economy
import me.aggellos2001.ksurvivaleuplugin.persistentdata.ShopPrices.getShopPrice
import me.aggellos2001.ksurvivaleuplugin.plugin.COREPROTECT_API
import me.aggellos2001.ksurvivaleuplugin.utils.stripDecimals
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.event.player.PlayerMoveEvent
import kotlin.random.Random

object EarningMoney : Listener {

    private val random = Random.Default

    @EventHandler(ignoreCancelled = true)
    fun onBlockBreak(e: BlockBreakEvent) {
        if (random.nextInt(100) < 70) return //30% to run
        val block = e.block
        val material = block.type
        val price = material.getShopPrice()

        if (price == 0.0) return
        val player = e.player

        val coreAPI = COREPROTECT_API
        val isModified = coreAPI.blockLookup(block, 2592000)
        if (isModified.isNotEmpty()) return

        val percentageOfPrice = random.nextDouble(0.001, 0.02)
        val balanceWon = price * percentageOfPrice.stripDecimals(4)
        Economy.add(player.uniqueId, balanceWon.toBigDecimal())
    }

    @EventHandler(ignoreCancelled = true)
    fun onEntityDeath(e: EntityDeathEvent) {
        if (random.nextInt(100) < 90) return //10% to run

        val entity = e.entity
        if (entity is Player) return

        val player = e.entity.killer ?: return
        val droppedExp = e.droppedExp
        val xpPrice = 5
//        val drops = e.drops
        var balanceWon = 0.000

//        var valuableDrops = 0

//        if (drops.isNotEmpty()) {
//            for (drop in drops) {
//                val price = drop.type.getShopPrice()
//                if (price == 0.0) continue
//                valuableDrops++
//                val dropAmount = drop.amount
//                val percentageOfPrice = random.nextDouble(0.001, 0.01)
//                balanceWon += price * percentageOfPrice * dropAmount
//            }
//        }
//        if (drops.isEmpty() || valuableDrops == 0) {
//            val percentageOfPrice = random.nextDouble(0.001, 0.1)
//            balanceWon += (droppedExp * xpPrice) * percentageOfPrice
//        }
//
//        if (balanceWon > 2) {
//            balanceWon -= (balanceWon.toInt() / 2)
//        }
        val percentageOfPrice = random.nextDouble(0.001, 0.1)
        balanceWon += (droppedExp * xpPrice) * percentageOfPrice

        balanceWon = balanceWon.stripDecimals(4)
        Economy.add(player.uniqueId, balanceWon.toBigDecimal())
    }

    @EventHandler(ignoreCancelled = true)
    fun onPlayerMoveEvent(e: PlayerMoveEvent) {
        if (random.nextInt(100) < 99) return // 1% of the time
        if (e.from.x == e.to.x && e.from.y == e.to.y && e.from.z == e.from.z) return
        val player = e.player

        val balanceWon = random.nextDouble(0.001, 0.01).stripDecimals(4)
        Economy.add(player.uniqueId, balanceWon.toBigDecimal())
    }

}