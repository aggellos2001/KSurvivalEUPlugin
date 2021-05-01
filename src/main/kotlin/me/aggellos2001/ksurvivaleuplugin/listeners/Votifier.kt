package me.aggellos2001.ksurvivaleuplugin.listeners

import com.earth2me.essentials.api.Economy
import com.vexsoftware.votifier.model.VotifierEvent
import me.aggellos2001.ksurvivaleuplugin.hooks.EssentialsHook.getPlayerIfExists
import me.aggellos2001.ksurvivaleuplugin.utils.sendColorizedMessage
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.inventory.ItemStack

object Votifier : Listener {

    @EventHandler(ignoreCancelled = true)
    fun onReceivingVote(e: VotifierEvent) {

        val vote = e.vote
        val player = vote.username.getPlayerIfExists() ?: return
        Bukkit.getServer().sendColorizedMessage("&6${player.name}&a voted for the server from &6${vote.serviceName}&a!")

        if (!player.isOnline) return

        val rewards = arrayOf(
            "acb " + player.name + " 50",
        )
        Economy.add(player.uniqueId, 72.toBigDecimal()) //adds 72$ silently

        for (reward in rewards) {
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), reward)
        }
        val didNotFitItems = player.inventory.addItem(ItemStack(Material.PHANTOM_MEMBRANE, 1))
        if (didNotFitItems.isNotEmpty()) {
            didNotFitItems.forEach { entry ->
                val loc = player.location
                loc.world.dropItemNaturally(loc, entry.value)
            }
            player.sendColorizedMessage("&eSome items did not fit on your inventory and were dropped on the ground!")
        }
        player.sendColorizedMessage("&aYou got 50 claim blocks, 1 phantom membrane and 72$ for your vote!")
    }
}
