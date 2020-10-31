package com.apache.fastandroid.sample;

/**
 * author: jerry
 * created on: 2020/6/11 7:35 PM
 * description:
 */
public class PlayerState {
    public final static int PLAYER_STATE_IDLE = 1 << 0;

    public final static int PLAYER_STATE_INITIALIZED = 1 << 1;

    public final static int PLAYER_STATE_PREPARING = 1 << 2;

    public final static int PLAYER_STATE_PREPARED = 1 << 3;

    public final static int PLAYER_STATE_STARTED = 1 << 4;

    public final static int PLAYER_STATE_PAUSED = 1 << 5;

    public final static int PLAYER_STATE_COMPLETE = 1 << 6;

    public final static int PLAYER_STATE_STOPPED = 1 << 7;

    public final static int PLAYER_STATE_ERROR = 1 << 8;

    private int state;

    /**
     * 默认状态为{@link #PLAYER_STATE_IDLE}
     */
    public PlayerState() {
        super();
        this.state = PLAYER_STATE_IDLE;
    }


    /**
     * 获取当前状态
     *
     * @return 当前状态
     */
    public synchronized int state() {
        return state;
    }

    /**
     * 状态迁移
     *
     * @param state 新状态
     */
    public synchronized void transfer(int state) {
        this.state = state;
    }

    public synchronized boolean equalSingleState(int singleState) {
        return (state & singleState) != 0;
    }


    /**
     * 判断状态
     *
     * @param states 预期的一个或多个状态
     * @return true，符合；false，不符合
     */
    public synchronized boolean equalState(int... states) {
        int temp = 0;
        for (int i = 0; i < states.length; i++) {
            temp |= states[i];
        }
        return (state & temp) != 0;
    }

    /**
     * 当满足状态条件时，进行wait。
     * 一旦被notify就退出，但是退出并不一定表示其不满足状态条件
     *
     * @param states 进入wait的条件状态
     */
    public synchronized void waitState(int... states) {
        if (equalState(states)) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
