package com.tesla.framework.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.tesla.framework.R
import com.tesla.framework.common.util.log.FastLog
import com.tesla.framework.network.biz.IResult
import com.tesla.framework.network.task.TaskException
import com.tesla.framework.network.task.WorkTask

/**
 * Created by Jerry on 2021/3/26.
 */
open abstract class ABaseTaskFragmentKt:ABaseStatusFragment() {


    /**
     * Fragment主要的刷新任务线程，定义任务加载流程，耦合Fragment各个状态下的视图刷新方法
     *
     * @param <Params>
     * @param <Progress>
     * @param <Result>
    </Result></Progress></Params> */
    protected abstract inner class ABaseTask<Params, Progress, Result>(taskId: String?) : WorkTask<Params, Progress, Result>(taskId, this@ABaseTaskFragmentKt) {
        override fun onPrepare() {
            super.onPrepare()
            onTaskStateChanged(ABaseTaskState.prepare, null)
        }

        override fun onSuccess(result: Result) {
            super.onSuccess(result)

            // 默认加载数据成功，且ContentView有数据展示
            setContentEmpty(resultIsEmpty(result))

            onTaskStateChanged(ABaseTaskState.success, null)
            if (result is IResult) {
                val iResult = result as IResult

                // 数据是缓存数据
                if (iResult.isFromCache) {
                    // 缓存过期刷新数据
                    if (iResult.isOutOfData) {
                        runUIRunnable(object : Runnable {
                            override fun run() {
                                FastLog.d(TAG, "数据过期，开始刷新, " + toString())
                                requestDataOutofdate()
                            }
                        }, configRequestDelay().toLong())
                    }
                } else {
                }
            } else {
            }
        }

        override fun onFailure(exception: TaskException) {
            super.onFailure(exception)
            onTaskStateChanged(ABaseTaskState.falid, exception)
        }

        override fun onCancelled() {
            super.onCancelled()
            onTaskStateChanged(ABaseTaskState.canceled, null)
        }

        override fun onFinished() {
            super.onFinished()
             this@ABaseTaskFragmentKt.onTaskStateChanged(ABaseTaskState.finished, null)
        }

        /**
         * 返回数据是否空
         *
         * @param result
         * @return
         */
        fun resultIsEmpty(result: Result?): Boolean {
            return if (result == null) true else false
        }
    }


    /**
     *
     */
    protected open fun onTaskStateChanged(state: ABaseTaskState, exception: TaskException?) {

        // 开始Task
        if (state == ABaseTaskState.prepare) {
            if (isContentEmpty() && loadingLayout != null) {
                setViewVisiable(loadingLayout, View.VISIBLE)

//                setViewVisiable(contentLayout, View.GONE);
            } else {
                setViewVisiable(loadingLayout, View.GONE)

//                setViewVisiable(contentLayout, View.VISIBLE);
            }
            setViewVisiable(noContentView, View.GONE)
            if (isContentEmpty() && loadingLayout == null) {
//                setViewVisiable(contentLayout, View.VISIBLE)
            }
            setViewVisiable(loadErrorView, View.GONE)
        } else if (state == ABaseTaskState.success) {
            setViewVisiable(loadingLayout, View.GONE)
            if (isContentEmpty()) {
                setViewVisiable(noContentView, View.VISIBLE)
//                setViewVisiable(contentLayout, View.GONE)
            } else {
//                setViewVisiable(contentLayout, View.VISIBLE)
                setViewVisiable(noContentView, View.GONE)
            }
        } else if (state == ABaseTaskState.canceled) {
            if (isContentEmpty()) {
                setViewVisiable(loadingLayout, View.GONE)
                setViewVisiable(noContentView, View.VISIBLE)
            }
        } else if (state == ABaseTaskState.falid) {
            if (isContentEmpty()) {
                if (loadErrorView != null) {
                    setViewVisiable(loadErrorView, View.VISIBLE)
                    if (exception != null) {
                        val txtLoadFailed = loadErrorView!!.findViewById<View>(R.id.txtLoadFailed) as TextView
                        if (txtLoadFailed != null) {
                            txtLoadFailed.text = exception.message
                        }
                    }
                    setViewVisiable(noContentView, View.GONE)
                } else {
                    setViewVisiable(noContentView, View.VISIBLE)
                }
                setViewVisiable(loadingLayout, View.GONE)
            }
        } else if (state == ABaseTaskState.finished) {
            //doNothing
        }
    }
}