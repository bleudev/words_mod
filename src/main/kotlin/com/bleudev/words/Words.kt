package com.bleudev.words

import com.bleudev.words.block.COLOR
import com.bleudev.words.block.SHOULD_RENDER_UP
import com.bleudev.words.block.WordsPlayerColor
import com.bleudev.words.block.enitity.LetterBlockEntity
import com.bleudev.words.custom.ModBlock
import com.bleudev.words.custom.ModItemGroups
import com.bleudev.words.custom.data.GameData
import com.mojang.brigadier.arguments.BoolArgumentType
import com.mojang.brigadier.arguments.StringArgumentType
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents
import net.fabricmc.fabric.api.message.v1.ServerMessageEvents
import net.minecraft.command.argument.BlockPosArgumentType
import net.minecraft.server.command.CommandManager
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.text.Text
import net.minecraft.util.Formatting

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
                    .literal("game")
                    .then(CommandManager
                        .literal("start")
                        .executes { ctx ->
                            var feedback: Text = Text.translatable("commands.words.game.start.failed")
                            if (GameData.from(ctx.source.world).start())
                                feedback = Text.translatable("commands.words.game.start.success")
                            ctx.source.sendFeedback({feedback}, false)
                            return@executes 1
                        })
                    .then(CommandManager
                        .literal("stop")
                        .executes { ctx ->
                            var feedback: Text = Text.translatable("commands.words.game.stop.failed")
                            if (GameData.from(ctx.source.world).stop())
                                feedback = Text.translatable("commands.words.game.stop.success")
                            ctx.source.sendFeedback({feedback}, false)
                            return@executes 1
                        }))
                .then(CommandManager
                    .literal("test")
                    .then(CommandManager
                        .argument("word", StringArgumentType.word())
                        .executes { ctx ->
                            val word = StringArgumentType.getString(ctx, "word")
                            ctx.source.player?.let { Game.get(ctx.source.world).causeAnswerEvent(it, word) }
                            return@executes 1
                        }))
                .then(CommandManager
                    // Dev command
                    .literal("reset")
                    .executes { ctx ->
                        GameData.from(ctx.source.world).full_reset()
                        return@executes 1
                    })
                .then(CommandManager
                    .argument("pos", BlockPosArgumentType.blockPos())
                    .then(CommandManager
                        .literal("color")
                        .then(CommandManager
                            .argument("color", StringArgumentType.word())
                            .suggests { _, bld ->
                                WordsPlayerColor.entries.forEach { c ->
                                    bld.suggest(c.asString())
                                }
                                return@suggests bld.buildFuture()
                            }
                            .executes { ctx ->
                                val pos = BlockPosArgumentType.getBlockPos(ctx, "pos")
                                val color = StringArgumentType.getString(ctx, "color")
                                val world = ctx.source.world
                                world.setBlockState(pos, world.getBlockState(pos).with(COLOR, WordsPlayerColor.valueOf(color.uppercase())))
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
                            }))
                    .then(CommandManager
                        .literal("should_render_up")
                        .then(CommandManager
                            .argument("should_render_up", BoolArgumentType.bool())
                            .executes { ctx ->
                                val pos = BlockPosArgumentType.getBlockPos(ctx, "pos")
                                val should_render_up = BoolArgumentType.getBool(ctx, "should_render_up")
                                val world = ctx.source.world
                                world.setBlockState(pos, world.getBlockState(pos).with(SHOULD_RENDER_UP, should_render_up))
                                return@executes 1
                            }))
                ))
        }
        ServerTickEvents.END_SERVER_TICK.register { server ->
            server.worlds.forEach { Game.get(it).tick() }
            println(GameData.from(server.overworld))
        }
        ServerMessageEvents.ALLOW_CHAT_MESSAGE.register { message, sender, _ ->
            val mes = message.content.literalString

            if (GameData.from(sender.entityWorld).started()) {
                answer_player(sender, mes)
                return@register false
            }
            return@register true
        }
    }

    fun answer_player(player: ServerPlayerEntity, message: String?) {
        val server = player.entityWorld.server

        server.playerManager.playerList.forEach {
            it.sendMessage(Text.translatable("words.message.text.answer.success", player.name.literalString)
                .formatted(Formatting.YELLOW))
        }
        Game.get(player.entityWorld).causeAnswerEvent(player, message ?: "")

        println("${player.name.literalString} $message")
    }
}

