package com.bleudev.words.custom.data.state

import net.minecraft.server.world.ServerWorld
import net.minecraft.world.PersistentState
import net.minecraft.world.PersistentStateType

interface DataStateCompanion<T : PersistentState> {
    fun type(): PersistentStateType<T>

    fun get(world: ServerWorld): T {
        return world.persistentStateManager
            .getOrCreate(type())
            .apply{this.markDirty()}
    }
}