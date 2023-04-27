package com.apache.fastandroid.demo.temp

import android.app.AlertDialog
import android.content.Context
import android.graphics.Rect
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Bundle
import android.text.InputFilter
import android.text.Spanned
import android.text.TextUtils
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.ViewTreeObserver
import android.widget.Toast
import androidx.activity.addCallback
import androidx.core.app.ActivityCompat
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.FragmentTempKnowledgeBinding
import com.apache.fastandroid.demo.designmode.FilterDemoFragment
import com.apache.fastandroid.demo.designmode.wrapper.AContext
import com.apache.fastandroid.demo.designmode.wrapper.AContextWrapper
import com.apache.fastandroid.demo.designmode.wrapper.MyToast
import com.apache.fastandroid.demo.temp.concurrency.Player
import com.blankj.utilcode.util.MetaDataUtils
import com.blankj.utilcode.util.ToastUtils
import com.blankj.utilcode.util.Utils
import com.example.android.architecture.blueprints.todoapp.util.hasFragment
import com.tesla.framework.component.dialog.DialogSelectFragment
import com.tesla.framework.component.ignore.IgnoreFirstEventListener
import com.tesla.framework.component.ignore.IgnoreMultiEventListener
import com.tesla.framework.component.livedata.NetworkLiveData
import com.tesla.framework.component.logger.Logger
import com.tesla.framework.ui.fragment.BaseBindingFragment
import com.tesla.framework.component.network.AutoRegisterNetListener
import com.zwb.lib_base.utils.network.NetworkStateChangeListener
import com.zwb.lib_base.utils.network.NetworkTypeEnum
import kotlinx.android.synthetic.main.fragment_temp_knowledge.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.util.concurrent.TimeUnit


/**
 * Created by Jerry on 2021/9/6.
 */
