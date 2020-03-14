package com.noahbres.meepmeep.entity

import com.acmerobotics.roadrunner.geometry.Pose2d
import com.acmerobotics.roadrunner.geometry.Vector2d
import com.noahbres.meepmeep.colorscheme.ColorScheme
import com.noahbres.meepmeep.util.FieldUtil
import java.awt.Color
import java.awt.Graphics2D
import java.awt.GraphicsEnvironment
import java.awt.Transparency
import java.awt.geom.AffineTransform
import java.awt.image.BufferedImage

class BotEntity(
        private var width: Double,
        private var height: Double,

        private var pose: Pose2d,
        private var colorScheme: ColorScheme,
        private val opacity: Double,

        private var canvasWidth: Int,
        private var canvasHeight: Int
) : ThemedEntity {
    override val zIndex = 2

    private val WHEEL_WIDTH = 0.2
    private val WHEEL_HEIGHT = 0.3

    private val WHEEL_PADDING_X = 0.05
    private val WHEEL_PADDING_Y = 0.05

    private val DIRECTION_LINE_WIDTH = 0.05
    private val DIRECTION_LINE_HEIGHT = 0.4

    private lateinit var baseBufferedImage: BufferedImage

    init {
        redrawBot()
    }

    override fun update(deltaTime: Long) {

        pose = Pose2d(pose.x, pose.y, pose.heading + Math.toRadians(0.1 * deltaTime))
    }

    override fun render(gfx: Graphics2D, canvasWidth: Int, canvasHeight: Int) {
        val coords = FieldUtil.fieldCoordsToScreenCoords(Vector2d(pose.x, pose.y), canvasWidth.toDouble(), canvasHeight.toDouble())

        val transform = AffineTransform()
        transform.translate(coords.x, coords.y)
        transform.rotate(pose.heading)
        transform.translate(
                FieldUtil.scaleInchesToPixel(-width / 2, canvasWidth.toDouble(), canvasHeight.toDouble()),
                FieldUtil.scaleInchesToPixel(-height / 2, canvasWidth.toDouble(), canvasHeight.toDouble())
        )
        transform.scale(
                FieldUtil.scaleInchesToPixel(width, canvasWidth.toDouble(), canvasHeight.toDouble()) / canvasWidth,
                FieldUtil.scaleInchesToPixel(height, canvasWidth.toDouble(), canvasHeight.toDouble()) / canvasHeight
        )

        gfx.drawImage(baseBufferedImage, transform, null)
    }

    private fun redrawBot() {
        val environment = GraphicsEnvironment.getLocalGraphicsEnvironment()
        val device = environment.defaultScreenDevice
        val config = device.defaultConfiguration

        baseBufferedImage = config.createCompatibleImage(canvasWidth.toInt(), canvasHeight.toInt(), Transparency.TRANSLUCENT)

        val gfx = baseBufferedImage.createGraphics()

        val colorAlphaBody = Color(colorScheme.BOT_BODY_COLOR.red, colorScheme.BOT_BODY_COLOR.green, colorScheme.BOT_BODY_COLOR.blue, (opacity * 255).toInt())
        gfx.color = colorAlphaBody
        gfx.fillRect(0, 0, canvasWidth.toInt(), canvasHeight.toInt())

        val colorAlphaWheel = Color(colorScheme.BOT_WHEEL_COLOR.red, colorScheme.BOT_WHEEL_COLOR.green, colorScheme.BOT_BODY_COLOR.blue, (opacity * 255).toInt())
        gfx.color = colorAlphaWheel
        gfx.fillRect((WHEEL_PADDING_X * canvasWidth).toInt(), (WHEEL_PADDING_Y * canvasHeight).toInt(), (WHEEL_WIDTH * canvasWidth).toInt(), (WHEEL_HEIGHT * canvasHeight).toInt())
        gfx.fillRect((canvasWidth - WHEEL_WIDTH * canvasWidth - WHEEL_PADDING_X * canvasWidth).toInt(), (WHEEL_PADDING_Y * canvasHeight).toInt(), (WHEEL_WIDTH * canvasWidth).toInt(), (WHEEL_HEIGHT * canvasHeight).toInt())
        gfx.fillRect((canvasWidth - WHEEL_WIDTH * canvasWidth - WHEEL_PADDING_X * canvasWidth).toInt(), (canvasHeight - WHEEL_HEIGHT * canvasHeight - WHEEL_PADDING_Y * canvasHeight).toInt(), (WHEEL_WIDTH * canvasWidth).toInt(), (WHEEL_HEIGHT * canvasHeight).toInt())
        gfx.fillRect((WHEEL_PADDING_X * canvasWidth).toInt(), (canvasHeight - WHEEL_HEIGHT * canvasHeight - WHEEL_PADDING_Y * canvasHeight).toInt(), (WHEEL_WIDTH * canvasWidth).toInt(), (WHEEL_HEIGHT * canvasHeight).toInt())

        val colorAlphaDirection = Color(colorScheme.BOT_DIRECTION_COLOR.red, colorScheme.BOT_DIRECTION_COLOR.green, colorScheme.BOT_DIRECTION_COLOR.blue, (opacity * 255).toInt())
        gfx.color = colorAlphaDirection
        gfx.fillRect((canvasWidth / 2 - DIRECTION_LINE_WIDTH * canvasWidth / 2).toInt(), 0, (DIRECTION_LINE_WIDTH * canvasWidth).toInt(), (canvasHeight * DIRECTION_LINE_HEIGHT).toInt())
    }

    override fun switchScheme(scheme: ColorScheme) {
        colorScheme = scheme
        redrawBot()
    }

    fun setDimensions(width: Double, height: Double): BotEntity {
        this.width = width
        this.height = height
        redrawBot()

        return this
    }

    override fun setCanvasDimensions(canvasWidth: Int, canvasHeight: Int) {
        this.canvasWidth = canvasWidth
        this.canvasHeight = canvasHeight
    }
}
