package com.nami

import net.dv8tion.jda.api.JDABuilder

class Bot(val token: String) {

    private val commands = setOf(
//        BotCommand("up", listOf("docker", "compose", "up", "-d")),
//        BotCommand("down", listOf("docker", "compose", "down")),
//        BotCommand("pwd", listOf("pwd"))

        BotCommand("start", listOf("docker", "compose", "start")),
        BotCommand("stop", listOf("docker", "compose", "stop")),
        BotCommand("restart", listOf("docker", "compose", "restart")),
        BotCommand("status", listOf("docker", "compose", "ps"))
    )

    fun start() {
        val jda = JDABuilder.createDefault(token)
            .addEventListeners(*commands.toTypedArray())
            .build()

        jda.updateCommands()
            .addCommands(commands.map { it.command() })
            .queue()
    }

}