package com.bleudev.words.client

import com.bleudev.words.client.block.entity.render.LetterBlockEntityRenderer
import com.bleudev.words.custom.ModBlock
import net.fabricmc.api.ClientModInitializer
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories

class WordsClient : ClientModInitializer {
    override fun onInitializeClient() {
        BlockEntityRendererFactories.register(ModBlock.Entity.LETTER_ENTITY, ::LetterBlockEntityRenderer)
    }
}
