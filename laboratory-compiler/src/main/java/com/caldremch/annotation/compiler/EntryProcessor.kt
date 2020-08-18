package com.caldremch.annotation.compiler

import com.caldremch.annotation.compiler.base.BaseProcessor
import com.caldremch.annotation.entry.Entry
import com.google.auto.service.AutoService
import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.TypeSpec
import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.element.Modifier
import javax.lang.model.element.TypeElement

/**
 *
 * @author Caldremch
 *
 * @date 2020-08-17
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
@AutoService(Processor::class)
class EntryProcessor : BaseProcessor() {
    override fun process(
        set: MutableSet<out TypeElement>?,
        roundEnvironment: RoundEnvironment
    ): Boolean {

        if (set != null && set.isNotEmpty()) {
            logger.info("EntryProcessor start.......")
            logger.info("EntryProcessor annotation size: ${set.size}")
            val typeSpec = TypeSpec.classBuilder("A")
                .addModifiers(Modifier.PUBLIC)
                .build()
            val file = JavaFile.builder("com.caldremch", typeSpec).build()
            file.writeTo(filter)
            logger.info("EntryProcessor done.......")
            return true
        }

        return false
    }

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        val types = mutableSetOf<String>()
        types.add(Entry::class.java.name)
        return types
    }
}