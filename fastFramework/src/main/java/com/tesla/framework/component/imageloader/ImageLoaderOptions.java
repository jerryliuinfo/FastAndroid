package com.tesla.framework.component.imageloader;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.view.View;
import com.tesla.framework.common.util.ObjPool;

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

    private boolean crossFade;


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


    private static final ObjPool<Builder, String> BUILDER_POOL = new ObjPool<Builder, String>() {
        @Override
        protected Builder newInstance(String componentName) {
            return new Builder();
        }
    };


    /**
     * 创建CC对象的Builder<br>
     * <b>此对象会被CC框架复用，请勿在程序中保存</b>
     * @param componentName 要调用的组件名称
     * @return 创建CC对象的Builder
     */
    public static Builder obtainBuilder(String componentName) {
        //从对象池中取出一个对象
        return BUILDER_POOL.get(componentName);
    }


    public final static  class Builder implements ObjPool.Initable,ObjPool.Resetable{
        private ImageLoaderOptions mOptions;

        private Builder() {
        }

        public Builder container(View view){
            mOptions.viewContainer = view;
            return this;
        }

        public Builder url(String url){
            mOptions.url = url;
            return this;
        }

        public Builder imageView(View view){
            mOptions.viewContainer = view;
            return this;
        }
        public Builder context(Context context){
            mOptions.context = context;
            return this;
        }

        public Builder placeholder(@DrawableRes int holderDrawable){
            mOptions.holderId = holderDrawable;
            return this;
        }

        public Builder placeholder(Drawable holderDrawable){
            mOptions.holderDrawable = holderDrawable;
            return this;
        }

        public Builder error(@DrawableRes int errorDrawable){
            mOptions.errorId = errorDrawable;
            return this;
        }

        public Builder error(Drawable errorDrawable){
            mOptions.errorDrawable = errorDrawable;
            return this;
        }

        public Builder emptyUrl(@DrawableRes int emptyUrlDrawable){
            mOptions.emptyUrlId = emptyUrlDrawable;
            return this;
        }
        public Builder emptyUrl(Drawable emptyUrlDrawable){
            mOptions.emptyUrlDrawable = emptyUrlDrawable;
            return this;
        }

        public Builder skipMemoryCache(boolean skip){
            mOptions.skipMemoryCache = skip;
            return this;
        }


        public Builder blurImage(boolean blurImage){
            mOptions.blurImage = blurImage;
            return this;
        }

        public Builder override(int width,int height){
            mOptions.imageSize = new ImageSize(width,height);
            return this;
        }
        public Builder asGif(boolean asGif){
            mOptions.asGif = asGif;
            return this;
        }

        public Builder diskCacheStrategy(DiskCacheStrategy mDiskCacheStrategy){
            mOptions.mDiskCacheStrategy = mDiskCacheStrategy;
            return this;
        }


        public Builder type(int type) {
            mOptions.type = type;
            return this;
        }
        public Builder wifiStrategy(int wifiStrategy) {
            mOptions.wifiStrategy = wifiStrategy;
            return this;
        }

        public Builder corssFade(boolean crossFade) {
            mOptions.crossFade = crossFade;
            return this;
        }




        public ImageLoaderOptions build(){
            ImageLoaderOptions options = this.mOptions;
            //将对象放到对象池中
            BUILDER_POOL.put(this);
            return options;
        }

        @Override
        public void reset() {
            this.mOptions = null;
        }

        @Override
        public void init(Object o) {
            this.mOptions = new ImageLoaderOptions();
        }
    }

    //对应重写图片size
    public final static class ImageSize{
        private int width;
        private int height;

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
