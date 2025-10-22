package com.bleudev.words.custom.data

import com.bleudev.words.custom.data.datafixers.WPair
import com.bleudev.words.util.extractFirsts
import com.bleudev.words.util.extractSeconds
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction

interface GameData {
    /**
     * Cached block poses of `words:direction` blocks
     *
     * To get `players_directions` use `GameData.players_directions`
    * */
    @Deprecated("Use `players_map_data` instead")
    fun players_block_poses(): List<BlockPos> {
        return players_map_data().values.extractFirsts()
    }
    @Deprecated("Use `players_map_data` instead")
    fun players_directions(): List<Direction> {
        return players_map_data().values.extractSeconds()
    }
    @Deprecated("Use `players_map_data` instead")
    fun players_names(): List<String> {
        return players_map_data().keys.toList()
    }

    fun players_map_data(): Map<String, WPair<BlockPos, Direction>>

    fun started(): Boolean

    // Utilities
    fun add_direction_block(pos: BlockPos, placer: String?): Boolean
    fun remove_direction_block(pos: BlockPos): Boolean

    fun full_reset()

    companion object {
        fun from(world: ServerWorld): GameData {
            return GameDataImpl(world)
        }
    }
}