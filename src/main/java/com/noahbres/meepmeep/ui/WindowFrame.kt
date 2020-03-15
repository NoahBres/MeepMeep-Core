package com.noahbres.meepmeep.ui

import java.awt.FlowLayout
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import java.awt.event.WindowListener
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

        layout = FlowLayout(0, 0, 0)
        contentPane.add(canvas)
//        contentPane.add(JButton("test"))
        pack()

        canvas.start()
    }
}