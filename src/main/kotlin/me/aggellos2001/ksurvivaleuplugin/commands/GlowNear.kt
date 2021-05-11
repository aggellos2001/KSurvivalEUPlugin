package me.aggellos2001.ksurvivaleuplugin.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.ConditionFailedException
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.Conditions
import co.aikar.commands.annotation.Default
import co.aikar.commands.annotation.Description
import me.aggellos2001.ksurvivaleuplugin.hooks.EssentialsHook.getEssentialsUser
import me.aggellos2001.ksurvivaleuplugin.hooks.LuckPermsHookUtil.getRankPrefix
import me.aggellos2001.ksurvivaleuplugin.utils.inTicks
import me.aggellos2001.ksurvivaleuplugin.utils.sendColorizedMessage
import me.aggellos2001.ksurvivaleuplugin.utils.setCoolDown
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import kotlin.time.Duration

@CommandAlias("glownear|gnear")
@Conditions("cooldown:time=180,name=GlowNear") //180 cooldown = 3mins
@Description("Gives the Glow Effect to nearby entities over a 45 blocks range")
object GlowNear : BaseCommand() {

    @Default
    private fun onGlowNearCommand(player: Player) {

        val essPlayer = player.getEssentialsUser()
        val price = 30.toBigDecimal()
        if (!essPlayer.canAfford(price)) {
            player.setCoolDown("GlowNear", Duration.seconds(10))
            throw ConditionFailedException("You must have 30$ to use this command!")
        }
        for (nearbyEntity in player.location.getNearbyLivingEntities(45.0)) {
            if (nearbyEntity is Player) {
                nearbyEntity.sendColorizedMessage("&ePlayer ${player.getRankPrefix()} ${player.name} &eused /glownear!")
                continue
            }
            nearbyEntity.addPotionEffect(
                PotionEffect(
                    PotionEffectType.GLOWING,
                    Duration.seconds(20).inTicks().toInt(),
                    0,
                    true,
                    true
                )
            )
        }
        essPlayer.takeMoney(price)
        player.sendColorizedMessage("&aYou can now see nearby entities for 20 seconds!")
    }

}