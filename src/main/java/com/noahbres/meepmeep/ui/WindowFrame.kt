package com.noahbres.meepmeep.ui

import javax.swing.JFrame

class WindowFrame(title: String): JFrame() {
    init {
        setTitle(title)

        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        setSize(600, 600)
        setLocationRelativeTo(null)

        add(MainCanvas())
    }
}