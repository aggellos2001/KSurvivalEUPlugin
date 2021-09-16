package me.aggellos2001.ksurvivaleuplugin.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.Conditions
import co.aikar.commands.annotation.Default
import co.aikar.commands.annotation.Description
import dev.triumphteam.gui.builder.item.ItemBuilder
import dev.triumphteam.gui.components.InteractionModifier
import dev.triumphteam.gui.guis.Gui
import dev.triumphteam.gui.guis.GuiItem
import me.aggellos2001.ksurvivaleuplugin.persistentdata.getPluginPlayerData
import me.aggellos2001.ksurvivaleuplugin.persistentdata.setPluginPlayerData
import me.aggellos2001.ksurvivaleuplugin.utils.*
import net.kyori.adventure.text.format.TextColor
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemFlag

@CommandAlias("settings")
@Conditions("cooldown:time=2,name=Settings")
@Description("Manage features like chat color, keep inventory, pvp and more")
object Settings : BaseCommand() {

    @Default
    fun settingsUI(player: Player) {

        val data = player.getPluginPlayerData()

        Gui(1, legacyTextOf("<g:#ff8000:#31bd1d>Settings Menu").content(), InteractionModifier.VALUES).apply {
//            setDefaultClickAction {
//                it.isCancelled = true
//            }
            addSlotAction(2) {
                val keepInv = data.keepInventory
                data.keepInventory = !keepInv
                player.setPluginPlayerData(data)
                this.updateItem(2, getStateButton("&eKeep Inventory", !keepInv))
            }
            addSlotAction(3) {
                val sitOnStairs = data.sittingOnStairs
                data.sittingOnStairs = !sitOnStairs
                player.setPluginPlayerData(data)
                this.updateItem(3, getStateButton("&eSit on stair blocks", !sitOnStairs))

            }
            addSlotAction(4) {
                if (Donation.hasDonationEffects(player)) {
                    player.sendColorizedMessage("&cYou cannot toggle PvP while donation potions are enabled!")
                    return@addSlotAction
                }
                val pvp = data.pvp
                data.pvp = !pvp
                player.setPluginPlayerData(data)
                this.updateItem(4, getStateButton("&ePvP", !pvp))
            }
            setItem(2, getStateButton("&eKeep Inventory", player.getPluginPlayerData().keepInventory))
            setItem(3, getStateButton("&eSit on stair blocks", player.getPluginPlayerData().sittingOnStairs))
            setItem(4, getStateButton("&ePvP", player.getPluginPlayerData().pvp))
            setItem(5, ItemBuilder.from(Material.PAPER)
                .name("&eChat color".colorizeToComponent())
                .glow(true)
                .lore(
                    ("&${data.chatColor}" + ChatColor.getByChar(player.getPluginPlayerData().chatColor)
                        ?.toNiceString()).colorizeToComponent()
                ).asGuiItem {
                    colorUI(player, this)
                })
            //finally open inventory to player
            open(player)
        }

    }

    private fun colorUI(player: Player, settingsUI: Gui) {

        Gui(2, legacyTextOf("<r:0.8:1.0>Chat Color Menu").content(), InteractionModifier.VALUES).run {
//            setDefaultClickAction {
//                it.isCancelled = true
//            }


            val data = player.getPluginPlayerData()

            //adding colors to UI
            for (color in ChatColor.values()) {
                if (!color.isColor) continue
                val menuName = "&${color.char}${color.toNiceString()}".colorize()
                addItem(ItemBuilder.from(getDyeFromColor(color))
                    .name(legacyTextOf(menuName))
                    .asGuiItem {
                        data.chatColor = color.char
                        player.setPluginPlayerData(data)
                        close(player)
                        player.sendColorizedMessage("&eChat color changed to: &r&l${menuName}")
                    })
            }

            setItem(17, ItemBuilder.from(Material.OAK_DOOR)
                .name(textOf("**Back**", TextColor.fromHexString("#bbbd60")))
                .glow(true)
                .asGuiItem {
                    settingsUI.open(it.whoClicked)
                }
            )
            //finally open inventory to player
            open(player)

        }
    }

    private fun getStateButton(name: String, state: Boolean): GuiItem {
        return ItemBuilder.from(if (state) Material.GREEN_WOOL else Material.RED_WOOL)
            .name((if (state) "&a&lON" else "&c&lOFF").colorizeToComponent())
            .lore(name.colorizeToComponent())
            .flags(*ItemFlag.values())
            .asGuiItem()
    }

    private fun getDyeFromColor(chatColor: ChatColor): Material {
        return when (chatColor) {
            ChatColor.RED, ChatColor.DARK_RED -> Material.RED_DYE
            ChatColor.AQUA -> Material.LIGHT_BLUE_DYE
            ChatColor.BLUE, ChatColor.DARK_BLUE -> Material.BLUE_DYE
            ChatColor.GOLD -> Material.ORANGE_DYE
            ChatColor.GRAY, ChatColor.DARK_GRAY -> Material.GRAY_DYE
            ChatColor.BLACK -> Material.BLACK_DYE
            ChatColor.DARK_AQUA -> Material.CYAN_DYE
            ChatColor.GREEN, ChatColor.DARK_GREEN -> Material.GREEN_DYE
            ChatColor.WHITE -> Material.WHITE_DYE
            ChatColor.YELLOW -> Material.YELLOW_DYE
            ChatColor.DARK_PURPLE -> Material.PURPLE_DYE
            ChatColor.LIGHT_PURPLE -> Material.MAGENTA_DYE
            else -> Material.WHITE_DYE
        }
    }
}

