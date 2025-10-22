package com.bleudev.words.custom

import com.bleudev.words.util.registerItemGroup
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.minecraft.item.ItemStack
import net.minecraft.text.Text

object ModItemGroups {
    val WORDS_ITEM_GROUP = registerItemGroup("words") { b -> b
        .icon { ItemStack(ModBlock.LETTER.asItem()) }
        .displayName(Text.translatable("itemgroup.words.words"))
    }

    fun initialize() {
        ItemGroupEvents.modifyEntriesEvent(WORDS_ITEM_GROUP).register { e ->
            e.add(ModBlock.LETTER)
            e.add(ModBlock.DIRECTION)
        }
    }
}