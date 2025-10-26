package com.bleudev.words.client.block.entity.render.state

import net.minecraft.client.render.block.entity.state.BlockEntityRenderState
import net.minecraft.util.math.Direction

class LetterBlockEntityRenderState : BlockEntityRenderState() {
    var letter: Char = 'a'
    var direction: Direction? = null
}