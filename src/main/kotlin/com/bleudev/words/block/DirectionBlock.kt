package com.bleudev.words.block

import com.bleudev.words.custom.data.GameData
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemPlacementContext
import net.minecraft.item.ItemStack
import net.minecraft.server.world.ServerWorld
import net.minecraft.state.StateManager
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.World
import net.minecraft.world.WorldAccess

class DirectionBlock(settings: Settings) : FacingBlock(settings) {
    init {
        this.defaultState = defaultState
            .with(FACING, Direction.NORTH)
            .with(COLOR, WordsPlayerColor.BLUE)
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) {
        super.appendProperties(builder)
        builder.add(COLOR)
    }

    override fun getPlacementState(ctx: ItemPlacementContext): BlockState? {
        return super.getPlacementState(ctx)
    }

    override fun onPlaced(world: World, pos: BlockPos, state: BlockState, placer: LivingEntity?, itemStack: ItemStack) {
        if (!world.isClient && world is ServerWorld && placer is PlayerEntity) {
            GameData.from(world).add_direction_block(pos, placer.name.literalString)
        }
    }

    override fun onBroken(world: WorldAccess, pos: BlockPos, state: BlockState) {
        if (!world.isClient && world is ServerWorld)
            GameData.from(world).remove_direction_block(pos)
    }
}