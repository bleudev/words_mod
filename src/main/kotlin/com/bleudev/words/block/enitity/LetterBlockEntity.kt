package com.bleudev.words.block.enitity

import com.bleudev.words.custom.ModBlock
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.nbt.NbtCompound
import net.minecraft.network.listener.ClientPlayPacketListener
import net.minecraft.network.packet.Packet
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket
import net.minecraft.registry.RegistryWrapper.WrapperLookup
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.storage.ReadView
import net.minecraft.storage.WriteView
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World


fun letterBlockEntityTicker(world: World, pos: BlockPos, state: BlockState, blockEntity: LetterBlockEntity) {
//    println("$pos ${blockEntity.getLetter()}")
}

class LetterBlockEntity(pos: BlockPos, state: BlockState) : BlockEntity(ModBlock.Entity.LETTER_ENTITY, pos, state) {
    private var letter: String = "a"

    private fun sync() {
        if (world == null || world!!.isClient) return
        val packet = toUpdatePacket() ?: return
        (world as ServerWorld).players.forEach {
            (it as ServerPlayerEntity).networkHandler.sendPacket(packet)
        }
    }

    fun setLetter(v: String) {
        if (v.length != 1) throw RuntimeException("LetterBlockEntity: Letter string must have length = 1")

        if (v != letter) {
            letter = v
            markDirty()
            if (!world?.isClient!!) {
                world?.updateListeners(pos, cachedState, cachedState, 3)
                sync()
            }
        }
    }
    fun getLetter(): String {
        return letter
    }

    override fun writeData(view: WriteView) {
        view.putString("letter", letter)
        super.writeData(view)
    }
    override fun readData(view: ReadView) {
        super.readData(view)
        letter = view.getString("letter", "a")
    }
    override fun toUpdatePacket(): Packet<ClientPlayPacketListener>? {
        return BlockEntityUpdateS2CPacket.create(this)
    }
    override fun toInitialChunkDataNbt(registryLookup: WrapperLookup): NbtCompound {
        return createNbt(registryLookup)
    }
}