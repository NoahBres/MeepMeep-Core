package com.noahbres.meepmeep.colorscheme.scheme

import com.noahbres.meepmeep.colorscheme.ColorManager
import com.noahbres.meepmeep.colorscheme.ColorScheme
import java.awt.Color

open class ColorSchemeRedDark : ColorSchemeRedLight() {
    override val AXIS_X_COLOR: Color = ColorManager.COLOR_PALETTE.GRAY_300
    override val AXIS_Y_COLOR: Color = ColorManager.COLOR_PALETTE.GRAY_300

    override val AXIS_NORMAL_OPACITY: Double = 0.2
}