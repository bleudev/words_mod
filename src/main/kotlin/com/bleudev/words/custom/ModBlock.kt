package com.bleudev.words.custom

import com.bleudev.words.block.LetterBlock
import com.bleudev.words.block.enitity.LetterBlockEntity
import com.bleudev.words.util.registerBlock
import com.bleudev.words.util.registerBlockEntity
import net.minecraft.block.AbstractBlock
import net.minecraft.block.Block
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.sound.BlockSoundGroup

object ModBlock {
    val LETTER: Block = registerBlock(
        "letter",
        ::LetterBlock,
        AbstractBlock.Settings.create().sounds(BlockSoundGroup.WOOD),
        true
    )

    object Entity {
        val LETTER_ENTITY: BlockEntityType<LetterBlockEntity> = registerBlockEntity(
            "computer",
            ::LetterBlockEntity,
            LETTER
        )
    }

    fun initialize() {}
}