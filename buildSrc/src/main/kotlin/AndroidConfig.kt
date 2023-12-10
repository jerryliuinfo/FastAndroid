object AndroidConfig {
    //android 12  对应的 compile sdk 为 31 和 32, android 11  对应的 compile sdk 为 30
    const val compileSdk = 30
    const val minSdk = 26
    const val targetSdk = 30
    const val ndk = "22.1.7171670"


    private val versionCodeBase = 105010 // XYYZZM; M = Module (tv, mobile)
    val versionCode = versionCodeBase + 3
    val versionName = "1.5.01" // X.Y.Z; X = Major, Y = minor, Z = Patch level


}


interface BuildType {

    companion object {
        const val RELEASE = "release"
        const val DEBUG = "debug"
    }

    val isMinifyEnabled: Boolean
}

object BuildTypeDebug : BuildType {
    override val isMinifyEnabled = false
}

object BuildTypeRelease : BuildType {
    override val isMinifyEnabled = false
}