package com.noahbres.meepmeep.colorscheme.scheme

import com.noahbres.meepmeep.colorscheme.ColorManager
import com.noahbres.meepmeep.colorscheme.ColorScheme
import java.awt.Color

class ColorSchemeBlue: ColorScheme() {
    override val BOT_BODY_COLOR = ColorManager.COLOR_PALETTE.BLUE_600
    override val BOT_WHEEL_COLOR = ColorManager.COLOR_PALETTE.BLUE_800
    override val BOT_DIRECTION_COLOR = ColorManager.COLOR_PALETTE.BLUE_800
    override val AXIS_X_COLOR: Color = ColorManager.COLOR_PALETTE.GRAY_900
    override val AXIS_Y_COLOR: Color = ColorManager.COLOR_PALETTE.GRAY_900

}