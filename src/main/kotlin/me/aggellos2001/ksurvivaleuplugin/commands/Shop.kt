package me.aggellos2001.ksurvivaleuplugin.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.Conditions
import co.aikar.commands.annotation.Default
import me.aggellos2001.ksurvivaleuplugin.hooks.EssentialsHook.getEssentialsUser
import me.aggellos2001.ksurvivaleuplugin.persistentdata.ShopPrices.getShopPrice
import me.aggellos2001.ksurvivaleuplugin.utils.colorize
import me.aggellos2001.ksurvivaleuplugin.utils.sendColorizedMessage
import me.aggellos2001.ksurvivaleuplugin.utils.toNiceString
import me.mattstudios.mfgui.gui.components.util.ItemBuilder
import me.mattstudios.mfgui.gui.guis.Gui
import me.mattstudios.mfgui.gui.guis.PaginatedGui
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

@CommandAlias("shop")
@Conditions("cooldown:time=2,name=Shop")
object Shop : BaseCommand() {

    @Default
    fun shopUI(player: Player, @Default("0") filter: Int) {
        PaginatedGui(6, 45, "<g:#ff0000:#001fff>SurvivalEU Shop".colorize()).run {
            setDefaultClickAction {
                it.isCancelled = true
            }
            //next btn
            setItem(6, 6, ItemBuilder.from(Material.LIME_DYE)
                .setName("&a&lNext".colorize())
                .asGuiItem {
                    this.next()
                })
            //previous btn
            setItem(6, 4, ItemBuilder.from(Material.GRAY_DYE)
                .setName("&c&lPrevious".colorize())
                .asGuiItem {
                    this.previous();
                })
            //exit btn
            setItem(
                6, 9, ItemBuilder.from(Material.BARRIER)
                    .setName("&4&lExit".colorize()).glow(true)
                    .asGuiItem {
                        this.close(player)
                    }
            )
            //info btn
            setItem(
                6, 1, ItemBuilder.from(Material.PAPER)
                    .setName("&eHow to get in-game money ($)?\n".colorize()).glow(true)
                    .setLore(
                        "&b1. Breaking blocks (All buyable blocks)&c*".colorize(),
                        "&b2. Killing mobs&c*".colorize(),
                        "&b3. Actively playing (Not being AFK)&c*".colorize(),
                        "&b4. Selling items on /ah".colorize(),
                        "&b5. Voting for the server /vote".colorize(),
                        "&c&o(*) Money drops are random!".colorize()
                    )
                    .asGuiItem()
            )
            //home btn 0 filter
            setItem(6, 5, ItemBuilder.from(Material.COMPASS)
                .setName("&eHome".colorize()).glow(true)
                .asGuiItem { shopUI(player, 0) }
            )

            //food(1) filter button
            setItem(6, 2, ItemBuilder.from(Material.COOKED_BEEF)
                .setName("&eFood".colorize()).glow(true)
                .asGuiItem { shopUI(player, 1) }
            )
            //tools & combat(2) filter
            setItem(6, 3, ItemBuilder.from(Material.DIAMOND_SWORD)
                .setName("&eTools & Combat".colorize())
                .addItemFlags(*ItemFlag.values())
                .asGuiItem { shopUI(player, 2) }
            )
            //transportation(3) filter
            setItem(6, 7, ItemBuilder.from(Material.MINECART)
                .setName("Transportation".colorize())
                .asGuiItem { shopUI(player, 3) }
            )
            //records(4) filter
            setItem(6, 8, ItemBuilder.from(Material.MUSIC_DISC_PIGSTEP)
                .setName("&eMusic Disc".colorize())
                .addItemFlags(*ItemFlag.values())
                .asGuiItem { shopUI(player, 4) }
            )

            val materialsToAdd: List<Material> = when (filter) {
                1 -> {
                    val result = mutableListOf<Material>()
                    for (material in Material.values()) {
                        if (material.isEdible && material.getShopPrice() > 0)
                            result.add(material)
                    }
                    result.toList()
                }
                2 -> {
                    val result = mutableListOf<Material>()
                    for (material in Material.values()) {
                        if (material.isTool() && material.getShopPrice() > 0)
                            result.add(material)
                    }
                    result.toList()
                }
                3 -> {
                    val result = mutableListOf<Material>()
                    for (material in Material.values()) {
                        if (material.isTransportation() && material.getShopPrice() > 0)
                            result.add(material)
                    }
                    result.toList()
                }
                4 -> {
                    val result = mutableListOf<Material>()
                    for (material in Material.values()) {
                        if (material.isRecord && material.getShopPrice() > 0)
                            result.add(material)
                    }
                    result.toList()
                }
                else -> {
                    val result = mutableListOf<Material>()
                    for (material in Material.values()) {
                        if (material.getShopPrice() > 0)
                            result.add(material)
                    }
                    result.toList()
                }
            }

            for (material in materialsToAdd) {
                addItem(ItemBuilder.from(material)
                    .setLore("&aBuy price: &f${material.getShopPrice()}&a$".colorize())
                    .addItemFlags(*ItemFlag.values())
                    .asGuiItem {
                        buyUI(player, this, material)
                    })
            }
            filler.fillBottom(ItemBuilder.from(Material.GRAY_STAINED_GLASS_PANE).setName(" ").asGuiItem())
            open(player)
        }
    }

