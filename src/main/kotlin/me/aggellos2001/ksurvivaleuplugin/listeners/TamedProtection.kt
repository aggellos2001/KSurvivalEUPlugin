package me.aggellos2001.ksurvivaleuplugin.listeners

import me.aggellos2001.ksurvivaleuplugin.utils.sendColorizedMessage
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.entity.Projectile
import org.bukkit.entity.Tameable
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.player.PlayerBucketEmptyEvent

object TamedProtection : Listener {

    @EventHandler(ignoreCancelled = true)
    fun onPlayerAttackingTamedMob(e: EntityDamageByEntityEvent) {

        val attacker = e.damager
        val defender = e.entity as? Tameable ?: return

        if (!defender.isTamed) return

        if (attacker is Player) {
            if (attacker.isOp) return
            val owner = defender.owner
            if (defender.owner != attacker && owner!= null) {
                attacker.sendColorizedMessage("&cYou cannot harm this animal. It is tamed by ${owner.name}!")
                e.isCancelled = true
            }
        }

        if (attacker is Projectile) {
            val owner = defender.owner
            if (attacker.shooter != defender.owner && owner != null ) {
                attacker.sendColorizedMessage("&cYou cannot harm this animal. It is tamed by ${owner.name}!")
                e.isCancelled = true
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    fun onPlacingLavaNearTamed(e: PlayerBucketEmptyEvent) {
        val player = e.player
        if (e.bucket != Material.LAVA_BUCKET) return
        for (nearbyLivingEntity in e.blockClicked.location.getNearbyLivingEntities(5.0)) {
            if (nearbyLivingEntity is Tameable && nearbyLivingEntity.isTamed && nearbyLivingEntity.owner != player) {
                player.sendColorizedMessage("&cYou cannot put lava here. There's a tamed animal nearby!")
                e.isCancelled = true
            }
        }
    }
}