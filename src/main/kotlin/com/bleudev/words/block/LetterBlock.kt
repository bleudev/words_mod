package com.bleudev.words.block

import com.bleudev.words.block.enitity.LetterBlockEntity
import com.bleudev.words.block.enitity.letterBlockEntityTicker
import com.bleudev.words.custom.ModBlock
import com.mojang.serialization.MapCodec
import net.minecraft.block.Block
import net.minecraft.block.BlockEntityProvider
import net.minecraft.block.BlockState
import net.minecraft.block.BlockWithEntity
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityTicker
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.item.ItemPlacementContext
import net.minecraft.screen.Property
import net.minecraft.state.StateManager
import net.minecraft.state.property.BooleanProperty
import net.minecraft.state.property.EnumProperty
import net.minecraft.util.StringIdentifiable
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

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
val SHOULD_RENDER_UP = BooleanProperty.of("should_render_up")

class LetterBlock(settings: Settings) : BlockWithEntity(settings), BlockEntityProvider {
    init {
        this.defaultState = defaultState
            .with(COLOR, LetterBlockColor.BLUE)
            .with(SHOULD_RENDER_UP, false)
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) {
        builder.add(COLOR, SHOULD_RENDER_UP)
    }

    override fun getPlacementState(ctx: ItemPlacementContext?): BlockState? {
        return this.defaultState
            .with(SHOULD_RENDER_UP, true)
    }

    override fun getCodec(): MapCodec<out BlockWithEntity> {
        return createCodec(::LetterBlock)
    }

    override fun createBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
        return LetterBlockEntity(pos, state)
    }

    override fun <T : BlockEntity> getTicker(world: World,
        state: BlockState,
        type: BlockEntityType<T>
    ): BlockEntityTicker<T>? {
        return validateTicker(type, ModBlock.Entity.LETTER_ENTITY, ::letterBlockEntityTicker)
    }
}