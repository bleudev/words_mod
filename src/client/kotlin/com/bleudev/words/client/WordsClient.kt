package com.bleudev.words.client

import com.bleudev.words.client.block.entity.render.LetterBlockEntityRenderer
import com.bleudev.words.custom.ModBlock
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.message.v1.ClientSendMessageEvents
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories

class WordsClient : ClientModInitializer {
    override fun onInitializeClient() {
        BlockEntityRendererFactories.register(ModBlock.Entity.LETTER_ENTITY, ::LetterBlockEntityRenderer)
//        ClientSendMessageEvents.CHAT.register { message ->
//            println("message: $message")
//        }
//        ClientSendMessageEvents.ALLOW_CHAT.register { message ->
//            println("allow? $message")
//            return@register !message.equals("hello")
//        }
    }
}
