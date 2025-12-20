package com.nami

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.commands.build.Commands
import java.nio.file.Path
import java.util.concurrent.CompletableFuture

class BotCommand(val name: String, val command: List<String>) : ListenerAdapter() {

    fun command() = Commands.slash(name, command.toString())

    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        val startTime = System.currentTimeMillis()

        if (event.name != name)
            return

        val channel = event.channelIdLong
        val instance = Environment.INSTANCES[channel] ?: return

        event.deferReply(true).queue()

        CompletableFuture.runAsync {
            val path = Path.of("/instances").resolve(instance.path)
            val process = ProcessBuilder(command)
                .directory(path.toFile())
                .start()

            val output = process.inputStream.bufferedReader()
            val error = process.errorStream.bufferedReader()

            val builder = StringBuilder()
            var lastUpdate = System.currentTimeMillis()
            while (process.isAlive) {
                while (output.ready())
                    builder.appendLine(output.readLine().trim())

                while (error.ready())
                    builder.appendLine(error.readLine().trim())

                if (System.currentTimeMillis() - lastUpdate >= 500) {
                    try {
                        val time = (System.currentTimeMillis() - startTime) / 1000.0
                        val emoji = if(time.toInt() %2 == 0) "⏳" else "⌛"

                        val sb = StringBuilder()
                        sb.appendLine("$emoji ${time}s")
                        sb.appendLine("```")
                        sb.appendLine(builder.toString().take(2000))
                        sb.appendLine("```")

                        event.hook.editOriginal(sb.toString()).queue()
                    } catch (_: Exception) {
                    }
                    lastUpdate = System.currentTimeMillis()
                }

                Thread.sleep(50)
            }

            Thread.sleep(1000)

            while (output.ready())
                builder.appendLine(output.readLine().trim())

            while (error.ready())
                builder.appendLine(error.readLine().trim())

            val time = (System.currentTimeMillis() - startTime) / 1000.0

            val sb = StringBuilder()
            sb.appendLine("✅ ${time}s")
            sb.appendLine("```")
            sb.appendLine(builder.toString().take(2000))
            sb.appendLine("```")

            event.hook.editOriginal(sb.toString()).queue()
            event.hook.editOriginal(sb.toString()).queue()
        }
    }

}