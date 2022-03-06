object Libs {


    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val swiperefreshLayout = "androidx.swiperefreshlayout:swiperefreshlayout:1.0.0"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintlayout}"
    const val cardview = "androidx.cardview:cardview:${Versions.cardview}"
    const val recyclerview = "androidx.recyclerview:recyclerview:${Versions.recyclerview}"
    const val palette = "androidx.palette:palette-ktx:1.0.0"

    const val core_ktx = "androidx.core:core-ktx:${Versions.core_ktx}"
    const val kotlin_stdlib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin_stdlib}"

    const val activity_ktx = "androidx.activity:activity-ktx:${Versions.activity_ktx}"
    const val fragment_ktx = "androidx.fragment:fragment-ktx:${Versions.fragment_ktx}"


    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val gson = "com.google.code.gson:gson:${Versions.gson}"
    const val utilcode = "com.blankj:utilcodex:1.31.0"
    const val brvh = "com.github.CymChad:BaseRecyclerViewAdapterHelper:${Versions.brvh}"



    const val agp = "com.android.tools.build:gradle:${Versions.agp}"
    const val kgp = "org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10"


    val lifecycle = arrayOf(
        "androidx.lifecycle:lifecycle-common-java8:${Versions.lifecycle_version}",
        "androidx.lifecycle:lifecycle-runtime:${Versions.lifecycle_version}",
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle_version}",
        "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle_version}",
        "androidx.lifecycle:lifecycle-service:${Versions.lifecycle_version}"
    )

    val navigation = arrayOf(
        "androidx.navigation:navigation-fragment-ktx:${Versions.navigationVersion}",
        "androidx.navigation:navigation-ui-ktx:${Versions.navigationVersion}"
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

    val room = arrayOf(
        "androidx.room:room-runtime:${Versions.Room}",
        "androidx.room:room-ktx:${Versions.Room}"
//        "androidx.room:room-compiler:${Versions.Room}",
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


}








