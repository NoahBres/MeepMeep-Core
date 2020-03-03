package com.noahbres.meepmeep

import com.noahbres.meepmeep.ui.WindowFrame
import javax.swing.UIManager

class MeepMeep {
    val windowFrame = WindowFrame("Meep Meep")

    init {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
    }

    fun start() {
        windowFrame.isVisible = true
    }
}