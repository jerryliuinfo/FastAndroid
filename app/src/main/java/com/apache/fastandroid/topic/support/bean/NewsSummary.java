package com.apache.fastandroid.topic.support.bean;

import android.text.TextUtils;

import com.tesla.framework.ui.fragment.adpater.IPagingAdapter;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 01370340 on 2017/11/23.
 */

public class NewsSummary implements Serializable,IPagingAdapter.ItemTypeData {
    public static final int TYPE_NORMAL = 10;
    public static final int TYPE_PHOTO = 20;


    public String postid;
    public boolean hasCover;
    public int hasHead;
    public int replyCount;
    public int hasImg;
    public String digest;//为空 说明是显示图片, 不为空说明是正常的item
    public boolean hasIcon;
    public String docid;
    public String title;
    public String ltitle;
    public int order;
    public int priority;
    public String lmodify;
    public String boardid;
    public String photosetID;
    public String template;
    public int votecount;
    public String skipID;
    public String alias;
    public String skipType;
    public String cid;
    public int hasAD;
    public String source;
    public String ename;
    public String imgsrc;
    public String tname;
    public String ptime;
    /**
     * title : "悬崖村" 孩子上学需爬800米悬崖
     * tag : photoset
     * imgsrc : http://img1.cache.netease.com/3g/2016/5/24/2016052421435478ea5.jpg
     * subtitle :
     * url : 00AP0001|119327
     */

    public List<AdsBean> ads;
    /**
     * imgsrc : http://img3.cache.netease.com/3g/2016/5/24/2016052416484243560.jpg
     */

    public List<ImgextraBean> imgextra;




    public static class AdsBean {
        private String title;
        private String tag;
        private String imgsrc;
        private String subtitle;
        private String url;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getImgsrc() {
            return imgsrc;
        }

        public void setImgsrc(String imgsrc) {
            this.imgsrc = imgsrc;
        }

        public String getSubtitle() {
            return subtitle;
        }

        public void setSubtitle(String subtitle) {
            this.subtitle = subtitle;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class ImgextraBean {
        private String imgsrc;

        public String getImgsrc() {
            return imgsrc;
        }

        public void setImgsrc(String imgsrc) {
            this.imgsrc = imgsrc;
        }
    }

    @Override
    public String toString() {
        return "NewsSummary{" + "postid='" + postid + '\'' + ", hasCover=" + hasCover + ", hasHead=" + hasHead + ", " +
                "replyCount=" + replyCount + ", hasImg=" + hasImg + ", digest='" + digest + '\'' + ", hasIcon=" +
                hasIcon + ", docid='" + docid + '\'' + ", title='" + title + '\'' + ", ltitle='" + ltitle + '\'' + "," +
                " order=" + order + ", priority=" + priority + ", lmodify='" + lmodify + '\'' + ", boardid='" +
                boardid + '\'' + ", photosetID='" + photosetID + '\'' + ", template='" + template + '\'' + ", " +
                "votecount=" + votecount + ", skipID='" + skipID + '\'' + ", alias='" + alias + '\'' + ", skipType='"
                + skipType + '\'' + ", cid='" + cid + '\'' + ", hasAD=" + hasAD + ", source='" + source + '\'' + ", " +
                "ename='" + ename + '\'' + ", imgsrc='" + imgsrc + '\'' + ", tname='" + tname + '\'' + ", ptime='" +
                ptime + '\'' + '}';
    }

    @Override
    public int itemType() {
        if (!TextUtils.isEmpty(digest)){
            return TYPE_NORMAL;
        }
        return TYPE_PHOTO;
    }
}

