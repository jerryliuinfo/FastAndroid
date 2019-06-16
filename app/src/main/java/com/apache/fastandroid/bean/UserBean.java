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

    private TempBean tempBean;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserBean() {
    }

    public TempBean getTempBean() {
        return tempBean;
    }

    public void setTempBean(TempBean tempBean) {
        this.tempBean = tempBean;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("UserBean{");
        sb.append("name='").append(name).append('\'');
        sb.append(", tempBean=").append(tempBean);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeParcelable(this.tempBean, flags);
    }

    protected UserBean(Parcel in) {
        this.name = in.readString();
        this.tempBean = in.readParcelable(TempBean.class.getClassLoader());
    }

    public static final Creator<UserBean> CREATOR = new Creator<UserBean>() {
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
