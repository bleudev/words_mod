package com.bleudev.words

import com.bleudev.words.block.COLOR
import com.bleudev.words.block.LetterBlockColor
import com.bleudev.words.block.enitity.LetterBlockEntity
import com.bleudev.words.custom.ModBlock
import com.bleudev.words.custom.ModItemGroups
import com.mojang.brigadier.arguments.StringArgumentType
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.minecraft.command.argument.BlockPosArgumentType
import net.minecraft.server.command.CommandManager

class Words : ModInitializer {
    override fun onInitialize() {
        // Custom
        ModBlock.initialize()
        ModItemGroups.initialize()

        // Events
        CommandRegistrationCallback.EVENT.register { d, _, _ ->
            d.register(CommandManager
                .literal("words")
                .then(CommandManager
                    .argument("pos", BlockPosArgumentType.blockPos())
                    .then(CommandManager
                        .literal("color")
                        .then(CommandManager
                            .argument("color", StringArgumentType.word())
                            .suggests { _, bld ->
                                LetterBlockColor.entries.forEach { c ->
                                    bld.suggest(c.asString())
                                }
                                return@suggests bld.buildFuture()
                            }
                            .executes { ctx ->
                                val pos = BlockPosArgumentType.getBlockPos(ctx, "pos")
                                val color = StringArgumentType.getString(ctx, "color")
                                val world = ctx.source.world
                                world.setBlockState(pos, world.getBlockState(pos).with(COLOR, LetterBlockColor.valueOf(color.uppercase())))
                                return@executes 1
                            }))
                    .then(CommandManager
                        .literal("letter")
                        .then(CommandManager
                            .argument("letter", StringArgumentType.word())
                            .executes { ctx ->
                                val pos = BlockPosArgumentType.getBlockPos(ctx, "pos")
                                (ctx.source.world.getBlockEntity(pos) as? LetterBlockEntity)?.
                                    setLetter(StringArgumentType.getString(ctx, "letter"))
                                return@executes 1
                            })
                    )
                ))
        }
    }
}

