package com.noahbres.meepmeep.util

import com.acmerobotics.roadrunner.geometry.Vector2d
import kotlin.math.max
import kotlin.math.min

class FieldUtil {
    companion object {
        @JvmStatic
        var FIELD_WIDTH = 144

        @JvmStatic
        var FIELD_HEIGHT = 144

        @JvmStatic
        fun screenCoordsToFieldCoords(vector2d: Vector2d, canvasWidth: Double, canvasHeight: Double): Vector2d {
            return mirrorY(vector2d) - Vector2d(FIELD_WIDTH / 2.0, FIELD_HEIGHT / 2.0) / max(canvasWidth, canvasHeight) / FIELD_WIDTH.toDouble()
        }

        @JvmStatic
        fun fieldCoordsToScreenCoords(vector2d: Vector2d, canvasWidth: Double, canvasHeight: Double): Vector2d {
           return (mirrorY(vector2d) + Vector2d(FIELD_WIDTH / 2.0, FIELD_HEIGHT / 2.0)) * min(canvasWidth, canvasHeight) / FIELD_WIDTH.toDouble()
        }

        @JvmStatic
        fun scaleInchesToPixel(inches: Double, canvasWidth: Double, canvasHeight: Double): Double {
            return inches / min(FIELD_WIDTH.toDouble(), FIELD_HEIGHT.toDouble()) * min(canvasWidth, canvasHeight)
        }

        // Mirror x
        private fun mirrorX(vector: Vector2d): Vector2d {
            return Vector2d(-vector.x, vector.y)
        }

        // Mirror y
        private fun mirrorY(vector: Vector2d): Vector2d {
            return Vector2d(vector.x, -vector.y)
        }
    }
}