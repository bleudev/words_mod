package com.bleudev.words.custom.data.impl

import com.bleudev.words.custom.data.GameOptions
import net.minecraft.SharedConstants.TICKS_PER_SECOND
import net.minecraft.server.MinecraftServer

class GameOptionsImpl(private var server: MinecraftServer) : GameOptions {
    override val LETTER_WIDTH: Int
        get() = 3
    override val ANSWER_TICKS: Int
        get() = 20 * TICKS_PER_SECOND
    override val AFTER_TICKS: Int
        get() = 15 * TICKS_PER_SECOND
}