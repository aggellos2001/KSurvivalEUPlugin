package me.aggellos2001.ksurvivaleuplugin.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.Default
import co.aikar.commands.annotation.Description
import com.github.benmanes.caffeine.cache.Caffeine
import com.github.benmanes.caffeine.cache.Scheduler
import dev.triumphteam.gui.builder.item.ItemBuilder
import dev.triumphteam.gui.components.InteractionModifier
import dev.triumphteam.gui.guis.Gui
import dev.triumphteam.gui.guis.PaginatedGui
import me.aggellos2001.ksurvivaleuplugin.hooks.EssentialsHook.getEssentialsUser
import me.aggellos2001.ksurvivaleuplugin.persistentdata.ShopPrices.getShopPrice
import me.aggellos2001.ksurvivaleuplugin.utils.*
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import kotlin.time.Duration
import kotlin.time.toJavaDuration

@CommandAlias("shop")
@Description("A shop that you can buy stuff")
object Shop : BaseCommand() {

    private val shopUICache =
        Caffeine.newBuilder().scheduler(Scheduler.systemScheduler())
            .expireAfterAccess(Duration.seconds(15).toJavaDuration())
            .build<Player, HashMap<Int, PaginatedGui>>()

    @Default
    fun shopUI(player: Player, @Default("0") filter: Int) {
        val shopUICacheValue = shopUICache.getIfPresent(player)
        var shopUI = shopUICacheValue?.get(filter)
        if (shopUICacheValue != null && shopUI != null) {
            shopUI.open(player)
            return
        }
        shopUI = PaginatedGui(
            6,
            45,
            legacyTextOf("<g:#ff0000:#001fff>SurvivalEU Shop").content(),
            InteractionModifier.VALUES
        ).apply {
            setDefaultClickAction {
                it.isCancelled = true
            }
            //next btn
            setItem(6, 6, ItemBuilder.from(Material.LIME_DYE)
                .name("&a&lNext".colorizeToComponent())
                .asGuiItem {
                    this.next()
                })
            //previous btn
            setItem(6, 4, ItemBuilder.from(Material.GRAY_DYE)
                .name("&c&lPrevious".colorizeToComponent())
                .asGuiItem {
                    this.previous()
                })
            //exit btn
            setItem(
                6, 9, ItemBuilder.from(Material.BARRIER)
                    .name("&4&lExit".colorizeToComponent()).glow(true)
                    .asGuiItem {
                        this.close(player)
                    }
            )
            //info btn
            setItem(
                6, 1, ItemBuilder.from(Material.PAPER)
                    .name("&eHow to get in-game money ($)?\n".colorizeToComponent()).glow(true)
                    .lore(
                        "&b1. Breaking blocks (All buyable blocks)&c*".colorizeToComponent(),
                        "&b2. Killing mobs&c*".colorizeToComponent(),
                        "&b3. Actively playing (Not being AFK)&c*".colorizeToComponent(),
                        "&b4. Selling items on /ah".colorizeToComponent(),
                        "&b5. Voting for the server /vote".colorizeToComponent(),
                        "&c&o(*) Money drops are random!".colorizeToComponent()
                    )
                    .asGuiItem()
            )
            //home btn 0 filter
            setItem(6, 5, ItemBuilder.from(Material.COMPASS)
                .name("&eHome".colorizeToComponent()).glow(true)
                .asGuiItem { shopUI(player, 0) }
            )

            //food(1) filter button
            setItem(6, 2, ItemBuilder.from(Material.COOKED_BEEF)
                .name("&eFood".colorizeToComponent()).glow(true)
                .asGuiItem { shopUI(player, 1) }
            )
            //tools & combat(2) filter
            setItem(6, 3, ItemBuilder.from(Material.DIAMOND_SWORD)
                .name("&eTools & Combat".colorizeToComponent())
                .flags(*ItemFlag.values())
                .asGuiItem { shopUI(player, 2) }
            )
            //transportation(3) filter
            setItem(6, 7, ItemBuilder.from(Material.MINECART)
                .name("Transportation".colorizeToComponent())
                .asGuiItem { shopUI(player, 3) }
            )
            //records(4) filter
            setItem(6, 8, ItemBuilder.from(Material.MUSIC_DISC_PIGSTEP)
                .name("&eMusic Disc".colorizeToComponent())
                .flags(*ItemFlag.values())
                .asGuiItem { shopUI(player, 4) }
            )

            val materialsToAdd: MutableList<Pair<Material, Double>> = when (filter) {
                1 -> {
                    val result = mutableListOf<Pair<Material, Double>>()
                    for (material in Material.values()) {
                        val price = material.getShopPrice()
                        if (material.isEdible && price > 0)
                            result.add(material to price)
                    }
                    result
                }
                2 -> {
                    val result = mutableListOf<Pair<Material, Double>>()
                    for (material in Material.values()) {
                        val price = material.getShopPrice()
                        if (material.isTool() && price > 0)
                            result.add(material to price)
                    }
                    result
                }
                3 -> {
                    val result = mutableListOf<Pair<Material, Double>>()
                    for (material in Material.values()) {
                        val price = material.getShopPrice()
                        if (material.isTransportation() && price > 0)
                            result.add(material to price)
                    }
                    result
                }
                4 -> {
                    val result = mutableListOf<Pair<Material, Double>>()
                    for (material in Material.values()) {
                        val price = material.getShopPrice()
                        if (material.isRecord && price > 0)
                            result.add(material to price)
                    }
                    result
                }
                else -> {
                    val result = mutableListOf<Pair<Material, Double>>()
                    for (material in Material.values()) {
                        val price = material.getShopPrice()
                        if (price > 0)
                            result.add(material to price)
                    }
                    result
                }
            }

            for (shopItems in materialsToAdd) {
                addItem(ItemBuilder.from(shopItems.first)
                    .lore("&aBuy price: &f${shopItems.second}&a$".colorizeToComponent())
                    .flags(*ItemFlag.values())
                    .asGuiItem {
                        buyUI(player, this, shopItems.first)
                    })
            }
            filler.fillBottom(ItemBuilder.from(Material.GRAY_STAINED_GLASS_PANE).name(textOf(" ")).asGuiItem())
            open(player)
        }
        if (shopUICache.getIfPresent(player) == null) {
            shopUICache.put(player, hashMapOf(filter to shopUI))
        } else {
            val hashMap = shopUICache.getIfPresent(player)!!
            hashMap[filter] = shopUI
            shopUICache.put(player, hashMap)
        }
    }

