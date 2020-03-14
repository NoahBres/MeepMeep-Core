package com.noahbres.meepmeep.util

class LoopManager(targetFPS: Long, val updateFunction: (deltaTime: Long) -> Unit, val renderFunction: () -> Unit): Runnable {
    private val targetDeltaLoop = (1000 * 1000 * 1000) / targetFPS // Nanoseconds / fps

    private var running = true

    var fps = 0.0
    private var fpsCounter = 0
    private var fpsCounterTime = 1000
    override fun run() {
        var beginLoopTime = 0L
        var endLoopTime = 0L
        var currentUpdateTime = System.nanoTime()
        var lastUpdateTime = 0L
        var deltaLoop = 0L

        var startFpsTime = System.currentTimeMillis()

        while(running) {
            beginLoopTime = System.nanoTime()

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

            fpsCounter++
            if(System.currentTimeMillis() - startFpsTime > fpsCounterTime) {
                fps = (fpsCounter.toDouble() / ((System.currentTimeMillis() - startFpsTime) / 1000))
                fpsCounter = 0
                startFpsTime = System.currentTimeMillis()
            }
        }
    }

    fun render() {
        renderFunction()
    }

    fun update(deltaTime: Long) {
        updateFunction(deltaTime)
    }
}