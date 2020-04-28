package com.tesla.framework.support.skin;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.tesla.framework.common.util.FrameworkLogUtil;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jerry on 2020-04-27.
 */
public class SkinFactory implements LayoutInflater.Factory2 {
    //装在收集起来需要换肤控件的容器
    private List<SkinView> viewList = new ArrayList<>();

    private static final String[] prefixList = {
            "android.widget.",
            "android.view.",
            "android.webkit."
    };

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        FrameworkLogUtil.d("SkinFactory onCreateView 4 params");

        //监听xml的生成过程，自己去创建这些空间
        View view = null;
        //自定义控件
        if (name.contains(".")){
            view = onCreateView(name,context,attrs);
        }else{
            for (String s : prefixList) {
                view = onCreateView(s+name,context,attrs);
                if (view != null){
                    break;
                }
            }
        }

        //收集所有需要换肤的控件
        if (view != null){
            //如果控件已经被实例化，就去判断这个控件是否满足我们的换肤要求，然后收集起来
            parseView(view,name,attrs);

        }
        return view;
    }

    public static final String PROPERTY_TYPE_BACKGROUND = "background";
    public static final String PROPERTY_TYPE_SRC = "src";
    public static final String PROPERTY_TYPE_TEXTCOLOR = "textColor";
    public static final String PROPERTY_TYPE_COLOR = "color";




    private void parseView(View view, String name, AttributeSet attrs) {
        List<SkintItem> itemList = new ArrayList<>();
        for (int i = 0; i < attrs.getAttributeCount(); i++) {
            //属性的名字
            String attributeName = attrs.getAttributeName(i);
            //属性的资源ID @1235443
            String attributeValue = attrs.getAttributeValue(i);
            if (attributeName.contains(PROPERTY_TYPE_BACKGROUND) || attributeName.contains(PROPERTY_TYPE_TEXTCOLOR) ||
                    attributeName.contains(PROPERTY_TYPE_COLOR) || attributeName.contains(PROPERTY_TYPE_SRC)){
                //获取资源id 去掉第0位的@
                int resId = Integer.parseInt(attributeName.substring(0));

                //获取属性的值的类型 colorPrimary
                String resourceTypeName = view.getResources().getResourceTypeName(resId);
                //获取属性的值的名字
                String resourceEntryName = view.getResources().getResourceEntryName(resId);
                SkintItem item = new SkintItem(attributeName,resId,resourceEntryName,resourceTypeName);
                itemList.add(item);
            }
        }
        if (itemList.size() > 0){
            SkinView skinView = new SkinView(view,itemList);
            viewList.add(skinView);
            skinView.apply();
        }
    }

    /**
     * 将控件进行实例化的方法
     * @param name
     * @param context
     * @param attrs
     * @return
     */
    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        FrameworkLogUtil.d("SkinFactory onCreateView 3 params");

        View view = null;

        Class<?> aClass = null;
        try {
            aClass = context.getClassLoader().loadClass(name);
            Constructor constructor = aClass.getConstructor(new Class[]{Context.class,AttributeSet.class});
            view = (View) constructor.newInstance(context,attrs);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return view;
    }

    public void apply(){
        if (viewList.size() > 0){
            for (SkinView skinView : viewList) {
                skinView.apply();
            }
        }
    }



}
