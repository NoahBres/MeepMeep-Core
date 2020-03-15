package com.noahbres.meepmeep.entity

import com.acmerobotics.roadrunner.geometry.Vector2d
import com.noahbres.meepmeep.anim.AnimationController
import com.noahbres.meepmeep.anim.Ease
import com.noahbres.meepmeep.colorscheme.ColorScheme
import com.noahbres.meepmeep.util.FieldUtil
import java.awt.Color
import java.awt.Font
import java.awt.Graphics2D
import java.awt.event.MouseEvent
import java.awt.event.MouseMotionListener
import kotlin.math.abs

class AxesEntity
@JvmOverloads constructor(
        private val axesThickness: Double,
        private var colorScheme: ColorScheme,

        private var canvasWidth: Int,
        private var canvasHeight: Int,

        private var font: Font? = null,
        private var fontSize: Float = 20f
) : ThemedEntity, MouseMotionListener {
    override val zIndex = 1

    private val NUMBER_X_AXIS_X_OFFSET = 0
    private val NUMBER_X_AXIS_Y_OFFSET = 0

    private val TICK_LENGTH = 3.0
    private val TICK_THICKNESS = 0.45

    private val X_INCREMENTS = 18
    private val Y_INCREMENTS = 18

    private val X_START = -FieldUtil.FIELD_WIDTH / 2
    private val X_END = FieldUtil.FIELD_WIDTH / 2
    private val X_TEXT_Y_OFFSET = 0.3
    private val X_TEXT_POSITIVE_X_OFFSET = -0.6
    private val X_TEXT_NEGATIVE_X_OFFSET = 0.7

    private val Y_START = -FieldUtil.FIELD_HEIGHT / 2
    private val Y_END = FieldUtil.FIELD_HEIGHT / 2
    private val Y_TEXT_X_OFFSET = 0.6

    private val X_LABEL_X_OFFSET = 1.5
    private val X_LABEL_Y_OFFSET = -1.0

    private val Y_LABEL_X_OFFSET = -4.0
    private val Y_LABEL_Y_OFFSET = 3.0

    private val HOVER_TARGET = 20.0

    private var currentOpacity = colorScheme.AXIS_NORMAL_OPACITY
    private val animationController = AnimationController(currentOpacity).clip(0.0, 1.0)

    override fun update(deltaTime: Long) {
        currentOpacity = animationController.value
        // Todo fix issue that necessitates the controller needing a clip
        // Starts at like 30 or 28 for some reason
//        println(currentOpacity)
        animationController.update()
    }

    override fun render(gfx: Graphics2D, canvasWidth: Int, canvasHeight: Int) {
        println(colorScheme)
        val pixelThickness = FieldUtil.scaleInchesToPixel(axesThickness)

        gfx.color = Color(colorScheme.AXIS_X_COLOR.red, colorScheme.AXIS_X_COLOR.green, colorScheme.AXIS_X_COLOR.blue, (currentOpacity * 255).toInt())
        gfx.fillRect(0, (canvasHeight / 2.0 - pixelThickness / 2).toInt(), canvasWidth, pixelThickness.toInt())

        gfx.color = Color(colorScheme.AXIS_Y_COLOR.red, colorScheme.AXIS_Y_COLOR.green, colorScheme.AXIS_Y_COLOR.blue, (currentOpacity * 255).toInt())
        gfx.fillRect((canvasWidth / 2.0 - pixelThickness / 2).toInt(), 0, pixelThickness.toInt(), canvasHeight)

        if (font != null) {
            gfx.font = font
        }

        val fontMetrics = gfx.fontMetrics

        // Draw x axis
        gfx.color = Color(colorScheme.AXIS_X_COLOR.red, colorScheme.AXIS_X_COLOR.green, colorScheme.AXIS_X_COLOR.blue, (currentOpacity * 255).toInt())
        for (i in X_START..X_END step X_INCREMENTS) {
            if (i == 0) continue

            // axis
            val tickCoords = FieldUtil.fieldCoordsToScreenCoords(Vector2d(i.toDouble() - (TICK_THICKNESS / 2), 0 + TICK_LENGTH / 2))

            gfx.fillRect(
                    tickCoords.x.toInt(),
                    tickCoords.y.toInt(),
                    FieldUtil.scaleInchesToPixel(TICK_THICKNESS).toInt(),
                    FieldUtil.scaleInchesToPixel(TICK_LENGTH).toInt()
            )

            // Draw number
            var xOffsetIn = 0.0
            var xOffsetPx = 0.0
            if (i > 0) {
                xOffsetIn = X_TEXT_POSITIVE_X_OFFSET
                xOffsetPx = -fontMetrics.stringWidth(i.toString()).toDouble() // Right align
            } else {
                xOffsetIn = X_TEXT_NEGATIVE_X_OFFSET
            }

            val textCoords = FieldUtil.fieldCoordsToScreenCoords(Vector2d(i.toDouble() + xOffsetIn, FieldUtil.scaleInchesToPixel(X_TEXT_Y_OFFSET)))

            gfx.drawString(i.toString(), (textCoords.x + xOffsetPx).toInt(), textCoords.y.toInt())
        }

        // Increase font size for the x labels
        font = font?.deriveFont((fontSize * 1.2).toFloat())
        gfx.font = font

        val textNegativeXCoords = FieldUtil.fieldCoordsToScreenCoords(Vector2d((X_START + X_LABEL_X_OFFSET / 2), X_LABEL_Y_OFFSET))
        val textPositiveXCoords = FieldUtil.fieldCoordsToScreenCoords(Vector2d((X_END - X_LABEL_X_OFFSET), X_LABEL_Y_OFFSET))

        gfx.drawString("-x", textNegativeXCoords.x.toInt(), (textNegativeXCoords.y + fontMetrics.ascent).toInt())
        gfx.drawString("x", (textPositiveXCoords.x - fontMetrics.stringWidth("x")).toInt(), (textPositiveXCoords.y + fontMetrics.ascent).toInt())

        // Reset font size
        font = font?.deriveFont(fontSize)
        gfx.font = font

        // Draw y ticks
        gfx.color = Color(colorScheme.AXIS_Y_COLOR.red, colorScheme.AXIS_Y_COLOR.green, colorScheme.AXIS_Y_COLOR.blue, (currentOpacity * 255).toInt())
        for (i in Y_START..Y_END step Y_INCREMENTS) {
            if (i == 0) continue

            val coords = FieldUtil.fieldCoordsToScreenCoords(Vector2d(0 - TICK_LENGTH / 2, i.toDouble() + (TICK_THICKNESS / 2)))

            gfx.fillRect(
                    coords.x.toInt(),
                    coords.y.toInt(),
                    FieldUtil.scaleInchesToPixel(TICK_LENGTH).toInt(),
                    FieldUtil.scaleInchesToPixel(TICK_THICKNESS).toInt()
            )

            // Draw number
            var yOffsetIn = 0.0
            var yOffsetPx = -fontMetrics.height.toDouble() / 2 // Bottom align

            if (i == Y_START) {
                yOffsetPx = 0.0
            } else if (i == Y_END) {
                yOffsetPx = -fontMetrics.height.toDouble()
            }

            val textCoords = FieldUtil.fieldCoordsToScreenCoords(Vector2d(FieldUtil.scaleInchesToPixel(Y_TEXT_X_OFFSET), i.toDouble() + yOffsetIn))

            gfx.drawString(i.toString(), textCoords.x.toInt(), (textCoords.y - yOffsetPx / 2).toInt())
        }

        // Increase font size for the x labels
        font = font?.deriveFont((fontSize * 1.2).toFloat())
        gfx.font = font

        val textNegativeYCoords = FieldUtil.fieldCoordsToScreenCoords(Vector2d(Y_LABEL_X_OFFSET, (Y_START + Y_LABEL_Y_OFFSET / 2)))
        val textPositiveYCoords = FieldUtil.fieldCoordsToScreenCoords(Vector2d(Y_LABEL_X_OFFSET, (Y_END - Y_LABEL_Y_OFFSET)))

        gfx.drawString("-y", (textNegativeYCoords.x - fontMetrics.stringWidth("-y")).toInt(), (textNegativeYCoords.y).toInt())
        gfx.drawString("y", (textPositiveYCoords.x - fontMetrics.stringWidth("y")).toInt(), (textPositiveYCoords.y).toInt())

        // Reset font size
        font = font?.deriveFont(fontSize)
        gfx.font = font
    }

    override fun setCanvasDimensions(canvasWidth: Int, canvasHeight: Int) {
        this.canvasWidth = canvasWidth
        this.canvasHeight = canvasHeight
    }

    override fun switchScheme(scheme: ColorScheme) {
        colorScheme = scheme
    }

    override fun mouseMoved(e: MouseEvent?) {
        val HOVER_TARGET_PIXELS = FieldUtil.scaleInchesToPixel(HOVER_TARGET)
        if ((e!!.x > canvasWidth / 2 - HOVER_TARGET_PIXELS / 2 && e.x < canvasWidth / 2 + HOVER_TARGET_PIXELS / 2) ||
                e.y > canvasHeight / 2 - HOVER_TARGET_PIXELS / 2 && e.y < canvasHeight / 2 + HOVER_TARGET_PIXELS / 2) {
//            currentOpacity = hoverOpacity
            animationController.anim(colorScheme.AXIS_HOVER_OPACITY, 200.0, Ease.EASE_OUT_CUBIC)
        } else {
//            currentOpacity = normalOpacity
            animationController.anim(colorScheme.AXIS_NORMAL_OPACITY, 200.0, Ease.EASE_OUT_CUBIC)
        }
    }

    override fun mouseDragged(p0: MouseEvent?) {}
}