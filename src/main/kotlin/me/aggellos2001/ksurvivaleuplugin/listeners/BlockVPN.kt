package me.aggellos2001.ksurvivaleuplugin.listeners

import com.github.benmanes.caffeine.cache.Caffeine
import com.github.benmanes.caffeine.cache.Scheduler
import me.aggellos2001.ksurvivaleuplugin.persistentdata.pluginConfig
import me.aggellos2001.ksurvivaleuplugin.plugin.pluginInstance
import me.aggellos2001.ksurvivaleuplugin.utils.PCDetection
import me.aggellos2001.ksurvivaleuplugin.utils.colorizeToComponent
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerPreLoginEvent
import java.io.IOException
import kotlin.time.hours
import kotlin.time.toJavaDuration


object BlockVPN : Listener {

    private val detectionCache = Caffeine.newBuilder()
        .scheduler(Scheduler.systemScheduler())
        .expireAfterAccess(1.hours.toJavaDuration()).build<String, PCDetection>()

    fun getFromCache(hostAddress: String): PCDetection? = detectionCache.getIfPresent(hostAddress)
    fun clearHostName(hostAddress: String) = detectionCache.invalidate(hostAddress)

    private val kickMessage: String =
        """
            &c&l---------------------------------------------
            
            &c&lDetected a high risk connection or VPN!!! 
            
            &eIf you believe this is a mistake contact the owner (aggellos#0001) in our Discord: &lhttp://discord.survivaleu.com/
            
            &aYour IP Address: &b&l%s
            
            &c&l---------------------------------------------
            """.trimIndent()


    @EventHandler(ignoreCancelled = true)
    fun onLoginCheckForVPN(e: AsyncPlayerPreLoginEvent) {

        val hostAddress = e.address.hostAddress
        val detection = detectionCache.getIfPresent(hostAddress) ?: getProxyCheckFromAPI(hostAddress)

        if (detection.status == "ok" || detection.status == "warning") {
            if (detection.risk > 67 || (detection.proxy == "yes" && detection.type == "vpn"))
                e.disallow(
                    AsyncPlayerPreLoginEvent.Result.KICK_OTHER,
                    String.format(kickMessage, hostAddress).colorizeToComponent()
                )
        } else
            pluginInstance.logger.warning("VPN/Proxy finder API call failed for player ${e.name}!")

        detectionCache.put(hostAddress, detection)
    }

    /**
     * Blocking method that queries the API and returns the result (Call this async!)
     */
    private fun getProxyCheckFromAPI(hostAddress: String): PCDetection {

        val token = if (pluginConfig.vpnProxyCheckerToken == null || pluginConfig.vpnProxyCheckerToken == "null")
            throw IllegalArgumentException("Proxy detector token key is not set in the config!") else pluginConfig.voteApiToken!!

        val detection = PCDetection(token)
        detection.useSSL()
        detection.setUseVpn(true)
        detection.setUseAsn(true)
        detection.setUseNode(true)
        detection.setUseTime(true)
        detection.setUseInf(true)
        detection.setUseRisk(1)
        detection.setUsePort(true)
        detection.setUseSeen(true)
        detection.setTag("SEUPlugin")
        try {
            detection.parseResults(hostAddress)
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
        return detection
    }
}