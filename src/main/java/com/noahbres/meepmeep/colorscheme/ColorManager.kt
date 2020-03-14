package com.noahbres.meepmeep.colorscheme

import com.noahbres.meepmeep.colorscheme.scheme.ColorSchemeRed

class ColorManager {
    companion object {
        @JvmField val COLOR_PALETTE = ColorPalette.DEFAULT_PALETTE

        @JvmField val DEFAULT_THEME: ColorScheme = ColorSchemeRed()
    }

    var isDarkMode = false

    private var lightTheme: ColorScheme = DEFAULT_THEME
    private var darkTheme: ColorScheme = DEFAULT_THEME

    val theme: ColorScheme
        get() {
            return if(!isDarkMode) lightTheme else darkTheme
        }

    @JvmOverloads
    fun setTheme(themeLight: ColorScheme, themeDark: ColorScheme = themeLight) {
        lightTheme = themeLight
        darkTheme = themeDark
    }
}