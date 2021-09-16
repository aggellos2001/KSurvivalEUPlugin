package me.aggellos2001.ksurvivaleuplugin.listeners

import me.aggellos2001.ksurvivaleuplugin.hooks.LuckPermsHookUtil.getRankPrefix
import me.aggellos2001.ksurvivaleuplugin.utils.colorize
import me.aggellos2001.ksurvivaleuplugin.utils.inTicks
import me.aggellos2001.ksurvivaleuplugin.utils.sendColorizedMessage
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.PlayerDeathEvent
import kotlin.random.Random
import kotlin.time.Duration

object DeathMessage : Listener {

    @EventHandler(ignoreCancelled = true)
    fun onPlayerDeathEvent(e: PlayerDeathEvent) {
        val lastDamageCause = e.entity.lastDamageCause
        val deadPlayer = e.entity
        val deadPlayerRankFormatted = "${deadPlayer.getRankPrefix()} &b${deadPlayer.name}&e"
        val killer = e.entity.killer
        val killerRankFormatted = "${killer?.getRankPrefix()} &b${killer?.name}&e "
        val randInt = Random.nextInt(2)
        val defaultMessage = "&6[&c&lDeath&r&6] $deadPlayerRankFormatted died!"

        if (lastDamageCause == null) {
            Bukkit.getServer().sendColorizedMessage(defaultMessage, false)
            return
        }

        val deathMessage: String = when (lastDamageCause.cause) {
            EntityDamageEvent.DamageCause.CONTACT -> {
                if (killer != null)
                    "walked into a cactus whilst trying to escape $killerRankFormatted!"
                else
                    "was pricked to death!"
            }
            EntityDamageEvent.DamageCause.ENTITY_ATTACK, EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK -> {
                if (killer != null) {
                    if (randInt == 0)
                        "died because of $killerRankFormatted!"
                    else
                        "got killed by $killerRankFormatted!"
                } else
                    "died!"
            }
            EntityDamageEvent.DamageCause.PROJECTILE -> {
                if (killer != null) {
                    if (randInt == 0)
                        "got shot by $killerRankFormatted!"
                    else
                        "got sniped by $killerRankFormatted!"
                } else
                    "died!"
            }
            EntityDamageEvent.DamageCause.SUFFOCATION -> {
                if (randInt == 0)
                    "suffocated!"
                else
                    "couldn't breathe!"
            }
            EntityDamageEvent.DamageCause.FALL -> {
                if (randInt == 0)
                    "fell down really hard!"
                else
                    "slipped from a high altitude!"
            }
            EntityDamageEvent.DamageCause.FIRE -> {
                if (randInt == 0)
                    "was burned alive!"
                else
                    "forgot he was on fire!"
            }
            EntityDamageEvent.DamageCause.FIRE_TICK -> {
                if (randInt == 0)
                    "did not put out the fire quickly enough!"
                else
                    "burned as he tried to save himself!"
            }
            EntityDamageEvent.DamageCause.MELTING, EntityDamageEvent.DamageCause.CUSTOM -> {
                "died!"
            }
            EntityDamageEvent.DamageCause.LAVA -> {
                when (Random.nextInt(3)) {
                    0 -> {
                        "was burned by some hot lava!"
                    }
                    1 -> "bathed in lava!"
                    else -> "he thought lava was red water!"
                }
            }
            EntityDamageEvent.DamageCause.DROWNING -> {
                if (randInt == 0)
                    "drowned!"
                else
                    "forgot that he needs to breath!"
            }
            EntityDamageEvent.DamageCause.BLOCK_EXPLOSION -> {
                if (randInt == 0)
                    "stood near an explosion to see what will happen!"
                else
                    "exploded into a million pieces!"
            }
            EntityDamageEvent.DamageCause.ENTITY_EXPLOSION -> {
                if (randInt == 0) {
                    "thought that explosions are nice!"
                } else {
                    "exploded by an explosion! How is that possible?"
                }
            }
            EntityDamageEvent.DamageCause.VOID -> {
                if (randInt == 0) {
                    "thought it was a good idea to jump into the void!"
                } else {
                    "died by falling into nothing!"
                }
            }
            EntityDamageEvent.DamageCause.LIGHTNING -> {
                if (randInt == 0) {
                    "died from an angry cloud!"
                } else {
                    "was so unlucky that a lightning killed him!"
                }
            }
            EntityDamageEvent.DamageCause.SUICIDE -> "committed suicide!"
            EntityDamageEvent.DamageCause.STARVATION -> {
                if (randInt == 0) {
                    "was starved to death"
                } else {
                    "forgot that there is a food bar in this game!"
                }
            }
            EntityDamageEvent.DamageCause.POISON -> {
                if (randInt == 0) {
                    "died from poison!"
                } else {
                    "was poisoned and died painfully!"
                }
            }
            EntityDamageEvent.DamageCause.MAGIC -> {
                if (killer != null) {
                    if (randInt == 0)
                        "died because of magic by $killerRankFormatted!"
                    else
                        "$killerRankFormatted used magic to kill $deadPlayerRankFormatted!"
                } else {
                    "died because of magic!"
                }
            }
            EntityDamageEvent.DamageCause.WITHER -> "withered far away!"
            EntityDamageEvent.DamageCause.FALLING_BLOCK -> {
                if (randInt == 0) {
                    "was severely hit by a falling block!"
                } else {
                    "was killed by a falling piano! Uh I mean anvil!"
                }
            }
            EntityDamageEvent.DamageCause.THORNS -> {
                "was stung by thorns and died"
            }
            EntityDamageEvent.DamageCause.DRAGON_BREATH -> "burned by the dragon's breath!"
            EntityDamageEvent.DamageCause.FLY_INTO_WALL -> "crashed into the wall too hard!"
            EntityDamageEvent.DamageCause.HOT_FLOOR -> "didn't notice that magma blocks are red hot!"
            EntityDamageEvent.DamageCause.CRAMMING -> "wanted to fit into one block with many others but failed!"
            EntityDamageEvent.DamageCause.DRYOUT -> "died!"
            EntityDamageEvent.DamageCause.FREEZE -> "froze to death!"
        }
        Bukkit.getServer()
            .sendColorizedMessage("&6[&c&lDeath&r&6] $deadPlayerRankFormatted $deathMessage", false)

        deadPlayer.sendTitle(
            "&c&lYOU DIED!".colorize(), "&eYou can do /back to get back to where you died!".colorize(),
            20, Duration.seconds(5).inTicks().toInt(), 40
        )

    }
}