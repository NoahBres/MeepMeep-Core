package com.noahbres.meepmeep.colorscheme

import java.awt.Color

class ColorManager {
    companion object {
        @JvmField val DEFAULT_THEME = Scheme(Color.RED, Color.RED, Color.RED, Color.RED)
        @JvmField val GF_THEME = Scheme(Color.RED, Color.RED, Color.RED, Color.RED)
    }

    var isDarkMode = false
    var theme = DEFAULT_THEME
}