package com.apache.fastandroid.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * author: 01370340
 * data: 2019/2/21
 * description:
 */
public class UserBean implements Parcelable {
    private String name;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserBean() {
    }

    public UserBean(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"name\":\"")
                .append(name).append('\"');
        sb.append('}');
        return sb.toString();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
    }

    public void readFromParcel(Parcel source) {
        this.name = source.readString();
    }

    protected UserBean(Parcel in) {
        this.name = in.readString();
    }

    public static final Parcelable.Creator<UserBean> CREATOR = new Parcelable.Creator<UserBean>() {
        @Override
        public UserBean createFromParcel(Parcel source) {
            return new UserBean(source);
        }

        @Override
        public UserBean[] newArray(int size) {
            return new UserBean[size];
        }
    };
}
