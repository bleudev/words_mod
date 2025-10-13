package com.bleudev.words.custom

import com.bleudev.words.util.registerBlock
import net.minecraft.block.AbstractBlock
import net.minecraft.block.Block
import net.minecraft.sound.BlockSoundGroup

object ModBlocks {
    val LETTER: Block = registerBlock(
        "letter",
        ::Block,
        AbstractBlock.Settings.create().sounds(BlockSoundGroup.WOOD),
        true
    )

    fun initialize() {}
}