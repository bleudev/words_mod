package com.bleudev.words.custom.data

import net.minecraft.server.MinecraftServer

interface GameOptions {
    val LETTER_WIDTH: Int
        get() = 3

    companion object {
        fun get(server: MinecraftServer): GameOptions = GameOptionsImpl(server)
    }
}