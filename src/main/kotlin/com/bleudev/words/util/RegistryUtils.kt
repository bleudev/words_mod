package com.bleudev.words.util

import com.bleudev.words.MOD_ID
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup
import net.fabricmc.fabric.api.`object`.builder.v1.block.entity.FabricBlockEntityTypeBuilder
import net.minecraft.block.AbstractBlock
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.RegistryKeys
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos

fun idOf(name: String): Identifier {
    return Identifier.of(MOD_ID, name)
}

fun <T> keyOf(type: RegistryKey<out Registry<T>>, name: String): RegistryKey<T> {
    return RegistryKey.of(type, idOf(name))
}

fun registerBlock(name: String, blockFactory: (AbstractBlock.Settings) -> Block, settings: AbstractBlock.Settings, shouldRegisterItem: Boolean = true): Block {
    val blockKey: RegistryKey<Block> = keyOf(RegistryKeys.BLOCK, name)
    val block: Block = blockFactory(settings.registryKey(blockKey))
    if (shouldRegisterItem) {
        val itemKey: RegistryKey<Item> = keyOf(RegistryKeys.ITEM, name)
        val blockItem = BlockItem(block, Item.Settings().registryKey(itemKey))
        Registry.register(Registries.ITEM, itemKey, blockItem)
    }
    return Registry.register(Registries.BLOCK, blockKey, block)
}

fun <T : BlockEntity> registerBlockEntity(name: String, blockEntityTypeFactory: (BlockPos, BlockState) -> T, block: Block): BlockEntityType<T> {
    return Registry.register(
        Registries.BLOCK_ENTITY_TYPE,
        Identifier.of(MOD_ID, name),
        FabricBlockEntityTypeBuilder.create(blockEntityTypeFactory, block).build()
    )
}

fun registerItemGroup(name: String, itemGroupFactory: (ItemGroup.Builder) -> ItemGroup.Builder): RegistryKey<ItemGroup> {
    val registryKey = keyOf(
        Registries.ITEM_GROUP.getKey(),
        name
    )
    Registry.register(Registries.ITEM_GROUP, registryKey, itemGroupFactory(FabricItemGroup.builder()).build())
    return registryKey
}