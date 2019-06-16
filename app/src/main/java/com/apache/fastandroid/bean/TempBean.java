package com.apache.fastandroid.bean;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.HashMap;

/**
 * author: 01370340
 * data: 2019/2/21
 * description:
 */
public class TempBean implements Parcelable {

    private int age;

    private HashMap<String,String> map;

    public TempBean(int age, HashMap<String, String> map) {
        this.age = age;
        this.map = map;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public HashMap<String, String> getMap() {
        return map;
    }

    public void setMap(HashMap<String, String> map) {
        this.map = map;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.age);
        dest.writeSerializable(this.map);
    }

    protected TempBean(Parcel in) {
        this.age = in.readInt();
        this.map = (HashMap<String, String>) in.readSerializable();
    }

    public static final Parcelable.Creator<TempBean> CREATOR = new Parcelable.Creator<TempBean>() {
        @Override
        public TempBean createFromParcel(Parcel source) {
            return new TempBean(source);
        }

        @Override
        public TempBean[] newArray(int size) {
            return new TempBean[size];
        }
    };
}
