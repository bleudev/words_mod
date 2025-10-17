package com.bleudev.words

import com.bleudev.words.custom.data.DataState
import net.minecraft.server.world.ServerWorld
import net.minecraft.world.PersistentState

const val MOD_ID: String = "words";

fun <T: PersistentState, D: DataState<T>> ServerWorld.getData(empty_data: D): T {
    return empty_data.get(this)
}