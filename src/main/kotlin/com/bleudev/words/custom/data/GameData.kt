package com.bleudev.words.custom.data

import com.bleudev.words.custom.data.datafixers.WPair
import com.bleudev.words.custom.data.impl.GameDataImpl
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction

interface GameData {
    fun players_map_data(): Map<String, WPair<BlockPos, Direction>>
    fun started(): Boolean

    // Utilities
    fun set_started(started: Boolean): Boolean
    fun start(): Boolean = set_started(true)
    fun stop(): Boolean = set_started(false)
    fun add_direction_block(pos: BlockPos, placer: String?): Boolean
    fun remove_direction_block(pos: BlockPos): Boolean

    val round_info: RoundInfo

    fun full_reset()

    companion object {
        fun from(world: ServerWorld): GameData = GameDataImpl(world)
    }
}