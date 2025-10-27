package com.bleudev.words.custom.data

import com.bleudev.words.custom.data.impl.RoundInfoImpl
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld

interface RoundInfo {
    val options: GameOptions

    fun tick(): Int
    fun ticks_before_end_answer(): Int = (options.ANSWER_TICKS - tick()).coerceAtLeast(0)
    fun ticks_before_end(): Int = options.AFTER_TICKS - tick() + options.ANSWER_TICKS

    fun answers(): Map<ServerPlayerEntity, String?>
    fun is_all_answered(): Boolean = answers().values.all{it != null}

    fun addAnswer(player: String, answer: String?): Boolean
    fun addAnswer(player: ServerPlayerEntity, answer: String?): Boolean =
        player.name.literalString?.let { addAnswer(it, answer) } == true

    fun setAnswer(player: String, answer: String?): Boolean
    fun setAnswer(player: ServerPlayerEntity, answer: String?): Boolean =
        player.name.literalString?.let { setAnswer(it, answer) } == true

    companion object {
        fun from(world: ServerWorld): RoundInfo = RoundInfoImpl(world)
    }
}