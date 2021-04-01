package me.aggellos2001.ksurvivaleuplugin.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.Conditions
import co.aikar.commands.annotation.Default
import co.aikar.commands.annotation.Subcommand
import me.aggellos2001.ksurvivaleuplugin.hooks.LuckPermsHookUtil.getLuckPermsGroup
import me.aggellos2001.ksurvivaleuplugin.hooks.LuckPermsHookUtil.hasDonated
import me.aggellos2001.ksurvivaleuplugin.utils.sendColorizedMessage
import me.aggellos2001.ksurvivaleuplugin.utils.wrapToWeakReference
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import java.lang.ref.WeakReference

@CommandAlias("donation|donations|rank|ranks")
object Donation : BaseCommand() {

    private val playersWithDonationEffectsOn = mutableListOf<WeakReference<Player>>()

    private val EMERALD_EFFECTS = listOf(
        PotionEffect(PotionEffectType.SPEED, Int.MAX_VALUE, 2),
        PotionEffect(PotionEffectType.FAST_DIGGING, Int.MAX_VALUE, 1),
        PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Int.MAX_VALUE, 1),
        PotionEffect(PotionEffectType.LUCK, Int.MAX_VALUE, 1),
        PotionEffect(PotionEffectType.NIGHT_VISION, Int.MAX_VALUE, 0),
        PotionEffect(PotionEffectType.INCREASE_DAMAGE, Int.MAX_VALUE, 1),
        PotionEffect(PotionEffectType.WATER_BREATHING, Int.MAX_VALUE, 0),
        PotionEffect(PotionEffectType.REGENERATION, Int.MAX_VALUE, 0),
        PotionEffect(PotionEffectType.FIRE_RESISTANCE, Int.MAX_VALUE, 0)
    )
    private val DIAMOND_EFFECTS = listOf(
        PotionEffect(PotionEffectType.SPEED, Int.MAX_VALUE, 1),
        PotionEffect(PotionEffectType.FAST_DIGGING, Int.MAX_VALUE, 1),
        PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Int.MAX_VALUE, 1),
        PotionEffect(PotionEffectType.LUCK, Int.MAX_VALUE, 1),
        PotionEffect(PotionEffectType.NIGHT_VISION, Int.MAX_VALUE, 0),
        PotionEffect(PotionEffectType.INCREASE_DAMAGE, Int.MAX_VALUE, 1),
        PotionEffect(PotionEffectType.WATER_BREATHING, Int.MAX_VALUE, 0)
    )
    private val IRON_EFFECTS = listOf(
        PotionEffect(PotionEffectType.SPEED, Int.MAX_VALUE, 1),
        PotionEffect(PotionEffectType.FAST_DIGGING, Int.MAX_VALUE, 0),
        PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Int.MAX_VALUE, 0),
        PotionEffect(PotionEffectType.LUCK, Int.MAX_VALUE, 0)
    )
    private val COAL_EFFECTS = listOf(
        PotionEffect(PotionEffectType.SPEED, Int.MAX_VALUE, 0),
        PotionEffect(PotionEffectType.FAST_DIGGING, Int.MAX_VALUE, 0),
        PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Int.MAX_VALUE, 0)
    )

    @Default
    fun donationInfoCommand(player: Player) {
        player.sendColorizedMessage(
            """
            &3&l[      Donations      ] 
            &e*What ranks exist? How can I get them?
            *Find all the info here: "&b&lhttps://survivaleu.com/ranks.html
        """.trimIndent()
        )
    }

    @Subcommand("potion|potions")
    @Conditions("cooldown:time=5,name=Donation")
    fun givePotions(player: Player) {
        if (!player.hasDonated()) return

        //TODO check if pvp is on here
        if (playersWithDonationEffectsOn.any { it.get() == player }) {
            for (activePotionEffect in player.activePotionEffects) {
                player.removePotionEffect(activePotionEffect.type)
            }
            playersWithDonationEffectsOn.removeIf { it.get() == player }
            player.sendColorizedMessage("&cDonation effects disabled! Thank you for your support!")
            return
        }
        when {
            player.getLuckPermsGroup() == "coal" -> player.addPotionEffects(COAL_EFFECTS)
            player.getLuckPermsGroup() == "iron" -> player.addPotionEffects(IRON_EFFECTS)
            player.getLuckPermsGroup() == "diamond" -> player.addPotionEffects(DIAMOND_EFFECTS)
            else -> player.addPotionEffects(EMERALD_EFFECTS)
        }
        playersWithDonationEffectsOn.add(player.wrapToWeakReference())
        player.sendColorizedMessage("&aDonation effects enabled! Thank you for your support!")
    }
}