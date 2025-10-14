package com.bleudev.words.block

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.state.StateManager
import net.minecraft.state.property.EnumProperty
import net.minecraft.util.StringIdentifiable

enum class LetterBlockColor(name: String) : StringIdentifiable {
    BLUE("blue"),
    GREEN("green"),
    LIGHT_BLUE("light_blue"),
    PURPLE("purple"),
    RED("red"),
    YELLOW("yellow");

    private val c: String = name

    override fun asString(): String {
        return c
    }
}

val COLOR: EnumProperty<LetterBlockColor> = EnumProperty.of("color", LetterBlockColor::class.java)

class LetterBlock(settings: Settings) : Block(settings) {
    init {
        this.defaultState = defaultState.with(COLOR, LetterBlockColor.BLUE)
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) {
        builder.add(COLOR)
    }
}