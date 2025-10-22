package com.bleudev.words.client.block.entity.render

import com.bleudev.words.block.SHOULD_RENDER_UP
import com.bleudev.words.block.enitity.LetterBlockEntity
import com.bleudev.words.client.block.entity.render.state.LetterBlockEntityRenderState
import net.minecraft.client.font.TextRenderer
import net.minecraft.client.render.block.entity.BlockEntityRenderer
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory
import net.minecraft.client.render.command.ModelCommandRenderer
import net.minecraft.client.render.command.OrderedRenderCommandQueue
import net.minecraft.client.render.state.CameraRenderState
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.text.Text
import net.minecraft.util.math.Direction
import net.minecraft.util.math.Vec3d
import org.joml.Math.PI_OVER_2_f
import org.joml.Math.PI_f
import org.joml.Quaternionf

class LetterBlockEntityRenderer(private val context: BlockEntityRendererFactory.Context) : BlockEntityRenderer<LetterBlockEntity, LetterBlockEntityRenderState> {
    override fun createRenderState(): LetterBlockEntityRenderState {
        return LetterBlockEntityRenderState()
    }

    override fun updateRenderState(
        blockEntity: LetterBlockEntity,
        state: LetterBlockEntityRenderState,
        tickProgress: Float,
        cameraPos: Vec3d,
        crumblingOverlay: ModelCommandRenderer.CrumblingOverlayCommand?
    ) {
        state.letter = blockEntity.getLetter()
        super.updateRenderState(blockEntity, state, tickProgress, cameraPos, crumblingOverlay)
    }

    private fun drawTextWithDirection(
        matrices: MatrixStack,
        queue: OrderedRenderCommandQueue,
        state: LetterBlockEntityRenderState,
        direction: Direction
    ) {
        if (state.letter.isEmpty()) return

        matrices.push()
        matrices.translate(Vec3d(direction.vector).multiply(.5))
        matrices.scale(1/9f, 1/9f, 1/9f)

        val q = Quaternionf().run{ when (direction) {
            Direction.NORTH -> return@run this.rotationY(PI_f)
            Direction.WEST -> return@run this.rotationY(PI_OVER_2_f)
            Direction.EAST -> return@run this.rotationY(-PI_OVER_2_f)
            Direction.UP -> return@run this.rotationXYZ(-PI_OVER_2_f, 0f, PI_OVER_2_f)
            Direction.DOWN -> return@run this.rotationXYZ(PI_OVER_2_f, 0f, -PI_OVER_2_f)
            else -> return@run this
        }}
        matrices.multiply(Quaternionf().rotationX(PI_f))
        matrices.multiply(q)

        val text = state.letter.uppercase()[0].toString()
        val width = context.textRenderer.getWidth(text).toFloat()
        queue.submitText(matrices, -width / 2  + 0.5f, -3.5f, Text.literal(text).asOrderedText(), false,
            TextRenderer.TextLayerType.POLYGON_OFFSET, 15728880,
            0xffffffff.toInt(), 0, 0)
        matrices.pop()
    }

    override fun render(
        state: LetterBlockEntityRenderState,
        matrices: MatrixStack,
        queue: OrderedRenderCommandQueue,
        cameraState: CameraRenderState
    ) {
        matrices.push()
        matrices.translate(0.5, 0.5, 0.5)

        drawTextWithDirection(matrices, queue, state, Direction.NORTH)
        drawTextWithDirection(matrices, queue, state, Direction.SOUTH)
        drawTextWithDirection(matrices, queue, state, Direction.EAST)
        drawTextWithDirection(matrices, queue, state, Direction.WEST)
        if (state.blockState.get(SHOULD_RENDER_UP)) {
            drawTextWithDirection(matrices, queue, state, Direction.UP)
            drawTextWithDirection(matrices, queue, state, Direction.DOWN)
        }

        matrices.pop()
    }
}