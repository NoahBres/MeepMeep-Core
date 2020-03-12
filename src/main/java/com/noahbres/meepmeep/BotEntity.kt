package com.noahbres.meepmeep

import com.acmerobotics.roadrunner.geometry.Pose2d
import java.awt.Color
import java.awt.Graphics2D

class BotEntity(val width: Double, val height: Double, val pose2d: Pose2d, val color: Color, val opacity: Double) : Entity {
    val pose = Pose2d()

    override fun update(deltaTime: Long) {

    }

    override fun render(gfx: Graphics2D, canvasWidth: Double, canvasHeight: Double) {

    }
}
