package com.noahbres.meepmeep.ui

import java.awt.Canvas
import java.awt.Color
import java.awt.Font
import java.awt.Graphics

class MainCanvas: Canvas() {
    init {
        background = Color.black
    }

    override fun paint(g: Graphics) {
        super.paint(g)

        g.color = Color.RED

        g.font = Font("Bold", 1, 20)

        g.drawString("end me", 100, 100)
    }
}