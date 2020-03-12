package com.noahbres.meepmeep.util

class FieldUtil {
    companion object {
        @JvmStatic
        var FIELD_WIDTH = 144

        @JvmStatic
        var FIELD_HEIGHT = 144

        @JvmStatic
        var CENTER_X = 0

        @JvmStatic
        var CENTER_Y = 0

        @JvmStatic
        var FLIP_X_Y_AXIS = true

        @JvmStatic
        var FLIP_SIGN_X_AXIS = false

        @JvmStatic
        var FLIP_SIGN_Y_AXIS = true

        @JvmStatic
        fun screenCoordsToFieldCoords(screenX: Double, screenY: Double, canvasWidth: Double, canvasHeight: Double): Array<Double> {
            // Assume that 0,0 is top left for java graphics coordinate system
            // Convert x/y to percentage
            val percentageX = screenX / canvasWidth
            val percentageY = screenY / canvasHeight

            // Figure out top left for field coordinates
            val topLeftX = (CENTER_X - FIELD_WIDTH) / 2
            val topLeftY = (CENTER_Y - FIELD_HEIGHT) / 2

            val fieldX = ((percentageX * FIELD_WIDTH) + topLeftX) * (if (FLIP_SIGN_X_AXIS) 1 else -1)
            val fieldY = ((percentageY * FIELD_HEIGHT) + topLeftY) * (if (FLIP_SIGN_Y_AXIS) -1 else 1)

            return if(FLIP_X_Y_AXIS) arrayOf(fieldY, fieldX) else arrayOf(fieldX, fieldY)
        }

        @JvmStatic
        fun fieldCoordsToScreenCoords(fieldX: Double, fieldY: Double, canvasWidth: Double, canvasHeight: Double): Array<Double> {
            var convertedX = fieldX * (if (FLIP_SIGN_X_AXIS) -1 else 1)
            var convertedY = fieldY * (if (FLIP_SIGN_Y_AXIS) -1 else 1)

            if(FLIP_X_Y_AXIS) {
                convertedX = fieldY * (if (FLIP_SIGN_Y_AXIS) -1 else 1)
                convertedY = fieldX * (if (FLIP_SIGN_X_AXIS) 1 else -1)
            }

            if(FLIP_SIGN_X_AXIS) convertedX *= -1
            if(!FLIP_SIGN_Y_AXIS) convertedY *= -1

            // Figure out top left for field coordinates
            val topLeftX = (CENTER_X - FIELD_WIDTH) / 2
            val topLeftY = (CENTER_Y - FIELD_HEIGHT) / 2

            return arrayOf(0.0, 0.0)
        }
    }
}