package com.bleudev.words

import com.bleudev.words.block.COLOR
import com.bleudev.words.block.SHOULD_RENDER_UP
import com.bleudev.words.block.WordsPlayerColor
import com.bleudev.words.block.enitity.LetterBlockEntity
import com.bleudev.words.custom.ModBlock
import com.bleudev.words.custom.data.GameData
import com.bleudev.words.custom.data.GameOptions
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction

class Game {
    private var world: ServerWorld

    private val data: GameData
        get() = GameData.from(world)

    private val options: GameOptions
        get() = GameOptions.get(world.server)

    private constructor(world: ServerWorld) {
        this.world = world
    }

    // Utils
    private fun findFirstNonLetterBlock(start_pos: BlockPos, direction: Direction): BlockPos {
        if (!world.getBlockState(start_pos).isOf(ModBlock.LETTER)) return start_pos
        return findFirstNonLetterBlock(start_pos.add(direction.vector), direction)
    }

    private fun placeAnswerLetter(pos: BlockPos, direction: Direction, letter: Char, isFirst: Boolean = false) {
        val centerIndex = (options.LETTER_WIDTH + 1) / 2

        val oldState = world.getBlockState(pos.add(direction.opposite.vector))
        var color = WordsPlayerColor.BLUE
        if (oldState.isOf(ModBlock.LETTER))
            color = oldState.get(COLOR)

        if (isFirst) color = WordsPlayerColor.entries.toMutableList().apply { this.remove(color) }.random()

        (1..options.LETTER_WIDTH).forEach { i ->
            val o = i - centerIndex
            val p = pos.add(direction.rotateYClockwise().vector.multiply(o))
            world.setBlockState(p, ModBlock.LETTER.defaultState
                .with(COLOR, color)
                .with(SHOULD_RENDER_UP, i == centerIndex))
            (world.getBlockEntity(p) as LetterBlockEntity).let { entity ->
                entity.setLetter(letter)
                entity.setDirection(direction)
            }
        }
    }

    // Public
    fun causeAnswerEvent(player: ServerPlayerEntity, answer: String) {
        answers[player] = answer
    }

    fun tick() {
        answers.forEach { if (it.value.isEmpty()) answers.remove(it.key) }

        if (answers.isNotEmpty()) {
            if (answer_tick % ANSWER_LETTER_TICKS == 0) {
                val playersData = data.players_map_data()
                answers.forEach { (player, answer) ->
                    if (answer.isEmpty()) return@forEach
                    playersData[player.name.literalString]?.let {
                        val direction = it.second
                        val pos = findFirstNonLetterBlock(it.first.add(0, 1, 0), direction)
                        placeAnswerLetter(pos, direction, answer[0], answer_tick == 0)
                        answers[player] = answer.substring(1)
                    }
                }
            }
            answer_tick++
        } else answer_tick = 0
    }

    companion object {
        const val ANSWER_LETTER_TICKS = 5
        var answer_tick: Int = 0
        var answers: HashMap<ServerPlayerEntity, String> = HashMap()

        fun get(world: ServerWorld): Game = Game(world)
    }
}