package com.noahbres.meepmeep.colorscheme

import java.awt.Color

abstract class ColorScheme {
    abstract val BOT_BODY_COLOR: Color
    abstract val BOT_WHEEL_COLOR: Color
    abstract val BOT_DIRECTION_COLOR: Color

    abstract val AXIS_X_COLOR: Color
    abstract val AXIS_Y_COLOR: Color
    abstract val AXIS_NORMAL_OPACITY: Double
    abstract val AXIS_HOVER_OPACITY: Double
}