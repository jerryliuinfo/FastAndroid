package com.apache.fastandroid.demo.temp.bean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import androidx.annotation.NonNull;

/**
 * Created by Jerry on 2022/1/15.
 */
public class ItemArrayLis<T extends Item> extends ArrayList<T> {
    @Override
    public boolean add(T t) {
        if (!t.isValid()){
            return false;
        }
        return super.add(t);
    }

    @Override
    public boolean addAll(@NonNull Collection<? extends T> c) {
        removeInvalidItem(c);
        return super.addAll(c);
    }

    private void removeInvalidItem(Collection<? extends T> c){
        Iterator<? extends T> iterator = c.iterator();
        while (iterator.hasNext()){
            T next = iterator.next();
            if (!next.isValid()){
                iterator.remove();
            }
        }
    }
}
