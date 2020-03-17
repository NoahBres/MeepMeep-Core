package com.noahbres.meepmeep.core.entity

import java.awt.Graphics2D

interface Entity {
    val zIndex: Int

    fun update(deltaTime: Long)
    fun render(gfx: Graphics2D, canvasWidth: Int, canvasHeight: Int)

    fun setCanvasDimensions(canvasWidth: Int, canvasHeight: Int)
}