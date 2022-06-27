



/**
 * AndroidX相关依赖
 *
 * @author zwb
 * @since 2/27/22
 */
object AndroidX {
    const val core_ktx = "androidx.core:core-ktx:${Versions.core_ktx}"
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val recyclerview = "androidx.recyclerview:recyclerview:1.2.0-alpha02"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintlayout}"
    const val swiperefreshLayout = "androidx.swiperefreshlayout:swiperefreshlayout:1.0.0"

    const val activity_ktx = "androidx.activity:activity-ktx:${Versions.activity_ktx}"
    const val fragment_ktx = "androidx.fragment:fragment-ktx:${Versions.fragment_ktx}"
    const val cardview = "androidx.cardview:cardview:${Versions.cardview}"
    const val palette = "androidx.palette:palette-ktx:1.0.0"
    const val preference = "androidx.preference:preference:${Versions.preference}"
}

object Libs {


//    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val gson = "com.google.code.gson:gson:${Versions.gson}"
    const val utilcode = "com.blankj:utilcodex:1.31.0"
    const val brvh = "com.github.CymChad:BaseRecyclerViewAdapterHelper:${Versions.brvh}"
    const val mmkv = "com.tencent:mmkv-static:1.2.10"
    const val anyLayer = "com.github.goweii:AnyLayer:4.1.4-androidx"
    const val easyTrack = "com.github.pengxurui:EasyTrack:v1.0.1"
    const val permissionMonitor = "com.github.huage2580:PermissionMonitor:1.0.1"
    const val weishu = "me.weishu:epic:1.0.0"
    const val sdk_editor = "com.github.iwhys:sdk-editor-plugin:1.1.7"

    const val qmui = "com.qmuiteam:qmui:2.0.0-alpha10"
    const val flexibledivider = "om.yqritc:recyclerview-flexibledivider:1.4.0"
    const val multidex = "androidx.multidex:multidex:2.0.0"



    const val agp = "com.android.tools.build:gradle:${Versions.agp}"
    const val kgp = "org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10"

    const val room_compiler = "androidx.room:room-compiler:${Versions.Room}"
    const val viewPage2 = "androidx.viewpager2:viewpager2:${Versions.viewPager2}"

    const val loadSir = "com.kingja.loadsir:loadsir:1.3.8"
    const val sandwich = "com.github.skydoves:sandwich:1.2.4"

    const val transformation = "com.github.skydoves:transformationlayout:1.1."
    const val rxbinding = "com.jakewharton.rxbinding4:rxbinding:4.0.0"

    const val arouter_api = "com.alibaba:arouter-api:${Versions.arouter_api_versoion}"



    val glide = arrayOf(
        "com.github.bumptech.glide:glide:${Versions.glide}",
//        "com.github.florent37:glidepalette:${Versions.glidePalette}"
    )

    val coroutine = arrayOf(
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}",
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    )


    val immersionbar = arrayOf(
        "com.gyf.immersionbar:immersionbar:${Versions.immersionbar}",
        "com.gyf.immersionbar:immersionbar-components:${Versions.immersionbar}",
        "com.gyf.immersionbar:immersionbar-ktx:${Versions.immersionbar}"
    )

    val rxJava = arrayOf(
        "io.reactivex.rxjava3:rxjava:${Versions.rxjava}",
        "io.reactivex.rxjava3:rxandroid:${Versions.rxjava}"
    )




    val otherThirdLibs = arrayOf(
//        anyLayer,
        "com.github.pengxurui:EasyTrack:v1.0.1",
        "com.github.huage2580:PermissionMonitor:1.0.1",
        "me.weishu:epic:1.0.0",
        "com.yqritc:recyclerview-flexibledivider:1.4.0",

        "com.youth.banner:banner:2.1.0",

        "com.github.Justson.AgentWeb:agentweb-core:v4.1.9",
        "com.squareup.picasso:picasso:2.5.2",
        "com.github.lzyzsd:jsbridge:1.0.4",
        "com.github.markzhai:blockcanary-android:1.5.0",
        "com.squareup.leakcanary:leakcanary-support-fragment:1.6.3",
        "com.permissionx.guolindev:permissionx:1.4.0",
        "dev.b3nedikt.viewpump:viewpump:4.0.7",
        "com.github.NoEndToLF:AppStartFaster:1.0.3",
        "com.airbnb.android:lottie:2.8.0",
        "com.github.lygttpod:SuperTextView:2.4.6",
        loadSir,
        "com.drakeet.about:about:2.5.1",
        "com.drakeet.multitype:multitype:4.3.0",
        "com.github.liangjingkanji:StateLayout:1.2.0",
        mmkv,
        sandwich,
        "com.github.skydoves:progressview:1.1.3",
//        transformation,
        rxbinding,
        "com.drakeet.drawer:drawer:1.0.3",

        //敏感词检测
        "com.github.houbb:sensitive-word:0.2.0",
        arouter_api,

    )



    val materialDialogs = arrayOf(
        "com.afollestad.material-dialogs:core:${Versions.materialDialog}",
        "com.afollestad.material-dialogs:input:${Versions.materialDialog}",
        "com.afollestad.material-dialogs:files:${Versions.materialDialog}",
        "com.afollestad.material-dialogs:color:${Versions.materialDialog}",
        "com.afollestad.material-dialogs:bottomsheets:${Versions.materialDialog}",
        "com.afollestad.material-dialogs:datetime:${Versions.materialDialog}",
        "com.afollestad:assent:2.3.1",
        "com.afollestad:date-picker:0.6.1",
    )

    val blackTech = arrayOf(
        //https://github.com/LSPosed/AndroidHiddenApiBypass
        "org.lsposed.hiddenapibypass:hiddenapibypass:4.2",

        //android 11 FreeReflection一个允许你在Android P上使用反射而没有任何限制的库
        "me.weishu:free_reflection:3.0.1",
    )


    val okhttp = arrayOf(
        "com.squareup.okhttp3:okhttp:${Versions.okhttp}",
        "com.squareup.okhttp3:okhttp-bom:${Versions.okhttp}",
        "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}"
    )

    val retrofit = arrayOf(
        "com.squareup.retrofit2:retrofit:${Versions.retrofit}",
        "com.squareup.retrofit2:converter-gson:${Versions.retrofit}",
        "com.squareup.retrofit2:converter-scalars:${Versions.retrofit}",
        "com.squareup.retrofit2:adapter-rxjava3:${Versions.retrofitRxjava}"
    )


    val tests = arrayOf(
        "junit:junit:4.13.2"
    )
    val androidTests = arrayOf(
        "androidx.test.ext:junit:1.1.3",
        "androidx.test.espresso:espresso-core:3.4.0"
    )

    const val restring = "dev.b3nedikt.restring:restring:5.1.4"
    const val viewPump = "dev.b3nedikt.viewpump:viewpump:4.0.10"
    const val reword = "dev.b3nedikt.reword:reword:4.0.1"
    const val bundler = "com.github.skydoves:bundler:1.0.4"


}

