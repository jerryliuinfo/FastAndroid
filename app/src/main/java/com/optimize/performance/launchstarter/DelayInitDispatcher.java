package com.optimize.performance.launchstarter;

import android.os.Looper;
import android.os.MessageQueue;

import com.optimize.performance.launchstarter.task.DispatchRunnable;
import com.optimize.performance.launchstarter.task.Task;

import java.util.LinkedList;
import java.util.Queue;

public class DelayInitDispatcher {

    private Queue<Task> mDelayTasks = new LinkedList<>();

    private MessageQueue.IdleHandler mIdleHandler = new MessageQueue.IdleHandler() {
        @Override
        public boolean queueIdle() {
            if(mDelayTasks.size()>0){
                Task task = mDelayTasks.poll();
                new DispatchRunnable(task).run();
            }
            //如果返回 false 则执行完毕之后移除这条消息（一次性完事），如果为true则保留，等到下次空闲时会再次执行（重复执行）。
            return !mDelayTasks.isEmpty();
        }
    };

    public DelayInitDispatcher addTask(Task task){
        mDelayTasks.add(task);
        return this;
    }

    public void start(){
        Looper.myQueue().addIdleHandler(mIdleHandler);
    }

}
