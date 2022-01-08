
plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-android-extensions")
}
android {
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }


    buildFeatures {
        buildConfig = false
    }


}

dependencies {

    implementation(Dependencies.appCompat)
    implementation(Dependencies.core_ktx)
    implementation(Dependencies.constraintlayout)
    implementation(Dependencies.googleMaterial)

    // implementation rootProject.androidLib.appcompatV7
    // implementation rootProject.androidLib.core_ktx
    // implementation rootProject.androidLib.constraintLayout
    // implementation rootProject.androidLib.googleMaterial

    implementation(project(":baselib"))
    implementation(project(":fastFramework"))

    // implementation project(':baselib')
    // implementation project(':fastFramework')



}
