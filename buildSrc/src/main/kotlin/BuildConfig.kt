
/**
 * 项目相关参数配置
 *
 * @author Qu Yunshuo
 * @since 4/24/21 5:56 PM
 */
object BuildConfig {
    const val baseVersionName = "2.2.1"
    val verName: String by lazy { "${baseVersionName}.${"git rev-parse --short HEAD".exec()}" }
    val verCode: Int by lazy { "git rev-list --count HEAD".exec().toInt() }

    const val isAppMode = false


}