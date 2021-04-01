package me.aggellos2001.ksurvivaleuplugin.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.ConditionFailedException
import co.aikar.commands.annotation.*
import me.aggellos2001.ksurvivaleuplugin.persistentdata.PlayerWarpData
import me.aggellos2001.ksurvivaleuplugin.persistentdata.PlayerWarpDataClass
import me.aggellos2001.ksurvivaleuplugin.utils.*
import me.mattstudios.mfgui.gui.components.util.ItemBuilder
import me.mattstudios.mfgui.gui.guis.PaginatedGui
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Player
import java.util.*

@CommandAlias("playerwarp|playerwarps|pwarp|pwarps")
@Conditions("cooldown:time=3,name=PlayerWarp")
object PlayerWarp : BaseCommand() {

    @Default
    fun playerWarpUI(player: Player) {

        val pWarpUI = PaginatedGui(6, 45, "<#0230fa>Player Warps".colorize()).apply {
            setDefaultClickAction {
                it.isCancelled = true
            }

            //next button
            setItem(6, 6, ItemBuilder.from(Material.LIME_DYE)
                .setName("&a&lNext".colorize())
                .asGuiItem {
                    this.next()
                }
            )

            //previous button
            setItem(6, 4, ItemBuilder.from(Material.GRAY_DYE)
                .setName("&c&lPrevious".colorize())
                .asGuiItem {
                    this.previous()
                }
            )

            //exit button
            setItem(6, 9, ItemBuilder.from(Material.BARRIER)
                .setName("&4&lExit".colorize())
                .glow(true)
                .asGuiItem {
                    this.close(player)
                }
            )

            //fill bottom row
            filler.fillBottom(ItemBuilder.from(Material.GRAY_STAINED_GLASS_PANE).setName(" ").asGuiItem())


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
                    .setName("&e${playerWarp.warpName}".colorize())
                    .setLore(
                        "<#595957>Warp Owner: &e${warpOwner.name}".colorize(),
                        "<#7fc8eb>Location: ${warpLocation.toNiceString()}".colorize(),
                        if (player == warpOwner)
                            "<#abaaa7>Right click: &cDelete".colorize()
                        else
                            "<#abaaa7>Click to teleport!".colorize()
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
    }

    @Subcommand("add")
    fun onAddWarpCommand(player: Player, @Single warpName: String) {

        if (warpName.length > 20) {
            throw ConditionFailedException("Warp name must be up to 20 characters!")
        }

        var warpsOwned = 0
        for (playerWarp in PlayerWarpData.playerWarpList) {
            if (player == Bukkit.getOfflinePlayer(UUID.fromString(playerWarp.playerUUID)))
                warpsOwned++
        }

        if (warpsOwned == 2) {
            throw ConditionFailedException("You cannot set more than $warpsOwned warps!")
        }

        if (!player.location.isSafe())
            throw ConditionFailedException("Unsafe location! Disabled blocks: &ePressure Plate,Leaves,Lava,Water,Magma Block")

        if (PlayerWarpData.playerWarpList.stream().anyMatch { it.warpName == warpName })
            throw ConditionFailedException("Warp &e${warpName}&c already exists!")

        PlayerWarpData.addWarp(player, warpName.toLowerCase())

        player.sendColorizedMessage("&aWarp ${warpName.toLowerCase()} created!")
    }

    @Subcommand("delete")
    fun onDeleteWarpCommand(player: Player, @Single warpName: String) {

        for (playerWarp in PlayerWarpData.playerWarpList) {
            val warpOwner = Bukkit.getOfflinePlayer(UUID.fromString(playerWarp.playerUUID))
            if (playerWarp.warpName.equals(warpName, true)) {
                if (warpOwner == player || player.isOp) {
                    PlayerWarpData.deleteWarp(player, playerWarp)
                    player.sendColorizedMessage("&aRemoved warp &e${playerWarp.warpName}&a successfully!")
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