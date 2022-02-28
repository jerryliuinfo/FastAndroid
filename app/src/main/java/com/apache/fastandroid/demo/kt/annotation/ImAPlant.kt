package com.apache.fastandroid.demo.kt.annotation

/**
 * Created by Jerry on 2022/2/27.
 */
annotation class ImAPlant()

@Target(AnnotationTarget.PROPERTY_GETTER)
annotation class OnGet


@Target(AnnotationTarget.PROPERTY_SETTER)
annotation class OnSet