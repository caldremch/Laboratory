package com.caldremch.annotation.compiler

import com.caldremch.annotation.EntryConst
import com.caldremch.annotation.compiler.base.BaseProcessor
import com.caldremch.annotation.entry.Entry
import com.caldremch.annotation.entry.IEntry
import com.caldremch.annotation.entry.IEntryCollection
import com.google.auto.service.AutoService
import com.squareup.javapoet.*
import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.element.Element
import javax.lang.model.element.ElementKind
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
            val clzs = roundEnvironment.getElementsAnnotatedWith(Entry::class.java)
            handleAnnotation(clzs)
            logger.info("EntryProcessor done.......")
            return true
        }

        return false
    }

    private fun handleAnnotation(entryElements: MutableSet<out Element>) {
        val typeEle = elementUtils.getTypeElement(IEntryCollection::class.java.name)
        logger.info("typeEle --> $${typeEle.toString()}")
        logger.info("enclosedElements --> $${typeEle.enclosedElements.toString()}")


        //创建参数
        val inputEntryListType = ParameterizedTypeName.get(
            ClassName.get(MutableList::class.java),
            ClassName.get(IEntry::class.java)
        )

        val entryParamSpec: ParameterSpec =
            ParameterSpec.builder(inputEntryListType, "entrys").build()

        val methodBuild =
            MethodSpec.methodBuilder(typeEle.enclosedElements[0].simpleName.toString())
                .addAnnotation(Override::class.java)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(entryParamSpec)

        println("entryElements: ${entryElements.size}")
        for (an in entryElements) {
            println("kind: ${an.kind}")
            if (an.kind == ElementKind.CLASS) {
                val typeElement = an as TypeElement
                methodBuild.addStatement("entrys.add(new \$T())", typeElement)
            }
        }

        //创建类
        try {
            JavaFile.builder(
                EntryConst.ENTRY_PKG, TypeSpec.classBuilder(EntryConst.ENTRY_NAME)
                    .addSuperinterface(IEntryCollection::class.java)
                    .addModifiers(Modifier.PUBLIC)
                    .addMethod(methodBuild.build())
                    .build()
            ).build().writeTo(filter)
        }catch (e:Exception){
            logger.error(e)
        }
    }

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        val types = mutableSetOf<String>()
        types.add(Entry::class.java.name)
        return types
    }
}