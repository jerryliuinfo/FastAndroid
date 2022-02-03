package com.apache.fastandroid.demo.task

import com.optimize.performance.launchstarter.task.Task

/**
 * Created by Jerry on 2022/2/3.
 */
class Project: Task(),OnProjectExecuteListener {

    private var mFinishTask:Task ?= null

    private var mStartTask:Task ?= null


    class Builder{
        private var mTaskFactory:TaskFactory ?= null

        private var mTimerMonitor:ExecuteTimerMonitor ?= null

        private var mFinishTask:Task ?= null

        private var mStartTask:Task ?= null

        private val mProject:Project = Project()

        init {
            mFinishTask = AnchorTask(false,"==AlphaDefaultFinishTask==",mProject)
            mStartTask = AnchorTask(true,"==AlphaDefaultStartTask==",mProject)
            mProject.mStartTask = mStartTask
            mProject.mFinishTask = mFinishTask
            mTimerMonitor = ExecuteTimerMonitor()
        }

        fun withCreator(taskFactory: TaskFactory):Builder{
            this.mTaskFactory = taskFactory
            return this
        }

        fun setMonitor(monitor: ExecuteTimerMonitor):Builder{
            this.mTimerMonitor = monitor
            return this
        }

    }


    private class AnchorTask(val isStart:Boolean,val taskName:String, val listener: OnProjectExecuteListener?):Task(){

        override fun run() {
            listener?.let {
                if (isStart) it.onProjectStart() else it.onProjectFinish()
            }

        }

    }

    override fun onProjectStart() {
    }

    override fun onTaskFinish(taskName: String?) {
    }

    override fun onProjectFinish() {
    }

    override fun run() {
    }
}