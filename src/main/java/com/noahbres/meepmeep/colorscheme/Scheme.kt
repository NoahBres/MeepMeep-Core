package com.noahbres.meepmeep.colorscheme

import java.awt.Color

data class Scheme(
        // Duplicate colors for light mode/dark mode
        val botMovingColorDark: Color,
        val botMovingColorLight: Color,

        val botEndpointColorDark: Color,
        val botEndpointColorLight: Color
)