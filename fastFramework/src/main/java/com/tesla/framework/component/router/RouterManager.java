package com.tesla.framework.component.router;

import android.content.Context;
import android.content.pm.PackageManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import dalvik.system.DexFile;

/**
 * Created by Jerry on 2020-05-01.
 */
public class RouterManager {

    private Context context;

    private static volatile RouterManager instance = null;
    private RouterManager(){}
    public static RouterManager getInstance() {
        if (instance == null) {
            synchronized (RouterManager.class) {
                if (instance == null){
                    instance = new RouterManager();
                }
            }
        }
        return instance;
    }

    public void init(Context context){
        this.context = context;
        //去执行生成的文件的方法
        List<String> classNameList = getClassNameList("com.apache.aroute.apt");
        if (!classNameList.isEmpty()){
            for (String className : classNameList) {
                try {
                    Class<?> aClass = Class.forName(className);
                    //判断这个类是否是IRouter的子类
                    if (IRouter.class.isAssignableFrom(aClass)){
                        IRouter iRouter = (IRouter) aClass.newInstance();
                        iRouter.putActivity("hello");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }




    private List<String> getClassNameList(String packageName){
        List<String> classList = new ArrayList<>();


        try {
            String path = context.getPackageManager().getApplicationInfo(context.getPackageName(),0).sourceDir;
            DexFile dexFile = new DexFile(path);
            //项目中所有类的集合
            Enumeration<String> entries = dexFile.entries();
            while (entries.hasMoreElements()){
                String name = entries.nextElement();
                //判断类的包名是否符合
                if (name.contains(packageName)){
                    classList.add(name);
                }
            }

        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


        return classList;
    }
}
