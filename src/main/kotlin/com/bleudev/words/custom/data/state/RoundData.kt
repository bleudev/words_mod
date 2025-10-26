package com.bleudev.words.custom.data.state

import com.bleudev.words.custom.data.datafixers.WPair
import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder

open class RoundData(var tick: Int, private var answers: ArrayList<WPair<String, String?>>) {
    fun addAnswer(player_name: String, answer: String? = null): Boolean = answers.add(WPair(player_name, answer))
    fun resetAnswer(player_name: String): Boolean {
        var updated = false
        answers = ArrayList(answers.map { if (it.first == player_name) {
            updated = true
            return@map WPair(it.first, null)
        } else return@map it })
        return updated
    }

    companion object {
        val DEFAULT = Properties.DEFAULT.toRoundData()
        val CODEC: Codec<RoundData> = Properties.CODEC.xmap(Properties::toRoundData, ::Properties)
    }

     class Properties(var tick: Int, var answers: ArrayList<WPair<String, String?>>) {
         constructor(tick: Int, answers: List<WPair<String, String?>>) : this(tick, ArrayList(answers))
         constructor(data: RoundData) : this(data.tick, data.answers)

        companion object {
            val DEFAULT = Properties(0, listOf())

            val CODEC: Codec<Properties> = RecordCodecBuilder.create { instance -> instance.group(
                Codec.INT.fieldOf("tick").forGetter(Properties::tick),
                Codec.list(WPair.CODEC(Codec.STRING, Codec.STRING)).fieldOf("answers").forGetter(Properties::answers)
            ).apply(instance, ::Properties) }
        }

        fun toRoundData(): RoundData {
            return RoundData(tick, answers)
        }
    }
}