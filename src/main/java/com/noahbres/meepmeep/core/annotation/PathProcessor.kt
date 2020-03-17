package com.noahbres.meepmeep.core.annotation

import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.RoundEnvironment
import javax.annotation.processing.SupportedAnnotationTypes
import javax.lang.model.element.TypeElement

@SupportedAnnotationTypes("com.noahbres.meepmeepcore.annotation.ValidPath")
class PathProcessor : AbstractProcessor() {
    override fun process(annotations: MutableSet<out TypeElement>?, roundEnv: RoundEnvironment?): Boolean {
        return false
    }

}