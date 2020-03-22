package com.noahbres.meepmeep.core.colorscheme.scheme

import com.noahbres.meepmeep.core.colorscheme.ColorManager
import java.awt.Color

open class ColorSchemeRedDark : ColorSchemeRedLight() {
    override val AXIS_X_COLOR: Color = ColorManager.COLOR_PALETTE.GRAY_300
    override val AXIS_Y_COLOR: Color = ColorManager.COLOR_PALETTE.GRAY_300

    override val AXIS_NORMAL_OPACITY: Double = 0.2
}