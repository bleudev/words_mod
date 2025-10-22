package com.bleudev.words.block

import net.minecraft.state.property.EnumProperty
import net.minecraft.state.property.Properties
import net.minecraft.util.StringIdentifiable
import net.minecraft.util.math.Direction

val FACING: EnumProperty<Direction> = Properties.HORIZONTAL_FACING

enum class WordsPlayerColor(private val colorName: String) : StringIdentifiable {
    BLUE("blue"),
    GREEN("green"),
    LIGHT_BLUE("light_blue"),
    PURPLE("purple"),
    RED("red"),
    YELLOW("yellow");

    override fun asString(): String = colorName
}
val COLOR: EnumProperty<WordsPlayerColor> = EnumProperty.of("color", WordsPlayerColor::class.java)
