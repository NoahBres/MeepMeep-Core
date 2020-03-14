package com.noahbres.meepmeep.anim

class AnimationController(val value: Double) {
    private var isAnimating = false

    private var currentStartTime = 0.0
    private var currentEndTime = 0.0
    private var currentTarget = 0.0
    private var currentTargetDelta = 0.0
    private var currentEase = Ease.LINEAR

    fun update() {
        if(!isAnimating) return

        val totalTime = currentEndTime - currentStartTime
        val elapsedTime = System.currentTimeMillis() - currentStartTime
        val progress = currentEase(elapsedTime / totalTime)


    }

    fun anim(target: Double, time: Double, ease: (t: Double) -> Double) {
        isAnimating = true

        currentTargetDelta = target - value
        currentTarget = target

        currentStartTime = System.currentTimeMillis().toDouble()
        currentEndTime = currentStartTime + time

        currentEase = ease
    }
}