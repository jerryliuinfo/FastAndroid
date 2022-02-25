

object Versions{
    const val appCompat = "1.1.0"
    const val material = "1.4.0-alpha01"
    const val constraintlayout = "2.0.4"
    const val cardview = "1.0.0"
    const val recyclerview = "1.1.0"
    const val core_ktx = "1.6.0"
    const val activity_ktx = "1.3.1"
    const val fragment_ktx = "1.3.6"
    const val palette_ktx = "1.0.0"
    const val annotation = "1.0.0"
    const val kotlin_stdlib = "1.5.20"
    const val navigationVersion = "2.3.5"
    const val lifecycle_version = "2.3.1"
    const val Room = "2.2.6"
    const val coroutines = "1.3.5"
    const val workmanager = "2.2.0"
    const val multidex = "2.0.0"
    const val gson = "2.8.2"
    const val fastjson = "1.2.29"
    const val glide = "3.7.0"
    const val qmui = "2.0.0-alpha10"
    const val rxjava = "3.0.0"
    const val immersionbar = "3.0.0"
    const val bugly = "3.2.1"
    const val brvh = "2.9.30"

}



object Dependencies {


    const val appCompat = "androidx.appcompat:appcompat:1.1.0"
    const val material = "com.google.android.material:material:1.4.0"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.0.4"

    const val restring = "dev.b3nedikt.restring:restring:5.1.4"
    const val viewPump = "dev.b3nedikt.viewpump:viewpump:4.0.10"
    const val reword = "dev.b3nedikt.reword:reword:4.0.1"
//
//    appcompatV7                      : 'androidx.appcompat:appcompat:1.1.0',
//    googleMaterial                   : 'com.google.android.material:material:1.4.0-alpha01',
//
//    constraintLayout                 : 'androidx.constraintlayout:constraintlayout:2.0.4',
//    cardview                         : 'androidx.cardview:cardview:1.0.0',
//    recyclerview                     : 'androidx.recyclerview:recyclerview:1.1.0',
//    // Android KTX 是包含在 Android Jetpack 及其他 Android 库中的一组 Kotlin 扩展程序。KTX 扩展程序可以为 Jetpack、Android 平台及其他 API 提供简洁的惯用 Kotlin 代码。
//    core_ktx                         : "androidx.core:core-ktx:1.6.0",
//    androidx_activity_ktx            : "androidx.activity:activity-ktx:1.3.1",
//    androidx_fragment_ktx            : "androidx.fragment:fragment-ktx:1.3.6",
//
//
//
//    palette                          : "androidx.palette:palette-ktx:1.0.0",
//
//    androidx_annotaion               : "androidx.annotaion:annotation:1.0.0",
//
//
//    //Kotlin
//    kotlin_stdlib                    : "org.jetbrains.kotlin:kotlin-stdlib:1.3.72",
//    kotlin_reflect                   : "org.jetbrains.kotlin:kotlin-reflect:1.3.72",
//
//
//
//
//
//    navigation_fragment_ktx          : "androidx.navigation:navigation-fragment-ktx:$navigationVersion",
//    navigation_ui_ktx                : "androidx.navigation:navigation-ui-ktx:$navigationVersion",
//
//
//    androidx_lifecycle_common        : "androidx.lifecycle:lifecycle-common-java8:$lifecycle_version",
//    androidx_lifecycle_runtime       : "androidx.lifecycle:lifecycle-runtime:$lifecycle_version",
//
//    // ViewModel的KTX扩展库，可以让我们在ViewModel中方便的使用Coroutine
//    androidx_lifecycle_viewmodel_ktx : "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version",
//    // LiveData的KTX扩展库，
//    androidx_lifecycle_livedata      : "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version",
//
//    //Room
//    room_runtime                     : "androidx.room:room-runtime:$roomVersion",
//    room_ktx                         : "androidx.room:room-ktx:$roomVersion",
//    room_compiler                    : "androidx.room:room-compiler:$roomVersion",
//
//
//    //coroutines
//    kotlinx_coroutines_core          : "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.5",
//    kotlinx_coroutines_android       : "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.5",
//
//    // optional - helpers for implementing LifecycleOwner in a Service
//    androidx_lifecycle_service       :"androidx.lifecycle:lifecycle-service:$lifecycle_version",
//
//    androidx_workmanager             : "androidx.work:work-runtime:2.2.0",
//
//    //分包
//    multidex                         :  "androidx.multidex:multidex:2.0.0",


//    implementation ThirdLib.bugly
//    implementation ThirdLib.qmui
//    implementation ThirdLib.brvh
//    implementation ThirdLib.mmkv
//    implementation ThirdLib.AnyLayer
//    implementation ThirdLib.EasyTrack
//    implementation ThirdLib.PermissionMonitor
//    implementation ThirdLib.weishu
//    implementation ThirdLib.flexible_divider
//    implementation ThirdLib.immersionbar
//    implementation ThirdLib.immersionbar_ktx
//    implementation ThirdLib.immersionbar_components

