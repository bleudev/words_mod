package com.bleudev.words.custom.data.impl

import com.bleudev.words.custom.data.GameOptions
import com.bleudev.words.custom.data.RoundInfo
import com.bleudev.words.custom.data.datafixers.WPair
import com.bleudev.words.custom.data.state.RoundData
import com.bleudev.words.custom.data.state.WordsDataState
import com.bleudev.words.getData
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld

class RoundInfoImpl(private var world: ServerWorld) : RoundInfo {
    private val data: RoundData
        get() = world.getData(WordsDataState).round_data
    override val options: GameOptions
        get() = GameOptions.get(world.server)

    override fun tick(): Int = data.tick

    override fun answers(): Map<ServerPlayerEntity, String?> {
        val answers = data.getAnswers()
        return HashMap<ServerPlayerEntity, String?>().apply {
            answers.forEach { (playerName, answer) ->
                world.getPlayers{it.name.literalString == playerName}.firstOrNull()?.let {
                    this@apply[it] = answer
                }
            }
        }
    }

    override fun addAnswer(player: String, answer: String?) = data.addAnswer(player, answer)
    override fun setAnswer(player: String, answer: String?): Boolean = data.setAnswer(player, answer)

    override fun toString(): String {
        val info: HashMap<String, Any> = HashMap()

        info["tick"] = tick()
        info["answers"] = answers().map { WPair(it.key.name.literalString, it.value) }

        var data = ""
        info.forEach { (key, value) ->
            data += "    $key: $value\n"
        }
        return "RoundInfo{\n$data  }"
    }
}