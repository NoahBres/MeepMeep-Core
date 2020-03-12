package com.noahbres.meepmeep

import java.awt.Graphics2D

interface Entity {
    fun update(deltaTime: Long)
    fun render(gfx: Graphics2D, canvasWidth: Double, canvasHeight: Double)
}