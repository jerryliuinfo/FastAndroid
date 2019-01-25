package com.apache.fastandroid.topic.support.sdk;

import com.apache.fastandroid.topic.support.bean.NewsSummary;
import com.tesla.framework.network.task.TaskException;

import java.util.Random;

/**
 * Created by 01370340 on 2017/11/27.
 */

public class TestSdk {
    public static TestSdk newInstance(){
        return new TestSdk();
    }


    public NewsSummary testWorkTask() throws TaskException{
        int random = new Random().nextInt(10);
        if (random < 3){
            throw new TaskException("failed");
        }
        NewsSummary summary =  new NewsSummary();
        summary.postid = String.valueOf(random);
        return summary;
    }
    public NewsSummary testAsyncTask(){
        int random = new Random().nextInt(10);
        if (random < 3){
            throw new RuntimeException("failed");
        }
        NewsSummary summary =  new NewsSummary();
        summary.postid = String.valueOf(random);
        return summary;
    }
}
