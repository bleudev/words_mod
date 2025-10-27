package com.bleudev.words.custom.data

import com.bleudev.words.custom.data.impl.GameOptionsImpl
import net.minecraft.server.MinecraftServer

interface GameOptions {
    val LETTER_WIDTH: Int
    val ANSWER_TICKS: Int
    val AFTER_TICKS: Int

    companion object {
        fun get(server: MinecraftServer): GameOptions = GameOptionsImpl(server)
    }
}