object Test{
    val tests = arrayOf(
        "junit:junit:4.13.2"
    )
}





object Kotlin{
    val kotlin = arrayOf(
        "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}",
        "org.jetbrains.kotlin:kotlin-reflect:${Versions.kotlin}"
    )

}

object Jetpack{

    val lifecycle = arrayOf(
        "androidx.lifecycle:lifecycle-common-java8:${Versions.lifecycle_version}",
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle_version}",
        "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle_version}",
//        "androidx.lifecycle:lifecycle-runtime:${Versions.lifecycle_version}",

//        "androidx.lifecycle:lifecycle-service:${Versions.lifecycle_version}"
    )
    val navigation = arrayOf(
        "androidx.navigation:navigation-fragment-ktx:${Versions.navigationVersion}",
        "androidx.navigation:navigation-ui-ktx:${Versions.navigationVersion}",
        "androidx.navigation:navigation-runtime-ktx:${Versions.navigationVersion}"
    )

    val workManager = "androidx.work:work-runtime-ktx:${Versions.workmanager}"


    val room = arrayOf(
        "androidx.room:room-runtime:${Versions.Room}",
        "androidx.room:room-ktx:${Versions.Room}"
    )
    val room_compiler = arrayOf(
        "androidx.room:room-compiler:${Versions.Room}",
    )


    val hit = arrayOf(
        "com.google.dagger:hilt-android:${Versions.hit}",
        //hit 对 viewmodel 的支持
//        "androidx.hilt:hilt-lifecycle-viewmodel:${Versions.hit_viewModel}",
    )
    val hit_compiler = arrayOf(
        //使用hit 时需要
//        "com.google.dagger:hilt-android-compiler:${Versions.hit}",
//        //hit 对 viewmodel 的支持
//        "androidx.hilt:hilt-compiler:${Versions.hit_viewModel}",

        "com.google.dagger:hilt-compiler:2.38.1"
    )


    val startUp = "androidx.startup:startup-runtime:1.0.0-alpha01"


}


/**
 * SDK相关依赖
 *
 * @author zwb
 * @since 2/27/22
 */
object SDK {
    // 腾讯Bugly 异常上报
    const val TencentBugly = "com.tencent.bugly:crashreport:${Versions.bugly}"

    // Bugly native异常上报
    const val TencentBuglyNative = "com.tencent.bugly:nativecrashreport:${Versions.buglyNative}"

    // 腾讯X5WebView
    const val TencentTBSX5 = "com.tencent.tbs.tbssdk:sdk:43939"
}

object Compiler {
    const val arouter_compiler = "com.alibaba:arouter-compiler:${Versions.arouter_compiler_versoion}"
}
// AndroidX Test - Instrumented testing
/*androidTestImplementation "androidx.test:core-ktx:$androidXTestCoreVersion"
androidTestImplementation "androidx.test.ext:junit-ktx:$androidXTestExtKotlinRunnerVersion"
androidTestImplementation "androidx.test:rules:$androidXTestRulesVersion"
androidTestImplementation "androidx.room:room-testing:$roomVersion"
androidTestImplementation "androidx.arch.core:core-testing:$archTestingVersion"
androidTestImplementation "androidx.navigation:navigation-testing:$navigationVersion"

*/

object AndroidTest{
    val espresso = arrayOf(
        "androidx.test.espresso:espresso-core:${Versions.espressoVersion}",
        "androidx.test.espresso:espresso-contrib:${Versions.espressoVersion}",
        "androidx.test.espresso:espresso-intents:${Versions.espressoVersion}",
        "androidx.test.espresso.idling:idling-concurrent:${Versions.espressoVersion}",
    )
    val espresso_idling = "androidx.test.espresso:espresso-idling-resource:${Versions.espressoVersion}"

}
