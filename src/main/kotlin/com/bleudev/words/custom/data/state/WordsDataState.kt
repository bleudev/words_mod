package com.bleudev.words.custom.data.state

import com.bleudev.words.MOD_ID
import com.bleudev.words.custom.data.datafixers.WPair
import com.bleudev.words.util.addNullable
import com.bleudev.words.util.popFirstIf
import com.bleudev.words.util.removeNullable
import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.util.math.BlockPos
import net.minecraft.world.PersistentState
import net.minecraft.world.PersistentStateType

typealias PlayerData = WPair<String, BlockPos>

class WordsDataState(
    var started: Boolean,
    private var players_data: ArrayList<PlayerData>,
    var round_data: RoundData
) : PersistentState() {
    constructor(started: Boolean, players_data: List<PlayerData>, round_data: RoundData) :
        this(started, ArrayList(players_data), round_data)

    fun getPlayersData(): List<PlayerData> = players_data.toList()

    fun addPlayerData(new: PlayerData?): Boolean = players_data.addNullable(new)
    fun addPlayerData(name: String, directionBlockPos: BlockPos): Boolean =
        addPlayerData(WPair(name, directionBlockPos))

    fun removePlayerData(old: PlayerData?): Boolean = players_data.removeNullable(old)
    fun removePlayerData(playerName: String? = null, directionBlockPos: BlockPos? = null): PlayerData? =
        players_data.popFirstIf { (name, pos) -> (
            (playerName != null && playerName == name) ||
            (directionBlockPos != null && directionBlockPos == pos)
        ) }

    fun reset() {
        started = Properties.DEFAULT.started
        players_data = ArrayList(Properties.DEFAULT.players_data)
    }

    companion object: DataStateCompanion<WordsDataState> {
        override fun type(): PersistentStateType<WordsDataState> {
            return PersistentStateType(MOD_ID, Properties.DEFAULT::toWordsDataState,
                Properties.CODEC.xmap(Properties::toWordsDataState, ::Properties), null)
        }
    }
    private class Properties(var started: Boolean, var players_data: List<PlayerData>, var round_data: RoundData) {
        constructor(data: WordsDataState) : this(data.started, data.players_data, data.round_data)

        companion object {
            val DEFAULT = Properties(false, listOf<PlayerData>(), RoundData.DEFAULT)

            val CODEC: Codec<Properties> = RecordCodecBuilder.create { instance -> instance.group(
                Codec.BOOL.fieldOf("started").forGetter(Properties::started),
                Codec.list(WPair.CODEC(Codec.STRING, BlockPos.CODEC)).fieldOf("players_data").forGetter(Properties::players_data),
                RoundData.CODEC.fieldOf("round").forGetter(Properties::round_data)
            ).apply(instance, ::Properties) }
        }

        fun toWordsDataState(): WordsDataState {
            return WordsDataState(started, players_data, round_data)
        }
    }
}