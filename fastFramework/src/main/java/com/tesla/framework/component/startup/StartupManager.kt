package com.tesla.framework.component.startup

import android.content.Context
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger
import kotlin.system.measureTimeMillis

/**
 * @author : zhaoyanjun
 * @time : 2021/9/27
 * @desc : 启动工具类
 */
object StartupManager {

    private var timeListener: TimeListener? = null
    //key:任务名字  value:groupId
    internal val startupIdMap = mutableMapOf<String, Int>()  //存所有任务groupId
    //key:任务名字  value:任务本身
    internal val allStartup = mutableMapOf<String, Startup>() //存所有任务groupId
    internal val mainResult = mutableListOf<Startup>() //主线程列表
    internal val ioResult = mutableListOf<Startup>()   //子线程列表
    internal val waitOnMainThreadResult = mutableListOf<Startup>()   //任务本身是在子线程中执行，但是却需要等待主线程执行完成的任务
    private val groupCount = AtomicInteger()  //组id
    private val countDownLatch by lazy { CountDownLatch(mainResult.size + ioResult.size) }
    private val executor by lazy { Executors.newCachedThreadPool() }

    fun addGroup(block: (Group) -> Unit): StartupManager {
        val group = Group(groupCount.getAndIncrement())
        block(group)
        return this
    }

    fun cost(time: TimeListener): StartupManager {
        timeListener = time
        return this
    }

    /**
     * 开始执行
     */
    fun start(context: Context) {
        val start = System.currentTimeMillis()
        //先执行子线程，后执行主线程，防止主线程执行耗时导致子线程迟迟得不到运行
        ioResult.forEach {
            executor.execute {
                checkDependenciesLegal(it)
                it.dependencies()?.forEach { item ->
                    allStartup[item.simpleName]?.await()
                }
                val costTime = measureTimeMillis {
                    it.execute(context)
                }
                timeListener?.itemCost(it.aliasName, costTime, Thread.currentThread().name)
                countDownLatch.countDown()
            }
        }

        //主线程
        mainResult.forEach {
            checkDependenciesLegal(it)
            it.dependencies()?.forEach { item ->
                allStartup[item.simpleName]?.await()
            }

            val costTime = measureTimeMillis {
                it.execute(context)
            }
            timeListener?.itemCost(it.aliasName, costTime, Thread.currentThread().name)
            countDownLatch.countDown()
        }

        //等待所有等待主线程的任务执行完
        waitOnMainThreadResult.forEach {
            it.await()
        }
        timeListener?.allCost(System.currentTimeMillis() - start)
        executeClear()
    }

    /**
     * 检查依赖合法性
     * groupId > 所有依赖的最大id
     */
    private fun checkDependenciesLegal(startup: Startup) {
        val dependencies = startup.dependencies()
            //没有依赖其他task的必须放在第一个group，否则都放到第二个 group
        if (dependencies.isNullOrEmpty()) {
            if (startup.groupId == 0) {
                return
            }
            throw Exception("${startup.aliasName} dependencies is illegal,Should be placed in the first group")
        }
        var dependenciesMaxGroupId = 0
        dependencies.forEach { item ->
            //取最大值
            dependenciesMaxGroupId =
                dependenciesMaxGroupId.coerceAtLeast(
                    startupIdMap[item.simpleName] ?: 0
                )
        }
        if (startup.groupId - dependenciesMaxGroupId != 1) {
            throw Exception("${startup.aliasName} dependencies is illegal,Should be placed in the ${dependenciesMaxGroupId + 2}st group")
        }
    }

    /**
     * 释放资源
     */
    private fun executeClear() {
        executor.execute {
            runCatching {
                //等待所有任务都执行完，才释放资源
                countDownLatch.await()
                clear()
            }.onFailure { it.printStackTrace() }
        }
    }

    /**
     * 数据置空
     */
    private fun clear() {
        startupIdMap.clear()
        allStartup.clear()
        mainResult.clear()
        ioResult.clear()
        waitOnMainThreadResult.clear()
    }
}

