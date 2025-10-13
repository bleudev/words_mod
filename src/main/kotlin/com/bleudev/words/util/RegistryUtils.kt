package com.bleudev.words.util

import com.bleudev.words.MOD_ID
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup
import net.minecraft.block.AbstractBlock
import net.minecraft.block.Block
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.RegistryKeys
import net.minecraft.util.Identifier
import java.util.function.Supplier


fun <T> keyOf(type: RegistryKey<Registry<T>>, name: String): RegistryKey<T?>? {
    return RegistryKey.of<T?>(type, Identifier.of(MOD_ID, name))
}

fun registerBlock(name: String, blockFactory: (AbstractBlock.Settings) -> Block, settings: AbstractBlock.Settings, shouldRegisterItem: Boolean = true): Block {
    val blockKey: RegistryKey<Block?>? = keyOf<Block?>(RegistryKeys.BLOCK, name)
    val block: Block = blockFactory(settings.registryKey(blockKey))
    if (shouldRegisterItem) {
        val itemKey: RegistryKey<Item?>? = keyOf<Item?>(RegistryKeys.ITEM, name)
        val blockItem = BlockItem(block, Item.Settings().registryKey(itemKey))
        Registry.register(Registries.ITEM, itemKey, blockItem)
    }
    return Registry.register(Registries.BLOCK, blockKey, block)
}

fun registerItemGroup(name: String, itemGroupFactory: (ItemGroup.Builder) -> ItemGroup.Builder): RegistryKey<ItemGroup> {
    val registryKey = RegistryKey.of(
        Registries.ITEM_GROUP.getKey(),
        Identifier.of(MOD_ID, name)
    )
    val itemGroup = itemGroupFactory(FabricItemGroup.builder()).build()

    Registry.register(Registries.ITEM_GROUP, registryKey, itemGroup)

    return registryKey
}