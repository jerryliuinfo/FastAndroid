package com.tesla.framework.support.skin;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Jerry on 2020-04-27.
 */
public class SkinManager {

    private SkinManager(){}
    private static class SingletonHolder{
        private SingletonHolder(){

        }
        private final static SkinManager INSTANCE = new SkinManager();
    }
    public static SkinManager getInstance() {

        return SingletonHolder.INSTANCE;
    }

    private String skinPkgName;

    private Resources skinResources;

    private Context context;;

    public void setContext(Context context) {
        this.context = context;
    }


    private void loadSkinApk(String apkPath){
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageArchiveInfo = packageManager.getPackageArchiveInfo(apkPath,
                PackageManager.GET_ACTIVITIES);

        skinPkgName = packageArchiveInfo.packageName;


        AssetManager assetManager = null;
        try {
            assetManager = AssetManager.class.newInstance();
            Method addAssetPath = assetManager.getClass().getDeclaredMethod("addAssetPath",
                    String.class);
            addAssetPath.invoke(assetManager,apkPath);
            skinResources = new Resources(assetManager,context.getResources().getDisplayMetrics(),context.getResources().getConfiguration());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


    }

    /**
     * 根据插过来的id 取匹配皮肤插件Apk资源对象
     * 如果名字和类型也一样的就返回
     * @param id：宿主中的资源id
     * @return
     */
    public int getColor(int id){
        if (skinResources == null){
            return id;
        }
        //获取到当前app资源属性值的名字 例如：colorPrimary
        String resourceEntryName = context.getResources().getResourceEntryName(id);

        //获取到当前app资源属性类型，例如： color
        String resourceTypeName = context.getResources().getResourceTypeName(id);

        //根据属性类型和名字取换肤apk中查找资源id
        int identifier = skinResources.getIdentifier(resourceEntryName, resourceTypeName, skinPkgName);
        if (identifier == 0){
            return id;
        }
        //从皮肤apk获取资源
        return skinResources.getColor(identifier);

    }

    public Drawable getDrawable(int id){
        if (skinResources == null){
            return ContextCompat.getDrawable(context,id);
        }
        //获取到当前app资源属性值的名字 例如：colorPrimary
        String resourceEntryName = context.getResources().getResourceEntryName(id);

        //获取到当前app资源属性类型，例如： color
        String resourceTypeName = context.getResources().getResourceTypeName(id);

        //根据属性类型和名字取换肤apk中查找资源id
        int identifier = skinResources.getIdentifier(resourceEntryName, resourceTypeName, skinPkgName);
        if (identifier == 0){
            return ContextCompat.getDrawable(context,id);
        }
        //从皮肤apk获取资源
        return skinResources.getDrawable(identifier);

    }



}
