package com.bleudev.words.custom.data

import com.bleudev.words.custom.data.state.PlayersBlockPosesDataState
import com.bleudev.words.getData
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction

class GameDataImpl(private val world: ServerWorld) : GameData {
    override fun players_block_poses(): List<BlockPos> {
        return world.getData(PlayersBlockPosesDataState).players_block_poses.toList()
    }
    override fun players_directions(): List<Direction> {
        TODO("Not yet implemented")
    }

    override fun started(): Boolean {
        TODO("Not yet implemented")
    }
}