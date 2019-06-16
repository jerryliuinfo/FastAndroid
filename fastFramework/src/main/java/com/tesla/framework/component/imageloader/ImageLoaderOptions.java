package com.tesla.framework.component.imageloader;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.view.View;


/**
 * Created by Administrator on 2017/3/20 0020.
 */
public class ImageLoaderOptions {

    public static final int PIC_LARGE = 0;
    public static final int PIC_MEDIUM = 1;
    public static final int PIC_SMALL = 2;

    public static final int LOAD_STRATEGY_NORMAL = 0;
    public static final int LOAD_STRATEGY_ONLY_WIFI = 1;

    private Context context;
    private View viewContainer;  // 图片容器
    private String url;  // 图片地址
    private int holderId = -1;  // 占位图
    private int errorId = -1;  //错误的图片
    private Drawable holderDrawable;  // 设置展位图
    private Drawable errorDrawable;  //加载错误的图片
    private int emptyUrlId = -1;
    private Drawable emptyUrlDrawable;
    private Integer resource;  // 图片地址
    private ImageSize imageSize;  //设置图片的大小
    private boolean asGif = false;   //是否作为gif展示
    private boolean isCrossFade = false; //是否渐变平滑的显示图片
    private  boolean skipMemoryCache = false; //是否禁用内存缓存
    private  boolean blurImage = false; //是否使用高斯模糊
    private  DiskCacheStrategy mDiskCacheStrategy;
    private int type;  //类型 (大图，中图，小图)
    private int wifiStrategy;//加载策略，是否在wifi下才加载


    ImageLoaderOptions (Builder builder ){
        this.context = builder.context;
        this.asGif = builder.asGif;

        this.errorId = builder.errorId;
        this.holderDrawable = builder.holderDrawable;

        this.holderId = builder.holderId;
        this.errorDrawable = builder.errorDrawable;

        this.emptyUrlId = builder.emptyUrlId;
        this.emptyUrlDrawable = builder.emptyUrlDrawable;

        this.imageSize = builder.mImageSize;
        this.isCrossFade = builder.isCrossFade;
        this.skipMemoryCache = builder.skipMemoryCache;
        this.mDiskCacheStrategy = builder.mDiskCacheStrategy;
        this.url = builder.url;
        this.resource = builder.resource;
        this.viewContainer = builder.mViewContainer;
        this.blurImage = builder.blurImage;
        this.type = builder.type;
        this.wifiStrategy = builder.wifiStrategy;
    }

    public Integer getResource() {
        return resource;
    }

    public boolean isBlurImage() {
        return blurImage;
    }

    public View getViewContainer() {
        return viewContainer;
    }

    public String getUrl() {
        return url;
    }

    public int getHolderId() {
        return holderId;
    }

    public int getErrorId() {
        return errorId;
    }

    public Drawable getHolderDrawable() {
        return holderDrawable;
    }

    public Drawable getErrorDrawable() {
        return errorDrawable;
    }

    public int getEmptyUrlId() {
        return emptyUrlId;
    }

    public Drawable getEmptyUrlDrawable() {
        return emptyUrlDrawable;
    }

    public ImageSize getImageSize() {
        return imageSize;
    }




    public boolean isAsGif() {
        return asGif;
    }

    public boolean isCrossFade() {
        return isCrossFade;
    }

    public boolean isSkipMemoryCache() {
        return skipMemoryCache;
    }

    public DiskCacheStrategy getDiskCacheStrategy() {
        return mDiskCacheStrategy;
    }

    public Context getContext() {
        return context;
    }


    public void setContext(Context context) {
        this.context = context;
    }

