import com.tesla.framework.component.startup.Startup

import android.content.Context

/**
 * @author : zhaoyanjun
 * @time : 2021/9/27
 * @desc :
 */
class SDK4 : Startup() {

    override fun create(context: Context) {
        //模拟初始化时间
        Thread.sleep(100)
    }

    //主线程
    override fun callCreateOnMainThread(): Boolean = false

    override fun dependencies(): List<Class<out Startup>>? {
        return listOf(SDK2::class.java)
    }

    override fun waitOnMainThread(): Boolean {
        return true
    }
}