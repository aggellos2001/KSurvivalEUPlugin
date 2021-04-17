package me.aggellos2001.ksurvivaleuplugin.plugin

import co.aikar.commands.ConditionFailedException
import me.aggellos2001.ksurvivaleuplugin.commands.*
import me.aggellos2001.ksurvivaleuplugin.commands.lockchest.LockChest
import me.aggellos2001.ksurvivaleuplugin.commands.lockchest.LockChestListeners
import me.aggellos2001.ksurvivaleuplugin.listeners.*
import me.aggellos2001.ksurvivaleuplugin.utils.getCoolDown
import me.aggellos2001.ksurvivaleuplugin.utils.getCoolDownErrorMessage
import me.aggellos2001.ksurvivaleuplugin.utils.setCoolDown
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import kotlin.time.seconds

fun JavaPlugin.registerEvents(e: Listener) {
    Bukkit.getPluginManager().registerEvents(e, this)
}

fun registerCommandsAndEvents() {

    COMMAND_MANAGER.run {
        registerCommand(Alert)
        registerCommand(PlayerWarp)
        registerCommand(Settings)
        registerCommand(SocialMedia)
        registerCommand(Donation)
        registerCommand(SlimeChunk)
        registerCommand(ReloadConfig)
        registerCommand(Vote)
        registerCommand(Wild)
        registerCommand(Shop)
        registerCommand(UwU)
        registerCommand(SignEditor)
        registerCommand(ProxyChecker)
        registerCommand(StaffChat)
        registerCommand(Report)
        registerCommand(PlayerFinder)
        registerCommand(ReportBug)
        registerCommand(LockChest)
        registerCommand(PlayTime)
        registerCommand(OreBroadcast)
    }

    pluginInstance.run {
        registerEvents(AntiCaps)
        registerEvents(FormatChat)
        registerEvents(PlayerData)
        registerEvents(DeathMessage)
        registerEvents(VillagerLead)
        registerEvents(VillagerLoseJob)
        registerEvents(PreventEmptyHome)
        registerEvents(PaySelfPrevention)
        registerEvents(TamedProtection)
        registerEvents(PvPHandler)
        registerEvents(SitOnStairs)
        registerEvents(TeleportProtection)
        registerEvents(Help)
        registerEvents(WarpUI)
        registerEvents(SignEditor.SignEditorListener)
        registerEvents(BlockVPN)
        registerEvents(AdFilter)
        registerEvents(KeepInventory)
        registerEvents(EarningMoney)
        registerEvents(JoinLeaveMessage)
        registerEvents(LockChestListeners)
        registerEvents(OreBroadcast)

    }

    COMMAND_MANAGER.run {

        this.commandConditions.run {

            addCondition("op") { context ->
                if (!context.issuer.issuer.isOp) throw ConditionFailedException("You must be op to run this command!")
            }

            addCondition(CommandSender::class.java, "op") { context, _, _ ->
                if (!context.issuer.issuer.isOp) throw ConditionFailedException("You must be op to run this command!")
            }

            addCondition("cooldown") { context ->
                if (!context.issuer.isPlayer) return@addCondition
                val player = context.issuer.player
                val commandName =
                    if (context.hasConfig("name")) context.getConfigValue("name", "null")
                    else return@addCondition
                val cooldown =
                    if (context.hasConfig("time")) context.getConfigValue("time", 0)
                    else return@addCondition

                if (player.getCoolDown(commandName).first) {
                    throw  ConditionFailedException(player.getCoolDownErrorMessage(commandName))
                }
                player.setCoolDown(commandName, cooldown.seconds)
            }
        }
    }
}