    public void setViewContainer(View viewContainer) {
        this.viewContainer = viewContainer;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public final static  class Builder{
        private Context context;

        private View mViewContainer;  // 图片容器
        private String url;  // 图片地址

        private int holderId = -1;  // 设置展位图
        private Drawable holderDrawable;  // 设置展位图

        private int errorId = -1;  //加载错误的图片
        private Drawable errorDrawable;  //加载错误的图片

        private int emptyUrlId = 0;        //url为空时加载资源
        private Drawable emptyUrlDrawable; //url为空时加载资源

        private Integer resource;  // 图片地址
        private ImageSize mImageSize;  //设置图片的大小

        private boolean asGif = false;   //是否作为gif展示
        private boolean isCrossFade = false; //是否渐变平滑的显示图片
        private  boolean skipMemoryCache = true; //是否跳过内存缓存

        private  boolean blurImage = false; //是否使用高斯模糊
        private int type;  //类型 (大图，中图，小图)
        private int wifiStrategy;//加载策略，是否在wifi下才加载

        private  DiskCacheStrategy mDiskCacheStrategy = DiskCacheStrategy.DEFAULT; //磁盘缓存策略
        //private BaseTarget target = null; //target

        public Builder(@NonNull View v, @NonNull String url,Context context){
            this.url=url;
            this.mViewContainer=v;
            this.context = context;
        }
        public Builder(@NonNull View v, @NonNull Integer resource){
            this.resource=resource;
            this.mViewContainer=v;
        }

        public Builder url(String url){
            this.url = url;
            return this;
        }

        public Builder imageView(View view){
            this.mViewContainer = view;
            return this;
        }
        public Builder context(Context context){
            this.context = context;
            return this;
        }

        public Builder placeholder(@DrawableRes int holderDrawable){
            this.holderId =holderDrawable;
            return this;
        }

        public Builder placeholder(Drawable holderDrawable){
            this.holderDrawable = holderDrawable;
            return this;
        }

        public Builder error(@DrawableRes int errorDrawable){
            this.errorId = errorDrawable;
            return this;
        }

        public Builder error(Drawable errorDrawable){
            this.errorDrawable = errorDrawable;
            return this;
        }

        public Builder emptyUrl(@DrawableRes int emptyUrlDrawable){
            this.emptyUrlId = emptyUrlDrawable;
            return this;
        }
        public Builder emptyUrl(Drawable emptyUrlDrawable){
            this.emptyUrlDrawable = emptyUrlDrawable;
            return this;
        }

        public Builder skipMemoryCache(boolean skip){
            this.skipMemoryCache = skip;
            return this;
        }

        public Builder isCrossFade(boolean isCrossFade){
            this.isCrossFade=isCrossFade;
            return this;
        }
        public Builder blurImage(boolean blurImage){
            this.blurImage=blurImage;
            return this;
        }

        public Builder override(int width,int height){
            this.mImageSize=new ImageSize(width,height);
            return this;
        }
        public Builder asGif(boolean asGif){
            this.asGif=asGif;
            return this;
        }

        public Builder diskCacheStrategy(DiskCacheStrategy mDiskCacheStrategy){
            this.mDiskCacheStrategy=mDiskCacheStrategy;
            return this;
        }


        public Builder type(int type) {
            this.type = type;
            return this;
        }
        public Builder wifiStrategy(int wifiStrategy) {
            this.wifiStrategy = wifiStrategy;
            return this;
        }


        public ImageLoaderOptions build(){
            return  new ImageLoaderOptions(this);
        }


    }

    //对应重写图片size
    public final static class ImageSize{
        private int width=0;
        private int height=0;
        public ImageSize(int width, int heigh){
            this.width=width;
            this.height=heigh;
        }

        public int getHeight() {
            return height;
        }

        public int getWidth() {
            return width;
        }
    }
    //对应磁盘缓存策略
    public enum DiskCacheStrategy{
        All,//表示既缓存原始图片，也缓存转换过后的图片
        NONE,//表示不缓存任何内容
        SOURCE,//表示只缓存原始图片
        RESULT,//表示只缓存转换过后的图片（默认选项）
        DEFAULT
    }




}
