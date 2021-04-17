package me.aggellos2001.ksurvivaleuplugin.listeners

import me.aggellos2001.ksurvivaleuplugin.persistentdata.pluginConfig
import me.aggellos2001.ksurvivaleuplugin.plugin.pluginInstance
import me.aggellos2001.ksurvivaleuplugin.utils.PCDetection
import me.aggellos2001.ksurvivaleuplugin.utils.colorizeToComponent
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerPreLoginEvent
import java.io.IOException


object BlockVPN : Listener {

    private val detectionCache = HashMap<String, PCDetection>()

    fun getFromCache(hostAddress: String): PCDetection? = detectionCache[hostAddress]
    fun clearHostName(hostAddress: String): PCDetection? = detectionCache.remove(hostAddress)

    private val kickMessageProxy: String =
        """
            &c&l---------------------------------------------
            
            &c&lDetected a high risk connection or VPN!!! 
            
            &eIf you believe this is a mistake contact the owner (aggellos#0001) in our Discord: &lhttp://discord.survivaleu.com/
            
            &aYour IP Address: &b&l%s
            
            &c&l---------------------------------------------
            """.trimIndent()

    private val blackListedMessage: String =
        """
            &c&l---------------------------------------------
            
            &c&lYou are blacklisted!!! 
            
            &eIf you believe this is a mistake contact the owner (aggellos#0001) in our Discord: &lhttp://discord.survivaleu.com/
            
            &aYour IP Address: &b&l%s
            
            &c&l---------------------------------------------
            """.trimIndent()


    @EventHandler(ignoreCancelled = true)
    fun onLoginCheckForVPN(e: AsyncPlayerPreLoginEvent) {

        val hostAddress = e.address.hostAddress
        val detection = detectionCache[hostAddress] ?: getProxyCheckFromAPI(hostAddress)

        if (detection.status == "ok" || detection.status == "warning") {
            if (detection.type?.startsWith("blacklisted", true) == true) {
                e.disallow(
                    AsyncPlayerPreLoginEvent.Result.KICK_OTHER,
                    String.format(blackListedMessage, hostAddress).colorizeToComponent()
                )
            }
            if (detection.risk > 67 || (detection.proxy == "yes" && detection.type == "VPN"))
                e.disallow(
                    AsyncPlayerPreLoginEvent.Result.KICK_OTHER,
                    String.format(kickMessageProxy, hostAddress).colorizeToComponent()
                )
        } else {
            pluginInstance.logger.warning("VPN/Proxy finder API call failed for player ${e.name}! Response = $detection")
        }

        detectionCache[hostAddress] = detection
    }

    /**
     * Blocking method that queries the API and returns the result (Call this async!)
     */
    private fun getProxyCheckFromAPI(hostAddress: String): PCDetection {

        val token = if (pluginConfig.vpnProxyCheckerToken == null || pluginConfig.vpnProxyCheckerToken == "null")
            throw IllegalArgumentException("Proxy detector token key is not set in the config!") else pluginConfig.vpnProxyCheckerToken!!

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