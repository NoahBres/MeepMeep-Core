package com.noahbres.meepmeep.gfx

import com.acmerobotics.roadrunner.geometry.Pose2d
import java.awt.Graphics2D

interface DrawCurrentBot {
    fun draw(gfx: Graphics2D, pose: Pose2d)
}