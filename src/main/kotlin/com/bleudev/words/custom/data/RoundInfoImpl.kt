package com.bleudev.words.custom.data

import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld

class RoundInfoImpl(private var world: ServerWorld) : RoundInfo {
    override fun tick(): Int {
        TODO("Not yet implemented")
    }

    override fun answers(): Map<ServerPlayerEntity, String?> {
        TODO("Not yet implemented")
    }
}