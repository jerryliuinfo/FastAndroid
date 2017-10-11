package com.tesla.framework.support.bean;

import java.io.Serializable;

/**
 * Created by jerryliu on 2017/3/28.
 */

public class RefreshConfig implements Serializable{
    private static final long serialVersionUID = 6244426943442129360L;

    public boolean pagingEnd = false;// 分页是否结束

    public String positionKey = null;// 最后阅读坐标的Key，null-不保存，针对缓存数据有效

    public boolean displayWhenScrolling = true;// 滚动的时候加载图片

    public int releaseDelay = 5 * 1000;// 当配置了releaseItemIds参数时，离开页面后自动释放资源

    public int[] releaseItemIds = null;// 离开页面时，释放图片的控件，针对ItemView

    public String emptyHint = "数据为空";// 如果EmptyLayout中有R.id.txtLoadEmpty这个控件，将这个提示绑定显示

    public boolean footerMoreEnable = true;// 是否允许FooterView加载更多

    public boolean refreshEnable = true;

    public int pageSize = 10;


    public void disalbeFooterMore(){
        footerMoreEnable = false;
    }
}
