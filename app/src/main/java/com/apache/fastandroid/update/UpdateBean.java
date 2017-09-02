package com.apache.fastandroid.update;

/**
 * @author haiyang.tan
 * @Description:
 * @date 2016/5/5 15:35
 * @copyright TCL-MIG
 */
public class UpdateBean {
    public String apkSize;
    public long innerVersion;
    public String version;
    public String description;
    public String downloadUrl;
    public String apkMd5;
    public Boolean forceUpdate;
    public int popup; //0是不弹窗， 1是弹窗， 2是立即弹窗
    public int popupTimeInterval;

    public int popupTotalCount;

    public static final int NOT_SHOW = 0;
    public static final int SHOW = 1;
    public static final int SHOW_NOW = 2;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UpdateBean that = (UpdateBean) o;

        if (innerVersion != that.innerVersion) return false;
        if (popup != that.popup) return false;
        if (popupTimeInterval != that.popupTimeInterval) return false;
        if (apkSize != null ? !apkSize.equals(that.apkSize) : that.apkSize != null) return false;
        if (version != null ? !version.equals(that.version) : that.version != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null)
            return false;
        if (downloadUrl != null ? !downloadUrl.equals(that.downloadUrl) : that.downloadUrl != null)
            return false;
        if (apkMd5 != null ? !apkMd5.equals(that.apkMd5) : that.apkMd5 != null) return false;
        return forceUpdate != null ? forceUpdate.equals(that.forceUpdate) : that.forceUpdate == null;

    }

    @Override
    public int hashCode() {
        int result = apkSize != null ? apkSize.hashCode() : 0;
        result = 31 * result + (int) (innerVersion ^ (innerVersion >>> 32));
        result = 31 * result + (version != null ? version.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (downloadUrl != null ? downloadUrl.hashCode() : 0);
        result = 31 * result + (apkMd5 != null ? apkMd5.hashCode() : 0);
        result = 31 * result + (forceUpdate != null ? forceUpdate.hashCode() : 0);
        result = 31 * result + popup;
        result = 31 * result + popupTimeInterval;
        return result;
    }

    @Override
    public String toString() {
        return "UpdateBean{" +
                "apkSize='" + apkSize + '\'' +
                ", innerVersion=" + innerVersion +
                ", version='" + version + '\'' +
                ", description='" + description + '\'' +
                ", downloadUrl='" + downloadUrl + '\'' +
                ", apkMd5='" + apkMd5 + '\'' +
                ", forceUpdate=" + forceUpdate +
                ", popup=" + popup +
                ", popupTimeInterval=" + popupTimeInterval +
                ", popupTotalCount=" + popupTotalCount +
                '}';
    }



    public static boolean isForceUpdate(UpdateBean bean){
        return bean != null && bean.popup == SHOW_NOW;
    }
}
