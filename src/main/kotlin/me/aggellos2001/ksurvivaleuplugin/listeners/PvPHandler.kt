package me.aggellos2001.ksurvivaleuplugin.listeners

import me.aggellos2001.ksurvivaleuplugin.persistentdata.getPluginPlayerData
import me.aggellos2001.ksurvivaleuplugin.utils.sendColorizedMessage
import org.bukkit.Material
import org.bukkit.World
import org.bukkit.entity.AreaEffectCloud
import org.bukkit.entity.Player
import org.bukkit.entity.Projectile
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.player.PlayerBucketEmptyEvent
import org.bukkit.event.player.PlayerInteractEvent

object PvPHandler : Listener {

    private fun Player.canBypassPvP(): Boolean = player?.hasPermission("seu.bypass") ?: false

    /**
     * Stops player PvP if they have it disabled
     */
    @EventHandler(ignoreCancelled = true)
    fun onEntityDamageByEntity(e: EntityDamageByEntityEvent) {

        val attacker = (e.damager as? Player) ?: return
        val defender = e.entity as? Player ?: return

        val attackerData = attacker.getPluginPlayerData()
        val defenderData = defender.getPluginPlayerData()

        if (!attackerData.pvp && !attacker.canBypassPvP()) {
            attacker.sendColorizedMessage("&cYou have PvP disabled! Go to /settings and enabled it first!")
            e.isCancelled = true
            return
        }

        if (!defenderData.pvp && !attacker.canBypassPvP()) {
            attacker.sendColorizedMessage("&cPlayer &e${defender.name}&c has PvP disabled!")
            e.isCancelled = true
        }
    }


    /**
     * Stops player pvp from projectiles if they have it disabled.
     */
    @EventHandler(ignoreCancelled = true)
    fun onProjectileDamageEntity(e: EntityDamageByEntityEvent) {

        val attacker = (e.damager as? Projectile)?.shooter as? Player ?: return
        val defender = e.entity as? Player ?: return

        val attackerData = attacker.getPluginPlayerData()
        val defenderData = defender.getPluginPlayerData()

        if (!attackerData.pvp && !attacker.isOp) {
            attacker.sendColorizedMessage("&cYou have PvP disabled! Go to /settings and enabled it first!")
            e.isCancelled = true
            return
        }

        if (!defenderData.pvp && !attacker.canBypassPvP()) {
            attacker.sendColorizedMessage("&cPlayer &e${defender.name}&c has PvP disabled!")
            e.isCancelled = true
        }
    }

    /**
     * Stops players to being able to place lava buckets if players nearby have pvp disabled
     */
    @EventHandler(ignoreCancelled = true)
    fun onLavaBucketPlace(e: PlayerBucketEmptyEvent) {

        if (e.bucket != Material.LAVA_BUCKET) return
        val attacker = e.player

        for (nearbyPlayer in e.blockClicked.location.getNearbyPlayers(5.0)) {
            val defenderPvP = nearbyPlayer.getPluginPlayerData().pvp
            if (!defenderPvP && attacker != nearbyPlayer && !attacker.isOp) {
                attacker.sendColorizedMessage("&cPlayer &e${nearbyPlayer.name}&c has PvP disabled! Place the bucket 5 blocks away!")
                e.isCancelled = true
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    fun onFlintTntBedInteraction(e: PlayerInteractEvent) {

        if (e.action != Action.RIGHT_CLICK_BLOCK) return

        val attacker = e.player
        val itemInHand = e.item ?: return
        val clickedBlock = e.clickedBlock ?: return
        val clickedBlockEnvironment = clickedBlock.world.environment

        if (itemInHand.type == Material.FLINT_AND_STEEL) {
            for (nearbyPlayer in clickedBlock.location.getNearbyPlayers(2.0)) {
                val defenderPvP = nearbyPlayer.getPluginPlayerData().pvp
                if (!defenderPvP && attacker != nearbyPlayer && !attacker.isOp) {
                    attacker.sendColorizedMessage("&cPlayer &e${nearbyPlayer.name}&c has PvP disabled! Place the bucket 5 blocks away!")
                    e.isCancelled = true
                    return //return cuz no need to check anything else
                }
            }
        }

        if (itemInHand.type == Material.FLINT_AND_STEEL && (clickedBlock.type == Material.TNT || clickedBlock.type == Material.TNT_MINECART)) {
            for (nearbyPlayer in clickedBlock.location.getNearbyPlayers(5.0)) {
                val defenderPvP = nearbyPlayer.getPluginPlayerData().pvp
                if (!defenderPvP && attacker != nearbyPlayer && !attacker.isOp) {
                    attacker.sendColorizedMessage("&cPlayer &e${nearbyPlayer.name}&c has PvP disabled! Place the bucket 5 blocks away!")
                    e.isCancelled = true
                    return //return cuz no need to check anything else
                }
            }
        }

        if (itemInHand.type.name.contains("BED") && itemInHand.type != Material.BEDROCK
            && (clickedBlockEnvironment == World.Environment.NETHER) || clickedBlockEnvironment == World.Environment.THE_END
        ) {
            for (nearbyPlayer in clickedBlock.location.getNearbyPlayers(5.0)) {
                val defenderPvP = nearbyPlayer.getPluginPlayerData().pvp
                if (!defenderPvP && attacker != nearbyPlayer && !attacker.isOp) {
                    attacker.sendColorizedMessage("&cPlayer &e${nearbyPlayer.name}&c has PvP disabled! Place the bucket 5 blocks away!")
                    e.isCancelled = true
                    return //return cuz no need to check anything else
                }
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    fun onLingeringPotionDamageEntity(e: EntityDamageByEntityEvent) {

        val attacker = (e.damager as? AreaEffectCloud)?.source as? Player ?: return
        val defender = e.entity as? Player ?: return

        if (attacker == defender) return

        val attackerData = attacker.getPluginPlayerData()
        val defenderData = defender.getPluginPlayerData()

        if (!attackerData.pvp && !attacker.isOp) {
            attacker.sendColorizedMessage("&cYou have PvP disabled! Go to /settings and enabled it first!")
            e.isCancelled = true
            return
        }

        if (!defenderData.pvp && !attacker.canBypassPvP()) {
            attacker.sendColorizedMessage("&cPlayer &e${defender.name}&c has PvP disabled!")
            e.isCancelled = true
        }
    }

}