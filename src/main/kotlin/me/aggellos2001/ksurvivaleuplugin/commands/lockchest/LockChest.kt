package me.aggellos2001.ksurvivaleuplugin.commands.lockchest

import co.aikar.commands.BaseCommand
import co.aikar.commands.ConditionFailedException
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandCompletion
import co.aikar.commands.annotation.Default
import co.aikar.commands.annotation.HelpCommand
import me.aggellos2001.ksurvivaleuplugin.utils.canBuild
import me.aggellos2001.ksurvivaleuplugin.utils.sendColorizedMessage
import me.aggellos2001.ksurvivaleuplugin.utils.sendHelpMessage
import org.bukkit.block.Chest
import org.bukkit.entity.Player

@CommandAlias("lockchest|lchest")
object LockChest : BaseCommand() {

    @Default
    @CommandCompletion("@players")
    fun onLockChestCommand(player: Player, vararg extraPlayers: String) {
        val target = player.getTargetBlock(5)?.state as? Chest
            ?: throw ConditionFailedException("You must look at a chest within 5 blocks range!")
        val chest = ChestLockUtil(target)
        if (extraPlayers.size > 2)
            throw ConditionFailedException("You can only add 1 or 2 players extra!")
        if (chest.chestOwners().isNotEmpty())
            throw ConditionFailedException("Chest is locked by &e${chest.chestOwners().joinToString(", ")}&c!")
        if (!player.canBuild())
            throw ConditionFailedException("You cannot lock a chest if you cannot break it!")
        val addSign = chest.addLockSign(player, *extraPlayers)
        if (addSign == 1)
            player.sendColorizedMessage("&aSuccessfully locked this chest!")
        else if (addSign == 0)
            player.sendColorizedMessage("&cSomething went wrong! Make sure there's enough space around the chest for the sign to appear!")
    }

    @HelpCommand
    fun onLockChestHelp(player: Player) {
        player.sendHelpMessage(
            this,
            """
            &b1. Lock a chest for yourself: &e/lockchest
            &b2. Lock a chest for yourself and 1 other player: &e/lockchest <player1>
            &b3. Lock a chest for yourself and 2 other players: &e/lockchest <player1> <player2>
            &ePlayers that you add as trusted, cannot break the chest only open it!
            """
        )
    }
}