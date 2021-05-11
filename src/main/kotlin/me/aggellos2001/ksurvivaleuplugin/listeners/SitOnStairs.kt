package me.aggellos2001.ksurvivaleuplugin.listeners

import me.aggellos2001.ksurvivaleuplugin.persistentdata.getPluginPlayerData
import me.aggellos2001.ksurvivaleuplugin.plugin.pluginInstance
import me.aggellos2001.ksurvivaleuplugin.utils.*
import org.bukkit.Material
import org.bukkit.block.BlockFace
import org.bukkit.block.data.Bisected
import org.bukkit.block.data.type.Stairs
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.EquipmentSlot
import org.spigotmc.event.entity.EntityDismountEvent
import java.util.*
import kotlin.time.Duration

object SitOnStairs : Listener {

    private val lastTimeSat = WeakHashMap<Player, Duration>()

    @EventHandler(ignoreCancelled = true)
    fun onStairBlocksInteract(e: PlayerInteractEvent) {

        if (e.hand == EquipmentSlot.OFF_HAND) return
        if (e.action != Action.RIGHT_CLICK_BLOCK) return

        val clickedBlock = e.clickedBlock ?: return
        val stair = e.clickedBlock?.blockData as? Stairs ?: return
        val player = e.player

        if (!player.getPluginPlayerData().sittingOnStairs) return

        if (player.getCoolDown("sitonstairs").first) {
            player.sendColorizedMessage(player.getCoolDownErrorMessage("sitonstairs"))
            return
        }

        val blockUp = clickedBlock.getRelative(BlockFace.UP).type
        if (stair.half == Bisected.Half.BOTTOM && (blockUp == Material.AIR || blockUp == Material.CAVE_AIR)) {
            val arrow = clickedBlock.world.spawnEntity(clickedBlock.location.add(0.5, 0.0, 0.5), EntityType.ARROW)
            arrow.addPassenger(player)
            player.setCoolDown("sitonstairs", Duration.seconds(5))
            //schedule a runnable because "press left shift to dismount" will override actionbar message
            scheduleRunnable({
                player.sendActionBar("&eGo to /settings to disable sitting on stair blocks!".colorizeToComponent())
            }, pluginInstance, Duration.seconds(2))
        }
    }

    @EventHandler(ignoreCancelled = true)
    fun cleanupArrow(e: EntityDismountEvent) {
        if (e.dismounted.type == EntityType.ARROW)
            e.dismounted.remove()
    }
}