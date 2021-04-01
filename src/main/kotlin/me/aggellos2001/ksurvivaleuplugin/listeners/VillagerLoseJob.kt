package me.aggellos2001.ksurvivaleuplugin.listeners

import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.entity.Villager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEntityEvent
import org.bukkit.inventory.EquipmentSlot

object VillagerLoseJob : Listener {

    @EventHandler(ignoreCancelled = true)
    fun onPlayerInteractingWithVillager(e: PlayerInteractEntityEvent) {

        val villager = e.rightClicked as? Villager ?: return
        val player = e.player

        if (villager.profession == Villager.Profession.NONE) return
        if (e.hand == EquipmentSlot.OFF_HAND) return
        if (!player.isSneaking) return
        if (villager.villagerLevel > 1 || villager.villagerExperience > 1) return

        val itemInHand = player.inventory.itemInMainHand
        if (itemInHand.type != Material.DIAMOND) return
        e.isCancelled = true

        if (player.gameMode != GameMode.CREATIVE)
            itemInHand.subtract()

        villager.profession = Villager.Profession.NONE

    }
}