class KnowledgeFragment: BaseBindingFragment<FragmentTempKnowledgeBinding>(FragmentTempKnowledgeBinding::inflate),
    NetworkStateChangeListener {
    private val items:MutableList<String> = ArrayList()
    companion object{
        private const val TAG = "KnowledgeFragment"
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)


        mBinding.btnDynamicProgress.setOnClickListener {
            dynamicProgress()
        }

        mBinding.btnWrapper.setOnClickListener {
            wrapperMode()
        }
        mBinding.tvFilter.setOnClickListener {
            filterModeUsage()
        }

        //注意：语言要是非中文才会 生效
        mBinding.tvQuatity.setOnClickListener {
            var size = 1
            var result1 = resources.getQuantityString(R.plurals.subtitle_plural,size,size)
            println("quantity = 1 output:$result1")

            size = 2
            result1 = resources.getQuantityString(R.plurals.subtitle_plural,size,size)
            println("quantity = 2 output:$result1")

            size = 4
            result1 = resources.getQuantityString(R.plurals.subtitle_plural,size,size)
            println("quantity = 4 output:$result1")
        }

        forAddSeperator()
        addOnPredrawListener()
        btn_textview_horizontal_scroll.setOnClickListener {
            textviewHorizontalScroll()
        }
        btn_collections_suffle.setOnClickListener {
            (0..20).forEach {
                items.add("value:${it}")
            }
            items.subList(0,5).shuffle()
        }

        mBinding.btnMultiChannel.setOnClickListener {
            ToastUtils.showShort(MetaDataUtils.getMetaDataInApp("HOST"))
        }

        btn_sisuo.setOnClickListener {

        }
        

        mBinding.btnVarargs.setOnClickListener {
            initvarArgs("aaa","bbb")
        }

        mBinding.btnConcurrence.setOnClickListener {
            concurencyUsage()
        }

        mBinding.btnShowDialogWithApplicationContext.setOnClickListener {
            showDialogWithApplicationContext()
        }

        mBinding.btnLocationOnscreen.setOnClickListener {
            val location = IntArray(2)
            mBinding.btnLocationOnscreen.getLocationOnScreen(location)
            val rect = Rect()
            rect.apply {
                left = location[0]
                top = location[1]
                right = location[0] + mBinding.btnLocationOnscreen.width
                bottom = location[1] + mBinding.btnLocationOnscreen.height
            }
            println("rect:${rect.toString()}")
        }

        mBinding.btnListInit.setOnClickListener {

        }
        mBinding.btnNetwork.setOnClickListener {
            listenerNetwork()
        }



        requireActivity().onBackPressedDispatcher.addCallback {
            onBackKeyPressed()
        }



        mBinding.btnMathFormat.setOnClickListener {
            mathFormat()
        }

        mBinding.btnTimeunitToMilles.setOnClickListener {
            timeUnitToMilles(TimeUnit.SECONDS,1)
            timeUnitToMilles(TimeUnit.MINUTES,1)
        }

        mBinding.btnArrayJoin.setOnClickListener {
            arrayJoin()
        }

        mBinding.btnMetaData.setOnClickListener {
            metaDataUsage()
        }


        val ignoreFirstEventListener = IgnoreFirstEventListener{
            Logger.d("ignoreFirstEventListener block")
        }

        val ignoreMultiEventListener = IgnoreMultiEventListener{
            Logger.d("IgnoreMultiEventListener block")
        }

        mBinding.btnIgnoreFirstEvent.setOnClickListener {
            ignoreFirstEventListener.onTrigger()
        }

        mBinding.btnIgnoreMultiEvent.setOnClickListener {
            ignoreMultiEventListener.onTrigger()
        }
    }

    private fun dynamicProgress() {
        runBlocking {
            // 定义实际任务进度值和前台任务进度值，两者均为 0
            var actualProgress = 0
            var frontendProgress = 0

            // 定义每隔固定时间更新一次前台任务进度值（默认为 1s）
            var intervalTime = 1000L

            // 定义完成整个任务所需的总时间（假设为 30s）
            val totalTime = 30000L

            // 定义前台进度从 1% 增加到 100% 的最小时间和最大时间
            val minTimeToComplete = 5000L
            val maxTimeToComplete = 10000L

            // 定义每个阶段内每个时间间隔更新的进度值
            var stepSize = 0

            // 循环更新前台任务进度值，直到达到 100%
            while (frontendProgress < 100) {

                // 计算从当前前台进度到实际任务进度的剩余时间
                val timeToComplete = ((actualProgress - frontendProgress) * totalTime) / 100

                // 如果当前前台进度大于实际任务进度，则直接将前台进度加 1
                if (frontendProgress > actualProgress) {
                    frontendProgress++
                }
                // 如果当前前台进度小于实际任务进度，则需要快速增加到实际任务进度值
                else if (frontendProgress < actualProgress) {
                    // 计算每个阶段内每个时间间隔更新的进度值
                    stepSize = ((actualProgress - frontendProgress) * intervalTime / timeToComplete).toInt()
                    // 将前台进度快速增加到实际任务进度值
                    while (frontendProgress < actualProgress) {
                        frontendProgress += stepSize
                        delay(intervalTime)
                    }
                }
                // 如果当前前台进度等于实际任务进度，则不做处理，维持当前前台进度不变

                // 更新前台任务进度展示
                println("Frontend progress: $frontendProgress%")

                // 如果前台进度从 1% 增加到 100% 的时间小于 5s，则将 intervalTime 设置为 5 / 99 秒
                if (frontendProgress == 1 && timeToComplete < minTimeToComplete) {
                    stepSize = 1
                    intervalTime = minTimeToComplete / 99
                }
                // 如果前台进度从 1% 增加到 100% 的时间大于 10s，则将 intervalTime 设置为 10 / 99 秒
                else if (frontendProgress == 1 && timeToComplete > maxTimeToComplete) {
                    stepSize = 1
                    intervalTime = maxTimeToComplete / 99
                }
                // 否则，按照默认的 intervalTime 继续更新前台任务进度值
            }

            // 前台进度达到 100%，任务完成
            println("Task completed!")
        }


    }

    private fun wrapperMode() {
        AContextWrapper(AContext()).doSomething1()

        MyToast.makeText(context,"I am toast",Toast.LENGTH_SHORT).show()
    }


    interface IPredicate<E> {
        fun evaluate(item: E): Boolean
    }
    private fun filterModeUsage() {
        fun <E> filter(collection: MutableCollection<E>?, predicate: IPredicate<E>?) {
            if (collection == null || predicate == null) return
            val it: MutableIterator<*> = collection.iterator()
            while (it.hasNext()) {
                if (!predicate.evaluate(it.next() as E)) {
                    it.remove()
                }
            }
        }
        var list = arrayListOf<Int>(1, 2, 4, 5, 6)
        filter(list,object : IPredicate<Int> {
            override fun evaluate(item: Int): Boolean {
                return item % 2 == 0
            }
        })
       println("result:${list.joinToString()}")
    }

    private fun metaDataUsage() {
        val appChannel = MetaDataUtils.getMetaDataInApp("appChannel")
        Logger.d("appChannel:$appChannel")
    }

    private fun arrayJoin() {
        val array = arrayOf("AAA","BBB","CCC")
        val json = TextUtils.join(",",array)
        println("json: $json")
    }

    private fun timeUnitToMilles(timeUnit: TimeUnit, amount:Long):Long {
        return timeUnit.toMillis(amount)
    }

    private fun mathFormat() {
        val format = "#%03d"
        val value1 = String.format(format,1)
        val value2 = String.format(format,100)
        println("value1:$value1, value2:$value2")
    }

    private fun listenerOnBackKey() {

    }

    private fun onBackKeyPressed() {
        if (!hasFragment(TAG)) {
            DialogSelectFragment.Builder()
                .setTitle("确认推出应用吗")
                .setConfirmText("退出")
                .setCancelText("取消")
                .setConfirmClickListener {
                    ActivityCompat.finishAffinity(requireActivity())
                }
                .build()
                .show(childFragmentManager)


        }

    }

    private fun listenerNetwork() {
        val networkCallback = NetworkCallbackImpl()
        val builder = NetworkRequest.Builder()
        val request = builder.build()
        val connMgr = activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connMgr.registerNetworkCallback(request, networkCallback)
        NetworkLiveData.getInstance().observe(this){
            Logger.d("network type: $it")
        }
        lifecycle.addObserver(AutoRegisterNetListener(this))



    }


    private fun showDialogWithApplicationContext(){
        AlertDialog.Builder(Utils.getApp())
            .setTitle("I am title")
            .show()

    }
    private fun concurencyUsage() {
        val player1:Player = Player(Player.PLAYER1)
        val player2:Player = Player(Player.PLAYER2)
        Thread(player1).start()
        Thread(player2).start()
    }

    class MyFilter(str: String?, val context: Context?) : InputFilter {
        var ch: String? = null
        var str: String? = null
        override fun filter(
            source: CharSequence, start: Int, end: Int,
            dest: Spanned, dstart: Int, dend: Int
        ): CharSequence {
            //最后输入的一个字符
            ch = if (dest.length < str!!.length) {
                //截取未过滤的最后一个字符
                str!!.substring(dstart + start, dstart + end)
            } else {
                return dest.subSequence(dstart, dend)
            }
            return if (ch == source) {

                Toast.makeText(
                    context, "符合要求",
                    Toast.LENGTH_SHORT
                ).show()
                //符合规定要求的字符以原输入显示
                dest.subSequence(dstart, dend).toString() + source.toString()
            } else {
                Toast.makeText(
                    context, "不符合要求喔~",
                    Toast.LENGTH_SHORT
                ).show()
                //如果没有按要求输入字符，则该字符被“*”代替，并显示
                dest.subSequence(dstart, dend).toString() + "*"
            }
        }

        init {
            this.str = str
        }
    }


    private fun initvarArgs(vararg params:String){

    }

    /**
     * for循环添加连接符
     */
    private fun forAddSeperator(){
        var map = mapOf("one" to 1, "two" to 2, "three" to 3)
        val paramBuilder = StringBuilder()
        var appendAnd = false
        for (key in map.keys) {
            if (appendAnd){
                paramBuilder.append("&")
            }
            paramBuilder.append(key).append("=").append(map[key])
            appendAnd = true
        }
        Logger.d(TAG, "msg: %s", paramBuilder.toString())
    }

    private fun addOnPredrawListener(){
        tv_name.viewTreeObserver.addOnPreDrawListener (object :ViewTreeObserver.OnPreDrawListener{
            override fun onPreDraw(): Boolean {
                tv_name.viewTreeObserver.removeOnPreDrawListener(this)
                return false
            }
        })
    }
    val result = StringBuilder()
    private fun textviewHorizontalScroll(){
        result.let {
            (0..20).forEach{
                result.append("add text:${it}")
            }
        }
        tv_message.setHorizontallyScrolling(true)
        tv_message.movementMethod= ScrollingMovementMethod.getInstance()
        tv_message.text= result.toString()
    }

    private val set1 = mutableListOf<String>("111")
    private val set2 = mutableListOf<String>("222")
    private val set3 = set1.addAll(set2)


    private fun <T> filter(originalList: List<T>, condition:(T) -> Boolean): List<T> {
        val list = arrayListOf<T>()
        for (t in originalList) {
            if (condition(t)) {
                list.add(t)
            }
        }
        return list
    }

    private fun <T,R> map(originalList: List<T>, transform:(T) -> R): List<R> {
        val list = arrayListOf<R>()
        for (t in originalList) {
            list.add(transform(t))
        }
        return list
    }

    class NetworkCallbackImpl: ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            println("网络已链接");

        }

        override fun onLost(network: Network) {
            super.onLost(network)
            println("网络已断开");

        }

        override fun onCapabilitiesChanged(
            network: Network,
            networkCapabilities: NetworkCapabilities
        ) {
            super.onCapabilitiesChanged(network, networkCapabilities)

            if (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)) {
                if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    println("KnowledgeFragment wifi已经连接")
                } else if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    println("KnowledgeFragment 数据流量已经连接")

                } else {
                    println("KnowledgeFragment 其他网络")

                }
            }
        }




    }

    override fun networkTypeChange(type: NetworkTypeEnum) {
        println("KnowledgeFragment networkTypeChange: $type")

    }

    override fun networkConnectChange(isConnected: Boolean) {
        println("KnowledgeFragment networkConnectChange: $isConnected")
    }

}