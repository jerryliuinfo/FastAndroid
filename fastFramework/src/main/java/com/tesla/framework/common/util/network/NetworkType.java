package com.tesla.framework.common.util.network;

/**
 * author: 01370340
 * data: 2019/5/17
 * description:
 */
public enum  NetworkType {
    NONE("None", false),
    WIFI("Wifi", true),
    MOBILE_2G("2G", true),
    MOBILE_3G("3G", true),
    MOBILE_4G("4G", true),
    ETHERNET("Ethernet", true),
    OTHERS("Other", true);

    private String name;
    private boolean available;

    private NetworkType(String friendlyName, boolean available) {
        this.setName(friendlyName);
        this.setAvailable(available);
    }

    public String getName() {
        return this.name;
    }

    public boolean isAvailable() {
        return this.available;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

}