    private fun buyUI(player: Player, shopUI: PaginatedGui, material: Material) {

        Gui(1, "<g:#00bdff:#0009ff>Buy ${material.toNiceString()}".colorize()).run {
            setDefaultClickAction { it.isCancelled = true }

            setItem(0, ItemBuilder.from(Material.OAK_DOOR)
                .setName("&eBack".colorize())
                .asGuiItem {
                    shopUI.open(player)
                }
            )

            setItem(
                4, ItemBuilder.from(material)
                    .setName("&e&l${material.toNiceString()}".colorize())
                    .setLore("&aBuy price: &f${material.getShopPrice()}&a$".colorize())
                    .asGuiItem()
            )


            //buy 1x
            setItem(2, ItemBuilder.from(Material.LIME_STAINED_GLASS)
                .setName("&aBuy 1x".colorize())
                .setLore("&f${material.getShopPrice()}&a$".colorize())
                .asGuiItem {
                    val amountToPay = material.getShopPrice().toBigDecimal()
                    if (!player.getEssentialsUser().canAfford(amountToPay)) {
                        player.sendColorizedMessage("&cYou don't have enough money!")
                    } else {
                        val item = ItemStack(material)
                        player.getEssentialsUser().takeMoney(amountToPay)
                        val items = player.inventory.addItem(item)
                        player.sendColorizedMessage("&aSuccessfully bought &b1x &e${material.toNiceString()}&a!")
                        if (items.isNotEmpty()) {
                            items.forEach { (_, itemStack) ->
                                player.world.dropItemNaturally(player.location, itemStack)
                            }
                            player.sendColorizedMessage("&eThere was not enough space! Some items were dropped on the ground!")
                        }
                    }
                }
            )
            if (material.maxStackSize > 1) {
                //buy 1/4 of stack
                setItem(3, ItemBuilder.from(Material.LIME_STAINED_GLASS)
                    .setName("&aBuy ${(material.maxStackSize * 0.25).toInt()}x".colorize())
                    .setLore("&f${material.getShopPrice() * (material.maxStackSize * 0.25)}&a$".colorize())
                    .asGuiItem {
                        val itemAmount = (material.maxStackSize * 0.25).toInt()
                        val amountToPay = (material.getShopPrice() * itemAmount).toBigDecimal()
                        if (!player.getEssentialsUser().canAfford(amountToPay)) {
                            player.sendColorizedMessage("&cYou don't have enough money!")
                        } else {
                            val item = ItemStack(material, itemAmount)
                            player.getEssentialsUser().takeMoney(amountToPay)
                            val items = player.inventory.addItem(item)
                            player.sendColorizedMessage("&aSuccessfully bought &b${itemAmount}x &e${material.toNiceString()}&a!")
                            if (items.isNotEmpty()) {
                                items.forEach { (_, itemStack) ->
                                    player.world.dropItemNaturally(player.location, itemStack)
                                }
                                player.sendColorizedMessage("&eThere was not enough space! Some items were dropped on the ground!")
                            }
                        }
                    }
                )
                //buy 2/4 of stack
                setItem(5, ItemBuilder.from(Material.LIME_STAINED_GLASS)
                    .setName("&aBuy ${(material.maxStackSize * 0.5).toInt()}x".colorize())
                    .setLore("&f${material.getShopPrice() * (material.maxStackSize * 0.5)}&a$".colorize())
                    .asGuiItem {
                        val itemAmount = (material.maxStackSize * 0.5).toInt()
                        val amountToPay = (material.getShopPrice() * itemAmount).toBigDecimal()
                        if (!player.getEssentialsUser().canAfford(amountToPay)) {
                            player.sendColorizedMessage("&cYou don't have enough money!")
                        } else {
                            val item = ItemStack(material, itemAmount)
                            player.getEssentialsUser().takeMoney(amountToPay)
                            val items = player.inventory.addItem(item)
                            player.sendColorizedMessage("&aSuccessfully bought &b${itemAmount}x &e${material.toNiceString()}&a!")
                            if (items.isNotEmpty()) {
                                items.forEach { (_, itemStack) ->
                                    player.world.dropItemNaturally(player.location, itemStack)
                                }
                                player.sendColorizedMessage("&eThere was not enough space! Some items were dropped on the ground!")
                            }
                        }
                    }
                )
                //buy stack
                setItem(
                    6, ItemBuilder.from(Material.LIME_STAINED_GLASS)
                        .setName("&aBuy ${material.maxStackSize}x".colorize())
                        .setLore("&f${material.getShopPrice() * material.maxStackSize}&a$".colorize())
                        .asGuiItem {
                            val itemAmount = material.maxStackSize
                            val amountToPay = (material.getShopPrice() * itemAmount).toBigDecimal()
                            if (!player.getEssentialsUser().canAfford(amountToPay)) {
                                player.sendColorizedMessage("&cYou don't have enough money!")
                            } else {
                                val item = ItemStack(material, itemAmount)
                                player.getEssentialsUser().takeMoney(amountToPay)
                                val items = player.inventory.addItem(item)
                                player.sendColorizedMessage("&aSuccessfully bought &b${itemAmount}x &e${material.toNiceString()}&a!")
                                if (items.isNotEmpty()) {
                                    items.forEach { (_, itemStack) ->
                                        player.world.dropItemNaturally(player.location, itemStack)
                                    }
                                    player.sendColorizedMessage("&eThere was not enough space! Some items were dropped on the ground!")
                                }
                            }
                        }
                )
            }
            open(player)
        }
    }

    private fun Material.isTransportation(): Boolean {
        val names = arrayOf(
            "rail", "boat", "minecart", "elytra", "a_stick", "saddle", "rocket", "pearl"
        )
        for (name in names) {
            if (this.name.toLowerCase().contains(name)) return true
        }
        return false
    }

    private fun Material.isTool(): Boolean {
        val toolNames = arrayOf(
            "pickaxe", "hoe", "sword", "shovel", "shears",
            "clock", "compass", "fishing", "lead", "tag",
            "shell", "bow", "arrow", "helmet", "chestplate", "leggings", "boots"
        )
        for (toolName in toolNames) {
            if (this.name.toLowerCase().contains(toolName)) return true
        }
        return false
    }
}
