package me.aggellos2001.ksurvivaleuplugin.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.ConditionFailedException
import co.aikar.commands.annotation.*
import com.github.benmanes.caffeine.cache.Caffeine
import com.github.benmanes.caffeine.cache.Scheduler
import dev.triumphteam.gui.builder.item.ItemBuilder
import dev.triumphteam.gui.components.InteractionModifier
import dev.triumphteam.gui.guis.PaginatedGui
import me.aggellos2001.ksurvivaleuplugin.persistentdata.PlayerWarpData
import me.aggellos2001.ksurvivaleuplugin.persistentdata.PlayerWarpDataClass
import me.aggellos2001.ksurvivaleuplugin.utils.*
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Player
import java.util.*
import kotlin.time.Duration
import kotlin.time.toJavaDuration

@CommandAlias("playerwarp|playerwarps|pwarp|pwarps")
@Description("Create, visit or delete warps. All players can add their warp here")
object PlayerWarp : BaseCommand() {

    private val pWarpUICache = Caffeine.newBuilder().scheduler(Scheduler.systemScheduler())
        .expireAfterAccess(Duration.seconds(15).toJavaDuration()).build<Player, PaginatedGui>()

    @Default
    fun playerWarpUI(player: Player) {
        var pWarpUI = pWarpUICache.getIfPresent(player)
        if (pWarpUI != null) {
            pWarpUI.open(player)
            return
        }
        pWarpUI = PaginatedGui(6, 45, "<#0230fa>Player Warps".colorize(), InteractionModifier.VALUES).apply {
////            disableAllInteractions()
//            setDefaultClickAction {
//                it.isCancelled = true
//            }

            //next button
            setItem(6, 6, ItemBuilder.from(Material.LIME_DYE)
                .name("&a&lNext".colorizeToComponent())
                .asGuiItem {
                    this.next()
                }
            )

            //previous button
            setItem(6, 4, ItemBuilder.from(Material.GRAY_DYE)
                .name("&c&lPrevious".colorizeToComponent())
                .asGuiItem {
                    this.previous()
                }
            )

            //exit button
            setItem(6, 9, ItemBuilder.from(Material.BARRIER)
                .name("&4&lExit".colorizeToComponent())
                .glow(true)
                .asGuiItem {
                    this.close(player)
                }
            )

            //fill bottom row
            filler.fillBottom(ItemBuilder.from(Material.GRAY_STAINED_GLASS_PANE).name(textOf(" ")).asGuiItem())


            //add warps to UI
            for (playerWarp in PlayerWarpData.playerWarpList) {
                val warpOwner = Bukkit.getOfflinePlayer(UUID.fromString(playerWarp.playerUUID))

                val warpLocation = Location(
                    Bukkit.getWorld(playerWarp.world),
                    playerWarp.x.toDouble(),
                    playerWarp.y.toDouble(),
                    playerWarp.z.toDouble(),
                    playerWarp.yaw,
                    playerWarp.pitch

                )
                val guiItem = ItemBuilder.from(Material.MAP)
                    .name("&e${playerWarp.warpName}".colorizeToComponent())
                    .lore(
                        "<#595957>Warp Owner: &e${warpOwner.name}".colorizeToComponent(),
                        "<#7fc8eb>Location: ${warpLocation.toNiceString()}".colorizeToComponent(),
                        if (player == warpOwner)
                            "<#abaaa7>Right click: &cDelete".colorizeToComponent()
                        else
                            "<#abaaa7>Click to teleport!".colorizeToComponent()
                    ).asGuiItem {
                        if (player == warpOwner) {
                            if (it.isRightClick) {
                                onDeleteWarpCommand(player, playerWarp.warpName)
                                close(player)
                                return@asGuiItem
                            }
                        }

                        if (!warpLocation.isSafe()) {
                            close(player)
                            player.sendColorizedMessage("&cUnsafe location!")
                            return@asGuiItem
                        }
                        it.whoClicked.teleportAsync(warpLocation.add(0.5, 0.0, 0.5)).thenAccept {
                            player.sendColorizedMessage("&aTeleported to player warp &e${playerWarp.warpName}&a successfully!")
                        }
                    }
                this.addItem(guiItem)
            }
            open(player)
        }
        pWarpUICache.put(player, pWarpUI)
    }

    @Subcommand("add")
    @Conditions("cooldown:time=5,name=PlayerWarp")
    fun onAddWarpCommand(player: Player, @Single warpName: String) {

        val warpNameLowerCaseFiltered = warpName.lowercase().replace(Regex("[^A-Za-z0-9]"), "")

        if (warpNameLowerCaseFiltered.length > 20) {
            throw ConditionFailedException("Warp name must be up to 20 characters!")
        }

        var warpsOwned = 0
        for (playerWarp in PlayerWarpData.playerWarpList) {
            if (player == Bukkit.getOfflinePlayer(UUID.fromString(playerWarp.playerUUID)))
                warpsOwned++
        }

        if (warpsOwned == 3) {
            throw ConditionFailedException("You cannot set more than $warpsOwned warps!")
        }

        if (!player.location.isSafe())
            throw ConditionFailedException("Unsafe location! Disabled blocks: &ePressure Plate,Leaves,Lava,Water,Magma Block")

        if (PlayerWarpData.playerWarpList.stream().anyMatch { it.warpName == warpNameLowerCaseFiltered })
            throw ConditionFailedException("Warp &e${warpNameLowerCaseFiltered}&c already exists!")


        PlayerWarpData.addWarp(player, warpNameLowerCaseFiltered)

        player.sendColorizedMessage("&aWarp $warpNameLowerCaseFiltered created!")
        pWarpUICache.invalidateAll()
    }

    @Subcommand("delete")
    @Conditions("cooldown:time=5,name=PlayerWarp")
    fun onDeleteWarpCommand(player: Player, @Single warpName: String) {

        for (playerWarp in PlayerWarpData.playerWarpList) {
            val warpOwner = Bukkit.getOfflinePlayer(UUID.fromString(playerWarp.playerUUID))
            if (playerWarp.warpName.equals(warpName, true)) {
                if (warpOwner == player || player.isOp) {
                    PlayerWarpData.deleteWarp(playerWarp)
                    player.sendColorizedMessage("&aRemoved warp &e${playerWarp.warpName}&a successfully!")
                    pWarpUICache.invalidateAll()
                } else {
                    player.sendColorizedMessage("&cYou do not own this warp!")
                }
                return //we found the warp no need to continue the loop
            }
        }
        player.sendColorizedMessage("&cWarp $warpName not found!")
    }

    @Subcommand("list")
    fun onWarpListCommand(player: Player) {
        val ownedWarps = mutableListOf<PlayerWarpDataClass>()
        for (playerWarp in PlayerWarpData.playerWarpList) {
            if (UUID.fromString(playerWarp.playerUUID).equals(player.uniqueId))
                ownedWarps.add(playerWarp)
        }
        if (ownedWarps.isEmpty())
            player.sendColorizedMessage("&cYou do not have any warps!")
        else {
            var c = 1
            val result = StringBuilder("&6Warps:")
            for (ownedWarp in ownedWarps) {
                result.append("\n${c++}. ${ownedWarp.warpName}")
            }
            player.sendColorizedMessage(result.toString())
        }
    }
}