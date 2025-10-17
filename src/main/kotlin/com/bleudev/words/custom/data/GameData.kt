package com.bleudev.words.custom.data

import com.bleudev.words.util.toPairMapWith
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction

interface GameData {
    /**
     * Cached block poses of `words:direction` blocks
     *
     * To get `players_directions` use `GameData.players_directions`
    * */
    fun players_block_poses(): List<BlockPos>
    fun players_directions(): List<Direction>

    fun players_map_data(): Map<BlockPos, Direction> {
        return players_block_poses().toPairMapWith(players_directions())
    }

    fun started(): Boolean

    companion object {
        fun from(world: ServerWorld): GameData {
            return GameDataImpl(world)
        }
    }
}