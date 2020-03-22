package com.noahbres.meepmeep.core.entity

import com.noahbres.meepmeep.core.MeepMeep
import java.awt.Graphics2D

interface Entity {
    val zIndex: Int

    val meepMeep: MeepMeep<*>

    fun update(deltaTime: Long)
    fun render(gfx: Graphics2D, canvasWidth: Int, canvasHeight: Int)

    fun setCanvasDimensions(canvasWidth: Double, canvasHeight: Double)
}