    val SHARE_IMPLS = mutableListOf(
//        "androidx.annotaion:annotation:1.0.0",
        "androidx.appcompat:appcompat:${Versions.appCompat}",
        "com.google.android.material:material:${Versions.material}",
        "androidx.constraintlayout:constraintlayout:${Versions.constraintlayout}",
        "androidx.cardview:cardview:${Versions.cardview}",
        "androidx.recyclerview:recyclerview:${Versions.recyclerview}",
        "androidx.palette:palette-ktx:1.0.0",

        "androidx.core:core-ktx:${Versions.core_ktx}",
        "androidx.activity:activity-ktx:${Versions.activity_ktx}",
        "androidx.fragment:fragment-ktx:${Versions.fragment_ktx}",
        "androidx.palette:palette-ktx:${Versions.palette_ktx}",


        "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin_stdlib}",


        "androidx.navigation:navigation-fragment-ktx:${Versions.navigationVersion}",
        "androidx.navigation:navigation-ui-ktx:${Versions.navigationVersion}",


        "androidx.lifecycle:lifecycle-common-java8:${Versions.lifecycle_version}",
        "androidx.lifecycle:lifecycle-runtime:${Versions.lifecycle_version}",
        //ViewModel的KTX扩展库，可以让我们在ViewModel中方便的使用Coroutine
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle_version}",
        //LiveData的KTX扩展库
        "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle_version}",
        //Service 的KTX扩展库
        "androidx.lifecycle:lifecycle-service:${Versions.lifecycle_version}",

        //ROM
        "androidx.room:room-runtime:${Versions.Room}",
        "androidx.room:room-ktx:${Versions.Room}",
        "androidx.room:room-compiler:${Versions.Room}",


        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}",
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}",



        "com.google.code.gson:gson:${Versions.gson}",
//        "com.alibaba:fastjson:${Versions.fastjson}",
        "com.github.bumptech.glide:glide:${Versions.glide}",

        "io.reactivex.rxjava3:rxjava:${Versions.rxjava}",
        "io.reactivex.rxjava3:rxandroid:${Versions.rxjava}",

        "com.blankj:utilcodex:1.31.0",
        "com.github.CymChad:BaseRecyclerViewAdapterHelper:${Versions.brvh}",
        "com.gyf.immersionbar:immersionbar:${Versions.immersionbar}",
        "com.gyf.immersionbar:immersionbar-components:${Versions.immersionbar}",
        "com.gyf.immersionbar:immersionbar-ktx:${Versions.immersionbar}",
        "com.google.guava:listenablefuture:9999.0-empty-to-avoid-conflict-with-guava"
        )

}


object ThirdLib{
    const val restring = "dev.b3nedikt.restring:restring:5.1.4"
    const val viewPump = "dev.b3nedikt.viewpump:viewpump:4.0.10"
    const val reword = "dev.b3nedikt.reword:reword:4.0.1"
    const val brvh = "com.github.CymChad:BaseRecyclerViewAdapterHelper:2.9.30"
    const val mmkv = "com.tencent:mmkv-static:1.2.10"
    const val bugly = "com.tencent.bugly:crashreport:${Versions.bugly}"
    const val qmui = "com.qmuiteam:qmui:2.0.0-alpha10"
    const val utilcodex = "com.blankj:utilcodex:1.31.0"
    const val AnyLayer = "com.github.goweii:AnyLayer:4.1.4-androidx"
    const val EasyTrack = "com.github.pengxurui:EasyTrack:v1.0.1"
    const val PermissionMonitor = "com.github.huage2580:PermissionMonitor:1.0.1"
    const val weishu = "me.weishu:epic:1.0.0"
    const val flexible_divider = "com.yqritc:recyclerview-flexibledivider:1.4.0"

    const val immersionbar = "com.gyf.immersionbar:immersionbar:${Versions.immersionbar}"
    const val immersionbar_components = "com.gyf.immersionbar:immersionbar-components:${Versions.immersionbar}"
    const val immersionbar_ktx = "com.gyf.immersionbar:immersionbar-ktx:${Versions.immersionbar}"
}

object BuildPluginsVersion {
    const val AGP = "7.1.1"
    const val KOTLIN = "1.6.10"
    const val KOTLINTER = "3.6.0"
    const val VERSIONS_PLUGIN = "0.42.0"
    const val ABOUTLIB_PLUGIN = "8.9.4"
}


object Test {
    const val junit = "junit:junit:4.13.2"
    const val kluent = "org.amshove.kluent:kluent-android:1.68"
    const val robolectric = "org.robolectric:robolectric:4.7.3"
    const val mockitoKotlin = "org.mockito.kotlin:mockito-kotlin:4.0.0"
    const val mockitoInline = "org.mockito:mockito-inline:4.2.0"
    const val testCore = "androidx.test:core:1.4.0"
}