    private fun buyUI(player: Player, shopUI: PaginatedGui, material: Material) {
        Gui(
            1,
            legacyTextOf("<g:#00bdff:#0009ff>Buy ${material.toNiceString()}").content(),
            InteractionModifier.VALUES
        ).run {
            setDefaultClickAction { it.isCancelled = true }

            setItem(0, ItemBuilder.from(Material.OAK_DOOR)
                .name(textOf("Back", NamedTextColor.YELLOW, TextDecoration.BOLD))
                .asGuiItem {
                    shopUI.open(player)
                }
            )

            setItem(
                4, ItemBuilder.from(material)
                    .name(legacyTextOf("&e&l${material.toNiceString()}"))
                    .lore(legacyTextOf("&aBuy price: &f${material.getShopPrice()}&a$"))
                    .asGuiItem()
            )


            //buy 1x
            setItem(2, ItemBuilder.from(Material.LIME_STAINED_GLASS)
                .name(legacyTextOf("&aBuy 1x"))
                .lore(legacyTextOf("&f${material.getShopPrice()}&a$"))
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
                    .name(legacyTextOf("&aBuy ${(material.maxStackSize * 0.25).toInt()}x"))
                    .lore(legacyTextOf("&f${material.getShopPrice() * (material.maxStackSize * 0.25)}&a$"))
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
                    .name(legacyTextOf("&aBuy ${(material.maxStackSize * 0.5).toInt()}x"))
                    .lore(legacyTextOf("&f${material.getShopPrice() * (material.maxStackSize * 0.5)}&a$"))
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
                        .name(legacyTextOf("&aBuy ${material.maxStackSize}x"))
                        .lore(legacyTextOf("&f${material.getShopPrice() * material.maxStackSize}&a$"))
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
            if (this.name.lowercase().contains(name)) return true
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
            if (this.name.lowercase().contains(toolName)) return true
        }
        return false
    }
}
