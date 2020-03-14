package com.noahbres.meepmeep.entity

import com.noahbres.meepmeep.colorscheme.ColorScheme

interface ThemedEntity: Entity {
    fun switchScheme(scheme: ColorScheme)
}