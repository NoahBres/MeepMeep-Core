package com.noahbres.meepmeep.ui

import java.awt.FlowLayout
import javax.swing.JFrame

class WindowFrame(title: String, windowSize: Int): JFrame() {
    var internalWidth = windowSize
    var internalHeight = windowSize

    val canvas = MainCanvas(internalWidth, internalHeight)

    init {
        setTitle(title)

        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        setSize(internalWidth, internalHeight)
        setLocationRelativeTo(null)

        isResizable = false

        layout = FlowLayout(0,0,0)
        contentPane.add(canvas)
//        contentPane.add(JButton("test"))
        pack()

        canvas.start()
    }
}