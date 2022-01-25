package com.apache.fastandroid.demo.temp.concurrency;

import com.tesla.framework.component.logger.Logger;

/**
 * Created by Jerry on 2022/1/25.
 */
public class Player implements Runnable{
    private static final Object car = new Object();
    private static final Object sword = new Object();

    public static final String PLAYER1 = "张三";
    public static final String PLAYER2 = "李四";

    private String name;

    public Player(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        play();
    }

    private void play() {
        if (PLAYER1.equals(name)){
            synchronized (car){
                Logger.d("张三 获得玩具车");
                synchronized (sword){
                    Logger.d("张三 获得玩具剑");
                }
            }

        }else if (PLAYER2.equals(name)){
            synchronized (sword){
                Logger.d("李四 获得玩具剑");
                synchronized (car){
                    Logger.d("李四 获得玩具车");
                }
            }
        }
    }
}
