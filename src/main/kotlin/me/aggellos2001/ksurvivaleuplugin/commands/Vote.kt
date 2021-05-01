package me.aggellos2001.ksurvivaleuplugin.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.Default
import me.aggellos2001.ksurvivaleuplugin.utils.sendColorizedMessage
import org.bukkit.entity.Player

@CommandAlias("vote")
object Vote : BaseCommand() {

//    private val client = HttpClient.newHttpClient()
//
//    /**
//     * Checks if [player] has voted and returns the response code from the API
//     */
//    private fun checkIfVoted(player: Player): String? {
//        val key = pluginConfig.voteApiToken ?: throw IllegalStateException("Vote api token cannot be null!")
//        if (key == "null")
//            throw IllegalStateException("Vote api token cannot be null!")
//        return try {
//            val request = HttpRequest.newBuilder()
//                .uri(URI("https://minecraft-mp.com/api/?object=votes&element=claim&key=$key&username=${player.name}"))
//                .version(HttpClient.Version.HTTP_2)
//                .timeout(20.seconds.toJavaDuration())
//                .header(
//                    "User-Agent",
//                    "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11"
//                )
//                .GET()
//                .build()
//
//            client.send(request, HttpResponse.BodyHandlers.ofString()).body()
//        } catch (e: Throwable) {
//            e.printStackTrace()
//            null
//        }
//    }
//
//    /**
//     * Attempts to claim the vote reward and returns the response code from the API
//     */
//    private fun claimReward(player: Player): String? {
//        val key = pluginConfig.voteApiToken ?: throw IllegalStateException("Vote api token cannot be null!")
//        if (key == "null")
//            throw IllegalStateException("Vote api token cannot be null!")
//        return try {
//            val request = HttpRequest.newBuilder()
//                .uri(URI("https://minecraft-mp.com/api/?action=post&object=votes&element=claim&key=$key&username=${player.name}"))
//                .timeout(20.seconds.toJavaDuration())
//                .POST(HttpRequest.BodyPublishers.noBody())
//                .build()
//            client.send(request, HttpResponse.BodyHandlers.ofString()).body()
//        } catch (t: Throwable) {
//            t.printStackTrace()
//            null
//        }
//    }
//
//    @Subcommand("check")
//    @Conditions("cooldown:time=120,name=Vote")
//    @CommandCompletion("@players @nothing")
//    fun onVoteCheck(player: Player, @Optional onlinePlayer: OnlinePlayer?) {
//        val chain: TaskChain<Any> = CHAIN_FACTORY.newChain()
//        if (onlinePlayer == null) {
//            chain.asyncFirst { checkIfVoted(player) }.syncLast {
//                when (it?.toIntOrNull()) {
//                    null -> player.sendColorizedMessage("&cThere was an error! Please try again later!")
//                    0 -> player.sendColorizedMessage("&cYou haven't voted yet!")
//                    1 -> player.sendColorizedMessage("&eYou have voted but you haven't done /vote claim yet.")
//                    2 -> player.sendColorizedMessage("&aYou have voted and taken your reward! Thank you for supporting us! Want to help more? Check /donation")
//                }
//            }.execute()
//        } else {
//            if (!player.isOp)
//                throw ConditionFailedException("You cannot check other players")
//            chain.asyncFirst { checkIfVoted(onlinePlayer.player) }.syncLast {
//                when (it?.toIntOrNull()) {
//                    null -> player.sendColorizedMessage("&cThere was an error! Please try again later!")
//                    0 -> player.sendColorizedMessage("&cPlayer ${onlinePlayer.player.name} haven't voted!")
//                    1 -> player.sendColorizedMessage("&ePlayer ${onlinePlayer.player.name} voted but haven't done /vote claim yet")
//                    2 -> player.sendColorizedMessage("&aPlayer ${onlinePlayer.player.name} voted successfully!")
//                }
//            }.execute()
//        }
//    }
//
//    @Subcommand("claim")
//    @Conditions("cooldown:time=120,name=Vote")
//    fun onVoteClaimAttempt(player: Player) {
//        val chain: TaskChain<Any> = CHAIN_FACTORY.newChain()
//        chain.asyncFirst { claimReward(player) }.storeAsData("claimReward").asyncFirst { checkIfVoted(player) }
//            .syncLast {
//                val checkIfVote = it?.toIntOrNull()
//                val checkIfClaimed = chain.getTaskData<String?>("claimReward")?.toIntOrNull()
//
//                if (checkIfVote == null || checkIfClaimed == null) {
//                    player.sendColorizedMessage("&cThere was an error! Please try again later!")
//                    return@syncLast
//                }
//
//                if (checkIfVote == 0 && checkIfClaimed == 0)
//                    player.sendColorizedMessage("&cYou haven't voted yet!")
//                else if (checkIfClaimed == 0)
//                    player.sendColorizedMessage("&cYou have already got your rewards!")
//                else {
//                    val rewards = arrayOf(
//                        "acb " + player.name + " 500",
//                        "eco give " + player.name + " 300"
//                    )
//                    for (reward in rewards) {
//                        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), reward)
//                    }
//                    val didNotFitItems = player.inventory.addItem(ItemStack(Material.PHANTOM_MEMBRANE, 2))
//                    if (didNotFitItems.isNotEmpty()) {
//                        didNotFitItems.forEach { entry ->
//                            val loc = player.location
//                            loc.world.dropItemNaturally(loc, entry.value)
//                        }
//                        player.sendColorizedMessage("&eSome items did not fit on your inventory and were dropped on the ground!")
//                    }
//                    player.sendColorizedMessage("&aYou got 500 claim blocks, 2 phantom membranes and 300$ for your vote!")
//                    Bukkit.getServer().sendColorizedMessage("&aPlayer &6${player.name}&a voted for the server!")
//                }
//
//            }.execute()
//    }

    @Default
    fun openVoteBook(player: Player) {
//        val title = Component.text("")
//        val author = Component.text("")
//
//        val page = Component.text()
//            .append(
//                Component.text("1. You can vote for our server here: ", TextColor.color(31, 133, 8))
//            )
//            .append(
//                Component.text("https://minecraft-mp.com/server/173291/vote/\n", TextColor.color(242, 82, 70))
//                    .clickEvent(ClickEvent.openUrl("https://minecraft-mp.com/server/173291/vote/"))
//            )
//            .append(
//                Component.text("2. Once you voted on the website by typing your minecraft name and clicking the vote button click ")
//                    .color(
//                        TextColor.color(31, 133, 8)
//                    )
//            )
//            .append(
//                Component.text("here", TextColor.color(242, 82, 70))
//                    .clickEvent(ClickEvent.runCommand("/vote claim"))
//            )
//
//            .build()
//
//        player.openBook(Book.book(title, author, page))

        player.sendColorizedMessage(
            """
            &aVote links:
            &e1. https://minecraft-mp.com/server/173291/vote/
            2. https://minecraftservers.org/vote/458700
            3. https://minecraft-server-list.com/server/476790/vote/
            4. https://minecraft-server.net/vote/SurvivalEU/
            5. https://topg.org/minecraft-servers/server-628600
            6. https://minecraftservers.biz/servers/73618/#vote_now
            7. https://minecraft.buzz/server/1483&tab=vote
        """.trimIndent()
        )

    }
}