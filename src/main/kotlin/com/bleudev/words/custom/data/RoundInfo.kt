package com.bleudev.words.custom.data

import net.minecraft.SharedConstants.TICKS_PER_SECOND
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld

interface RoundInfo {
    fun tick(): Int
    fun ticks_before_end_answer(): Int = (ANSWER_TICKS - tick()).coerceAtLeast(0)
    fun ticks_before_end(): Int = AFTER_TICKS - tick() + ANSWER_TICKS

    fun answers(): Map<ServerPlayerEntity, String?>
    fun is_all_answered(): Boolean = answers().values.all{it != null}

    companion object {
        const val ANSWER_TICKS = 20 * TICKS_PER_SECOND
        const val AFTER_TICKS = 15 * TICKS_PER_SECOND

        fun from(world: ServerWorld): RoundInfo = RoundInfoImpl(world)
    }
}