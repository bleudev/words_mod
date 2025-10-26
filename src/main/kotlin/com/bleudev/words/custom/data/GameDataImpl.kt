package com.bleudev.words.custom.data

import com.bleudev.words.block.FACING
import com.bleudev.words.custom.ModBlock
import com.bleudev.words.custom.data.datafixers.WPair
import com.bleudev.words.custom.data.state.WordsDataState
import com.bleudev.words.getData
import com.bleudev.words.util.extractFirsts
import com.bleudev.words.util.extractSeconds
import com.bleudev.words.util.toMapWith
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction

class GameDataImpl(private val world: ServerWorld) : GameData {
    override fun players_map_data(): Map<String, WPair<BlockPos, Direction>> {
        val data = world.getData(WordsDataState).getPlayersData()
        val poses = data.extractSeconds()
        return data.extractFirsts().toMapWith(poses, ArrayList<Direction>().apply { poses.forEach { pos ->
            val state = world.getBlockState(pos)
            if (state.isOf(ModBlock.DIRECTION)) this.add(state.get(FACING))
            else world.getData(WordsDataState).removePlayerData(directionBlockPos = pos)
        } })
    }

    override fun started(): Boolean = world.getData(WordsDataState).started
    override fun set_started(started: Boolean): Boolean {
        val old_started = started()
        world.getData(WordsDataState).started = started
        return old_started != started
    }

    override fun add_direction_block(pos: BlockPos, placer: String?): Boolean {
        return world.getData(WordsDataState).addPlayerData(get_player_for_direction_block(placer) ?: return false, pos)
    }

    override fun remove_direction_block(pos: BlockPos): Boolean = world
        .getData(WordsDataState).removePlayerData(directionBlockPos = pos) != null

    override fun round_info(): RoundInfo = RoundInfo.from(world)

    override fun full_reset() {
        world.getData(WordsDataState).reset()
    }

    // Utils
    private fun get_player_for_direction_block(placer: String?): String? {
        val playerNames = players_map_data().keys
        if (placer != null && placer in playerNames) return placer
        return world.players
            .map { it.name.literalString }
            .filterNot { playerNames.contains(it) }
            .randomOrNull()
    }

    override fun toString(): String {
        val info: HashMap<String, Any> = HashMap()

        info["started"] = started()
        info["players_data"] = players_map_data()

        var data = ""
        info.forEach { (key, value) ->
            data += "  $key: $value\n"
        }
        return "GameData{\n$data}"
    }
}