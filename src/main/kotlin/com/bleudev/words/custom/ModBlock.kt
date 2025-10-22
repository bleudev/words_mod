package com.bleudev.words.custom

import com.bleudev.words.block.DirectionBlock
import com.bleudev.words.block.LetterBlock
import com.bleudev.words.block.enitity.LetterBlockEntity
import com.bleudev.words.util.registerBlock
import com.bleudev.words.util.registerBlockEntity
import net.minecraft.block.AbstractBlock
import net.minecraft.block.Block
import net.minecraft.block.entity.BlockEntityType

object ModBlock {
    val LETTER: Block = registerBlock(
        "letter",
        ::LetterBlock,
        AbstractBlock.Settings.create(),
        true
    )
    val DIRECTION: Block = registerBlock(
        "direction",
        ::DirectionBlock,
        AbstractBlock.Settings.create(),
        true
    )

    object Entity {
        val LETTER_ENTITY: BlockEntityType<LetterBlockEntity> = registerBlockEntity(
            "letter",
            ::LetterBlockEntity,
            LETTER
        )
    }

    fun initialize() {}
}