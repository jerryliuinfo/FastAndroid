package com.apache.fastandroid.tink;

import android.content.Context;

import com.apache.fastandroid.util.MainLogUtil;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;

/**
 * Created by Jerry on 2020-05-03.
 */
public class FixManager {
    private static volatile FixManager instance = null;
    private FixManager(){}
    public static FixManager getInstance() {
        if (instance == null) {
            synchronized (FixManager.class) {
                if (instance == null){
                    instance = new FixManager();
                }
            }
        }
        return instance;
    }

    private static Set<File> loadedDex = new HashSet<>();
    static {
        loadedDex.clear();
    }

    public static void loadDex(Context context){
        if (context == null){
            return;
        }
        //获取当前应用的上下文的私有路径，也就是dex目录 /data/data/com.apache.fastandroid/app_odex/
        File filesDir = context.getDir("odex", Context.MODE_PRIVATE);
        MainLogUtil.d("loadDex filesDir path : "+filesDir.getAbsolutePath());
        File[] files = filesDir.listFiles();
        for (File file : files) {
            MainLogUtil.d("loadDex file path : "+file.getAbsolutePath() +", name:"+file.getName());
            if (file.getName().contains("classes") || file.getName().endsWith(".dex")){
                loadedDex.add(file);
            }
        }
        //创建一个目录，用来存放解压的文件 /data/data/com.apache.fastandroid/app_odex/opt_dex
        String optmizeDir = filesDir.getAbsolutePath() + File.separator + "opt_dex";
        MainLogUtil.d("loadDex optmizeDir +"+optmizeDir);
        File optimizeDir = new File(optmizeDir);
        if (!optimizeDir.exists()){
            optimizeDir.mkdirs();
        }

        MainLogUtil.d("loadDex loadedDex : "+loadedDex);

        for (File dex : loadedDex) {
            DexClassLoader myDexClassLoader = new DexClassLoader(dex.getAbsolutePath(),optimizeDir.getAbsolutePath()
            ,null,context.getClassLoader());
            //实现一个类加载器对象
            PathClassLoader pathClassLoader = (PathClassLoader) context.getClassLoader();
            try {
                /**************获取系统的未修复的dexElements开始****************/

                //获取到DexClassLoader类加载器的父类
                Class<?> baseDexCkazzLoader = Class.forName("dalvik.system.BaseDexClassLoader");
                Field pathListField = baseDexCkazzLoader.getDeclaredField("pathList");
                //设置成员变量允许被访问，用于反射
                pathListField.setAccessible(true);
                //执行pathList的get方法
                Object pathListObject = pathListField.get(pathClassLoader);
                //获取到PathList类对象
                Class<?> systemPathListClass = pathListObject.getClass();
                //通过这类对象获取到它里面的一个叫做dexElement的成员表变量
                Field dexElements = systemPathListClass.getDeclaredField("dexElements");
                dexElements.setAccessible(true);
                //获取到dexElements成员变量的值
                Object systemElements = dexElements.get(pathListObject);
                /**************获取系统的未修复的dexElements结束****************/



                /**************获取已修复的dexElements开始****************/
                //通过反射获取类加载器的父类
                Class<?> myDexClazzLoader = Class.forName("dalvik.system.BaseDexClassLoader");
                //从父类的对象中去获取pathList的成员变量
                Field myPathListField = baseDexCkazzLoader.getDeclaredField("pathList");
                myPathListField.setAccessible(true);
                Object myPathListObject = myPathListField.get(myDexClassLoader);
                Field myDexElements1Field = myPathListObject.getClass().getDeclaredField("dexElements");
                myDexElements1Field.setAccessible(true);
                Object myElements= myDexElements1Field.get(myPathListObject);
                /**************获取已修复的dexElements结束****************/

                /**************dex文件的融合****************/

                //获取到系统的（未修复）systemElements的类对象
                Class<?> componentType = systemElements.getClass().getComponentType();
                //得到系统的的dexElements的长度
                int systemLength = Array.getLength(systemElements);
                //获取到修复的dexElements长度
                int myLength = Array.getLength(myElements);
                //创建一个新的长度
                int newLength = systemLength + myLength;
                Object newElementArray = Array.newInstance(componentType, newLength);
                for (int i = 0; i < newLength; i++) {
                    if (i < myLength){
                        //将补丁的myElements的第i位放到newElementArray 的第i位置
                        Array.set(newElementArray,i,Array.get(myElements,i));
                    }else {
                        Array.set(newElementArray,i,Array.get(systemElements,i - myLength));
                    }

                }
                //将系统的数组和我们定义的数组融合之后，再放入到系统的数组中
                Field dexElementsField = pathListObject.getClass().getDeclaredField("dexElements");
                dexElementsField.setAccessible(true);
                dexElementsField.set(pathListObject,newElementArray);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }







}
