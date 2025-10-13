package com.bleudev.words

import com.bleudev.words.custom.ModBlocks
import com.bleudev.words.custom.ModItemGroups
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents
import net.minecraft.server.MinecraftServer

class Words : ModInitializer {
    override fun onInitialize() {
        // Custom
        ModBlocks.initialize()
        ModItemGroups.initialize()

        // Events
        ServerTickEvents.END_SERVER_TICK.register{s -> tick(s)}
    }

    private fun tick(server: MinecraftServer) {

    }
}

