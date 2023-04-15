package com.apache.fastandroid.demo.weaknetwork.connectionclass;

/**
 * Created by Jerry on 2023/4/1.
 */
public class NetApp {

    //default this to 1 which is a poor connection.
    public static int networkSpeedClass = 1;

    public static void setSpeed(int value) {
        networkSpeedClass = value;
    }

    public static int getSpeed() {
        return networkSpeedClass;
    }

}
