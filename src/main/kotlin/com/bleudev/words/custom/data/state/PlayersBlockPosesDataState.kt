package com.bleudev.words.custom.data.state

import com.bleudev.words.MOD_ID
import com.bleudev.words.custom.data.DataState
import com.mojang.serialization.Codec
import net.minecraft.util.math.BlockPos
import net.minecraft.world.PersistentState
import net.minecraft.world.PersistentStateType
import java.util.ArrayList

class PlayersBlockPosesDataState(players_block_poses: List<BlockPos> = listOf()) : PersistentState() {
    var players_block_poses: ArrayList<BlockPos> = ArrayList(players_block_poses)
    fun setPlayersBlockPoses(new: List<BlockPos>) {
        players_block_poses = ArrayList(new)
    }
    fun setPlayersBlockPoses(new: BlockPos) {
        setPlayersBlockPoses(listOf(new))
    }

    companion object : DataState<PlayersBlockPosesDataState> {
        val CODEC: Codec<PlayersBlockPosesDataState> = Codec.list(BlockPos.CODEC)
            .fieldOf("players_block_poses").codec()
            .xmap(::PlayersBlockPosesDataState, PlayersBlockPosesDataState::players_block_poses)

        override fun type(): PersistentStateType<PlayersBlockPosesDataState> {
            return PersistentStateType(MOD_ID, ::PlayersBlockPosesDataState, CODEC, null)
        }
    }
}