package com.noahbres.meepmeep.util

import java.awt.Graphics2D

class LoopManager(targetFPS: Long, val updateFunction: (deltaTime: Long) -> Unit, val renderFunction: () -> Unit): Runnable {
    private val targetDeltaLoop = (1000 * 1000 * 1000) / targetFPS // Nanoseconds / fps

    private var running = true

    override fun run() {
        var beginLoopTime = 0L
        var endLoopTime = 0L
        var currentUpdateTime = System.nanoTime()
        var lastUpdateTime = 0L
        var deltaLoop = 0L

        while(running) {
            beginLoopTime = System.nanoTime()

            render()

            lastUpdateTime = currentUpdateTime
            currentUpdateTime = System.nanoTime()
            update((currentUpdateTime - lastUpdateTime) / (1000 * 1000))

            endLoopTime = System.nanoTime()
            deltaLoop = endLoopTime - beginLoopTime

            if(deltaLoop > targetDeltaLoop) {

            } else {
                Thread.sleep((targetDeltaLoop - deltaLoop) / (1000 * 1000))
            }

            render()
        }
    }

    fun render() {
        renderFunction()
    }

    fun update(deltaTime: Long) {
        updateFunction(deltaTime)
    }
}