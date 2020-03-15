package com.noahbres.meepmeep.colorscheme

import com.noahbres.meepmeep.colorscheme.scheme.ColorSchemeRedDark
import com.noahbres.meepmeep.colorscheme.scheme.ColorSchemeRedLight

class ColorManager {
    companion object {
        @JvmField val COLOR_PALETTE = ColorPalette.DEFAULT_PALETTE

        @JvmField val DEFAULT_THEME_LIGHT: ColorScheme = ColorSchemeRedLight()
        @JvmField val DEFAULT_THEME_DARK: ColorScheme = ColorSchemeRedDark()
    }

    var isDarkMode = false

    private var lightTheme: ColorScheme = DEFAULT_THEME_LIGHT
    private var darkTheme: ColorScheme = DEFAULT_THEME_DARK

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