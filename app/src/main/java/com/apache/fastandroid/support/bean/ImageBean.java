package com.apache.fastandroid.support.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jerryliu on 2017/4/11.
 */

public class ImageBean implements Serializable {

    public String id;
    public String desc;
    public List<String> tags;
    public String fromPageTitle;
    public String column;
    public String date;
    public String downloadUrl;
    public String imageUrl;
    public int imageWidth;
    public int imageHeight;
    public String thumbnailUrl;
    public int thumbnailWidth;
    public int thumbnailHeight;
    public String thumbnailLargeUrl;
    public int thumbnailLargeWidth;
    public int thumbnailLargeHeight;
    public String thumbnailLargeTnUrl;
    public int thumbnailLargeTnWidth;
    public int thumbnailLargeTnHeight;
    public String siteName;
    public String siteLogo;
    public String siteUrl;
    public String fromUrl;
    public String objUrl;
    public String shareUrl;
    public String albumId;
    public int isAlbum;
    public String albumName;
    public int albumNum;
    public String title;


    @Override
    public String toString() {
        return "ImageBean{" + "id='" + id + '\'' + ", desc='" + desc + '\'' + ", title='" + title + '\'' + '}';
    }
}
