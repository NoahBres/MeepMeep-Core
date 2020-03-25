package com.noahbres.meepmeep.core.ui

import java.awt.BorderLayout
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import javax.swing.JFrame
import kotlin.system.exitProcess

class WindowFrame(title: String, windowSize: Int) : JFrame() {
    var internalWidth = windowSize
    var internalHeight = windowSize

    val canvas = MainCanvas(internalWidth, internalHeight)

    init {
        setTitle(title)

        defaultCloseOperation = DO_NOTHING_ON_CLOSE
        addWindowListener(object : WindowAdapter() {
            override fun windowClosing(we: WindowEvent?) {
                super.windowClosing(we)

                dispose()
                exitProcess(0)
            }
        })

        setSize(internalWidth, internalHeight)
        setLocationRelativeTo(null)

        isResizable = false

        layout = BorderLayout()
        contentPane.add(canvas, BorderLayout.CENTER)
//        contentPane.add(JButton("test"))
        pack()

        canvas.start()
    }
}