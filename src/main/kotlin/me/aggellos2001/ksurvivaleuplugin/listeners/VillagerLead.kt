package me.aggellos2001.ksurvivaleuplugin.listeners

import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.entity.Villager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEntityEvent
import org.bukkit.inventory.EquipmentSlot

object VillagerLead : Listener {

    @EventHandler(ignoreCancelled = true)
    fun onVillageInteract(e: PlayerInteractEntityEvent) {

        val villager = e.rightClicked as? Villager ?: return
        val player = e.player
        if (!player.isSneaking) return
        if (e.hand == EquipmentSlot.OFF_HAND) return
        if (e.player.inventory.itemInMainHand.type != Material.LEAD) return

        if (villager.isLeashed) return

        e.isCancelled = true
        val lead = player.inventory.itemInMainHand

        if (player.gameMode != GameMode.CREATIVE) {
            lead.subtract()
        }
        villager.setLeashHolder(player)
    }
}