package com.nami

import java.nio.file.Path


object Environment {

    val TOKEN = System.getenv("TOKEN") ?: throw IllegalStateException("TOKEN is undefined.")
    val INSTANCES = System.getenv("INSTANCES").lines().map {
        val split = it.trim().split(":")

        val path = Path.of(split[0].trim())
        val channel = split[1].trim().toLong()

        Instance(path, channel)
    }.associateBy { it.channel }

    data class Instance(
        val path: Path,
        val channel: Long
    )

}