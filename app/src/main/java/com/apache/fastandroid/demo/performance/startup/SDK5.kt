import com.tesla.framework.component.startup.Startup

import android.content.Context
import com.apache.fastandroid.demo.performance.startup.SDK1

/**
 * @author : zhaoyanjun
 * @time : 2021/9/27
 * @desc :
 */
class SDK5 : Startup() {

    override fun create(context: Context) {
        //模拟初始化时间
        Thread.sleep(800)
    }

    //主线程
    override fun callCreateOnMainThread(): Boolean = false

    override fun dependencies(): List<Class<out Startup>>? {
        return listOf(SDK1::class.java)
    }

    override fun waitOnMainThread(): Boolean {
        return false
    }
}