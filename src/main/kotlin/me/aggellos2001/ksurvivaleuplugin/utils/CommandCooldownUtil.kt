package me.aggellos2001.ksurvivaleuplugin.utils

import co.aikar.commands.BaseCommand
import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.Caffeine
import com.github.benmanes.caffeine.cache.Scheduler
import org.bukkit.entity.Player
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.time.Duration
import kotlin.time.toDuration

/**
 * A [Cache] that keeps players cooldowns. If the cooldown is not accessed within 3 hours it will be removed!
 *
 * So the better if the cooldown is < 3 hours.
 *
 * Example usage code:
 * ```
 *  if (player.getCoolDown(commandName).first) {
 *       throw  ConditionFailedException(player.getCoolDownErrorMessage(commandName))
 *  }
 *  player.setCoolDown(commandName, cooldown.seconds)
 *  ```
 *  Using ACF we can register a @Conditions("cooldown:time=seconds,name=key")
 *  where seconds is the cooldown we desire and key can be anything we want, optimally the command name itself.
 *
 *  Here's the code to register the condition:
 *  ```
 *  addCondition("cooldown") { context ->
 *  if (!context.issuer.isPlayer) return@addCondition
 *  val player = context.issuer.player
 *  val commandName = if (context.hasConfig("name")) context.getConfigValue("name", "null")
 *      else return@addCondition
 *  val cooldown = if (context.hasConfig("time")) context.getConfigValue("time", 0)
 *      else return@addCondition
 *  if (player.getCoolDown(commandName).first) {
 *      throw  ConditionFailedException(player.getCoolDownErrorMessage(commandName))
 *  }
 *  player.setCoolDown(commandName, cooldown.seconds)
 *  }
 *
 */
private val coolDownCache: Cache<String, HashMap<String, Duration>> =
    Caffeine.newBuilder().scheduler(Scheduler.systemScheduler()).expireAfterAccess(3, TimeUnit.HOURS).build()

/**
 * Method that adds a cooldown for a specific command to the specified player.
 *
 * Accepts [Duration] values which makes it experimental but very flexible with time computations.
 *
 * @param key The command that extends [BaseCommand] (ACF command)
 * @param cooldown The cooldown duration. Accepting [Duration] values.
 */
fun Player.setCoolDown(key: String, cooldown: Duration) {
    if (this.hasPermission("seu.cooldown")) return
    val newCooldown = System.currentTimeMillis().toDuration(TimeUnit.MILLISECONDS) + cooldown
    val playerCooldowns = coolDownCache.getIfPresent(this.name)
    if (playerCooldowns != null)
        playerCooldowns[key] = newCooldown
    else
        coolDownCache.put(this.name, hashMapOf(key to newCooldown))
}

/**
 * Method that gets the cooldown that a player has in the specified command which extends [BaseCommand] (ACF command)
 *
 * @return A pair that contains a [Boolean] and a [Duration].
 * The boolean will be true if the player has a cooldown and the Duration will have the time remaining or [Duration.ZERO]
 * if no cooldown was found.
 */
fun Player.getCoolDown(key: String): Pair<Boolean, Duration> {

    val noCooldown = false to Duration.ZERO

    if (this.hasPermission("seu.cooldown")) return noCooldown
    val playerCooldowns = coolDownCache.getIfPresent(this.name) ?: return noCooldown
    val commandCooldown = playerCooldowns.getOrDefault(key, null) ?: return noCooldown

    val currentTime = System.currentTimeMillis().toDuration(TimeUnit.MILLISECONDS)
    return if (currentTime < commandCooldown)
        true to commandCooldown - currentTime
    else noCooldown

//    if (coolDownCache.containsKey(this)) {
//        if (coolDownCache.getValue(this).containsKey(key)) {
//            val commandCoolDown = coolDownCache.getValue(this).getValue(key)
//            val currentTime = System.currentTimeMillis().toDuration(TimeUnit.MILLISECONDS)
//            return if (currentTime < commandCoolDown)
//                true to commandCoolDown - currentTime
//            else
//                false to Duration.ZERO
//        }
//    }
//    return false to Duration.ZERO
}

/**
 *  Helper method that returns an error message that is suitable to be send to a player
 *  if a cooldown was found.
 *
 *  @param key [BaseCommand] to check against
 */
fun Player.getCoolDownErrorMessage(key: String): String {
    val coolDown = this.getCoolDown(key).second
    return when {
        coolDown.inSeconds <= 60 -> "&cPlease wait ${coolDown.toInt(TimeUnit.SECONDS)} seconds before doing that again!"
        coolDown.inMinutes <= 60 -> "&cPlease wait ${coolDown.toInt(TimeUnit.MINUTES)} minutes before doing that again!"
        coolDown.inHours <= 24 -> "&cPlease wait ${coolDown.toInt(TimeUnit.HOURS)} hours before doing that again!"
        coolDown.inDays <= 60 -> "&cPlease wait ${coolDown.toInt(TimeUnit.DAYS)} days before doing that again!"
        else -> "&cPlease wait ${coolDown.toInt(TimeUnit.SECONDS)} seconds before doing that again!"
    }
}