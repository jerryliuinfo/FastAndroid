package com.hencoder.plugin

import com.android.build.gradle.BaseExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class CustomGradlePlugin implements Plugin<Project> {
  @Override
  void apply(Project target) {
    def extension = target.extensions.create('releaseInfo', ReleaseInfo)
    target.afterEvaluate {
      println "PluginHi ${extension.name}!"
    }
    def transform = new HenCoderTransform()
    def baseExtension = target.extensions.getByType(BaseExtension)
    baseExtension.registerTransform(transform)
  }
}