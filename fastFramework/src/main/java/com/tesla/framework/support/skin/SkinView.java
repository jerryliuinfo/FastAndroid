package com.tesla.framework.support.skin;

import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import static com.tesla.framework.support.skin.SkinFactory.PROPERTY_TYPE_BACKGROUND;
import static com.tesla.framework.support.skin.SkinFactory.PROPERTY_TYPE_TEXTCOLOR;

/**
 * Created by Jerry on 2020-04-28.
 */
public class SkinView {
    View view;
    List<SkintItem> items;

    public SkinView(View view, List<SkintItem> items) {
        this.view = view;
        this.items = items;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public List<SkintItem> getItems() {
        return items;
    }

    public void setItems(List<SkintItem> items) {
        this.items = items;
    }


    public void apply(){
        if (items.size() > 0){
            for (SkintItem item : items) {
                int resId = item.getResId();
                //background 可以设置color，也可以设置drawable
                if (item.getName().equals(PROPERTY_TYPE_BACKGROUND)){
                    //color
                    if (item.getTypeName().equals("color")){
                        view.setBackgroundColor(SkinManager.getInstance().getColor(resId));
                    }

                    //drawable
                    else if (item.getTypeName().equals("drawable") || item.getTypeName().equals("mipmap")){
                        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN){
                            view.setBackground(SkinManager.getInstance().getDrawable(resId));
                        }else{
                            view.setBackgroundDrawable(SkinManager.getInstance().getDrawable(resId));
                        }
                    }
                }

                else if (item.getName().equals(PROPERTY_TYPE_TEXTCOLOR)){
                    if (view instanceof TextView){
                        TextView textView = (TextView) view;
                        textView.setTextColor(SkinManager.getInstance().getColor(resId));
                    }else if (view instanceof Button){
                        Button button = (Button) view;
                        button.setTextColor(SkinManager.getInstance().getColor(resId));
                    }
                }
            }
        }
    }
}
